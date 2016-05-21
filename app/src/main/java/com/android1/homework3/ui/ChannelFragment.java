package com.android1.homework3.ui;

import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android1.homework3.R;

public class ChannelFragment extends ListFragment implements UiFragment {
    private Controller mController;

    public static ChannelFragment newInstance(Controller controller) {
        ChannelFragment fragment = new ChannelFragment();
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
        return inflater.inflate(R.layout.fragment_channel, container, false);
    }

    @Override
    public void setUiEnabled(boolean enabled) {
        getListView().setEnabled(enabled);
    }

    public static String tag() {
        return ChannelFragment.class.getSimpleName();
    }
}
