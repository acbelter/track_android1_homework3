package com.android1.homework3.net;

import java.io.IOException;

public interface ConnectionHandler extends Runnable {
    void send(String data) throws IOException;
    void addListener(SocketListener listener);
    void stop();
}
