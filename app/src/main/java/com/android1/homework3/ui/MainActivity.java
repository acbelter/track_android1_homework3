package com.android1.homework3.ui;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
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
    private Controller mController;
    private ProgressDialog mLoadingDialog;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Logger.d("Connected to network service");
            mNetworkService = INetworkService.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Logger.d("Network service is disconnected");
            mController.processConnectionFailed();
        }
    };

    private BroadcastReceiver mNetworkServiceReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case NetworkService.ACTION_CONNECTED: {
                    Logger.d("Successful connection");
                    mNetworkServiceConnected = true;
                    break;
                }
                case NetworkService.ACTION_CONNECTION_FAILED: {
                    Logger.d("Connection failed");
                    mNetworkServiceConnected = false;
                    mController.processConnectionFailed();
                    Toast.makeText(getApplicationContext(), R.string.toast_connection_failed, Toast.LENGTH_SHORT).show();
                    break;
                }
                case NetworkService.ACTION_DATA_RECEIVED: {
                    String data = intent.getStringExtra("data");
                    Logger.d("Message received: " + data);
                    BaseMessage message = mMessageParser.parseMessage(data);
                    if (message != null) {
                        mController.processResponse(message);
                    } else {
                        Logger.d("Unknown message");
                        mNetworkServiceConnected = false;
                        mController.processConnectionFailed();
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
        mController = new Controller(this);

        mLoadingDialog = new ProgressDialog(this);
        mLoadingDialog.setTitle(null);
        mLoadingDialog.setMessage(getString(R.string.loading_data));
        mLoadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        if (savedInstanceState == null) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, new SplashFragment(), SplashFragment.tag());
            ft.commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    public void sendMessage(BaseMessage message) {
        if (mNetworkServiceConnected) {
            try {
                String messageData = mMessageBuilder.buildMessage(message);
                if (messageData != null) {
                    Logger.d("Send message: " + messageData);
                    mNetworkService.sendMessage(messageData);
                } else {
                    Logger.d("Attempt to send unknown message for class " + message.getClass().getSimpleName());
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void showLoadingDialog() {
        mLoadingDialog.show();
    }

    public void hideLoadingDialog() {
        mLoadingDialog.dismiss();
    }

    public void connectToNetworkService() {
        disconnect();
        Intent intent = new Intent(MainActivity.this, NetworkService.class);
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
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
        connectToNetworkService();
    }

    @Override
    protected void onStop() {
        disconnect();
        super.onStop();
    }
}
