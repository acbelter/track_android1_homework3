package com.android1.homework3.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android1.homework3.R;

public class ReconnectFragment extends UiFragment {
    private Button mReconnectButton;
    private Controller mController;

    public static ReconnectFragment newInstance(Controller controller) {
        ReconnectFragment fragment = new ReconnectFragment();
        fragment.mController = controller;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reconnect, container, false);
        mReconnectButton = (Button) view.findViewById(R.id.btn_reconnect);
        mReconnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUiEnabled(false);
                mController.connectToNetworkService();
            }
        });

        return view;
    }

    @Override
    public void setUiEnabled(boolean enabled) {
        mReconnectButton.setEnabled(enabled);
    }

    public static String tag() {
        return ReconnectFragment.class.getSimpleName();
    }
}
