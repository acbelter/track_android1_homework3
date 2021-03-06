package com.android1.homework3.msg.request;

import android.os.Parcel;

import com.android1.homework3.msg.BaseMessage;
import com.android1.homework3.msg.MessageAction;

/*{
    "action":"register",
    "data":{
        "login":"MY_LOGIN",
        "pass":"MD5_FROM_PASS",
        "nick":"NICKNAME"
    }
}*/
public class RegisterRequestMessage implements BaseMessage {
    public String login;
    public String pass;
    public String nick;

    public RegisterRequestMessage() {
    }

    protected RegisterRequestMessage(Parcel in) {
        login = in.readString();
        pass = in.readString();
        nick = in.readString();
    }

    public static final Creator<RegisterRequestMessage> CREATOR = new Creator<RegisterRequestMessage>() {
        @Override
        public RegisterRequestMessage createFromParcel(Parcel in) {
            return new RegisterRequestMessage(in);
        }

        @Override
        public RegisterRequestMessage[] newArray(int size) {
            return new RegisterRequestMessage[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(login);
        out.writeString(pass);
        out.writeString(nick);
    }

    @Override
    public String getAction() {
        return MessageAction.REGISTER;
    }
}
