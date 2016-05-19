package com.android1.homework3.ui;

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

public class AuthFragment extends UiFragment {
    private Controller mController;
    private EditText mLogin;
    private EditText mPassword;
    private Button mLoginButton;
    private Button mRegisterButton;

    public static AuthFragment newInstance(Controller controller) {
        AuthFragment fragment = new AuthFragment();
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
        View view = inflater.inflate(R.layout.fragment_auth, container, false);
        mLogin = (EditText) view.findViewById(R.id.login);
        mPassword = (EditText) view.findViewById(R.id.password);
        mLoginButton = (Button) view.findViewById(R.id.btn_login);
        mRegisterButton = (Button) view.findViewById(R.id.btn_register);

        mLogin.setFilters(new InputFilter[] {new NoSpaceFilter()});
        mPassword.setFilters(new InputFilter[] {new NoSpaceFilter()});

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = mLogin.getText().toString().trim();
                String pass = mPassword.getText().toString().trim();

                if (login.isEmpty()) {
                    Toast.makeText(getActivity(), R.string.toast_empty_login, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (pass.isEmpty()) {
                    Toast.makeText(getActivity(), R.string.toast_empty_pass, Toast.LENGTH_SHORT).show();
                    return;
                }

                setUiEnabled(false);
                mController.startAuthorization(login, pass);
            }
        });

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mController.showRegisterFragment();
            }
        });

        return view;
    }

    @Override
    public void setUiEnabled(boolean enabled) {
        mLogin.setEnabled(enabled);
        mPassword.setEnabled(enabled);
        mLoginButton.setEnabled(enabled);
        mRegisterButton.setEnabled(enabled);
    }

    public static String tag() {
        return AuthFragment.class.getSimpleName();
    }
}
