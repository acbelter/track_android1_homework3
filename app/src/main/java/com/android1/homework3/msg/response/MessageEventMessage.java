package com.android1.homework3.msg.response;

import android.os.Parcel;

import com.android1.homework3.msg.BaseMessage;
import com.android1.homework3.msg.MessageAction;

/*{
    "action":"ev_message",
    "data":{
        "chid":"CHANNEL_ID",
        "from":"USER_ID",
        "nick":"NICKNAME",
        "body":"TEXT_OF_MESSAGE"
    }
}*/
public class MessageEventMessage implements BaseMessage {
    public String chid; // channel id
    public String from;  // user id
    public String nick;
    public String body;

    public MessageEventMessage() {
    }

    protected MessageEventMessage(Parcel in) {
        chid = in.readString();
        from = in.readString();
        nick = in.readString();
        body = in.readString();
    }

    public static final Creator<MessageEventMessage> CREATOR = new Creator<MessageEventMessage>() {
        @Override
        public MessageEventMessage createFromParcel(Parcel in) {
            return new MessageEventMessage(in);
        }

        @Override
        public MessageEventMessage[] newArray(int size) {
            return new MessageEventMessage[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(chid);
        out.writeString(from);
        out.writeString(nick);
        out.writeString(body);
    }

    @Override
    public String getAction() {
        return MessageAction.EVENT_MESSAGE;
    }
}
