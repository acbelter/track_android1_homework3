package com.android1.homework3.msg.request;

import android.os.Parcel;

import com.android1.homework3.msg.BaseMessage;
import com.android1.homework3.msg.SenderType;

/*{
    "action":"auth",
    "data":{
        "login":"MY_LOGIN",
        "pass":"MD5_FROM_PASS"
    }
}*/
public class AuthRequestMessage implements BaseMessage {
    public String login;
    public String pass;

    protected AuthRequestMessage(Parcel in) {
        login = in.readString();
        pass = in.readString();
    }

    public static final Creator<AuthRequestMessage> CREATOR = new Creator<AuthRequestMessage>() {
        @Override
        public AuthRequestMessage createFromParcel(Parcel in) {
            return new AuthRequestMessage(in);
        }

        @Override
        public AuthRequestMessage[] newArray(int size) {
            return new AuthRequestMessage[size];
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
    }

    @Override
    public SenderType getSenderType() {
        return SenderType.CLIENT;
    }

    @Override
    public String getAction() {
        return "auth";
    }
}
