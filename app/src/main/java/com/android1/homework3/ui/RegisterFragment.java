package com.android1.homework3.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android1.homework3.R;
import com.android1.homework3.msg.NoSpaceFilter;

public class RegisterFragment extends Fragment {
    private EditText mLogin;
    private EditText mNickname;
    private EditText mPassword;
    private EditText mRepeatPassword;
    private Button mRegisterButton;
    private Controller mController;

    public static RegisterFragment newInstance(Controller controller) {
        RegisterFragment fragment = new RegisterFragment();
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
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        mLogin = (EditText) view.findViewById(R.id.login);
        mNickname = (EditText) view.findViewById(R.id.nickname);
        mPassword = (EditText) view.findViewById(R.id.password);
        mRepeatPassword = (EditText) view.findViewById(R.id.repeat_password);
        mRegisterButton = (Button) view.findViewById(R.id.btn_register);

        mLogin.setFilters(new InputFilter[] {new NoSpaceFilter()});
        mNickname.setFilters(new InputFilter[] {new NoSpaceFilter()});
        mPassword.setFilters(new InputFilter[] {new NoSpaceFilter()});
        mRepeatPassword.setFilters(new InputFilter[] {new NoSpaceFilter()});

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = mLogin.getText().toString().trim();
                String nick = mNickname.getText().toString().trim();
                String pass = mPassword.getText().toString().trim();
                String repeatPass = mRepeatPassword.getText().toString().trim();

                if (login.isEmpty()) {
                    Toast.makeText(getActivity(), R.string.toast_empty_login, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (nick.isEmpty()) {
                    Toast.makeText(getActivity(), R.string.toast_empty_nickname, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (pass.isEmpty()) {
                    Toast.makeText(getActivity(), R.string.toast_empty_pass, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!pass.equals(repeatPass)) {
                    Toast.makeText(getActivity(), R.string.toast_passwords_not_equals, Toast.LENGTH_SHORT).show();
                    return;
                }

                mController.register(login, nick, pass);
            }
        });

        return view;
    }

    public static String tag() {
        return RegisterFragment.class.getSimpleName();
    }
}
