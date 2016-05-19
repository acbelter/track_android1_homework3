package com.android1.homework3.msg.request;

import android.os.Parcel;

import com.android1.homework3.msg.BaseMessage;
import com.android1.homework3.msg.MessageAction;

/*{
    "action":"userinfo",
    "data": {
        "user":"USER_ID",
        "cid":"MY_USER_ID",
        "sid":"MY_SESSION_ID"
    }
}*/
public class UserInfoRequestMessage implements BaseMessage {
    public String user;
    public String cid;  // client id
    public String sid;  // session id

    public UserInfoRequestMessage() {
    }

    protected UserInfoRequestMessage(Parcel in) {
        user = in.readString();
        cid = in.readString();
        sid = in.readString();
    }

    public static final Creator<UserInfoRequestMessage> CREATOR = new Creator<UserInfoRequestMessage>() {
        @Override
        public UserInfoRequestMessage createFromParcel(Parcel in) {
            return new UserInfoRequestMessage(in);
        }

        @Override
        public UserInfoRequestMessage[] newArray(int size) {
            return new UserInfoRequestMessage[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(user);
        out.writeString(cid);
        out.writeString(sid);
    }

    @Override
    public String getAction() {
        return MessageAction.USER_INFO;
    }
}
