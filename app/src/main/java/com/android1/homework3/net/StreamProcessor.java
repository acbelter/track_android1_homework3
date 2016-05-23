package com.android1.homework3.net;

import java.io.InputStream;
import java.util.List;

public interface StreamProcessor {
    void read(InputStream in, final byte[] buf, List<SocketListener> listeners) throws Exception;
    List<String> read(InputStream in, final byte[] buf) throws Exception;
}
