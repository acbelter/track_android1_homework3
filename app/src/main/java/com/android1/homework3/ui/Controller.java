package com.android1.homework3.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.android1.homework3.HashUtil;
import com.android1.homework3.Logger;
import com.android1.homework3.Pref;
import com.android1.homework3.R;
import com.android1.homework3.msg.BaseMessage;
import com.android1.homework3.msg.MessageAction;
import com.android1.homework3.msg.request.AuthRequestMessage;
import com.android1.homework3.msg.request.RegisterRequestMessage;
import com.android1.homework3.msg.response.AuthResponseMessage;
import com.android1.homework3.msg.response.RegisterResponseMessage;
import com.android1.homework3.msg.response.WelcomeMessage;

import java.lang.ref.WeakReference;

public final class Controller {
    private WeakReference<MainActivity> mMainActivityWeakRef;
    private SharedPreferences mPrefs;
    private boolean mFirstWelcome;

    private static String[] sUiFragmentTags = new String[] {
            AuthFragment.tag(),
            ChangeInfoFragment.tag(),
            ReconnectFragment.tag(),
            RegisterFragment.tag(),
            UserInfoFragment.tag()
    };

    public Controller(MainActivity mainActivity) {
        mMainActivityWeakRef = new WeakReference<>(mainActivity);
        mPrefs = PreferenceManager.getDefaultSharedPreferences(mainActivity);
        mFirstWelcome = true;
    }

    public void processResponse(BaseMessage message) {
        switch (message.getAction()) {
            case MessageAction.AUTH: {
                processAuthMessage((AuthResponseMessage) message);
                break;
            }
            case MessageAction.CHANNEL_LIST: {
                break;
            }
            case MessageAction.CREATE_CHANNEL: {
                break;
            }
            case MessageAction.EVENT_ENTER: {
                break;
            }
            case MessageAction.ENTER: {
                break;
            }
            case MessageAction.EVENT_LEAVE: {
                break;
            }
            case MessageAction.LEAVE: {
                break;
            }
            case MessageAction.EVENT_MESSAGE: {
                break;
            }
            case MessageAction.REGISTER: {
                processRegisterMessage((RegisterResponseMessage) message);
                break;
            }
            case MessageAction.SET_USER_INFO: {
                break;
            }
            case MessageAction.USER_INFO: {
                break;
            }
            case MessageAction.WELCOME: {
                processWelcomeMessage((WelcomeMessage) message);
                break;
            }
        }
    }

    private void replaceFragment(MainActivity mainActivity, Fragment fragment,
                                 String tag, boolean addToBackStack) {
        FragmentTransaction ft = mainActivity.getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, fragment, tag);
        if (addToBackStack) {
            ft.addToBackStack(null);
        }
        ft.commit();
    }

    private void enableUi() {
        MainActivity mainActivity = mMainActivityWeakRef.get();
        if (mainActivity == null) {
            return;
        }

        FragmentManager fragmentManager = mainActivity.getFragmentManager();
        Fragment fragment;
        for (String tag : sUiFragmentTags) {
            fragment = fragmentManager.findFragmentByTag(tag);
            if (fragment instanceof UiFragment) {
                ((UiFragment) fragment).setUiEnabled(true);
            }
        }

    }

    private void processWelcomeMessage(WelcomeMessage message) {
        if (!mFirstWelcome) {
            enableUi();
            return;
        }

        MainActivity mainActivity = mMainActivityWeakRef.get();
        if (mainActivity == null) {
            return;
        }

        if (Pref.isLoggedIn(mPrefs)) {
            AuthRequestMessage authRequestMessage = new AuthRequestMessage();
            authRequestMessage.login = Pref.loadLogin(mPrefs);
            authRequestMessage.pass = Pref.loadPass(mPrefs);
            mainActivity.sendMessage(authRequestMessage);
        } else {
            showAuthFragment();
        }

        mFirstWelcome = false;
    }

    private void processAuthMessage(AuthResponseMessage message) {
        MainActivity mainActivity = mMainActivityWeakRef.get();
        if (mainActivity == null) {
            return;
        }

        switch (message.status) {
            case ERR_OK: {
                // TODO
                Logger.d("Successful authorization");
                break;
            }
            case ERR_ALREADY_EXIST: {
                Toast.makeText(mainActivity.getApplicationContext(),
                        R.string.toast_login_already_exists, Toast.LENGTH_SHORT).show();
                mainActivity.connectToNetworkService();
                break;
            }
            case ERR_INVALID_PASS: {
                Toast.makeText(mainActivity.getApplicationContext(),
                        R.string.toast_invalid_pass, Toast.LENGTH_SHORT).show();
                mainActivity.connectToNetworkService();
                break;
            }
            case ERR_INVALID_DATA: {
                break;
            }
            case ERR_EMPTY_FIELD: {
                break;
            }
            case ERR_ALREADY_REGISTER: {
                break;
            }
            case ERR_NEED_AUTH: {
                break;
            }
            case ERR_NEED_REGISTER: {
                Toast.makeText(mainActivity.getApplicationContext(),
                        R.string.toast_need_register, Toast.LENGTH_SHORT).show();
                mainActivity.connectToNetworkService();
                break;
            }
            case ERR_USER_NOT_FOUND: {
                break;
            }
            case ERR_CHANNEL_NOT_FOUND: {
                break;
            }
            case ERR_INVALID_CHANNEL: {
                break;
            }
        }
    }

    private void processRegisterMessage(RegisterResponseMessage message) {
        MainActivity mainActivity = mMainActivityWeakRef.get();
        if (mainActivity == null) {
            return;
        }

        switch (message.status) {
            case ERR_OK: {
                // TODO
                Logger.d("Successful registration");
                break;
            }
            case ERR_ALREADY_EXIST: {
                Toast.makeText(mainActivity.getApplicationContext(),
                        R.string.toast_login_already_exists, Toast.LENGTH_SHORT).show();
                mainActivity.connectToNetworkService();
                break;
            }
            case ERR_INVALID_PASS: {
                Toast.makeText(mainActivity.getApplicationContext(),
                        R.string.toast_invalid_pass, Toast.LENGTH_SHORT).show();
                mainActivity.connectToNetworkService();
                break;
            }
            case ERR_INVALID_DATA: {
                break;
            }
            case ERR_EMPTY_FIELD: {
                break;
            }
            case ERR_ALREADY_REGISTER: {
                Toast.makeText(mainActivity.getApplicationContext(),
                        R.string.toast_login_already_exists, Toast.LENGTH_SHORT).show();
                mainActivity.connectToNetworkService();
                break;
            }
            case ERR_NEED_AUTH: {
                break;
            }
            case ERR_NEED_REGISTER: {
                Toast.makeText(mainActivity.getApplicationContext(),
                        R.string.toast_need_register, Toast.LENGTH_SHORT).show();
                mainActivity.connectToNetworkService();
                break;
            }
            case ERR_USER_NOT_FOUND: {
                break;
            }
            case ERR_CHANNEL_NOT_FOUND: {
                break;
            }
            case ERR_INVALID_CHANNEL: {
                break;
            }
        }
    }

    public void processConnectionFailed(boolean silent) {
        MainActivity mainActivity = mMainActivityWeakRef.get();
        if (mainActivity == null) {
            return;
        }

        if (silent) {
            mainActivity.connectToNetworkService();
        } else {
            showReconnectFragment();
        }
    }

    public void connectToNetworkService() {
        MainActivity mainActivity = mMainActivityWeakRef.get();
        if (mainActivity == null) {
            return;
        }
        mainActivity.connectToNetworkService();
    }

    public void showReconnectFragment() {
        mFirstWelcome = false;
        MainActivity mainActivity = mMainActivityWeakRef.get();
        if (mainActivity == null) {
            return;
        }
        replaceFragment(mainActivity, ReconnectFragment.newInstance(this),
                ReconnectFragment.tag(), false);
    }

    public void startAuthorization(String login, String pass) {
        MainActivity mainActivity = mMainActivityWeakRef.get();
        if (mainActivity == null) {
            return;
        }

        AuthRequestMessage authRequestMessage = new AuthRequestMessage();
        authRequestMessage.login = login;
        authRequestMessage.pass = HashUtil.generateHash(pass);
        mainActivity.sendMessage(authRequestMessage);
    }

    public void startRegistration(String login, String nick, String pass) {
        MainActivity mainActivity = mMainActivityWeakRef.get();
        if (mainActivity == null) {
            return;
        }

        RegisterRequestMessage registerRequestMessage = new RegisterRequestMessage();
        registerRequestMessage.login = login;
        registerRequestMessage.nick = nick;
        registerRequestMessage.pass = HashUtil.generateHash(pass);
        mainActivity.sendMessage(registerRequestMessage);
    }

    public void showSplashFragment() {
        MainActivity mainActivity = mMainActivityWeakRef.get();
        if (mainActivity == null) {
            return;
        }
        replaceFragment(mainActivity, new SplashFragment(),
                SplashFragment.tag(), false);
    }

    public void showAuthFragment() {
        MainActivity mainActivity = mMainActivityWeakRef.get();
        if (mainActivity == null) {
            return;
        }
        replaceFragment(mainActivity, AuthFragment.newInstance(this),
                AuthFragment.tag(), false);
    }

    public void showRegisterFragment() {
        MainActivity mainActivity = mMainActivityWeakRef.get();
        if (mainActivity == null) {
            return;
        }
        replaceFragment(mainActivity, RegisterFragment.newInstance(this),
                RegisterFragment.tag(), true);
    }
}
