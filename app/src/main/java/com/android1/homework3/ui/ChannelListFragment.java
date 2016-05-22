package com.android1.homework3.ui;

import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android1.homework3.Logger;
import com.android1.homework3.R;
import com.android1.homework3.msg.adapter.ChannelListAdapter;
import com.android1.homework3.msg.response.Channel;

import java.util.List;

public class ChannelListFragment extends ListFragment {
    private Controller mController;
    private String mUserId;
    private List<Channel> mChannels;
    private ChannelListAdapter mAdapter;
    private TextView mNoConnectionStub;

    public static ChannelListFragment newInstance(Controller controller,
                                                  String userId,
                                                  List<Channel> channels) {
        ChannelListFragment fragment = new ChannelListFragment();
        fragment.mController = controller;
        fragment.mUserId = userId;
        fragment.mChannels = channels;
        return fragment;
    }

    public void processEnterChannel(String userId, String channelId) {
        Logger.d("PROCESS ENTER CHANNEL");
        for (Channel channel : mChannels) {
            if (mUserId != null) {
                if (channelId.equals(channel.chid)) {
                    channel.online++;
                    if (mUserId.equals(userId)) {
                        channel.isEntered = true;
                    }
                }
            } else {
                if (channelId.equals(channel.chid)) {
                    channel.online++;
                    channel.isEntered = true;
                }
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    public void processLeaveChannel(String userId, String channelId) {
        Logger.d("PROCESS LEAVE CHANNEL");
        for (Channel channel : mChannels) {
            if (mUserId != null) {
                if (channelId.equals(channel.chid)) {
                    if (channel.online > 0) {
                        channel.online--;
                    }
                    if (mUserId.equals(userId)) {
                        channel.isEntered = false;
                    }
                }
            } else {
                if (channelId.equals(channel.chid)) {
                    if (channel.online > 0) {
                        channel.online--;
                    }
                    channel.isEntered = false;
                }
            }
        }
        mAdapter.notifyDataSetChanged();
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
        registerForContextMenu(getListView());
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.channel_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.leave_channel: {
                Channel channel = mAdapter.getItem(info.position);
                mController.leaveChannel(channel);
                return true;
            }
            default: {
                return super.onContextItemSelected(item);
            }
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Channel channel = mAdapter.getItem(position);
        mController.enterChannel(channel);
    }

    public static String tag() {
        return ChannelListFragment.class.getSimpleName();
    }
}
