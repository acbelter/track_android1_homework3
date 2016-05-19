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
import android.widget.Toast;

import com.android1.homework3.Logger;
import com.android1.homework3.R;
import com.android1.homework3.msg.BaseMessage;
import com.android1.homework3.msg.request.JSONMessageBuilder;
import com.android1.homework3.msg.request.MessageBuilder;
import com.android1.homework3.msg.response.JSONMessageParser;
import com.android1.homework3.msg.response.MessageParser;
import com.android1.homework3.net.NetworkService;

import android1.homework3.INetworkService;

public class MainActivity extends AppCompatActivity {
    private INetworkService mNetworkService;
    private boolean mNetworkServiceConnected;
    private MessageParser mMessageParser;
    private MessageBuilder mMessageBuilder;
    private FragmentRouter mFragmentRouter;

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
                    mNetworkServiceConnected = true;
                    Logger.d("Connected to socket");
                    try {
                        mNetworkService.sendMessage("Message");
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case NetworkService.ACTION_CONNECTION_FAILED: {
                    mNetworkServiceConnected = false;
                    Logger.d("Connection failed");
                    break;
                }
                case NetworkService.ACTION_DATA_RECEIVED: {
                    String data = intent.getStringExtra("data");
                    Logger.d("Message received: " + data);
                    BaseMessage message = mMessageParser.parseMessage(data);
                    if (message != null) {
                        mFragmentRouter.route(message);
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Unknown message", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMessageParser = new JSONMessageParser();
        mMessageBuilder = new JSONMessageBuilder();
        mFragmentRouter = new FragmentRouter(this);
    }

    public void sendMessage(BaseMessage data) {
        if (mNetworkServiceConnected) {
            try {
                String message = mMessageBuilder.buildMessage(data);
                if (message != null) {
                    mNetworkService.sendMessage(message);
                } else {
                    Logger.d("Attempt to send unknown message");
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
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
