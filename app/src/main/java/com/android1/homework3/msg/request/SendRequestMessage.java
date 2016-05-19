package com.android1.homework3.msg.request;

import android.os.Parcel;

import com.android1.homework3.msg.BaseMessage;
import com.android1.homework3.msg.MessageAction;

/*{
    "action":"message",
    "data": {
        "cid":"MY_USER_ID",
        "sid":"MY_SESSION_ID"
        "channel":"NEED_CHANNEL_ID",
        "body":"MESSAGE"
    }
}*/
public class SendRequestMessage implements BaseMessage {
    public String cid;  // client id
    public String sid;  // session id
    public String channel;
    public String body;

    public SendRequestMessage() {
    }

    protected SendRequestMessage(Parcel in) {
        cid = in.readString();
        sid = in.readString();
        channel = in.readString();
        body = in.readString();
    }

    public static final Creator<SendRequestMessage> CREATOR = new Creator<SendRequestMessage>() {
        @Override
        public SendRequestMessage createFromParcel(Parcel in) {
            return new SendRequestMessage(in);
        }

        @Override
        public SendRequestMessage[] newArray(int size) {
            return new SendRequestMessage[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(cid);
        out.writeString(sid);
        out.writeString(channel);
        out.writeString(body);
    }

    @Override
    public String getAction() {
        return MessageAction.SEND_MESSAGE;
    }
}
