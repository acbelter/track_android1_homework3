package com.android1.homework3.ui;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.android1.homework3.HashUtil;
import com.android1.homework3.Pref;
import com.android1.homework3.R;
import com.android1.homework3.msg.BaseMessage;
import com.android1.homework3.msg.MessageAction;
import com.android1.homework3.msg.request.AuthRequestMessage;
import com.android1.homework3.msg.response.WelcomeMessage;

import java.lang.ref.WeakReference;

public final class Controller {
    private WeakReference<MainActivity> mMainActivityWeakRef;
    private SharedPreferences mPrefs;

    public Controller(MainActivity mainActivity) {
        mMainActivityWeakRef = new WeakReference<>(mainActivity);
        mPrefs = PreferenceManager.getDefaultSharedPreferences(mainActivity);
    }

    public void processResponse(BaseMessage message) {
        switch (message.getAction()) {
            case MessageAction.AUTH: {
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

    private void replaceFragment(MainActivity mainActivity, Fragment fragment, String tag) {
        FragmentTransaction ft = mainActivity.getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, fragment, tag);
        ft.commit();
    }

    private void processWelcomeMessage(WelcomeMessage message) {
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
            replaceFragment(mainActivity, AuthFragment.newInstance(this), AuthFragment.tag());
        }
    }

    public void processConnectionFailed() {
        MainActivity mainActivity = mMainActivityWeakRef.get();
        if (mainActivity == null) {
            return;
        }
        replaceFragment(mainActivity, ReconnectFragment.newInstance(this), ReconnectFragment.tag());
    }

    public void connectToNetworkService() {
        MainActivity mainActivity = mMainActivityWeakRef.get();
        if (mainActivity == null) {
            return;
        }
        mainActivity.connectToNetworkService();
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
}
