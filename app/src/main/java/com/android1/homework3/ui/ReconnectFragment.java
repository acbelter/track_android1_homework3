package com.android1.homework3.ui;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android1.homework3.R;

import java.lang.ref.WeakReference;

public class ReconnectFragment extends Fragment {
    private Button mReconnectButton;
    private WeakReference<MainActivity> mMainActivityWeakRef;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reconnect, container, false);
        mReconnectButton = (Button) view.findViewById(R.id.btn_reconnect);
        mReconnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = mMainActivityWeakRef.get();
                if (mainActivity != null) {
                    mainActivity.connectToNetworkService();
                }
            }
        });

        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mMainActivityWeakRef = new WeakReference<>((MainActivity) activity);
        } catch (ClassCastException e) {
            throw new ClassCastException("This fragment must be attached to " +
                    MainActivity.class.getSimpleName());
        }
    }

    public static String tag() {
        return ReconnectFragment.class.getSimpleName();
    }
}
