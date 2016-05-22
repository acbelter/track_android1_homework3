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
import com.android1.homework3.msg.request.EnterRequestMessage;
import com.android1.homework3.msg.request.LeaveRequestMessage;
import com.android1.homework3.msg.request.RegisterRequestMessage;
import com.android1.homework3.msg.response.AuthResponseMessage;
import com.android1.homework3.msg.response.Channel;
import com.android1.homework3.msg.response.ChannelListResponseMessage;
import com.android1.homework3.msg.response.EnterEventMessage;
import com.android1.homework3.msg.response.EnterResponseMessage;
import com.android1.homework3.msg.response.LastMessage;
import com.android1.homework3.msg.response.LeaveEventMessage;
import com.android1.homework3.msg.response.LeaveResponseMessage;
import com.android1.homework3.msg.response.RegisterResponseMessage;
import com.android1.homework3.msg.response.User;
import com.android1.homework3.msg.response.WelcomeMessage;

import java.lang.ref.WeakReference;
import java.util.List;

// Controller shouldn't store its state!
public final class Controller {
    private WeakReference<MainActivity> mMainActivityWeakRef;
    private SharedPreferences mPrefs;

    // FIXME This field needs because server don't send channel_id in enter response
    private String mLastEnterChannelId;
    // FIXME This field needs because server don't send channel_id in leave response
    private String mLastLeaveChannelId;

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
                processEnterEvent((EnterEventMessage) message);
                break;
            }
            case MessageAction.ENTER: {
                processEnterMessage((EnterResponseMessage) message);
                break;
            }
            case MessageAction.EVENT_LEAVE: {
                processLeaveEvent((LeaveEventMessage) message);
                break;
            }
            case MessageAction.LEAVE: {
                processLeaveMessage((LeaveResponseMessage) message);
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

    private void processWelcomeMessage(WelcomeMessage message) {
        hideLoadingDialog();

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
                Pref.saveUserId(mPrefs, message.cid);
                Pref.saveSessionId(mPrefs, message.sid);
                getChannelList(message.cid, message.sid);
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
                hideLoadingDialog();
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

    private void processEnterMessage(EnterResponseMessage message) {
        MainActivity mainActivity = mMainActivityWeakRef.get();
        if (mainActivity == null) {
            return;
        }

        switch (message.status) {
            case ERR_OK: {
                Logger.d("Successful enter to channel");
                hideLoadingDialog();
                showChannelFragment(message.users, message.lastMsg, true);
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
                Toast.makeText(mainActivity.getApplicationContext(),
                        R.string.toast_user_not_found, Toast.LENGTH_SHORT).show();
                mainActivity.connectToNetworkService();
                break;
            }
            case ERR_CHANNEL_NOT_FOUND: {
                Toast.makeText(mainActivity.getApplicationContext(),
                        R.string.toast_channel_not_found, Toast.LENGTH_SHORT).show();
                mainActivity.connectToNetworkService();
                break;
            }
            case ERR_INVALID_CHANNEL: {
                break;
            }
        }
    }

    private void processLeaveMessage(LeaveResponseMessage message) {
        MainActivity mainActivity = mMainActivityWeakRef.get();
        if (mainActivity == null) {
            return;
        }

        switch (message.status) {
            case ERR_OK: {
                Logger.d("Successful leave from channel");
                FragmentManager fm = mainActivity.getFragmentManager();

                String userId = Pref.loadUserId(mPrefs);
                ChannelListFragment channelListFragment =
                        (ChannelListFragment) fm.findFragmentByTag(ChannelListFragment.tag());
                if (channelListFragment != null) {
                    channelListFragment.processLeaveChannel(userId, mLastLeaveChannelId);
                }
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
                Toast.makeText(mainActivity.getApplicationContext(),
                        R.string.toast_user_not_found, Toast.LENGTH_SHORT).show();
                mainActivity.connectToNetworkService();
                break;
            }
            case ERR_CHANNEL_NOT_FOUND: {
                Toast.makeText(mainActivity.getApplicationContext(),
                        R.string.toast_channel_not_found, Toast.LENGTH_SHORT).show();
                mainActivity.connectToNetworkService();
                break;
            }
            case ERR_INVALID_CHANNEL: {
                Toast.makeText(mainActivity.getApplicationContext(),
                        R.string.toast_user_out_of_channel, Toast.LENGTH_SHORT).show();
                mainActivity.connectToNetworkService();
                break;
            }
        }
    }

    public void processEnterEvent(EnterEventMessage message) {
        MainActivity mainActivity = mMainActivityWeakRef.get();
        if (mainActivity == null) {
            return;
        }

        FragmentManager fm = mainActivity.getFragmentManager();
        ChannelListFragment channelListFragment =
                (ChannelListFragment) fm.findFragmentByTag(ChannelListFragment.tag());
        if (channelListFragment != null) {
            channelListFragment.processEnterChannel(message.uid, message.chid);
        }
    }

    public void processLeaveEvent(LeaveEventMessage message) {
        MainActivity mainActivity = mMainActivityWeakRef.get();
        if (mainActivity == null) {
            return;
        }

        FragmentManager fm = mainActivity.getFragmentManager();
        ChannelListFragment channelListFragment =
                (ChannelListFragment) fm.findFragmentByTag(ChannelListFragment.tag());
        if (channelListFragment != null) {
            channelListFragment.processLeaveChannel(message.uid, message.chid);
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

    public void showLoadingDialog() {
        MainActivity mainActivity = mMainActivityWeakRef.get();
        if (mainActivity == null) {
            return;
        }
        mainActivity.showLoadingDialog();
    }

    public void hideLoadingDialog() {
        MainActivity mainActivity = mMainActivityWeakRef.get();
        if (mainActivity == null) {
            return;
        }
        mainActivity.hideLoadingDialog();
    }

    public void authorize(String login, String pass) {
        MainActivity mainActivity = mMainActivityWeakRef.get();
        if (mainActivity == null) {
            return;
        }

        showLoadingDialog();

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

        showLoadingDialog();

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

    public void enterChannel(Channel channel) {
        MainActivity mainActivity = mMainActivityWeakRef.get();
        if (mainActivity == null) {
            return;
        }

        String userId = Pref.loadUserId(mPrefs);
        String sessionId = Pref.loadSessionId(mPrefs);

        if (userId == null || sessionId == null || channel == null) {
            Toast.makeText(mainActivity.getApplicationContext(),
                    R.string.toast_unable_channel_enter, Toast.LENGTH_SHORT).show();
            return;
        }

        showLoadingDialog();

        mLastEnterChannelId = channel.chid;

        EnterRequestMessage enterMessage = new EnterRequestMessage();
        enterMessage.cid = userId;
        enterMessage.sid = sessionId;
        enterMessage.channel = channel.chid;
        mainActivity.sendMessage(enterMessage);
    }

    public void leaveChannel(Channel channel) {
        MainActivity mainActivity = mMainActivityWeakRef.get();
        if (mainActivity == null) {
            return;
        }

        String userId = Pref.loadUserId(mPrefs);
        String sessionId = Pref.loadSessionId(mPrefs);

        if (userId == null || sessionId == null || channel == null) {
            return;
        }

        mLastLeaveChannelId = channel.chid;

        LeaveRequestMessage leaveMessage = new LeaveRequestMessage();
        leaveMessage.cid = userId;
        leaveMessage.sid = sessionId;
        leaveMessage.channel = channel.chid;
        mainActivity.sendMessage(leaveMessage);
    }

    public void leaveAllChannels() {
        MainActivity mainActivity = mMainActivityWeakRef.get();
        if (mainActivity == null) {
            return;
        }

        String userId = Pref.loadUserId(mPrefs);
        String sessionId = Pref.loadSessionId(mPrefs);

        if (userId == null || sessionId == null) {
            return;
        }

        LeaveRequestMessage leaveMessage = new LeaveRequestMessage();
        leaveMessage.cid = userId;
        leaveMessage.sid = sessionId;
        leaveMessage.channel = "*";
        mainActivity.sendMessage(leaveMessage);
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

        String userId = Pref.loadUserId(mPrefs);
        replaceFragment(mainActivity, ChannelListFragment.newInstance(this, userId, channels),
                ChannelListFragment.tag(), addToBackStack);
    }

    public void showChannelFragment(List<User> users,
                                    List<LastMessage> lastMessages,
                                    boolean addToBackStack) {
        MainActivity mainActivity = mMainActivityWeakRef.get();
        if (mainActivity == null) {
            return;
        }

        String userId = Pref.loadUserId(mPrefs);
        replaceFragment(mainActivity, ChannelFragment.newInstance(this,
                userId, mLastEnterChannelId, users, lastMessages),
                ChannelFragment.tag(), addToBackStack);
    }
}
