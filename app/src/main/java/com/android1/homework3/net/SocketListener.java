package com.android1.homework3.net;

public interface SocketListener {
    void onConnected();
    void onConnectionFailed();
    void onDataReceived(String data);
}
