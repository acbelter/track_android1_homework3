package com.android1.homework3.net;

import java.util.List;

public interface DataProcessor {
    void process(String data, List<SocketListener> listeners) throws Exception;
    List<String> process(String data) throws Exception;
}
