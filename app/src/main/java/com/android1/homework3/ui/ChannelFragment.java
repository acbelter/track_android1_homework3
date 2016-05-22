package com.android1.homework3.ui;

import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.android1.homework3.R;
import com.android1.homework3.msg.adapter.ChannelMessageAdapter;
import com.android1.homework3.msg.response.ChannelMessage;
import com.android1.homework3.msg.response.LastMessage;
import com.android1.homework3.msg.response.User;

import java.util.ArrayList;
import java.util.List;

public class ChannelFragment extends ListFragment {
    private Controller mController;
    private String mUserId;
    private String mChannelId;

    private List<User> mChannelUsers;
    private List<ChannelMessage> mChannelMessages;

    private ChannelMessageAdapter mAdapter;

    private Button mChannelUsersButton;
    private EditText mMessage;
    private ImageView mSendButton;

    public static ChannelFragment newInstance(Controller controller,
                                              String userId,
                                              String channelId,
                                              List<User> users,
                                              List<LastMessage> lastMessages) {
        ChannelFragment fragment = new ChannelFragment();
        fragment.mUserId = userId;
        fragment.mChannelId = channelId;
        fragment.mChannelUsers = users;
        fragment.mChannelMessages = new ArrayList<>();
        for (LastMessage lastMessage : lastMessages) {
            fragment.mChannelMessages.add(new ChannelMessage(lastMessage));
        }
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
        View view = inflater.inflate(R.layout.fragment_channel, container, false);
        mChannelUsersButton = (Button) view.findViewById(R.id.btn_channel_users);
        mMessage = (EditText) view.findViewById(R.id.message);
        mSendButton = (ImageView) view.findViewById(R.id.btn_send);

        mChannelUsersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mController.showUserListFragment(mChannelUsers, true);
            }
        });

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter = new ChannelMessageAdapter(getActivity(), mUserId, mChannelMessages);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

    }

    public static String tag() {
        return ChannelFragment.class.getSimpleName();
    }
}
