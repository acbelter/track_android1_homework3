package com.android1.homework3.ui;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;

import com.android1.homework3.Logger;
import com.android1.homework3.R;
import com.android1.homework3.net.NetworkService;

import android1.homework3.INetworkService;

public class MainActivity extends AppCompatActivity {
    private INetworkService mNetworkService;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mNetworkService = INetworkService.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Logger.d("Network service is disconnected");
        }
    };

    private BroadcastReceiver mNetworkServiceReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case NetworkService.ACTION_CONNECTED: {
                    Logger.d("Connected to socket");
                    try {
                        mNetworkService.sendMessage("Message");
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case NetworkService.ACTION_CONNECTION_FAILED: {
                    Logger.d("Connection failed");
                    break;
                }
                case NetworkService.ACTION_DATA_RECEIVED: {
                    String data = intent.getStringExtra("data");
                    Logger.d("Message received: " + data);
                    break;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void disconnect() {
        if (mNetworkService != null) {
            unbindService(mServiceConnection);
            mNetworkService = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(this);
        manager.registerReceiver(mNetworkServiceReceiver,
                new IntentFilter(NetworkService.ACTION_CONNECTED));
        manager.registerReceiver(mNetworkServiceReceiver,
                new IntentFilter(NetworkService.ACTION_CONNECTION_FAILED));
        manager.registerReceiver(mNetworkServiceReceiver,
                new IntentFilter(NetworkService.ACTION_DATA_RECEIVED));
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mNetworkServiceReceiver);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mNetworkService == null) {
            Intent intent = new Intent(MainActivity.this, NetworkService.class);
            bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
        }
    }

    @Override
    protected void onStop() {
        disconnect();
        super.onStop();
    }
}
