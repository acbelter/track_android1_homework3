package com.android1.homework3.msg.response;

import android.os.Parcel;

import com.android1.homework3.msg.BaseMessage;
import com.android1.homework3.msg.MessageAction;

/*{
    "action":"welcome",
    "message": "WELCOME_TEXT",
    "time":UNIXTIMESTAMP
}*/
public class WelcomeMessage implements BaseMessage {
    public String message;
    public long time;

    public WelcomeMessage() {
    }

    protected WelcomeMessage(Parcel in) {
        message = in.readString();
        time = in.readLong();
    }

    public static final Creator<WelcomeMessage> CREATOR = new Creator<WelcomeMessage>() {
        @Override
        public WelcomeMessage createFromParcel(Parcel in) {
            return new WelcomeMessage(in);
        }

        @Override
        public WelcomeMessage[] newArray(int size) {
            return new WelcomeMessage[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(message);
        out.writeLong(time);
    }

    @Override
    public String getAction() {
        return MessageAction.WELCOME;
    }
}
