package com.android1.homework3.net;

public interface SocketListener {
    void onSocketConnected();
    void onDataReceived(String data);
}
