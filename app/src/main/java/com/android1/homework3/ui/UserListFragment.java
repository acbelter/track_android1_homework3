package com.android1.homework3.ui;

import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android1.homework3.R;
import com.android1.homework3.msg.adapter.UserListAdapter;
import com.android1.homework3.msg.response.User;

import java.util.List;

public class UserListFragment extends ListFragment {
    private Controller mController;
    private UserListAdapter mAdapter;
    private List<User> mUsers;

    private String mUserId;
    private String mSessionId;

    public static UserListFragment newInstance(Controller controller,
                                               String userId,
                                               String sessionId,
                                               List<User> users) {
        UserListFragment fragment = new UserListFragment();
        fragment.mController = controller;
        fragment.mUsers = users;
        fragment.mUserId = userId;
        fragment.mSessionId = sessionId;
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
        return inflater.inflate(R.layout.fragment_user_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter = new UserListAdapter(getActivity(), mUsers);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        User user = mAdapter.getItem(position);
        mController.getUserInfo(user.uid, mUserId, mSessionId);
    }

    public static String tag() {
        return UserListFragment.class.getSimpleName();
    }
}
