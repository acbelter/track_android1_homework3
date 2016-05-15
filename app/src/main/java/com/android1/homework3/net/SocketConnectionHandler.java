package com.android1.homework3.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.android1.homework3.Logger;

/**
 * Класс работающий с сокетом, умеет отправлять данные в сокет
 * Также слушает сокет и рассылает событие о сообщении всем подписчикам (асинхронность)
 */
public class SocketConnectionHandler implements ConnectionHandler {
    private List<SocketListener> mListeners = new ArrayList<>();
    private InputStream mInputStream;
    private OutputStream mOutputStream;
    private String mHost;
    private int mPort;

    public SocketConnectionHandler(String host, int port) throws IOException {
        mHost = host;
        mPort = port;
    }

    @Override
    public void send(String data) throws IOException {
        if (data == null) {
            return;
        }
        mOutputStream.write(data.getBytes("UTF-8"));
        mOutputStream.flush();
    }

    @Override
    public void addListener(SocketListener listener) {
        mListeners.add(listener);
    }

    @Override
    public void run() {
        Socket socket = null;
        try {
            socket = new Socket(mHost, mPort);
            mInputStream = socket.getInputStream();
            mOutputStream = socket.getOutputStream();
            for (SocketListener listener : mListeners) {
                listener.onSocketConnected();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
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
                Logger.d("Failed to handle connection");
                e.printStackTrace();
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