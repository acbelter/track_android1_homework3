package com.android1.homework3.msg.response;

import com.android1.homework3.msg.BaseMessage;

public interface MessageParser {
    BaseMessage parseMessage(String data);
}
