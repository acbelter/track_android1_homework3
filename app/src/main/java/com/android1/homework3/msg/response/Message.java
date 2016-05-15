package com.android1.homework3.msg.response;

import android.os.Parcel;
import android.os.Parcelable;

/*{
    "mid":"MESSAGE_ID",
    "from":"USER_ID",
    "nick":"USERS_NICKNAME"
    "body":"TEXT_OF_MESSAGE",
    "time":UNIXTIMESTAMP_OF_MESSAGE,
}*/
public class Message implements Parcelable {
    public String mid;  // message id
    public String from; // user id
    public String nick;
    public String body;
    public long time;

    protected Message(Parcel in) {
        mid = in.readString();
        from = in.readString();
        nick = in.readString();
        body = in.readString();
        time = in.readLong();
    }

    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(mid);
        out.writeString(from);
        out.writeString(nick);
        out.writeString(body);
        out.writeLong(time);
    }
}
