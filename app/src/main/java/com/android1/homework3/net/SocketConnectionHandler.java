package com.android1.homework3.net;

import com.android1.homework3.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SocketConnectionHandler implements ConnectionHandler {
    private static final long RECONNECT_DELAY = 3000L;
    private List<SocketListener> mListeners;
    private InputStream mInputStream;
    private OutputStream mOutputStream;
    private String mHost;
    private int mPort;

    public SocketConnectionHandler(String host, int port) {
        mListeners = new ArrayList<>();
        mHost = host;
        mPort = port;
    }

    @Override
    public void send(String data) throws IOException {
        if (data != null) {
            mOutputStream.write(data.getBytes("UTF-8"));
            mOutputStream.flush();
        }
    }

    @Override
    public void addListener(SocketListener listener) {
        mListeners.add(listener);
    }

    @Override
    public void run() {
        Socket socket = null;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Logger.d("Attempt to connect to " + mHost + ":" + mPort + " " + hashCode());
                socket = new Socket(mHost, mPort);
                mInputStream = socket.getInputStream();
                mOutputStream = socket.getOutputStream();

                for (SocketListener listener : mListeners) {
                    listener.onConnected();
                }
                break;
            } catch (IOException e) {
                try {
                    Thread.sleep(RECONNECT_DELAY);
                } catch (InterruptedException ex) {
                    // Ignore
                }
            }
        }

        final byte[] buf = new byte[1024 * 64];
        while (!Thread.currentThread().isInterrupted()) {
            try {
                int read = mInputStream.read(buf);
                if (read > 0) {
                    String data = new String(Arrays.copyOf(buf, read), "UTF-8");
                    for (SocketListener listener : mListeners) {
                        listener.onDataReceived(data);
                    }
                }
            } catch (Exception e) {
                Logger.d("Failed to handle connection: " + e.getMessage());
                for (SocketListener listener : mListeners) {
                    listener.onConnectionFailed();
                }
                Thread.currentThread().interrupt();
            }
        }

        if (socket != null && !socket.isClosed()) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void stop() {
        Thread.currentThread().interrupt();
    }
}