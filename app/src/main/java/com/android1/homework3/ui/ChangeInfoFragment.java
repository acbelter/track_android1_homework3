package com.android1.homework3.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android1.homework3.R;

public class ChangeInfoFragment extends Fragment {
    private ImageView mAvatar;
    private EditText mNickname;
    private EditText mStatus;
    private Button mSaveButton;

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
        mNickname = (EditText) view.findViewById(R.id.nickname);
        mStatus = (EditText) view.findViewById(R.id.status);
        mSaveButton = (Button) view.findViewById(R.id.btn_save);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }

    public static String tag() {
        return ChangeInfoFragment.class.getSimpleName();
    }
}
