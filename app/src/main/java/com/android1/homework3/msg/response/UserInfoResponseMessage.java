package com.android1.homework3.msg.response;

import android.os.Parcel;

import com.android1.homework3.msg.BaseMessage;
import com.android1.homework3.msg.MessageAction;
import com.android1.homework3.msg.Status;

/*{
    "action":"userinfo",
    "data":{
        "status":[0-9]+,
        "error":"TEXT_OF_ERROR",
        "nick":"NICKNAME",
        "user_status":"STATUS_STRING",
    }
}*/
public class UserInfoResponseMessage implements BaseMessage {
    public Status status;
    public String error;
    public String nick;
    public String userStatus;

    public UserInfoResponseMessage() {
    }

    protected UserInfoResponseMessage(Parcel in) {
        status = Status.values()[in.readInt()];
        error = in.readString();
        nick = in.readString();
        userStatus = in.readString();
    }

    public static final Creator<UserInfoResponseMessage> CREATOR = new Creator<UserInfoResponseMessage>() {
        @Override
        public UserInfoResponseMessage createFromParcel(Parcel in) {
            return new UserInfoResponseMessage(in);
        }

        @Override
        public UserInfoResponseMessage[] newArray(int size) {
            return new UserInfoResponseMessage[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(status.ordinal());
        out.writeString(error);
        out.writeString(nick);
        out.writeString(userStatus);
    }

    @Override
    public String getAction() {
        return MessageAction.USER_INFO;
    }
}
