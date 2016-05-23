package com.android1.homework3.net;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class JSONStreamProcessor implements StreamProcessor {
    private static final char OPEN_BRACE = '{';
    private static final char CLOSE_BRACE = '}';

    @Override
    public void read(InputStream in, final byte[] buf, List<SocketListener> listeners) throws Exception {
        int read = in.read(buf);
        if (read > 0) {
            String data = new String(Arrays.copyOf(buf, read), "UTF-8");
            List<String> dataParts = new ArrayList<>();
            Stack<Character> stack = new Stack<>();
            int firstOpenBrace = -1;
            for (int i = 0; i < data.length(); i++) {
                if (data.charAt(i) == OPEN_BRACE) {
                    if (firstOpenBrace == -1) {
                        firstOpenBrace = i;
                    }
                    stack.push(OPEN_BRACE);
                } else if (data.charAt(i) == CLOSE_BRACE) {
                    if (!stack.isEmpty()) {
                        stack.pop();
                        if (stack.isEmpty()) {
                            dataParts.add(data.substring(firstOpenBrace, i + 1));
                            firstOpenBrace = -1;
                        }
                    }
                }
            }

            if (listeners != null) {
                for (SocketListener listener : listeners) {
                    for (String dataPart : dataParts) {
                        listener.onDataReceived(dataPart);
                    }
                }
            }
        }
    }

    @Override
    public List<String> read(InputStream in, final byte[] buf) throws Exception {
        int read = in.read(buf);
        List<String> dataParts = new ArrayList<>();
        if (read > 0) {
            String data = new String(Arrays.copyOf(buf, read), "UTF-8");
            Stack<Character> stack = new Stack<>();
            int firstOpenBrace = -1;
            for (int i = 0; i < data.length(); i++) {
                if (data.charAt(i) == OPEN_BRACE) {
                    if (firstOpenBrace == -1) {
                        firstOpenBrace = i;
                    }
                    stack.push(OPEN_BRACE);
                } else if (data.charAt(i) == CLOSE_BRACE) {
                    if (!stack.isEmpty()) {
                        stack.pop();
                        if (stack.isEmpty()) {
                            dataParts.add(data.substring(firstOpenBrace, i + 1));
                            firstOpenBrace = -1;
                        }
                    }
                }
            }
        }
        return dataParts;
    }
}
