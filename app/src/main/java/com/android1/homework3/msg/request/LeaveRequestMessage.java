package com.android1.homework3.msg.request;

import android.os.Parcel;

import com.android1.homework3.msg.BaseMessage;

/*{
    "action":"leave",
    "data": {
        "cid":"MY_USER_ID",
        "sid":"MY_SESSION_ID"
        "channel":"NEED_CHANNEL_ID"|"*"
    }
}*/
public class LeaveRequestMessage implements BaseMessage {
    public String cid;  // client id
    public String sid;  // session id
    public String channel;

    public LeaveRequestMessage() {
    }

    protected LeaveRequestMessage(Parcel in) {
        cid = in.readString();
        sid = in.readString();
        channel = in.readString();
    }

    public static final Creator<LeaveRequestMessage> CREATOR = new Creator<LeaveRequestMessage>() {
        @Override
        public LeaveRequestMessage createFromParcel(Parcel in) {
            return new LeaveRequestMessage(in);
        }

        @Override
        public LeaveRequestMessage[] newArray(int size) {
            return new LeaveRequestMessage[size];
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
