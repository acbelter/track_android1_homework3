package com.android1.homework3.ui;

import com.android1.homework3.msg.BaseMessage;

public final class Controller {
    private MainActivity mMainActivity;

    public Controller(MainActivity mainActivity) {
        mMainActivity = mainActivity;
    }

    public void process(BaseMessage message) {

    }

    public void processConnectionFailed() {

    }

    public void connectToNetworkService() {
        mMainActivity.connectToNetworkService();
    }
}
