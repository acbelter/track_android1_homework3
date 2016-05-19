package com.android1.homework3.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android1.homework3.R;

public class UserInfoFragment extends Fragment {
    private ImageView mAvatar;
    private TextView mNickname;
    private TextView mStatus;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_info, container, false);
        mAvatar = (ImageView) view.findViewById(R.id.avatar);
        mNickname = (TextView) view.findViewById(R.id.nickname);
        mStatus = (TextView) view.findViewById(R.id.status);
        return view;
    }

    public static String tag() {
        return UserInfoFragment.class.getSimpleName();
    }
}
