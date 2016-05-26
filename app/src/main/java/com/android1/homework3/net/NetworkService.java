package com.android1.homework3.net;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import java.io.IOException;

import android1.homework3.INetworkService;
import android1.homework3.INetworkServiceCallback;

public class NetworkService extends Service implements SocketListener {
    private static final String HOST = "188.166.49.215";
    private static final int PORT = 7777;

    private NetworkServiceBinder mNetworkServiceBinder;
    private SocketConnectionHandler mSocketConnectionHandler;

    private INetworkServiceCallback mCallback;

    @Override
    public void onCreate() {
        super.onCreate();
        mNetworkServiceBinder = new NetworkServiceBinder();
        mSocketConnectionHandler = new SocketConnectionHandler(HOST, PORT);
        mSocketConnectionHandler.addListener(this);

        Thread thread = new Thread(mSocketConnectionHandler);
        thread.start();
    }

    private void notifyConnected() {
        if (mCallback != null) {
            try {
                mCallback.onConnected();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private void notifyConnectionFailed() {
        if (mCallback != null) {
            try {
                mCallback.onConnectionFailed();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
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
    public void onConnected() {
        notifyConnected();
    }

    @Override
    public void onConnectionFailed() {
        notifyConnectionFailed();
    }

    @Override
    public void onDataReceived(String data) {
        if (mCallback != null) {
            try {
                mCallback.onDataReceived(data);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
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

        @Override
        public void setCallback(INetworkServiceCallback callback) throws RemoteException {
            mCallback = callback;
        }
    }
}