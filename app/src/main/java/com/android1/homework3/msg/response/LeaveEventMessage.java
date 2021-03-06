package com.android1.homework3.msg.response;

import android.os.Parcel;

import com.android1.homework3.msg.BaseMessage;
import com.android1.homework3.msg.MessageAction;

/*{
    "action":"ev_leave",
    "data":{
        "chid":"CHANNEL_ID",
        "uid":"USER_ID"
        "nick":"NICKNAME",
    }
}*/
public class LeaveEventMessage implements BaseMessage {
    public String chid; // channel id
    public String uid;  // user id
    public String nick;

    public LeaveEventMessage() {
    }

    protected LeaveEventMessage(Parcel in) {
        chid = in.readString();
        uid = in.readString();
        nick = in.readString();
    }

    public static final Creator<LeaveEventMessage> CREATOR = new Creator<LeaveEventMessage>() {
        @Override
        public LeaveEventMessage createFromParcel(Parcel in) {
            return new LeaveEventMessage(in);
        }

        @Override
        public LeaveEventMessage[] newArray(int size) {
            return new LeaveEventMessage[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(chid);
        out.writeString(uid);
        out.writeString(nick);
    }

    @Override
    public String getAction() {
        return MessageAction.EVENT_LEAVE;
    }
}
