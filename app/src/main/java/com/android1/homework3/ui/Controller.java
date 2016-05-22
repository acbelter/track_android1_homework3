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
import com.android1.homework3.msg.Status;
import com.android1.homework3.msg.request.AuthRequestMessage;
import com.android1.homework3.msg.request.ChannelListRequestMessage;
import com.android1.homework3.msg.request.RegisterRequestMessage;
import com.android1.homework3.msg.response.AuthResponseMessage;
import com.android1.homework3.msg.response.Channel;
import com.android1.homework3.msg.response.ChannelListResponseMessage;
import com.android1.homework3.msg.response.RegisterResponseMessage;
import com.android1.homework3.msg.response.WelcomeMessage;

import java.lang.ref.WeakReference;
import java.util.List;

// Controller shouldn't store its state!
public final class Controller {
    private WeakReference<MainActivity> mMainActivityWeakRef;
    private SharedPreferences mPrefs;

    private static String[] sUiFragmentTags = new String[] {
            AuthFragment.tag(),
            ChangeInfoFragment.tag(),
            RegisterFragment.tag(),
            UserInfoFragment.tag(),
            ChannelListFragment.tag(),
            ChannelFragment.tag()
    };

    public Controller(MainActivity mainActivity) {
        mMainActivityWeakRef = new WeakReference<>(mainActivity);
        mPrefs = PreferenceManager.getDefaultSharedPreferences(mainActivity);
    }

    public void processResponse(BaseMessage message) {
        switch (message.getAction()) {
            case MessageAction.AUTH: {
                processAuthMessage((AuthResponseMessage) message);
                break;
            }
            case MessageAction.CHANNEL_LIST: {
                processChannelListMessage((ChannelListResponseMessage) message);
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

    private void setUiEnabled(boolean enabled) {
        MainActivity mainActivity = mMainActivityWeakRef.get();
        if (mainActivity == null) {
            return;
        }

        FragmentManager fragmentManager = mainActivity.getFragmentManager();
        Fragment fragment;
        for (String tag : sUiFragmentTags) {
            fragment = fragmentManager.findFragmentByTag(tag);
            if (fragment instanceof UiFragment && fragment.isAdded()) {
                ((UiFragment) fragment).setUiEnabled(enabled);
            }
        }
    }

    private void processWelcomeMessage(WelcomeMessage message) {
        setUiEnabled(true);

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
            showAuthFragment(false);
        }
    }

    private void processAuthMessage(AuthResponseMessage message) {
        MainActivity mainActivity = mMainActivityWeakRef.get();
        if (mainActivity == null) {
            return;
        }

        if (message.status != Status.ERR_OK) {
            Pref.deleteAuthData(mPrefs);
        }

        switch (message.status) {
            case ERR_OK: {
                Logger.d("Successful authorization");
                Pref.saveUserId(mPrefs, message.uid);
                Pref.saveSessionId(mPrefs, message.sid);
                getChannelList(message.uid, message.sid);
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

        if (message.status != Status.ERR_OK) {
            Pref.deleteAuthData(mPrefs);
        }

        switch (message.status) {
            case ERR_OK: {
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

    private void processChannelListMessage(ChannelListResponseMessage message) {
        MainActivity mainActivity = mMainActivityWeakRef.get();
        if (mainActivity == null) {
            return;
        }

        switch (message.status) {
            case ERR_OK: {
                Logger.d("Successful getting channel list");
                showChannelListFragment(message.channels, false);
                break;
            }
            case ERR_ALREADY_EXIST: {
                break;
            }
            case ERR_INVALID_PASS: {
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
                showAuthFragment(false);
                break;
            }
            case ERR_NEED_REGISTER: {
                showRegisterFragment(false);
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

    public void processConnectionFailed() {
        MainActivity mainActivity = mMainActivityWeakRef.get();
        if (mainActivity == null) {
            return;
        }

        showSplashFragment(false);
        mainActivity.connectToNetworkService();
    }

    public void authorize(String login, String pass) {
        MainActivity mainActivity = mMainActivityWeakRef.get();
        if (mainActivity == null) {
            return;
        }

        AuthRequestMessage authMessage = new AuthRequestMessage();
        authMessage.login = login;
        authMessage.pass = HashUtil.generateHash(pass);

        Pref.saveLogin(mPrefs, authMessage.login);
        Pref.savePass(mPrefs, authMessage.pass);

        mainActivity.sendMessage(authMessage);
    }

    public void register(String login, String nick, String pass) {
        MainActivity mainActivity = mMainActivityWeakRef.get();
        if (mainActivity == null) {
            return;
        }

        RegisterRequestMessage registerMessage = new RegisterRequestMessage();
        registerMessage.login = login;
        registerMessage.nick = nick;
        registerMessage.pass = HashUtil.generateHash(pass);

        Pref.saveLogin(mPrefs, registerMessage.login);
        Pref.savePass(mPrefs, registerMessage.pass);

        mainActivity.sendMessage(registerMessage);
    }

    public void getChannelList(String userId, String sessionId) {
        MainActivity mainActivity = mMainActivityWeakRef.get();
        if (mainActivity == null) {
            return;
        }

        ChannelListRequestMessage channelListMessage = new ChannelListRequestMessage();
        channelListMessage.cid = userId;
        channelListMessage.sid = sessionId;
        mainActivity.sendMessage(channelListMessage);
    }

    public void showSplashFragment(boolean addToBackStack) {
        MainActivity mainActivity = mMainActivityWeakRef.get();
        if (mainActivity == null) {
            return;
        }
        replaceFragment(mainActivity, new SplashFragment(),
                SplashFragment.tag(), addToBackStack);
    }

    public void showAuthFragment(boolean addToBackStack) {
        MainActivity mainActivity = mMainActivityWeakRef.get();
        if (mainActivity == null) {
            return;
        }
        replaceFragment(mainActivity, AuthFragment.newInstance(this),
                AuthFragment.tag(), addToBackStack);
    }

    public void showRegisterFragment(boolean addToBackStack) {
        MainActivity mainActivity = mMainActivityWeakRef.get();
        if (mainActivity == null) {
            return;
        }
        replaceFragment(mainActivity, RegisterFragment.newInstance(this),
                RegisterFragment.tag(), addToBackStack);
    }

    public void showChannelListFragment(List<Channel> channels, boolean addToBackStack) {
        MainActivity mainActivity = mMainActivityWeakRef.get();
        if (mainActivity == null) {
            return;
        }
        replaceFragment(mainActivity, ChannelListFragment.newInstance(this, channels),
                ChannelListFragment.tag(), addToBackStack);
    }

    public void showChannelFragment(Channel channel, boolean addToBackStack) {
        MainActivity mainActivity = mMainActivityWeakRef.get();
        if (mainActivity == null) {
            return;
        }
        replaceFragment(mainActivity, ChannelFragment.newInstance(this, channel),
                ChannelFragment.tag(), addToBackStack);
    }
}
