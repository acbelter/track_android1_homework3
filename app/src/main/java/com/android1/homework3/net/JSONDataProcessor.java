package com.android1.homework3.net;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class JSONDataProcessor implements DataProcessor {
    private static final char OPEN_BRACE = '{';
    private static final char CLOSE_BRACE = '}';

    @Override
    public void process(String data, List<SocketListener> listeners) throws Exception {
        List<String> dataParts = new ArrayList<>();

        if (data == null) {
            return;
        }
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

    @Override
    public List<String> process(String data) throws Exception {
        List<String> dataParts = new ArrayList<>();
        if (data == null) {
            return dataParts;
        }

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

        return dataParts;
    }
}
