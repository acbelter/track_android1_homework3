package com.android1.homework3.net;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import java.io.IOException;

import android1.homework3.INetworkService;

public class NetworkService extends Service implements SocketListener {
    public static final String ACTION_SOCKET_CONNECTED = "com.android1.homework3.ACTION_SOCKED_CONNECTED";
    public static final String ACTION_DATA_RECEIVED = "com.android1.homework3.ACTION_DATA_RECEIVED";
    private static final String HOST = "188.166.49.215";
    private static final int PORT = 7777;

    private NetworkServiceBinder mNetworkServiceBinder = new NetworkServiceBinder();
    private SocketConnectionHandler mSocketConnectionHandler;
    private LocalBroadcastManager mLocalBroadcastManager;

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
            mSocketConnectionHandler = new SocketConnectionHandler(HOST, PORT);
            mSocketConnectionHandler.addListener(this);

            Thread thread = new Thread(mSocketConnectionHandler);
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        if (mSocketConnectionHandler != null) {
            mSocketConnectionHandler.stop();
        }
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mNetworkServiceBinder;
    }

    @Override
    public void onSocketConnected() {
        Intent intent = new Intent(ACTION_SOCKET_CONNECTED);
        mLocalBroadcastManager.sendBroadcast(intent);
    }

    @Override
    public void onDataReceived(String data) {
        Intent intent = new Intent(ACTION_DATA_RECEIVED);
        intent.putExtra("data", data);
        mLocalBroadcastManager.sendBroadcast(intent);
    }

    private class NetworkServiceBinder extends INetworkService.Stub {
        @Override
        public void sendMessage(String message) throws RemoteException {
            try {
                mSocketConnectionHandler.send(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}