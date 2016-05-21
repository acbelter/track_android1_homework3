package com.android1.homework3.ui;

import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.android1.homework3.R;
import com.android1.homework3.msg.adapter.ChannelListAdapter;
import com.android1.homework3.msg.response.Channel;

import java.util.List;

public class ChannelListFragment extends ListFragment implements UiFragment {
    private Controller mController;
    private List<Channel> mChannels;
    private ChannelListAdapter mAdapter;
    private TextView mNoConnectionStub;

    public static ChannelListFragment newInstance(Controller controller, List<Channel> channels) {
        ChannelListFragment fragment = new ChannelListFragment();
        fragment.mController = controller;
        fragment.mChannels = channels;
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
        View view = inflater.inflate(R.layout.fragment_channel_list, container, false);
        mNoConnectionStub = (TextView) view.findViewById(R.id.no_connection_stub);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter = new ChannelListAdapter(getActivity(), mChannels);
        setListAdapter(mAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Channel channel = mAdapter.getItem(position);
    }

    @Override
    public void setUiEnabled(boolean enabled) {
        if (!enabled) {
            getListView().setAdapter(null);
            mNoConnectionStub.setVisibility(View.VISIBLE);
        } else {
            mNoConnectionStub.setVisibility(View.INVISIBLE);
        }
    }

    public static String tag() {
        return ChannelListFragment.class.getSimpleName();
    }
}