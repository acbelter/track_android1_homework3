package com.android1.homework3.msg;

public interface MessageProcessor {
    BaseMessage parseMessage(String data);
    String buildMessage(BaseMessage message);
}
