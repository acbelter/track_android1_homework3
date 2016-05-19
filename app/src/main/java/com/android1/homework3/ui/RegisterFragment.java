package com.android1.homework3.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android1.homework3.R;

public class RegisterFragment extends UiFragment {
    private EditText mLogin;
    private EditText mPassword;
    private EditText mRepeatPassword;
    private Button mRegisterButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        mLogin = (EditText) view.findViewById(R.id.login);
        mPassword = (EditText) view.findViewById(R.id.password);
        mRepeatPassword = (EditText) view.findViewById(R.id.repeat_password);
        mRegisterButton = (Button) view.findViewById(R.id.btn_register);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }

    @Override
    public void setUiEnabled(boolean enabled) {
        mLogin.setEnabled(enabled);
        mPassword.setEnabled(enabled);
        mRepeatPassword.setEnabled(enabled);
        mRegisterButton.setEnabled(enabled);
    }

    public static String tag() {
        return RegisterFragment.class.getSimpleName();
    }
}
