package com.android1.homework3.msg.event;

import android.os.Parcel;

import com.android1.homework3.msg.BaseMessage;
import com.android1.homework3.msg.SenderType;

/*{
    "action":"ev_enter",
    "data":{
        "chid":"CHANNEL_ID",
        "uid":"USER_ID",
        "nick":"NICKNAME",
    }
}*/
public class EnterEventMessage implements BaseMessage {
    public String chid; // channel id
    public String uid;  // user id
    public String nick;

    protected EnterEventMessage(Parcel in) {
        chid = in.readString();
        uid = in.readString();
        nick = in.readString();
    }

    public static final Creator<EnterEventMessage> CREATOR = new Creator<EnterEventMessage>() {
        @Override
        public EnterEventMessage createFromParcel(Parcel in) {
            return new EnterEventMessage(in);
        }

        @Override
        public EnterEventMessage[] newArray(int size) {
            return new EnterEventMessage[size];
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
    public SenderType getSenderType() {
        return SenderType.SERVER;
    }

    @Override
    public String getAction() {
        return "ev_enter";
    }
}
