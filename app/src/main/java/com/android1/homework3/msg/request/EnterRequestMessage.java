package com.android1.homework3.msg.request;

import android.os.Parcel;

import com.android1.homework3.msg.BaseMessage;

/*{
    "action":"enter",
    "data": {
        "cid":"MY_USER_ID",
        "sid":"MY_SESSION_ID"
        "channel":"NEED_CHANNEL_ID"
    }
}*/
public class EnterRequestMessage implements BaseMessage {
    public String cid;  // client id
    public String sid;  // session id
    public String channel;

    public EnterRequestMessage() {
    }

    protected EnterRequestMessage(Parcel in) {
        cid = in.readString();
        sid = in.readString();
        channel = in.readString();
    }

    public static final Creator<EnterRequestMessage> CREATOR = new Creator<EnterRequestMessage>() {
        @Override
        public EnterRequestMessage createFromParcel(Parcel in) {
            return new EnterRequestMessage(in);
        }

        @Override
        public EnterRequestMessage[] newArray(int size) {
            return new EnterRequestMessage[size];
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
    }
}
