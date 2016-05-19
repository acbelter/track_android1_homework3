package com.android1.homework3.msg.request;

import android.os.Parcel;

import com.android1.homework3.msg.BaseMessage;
import com.android1.homework3.msg.MessageAction;

/*{
    "action":"setuserinfo",
    "data": {
        "user_status":"STATUS_STRING",
        "cid":"MY_USER_ID",
        "sid":"MY_SESSION_ID"
    }
}*/
public class SetUserInfoRequestMessage implements BaseMessage {
    public String userStatus;
    public String cid;  // client id
    public String sid;  // session id

    public SetUserInfoRequestMessage() {
    }

    protected SetUserInfoRequestMessage(Parcel in) {
        userStatus = in.readString();
        cid = in.readString();
        sid = in.readString();
    }

    public static final Creator<SetUserInfoRequestMessage> CREATOR = new Creator<SetUserInfoRequestMessage>() {
        @Override
        public SetUserInfoRequestMessage createFromParcel(Parcel in) {
            return new SetUserInfoRequestMessage(in);
        }

        @Override
        public SetUserInfoRequestMessage[] newArray(int size) {
            return new SetUserInfoRequestMessage[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(userStatus);
        out.writeString(cid);
        out.writeString(sid);
    }

    @Override
    public String getAction() {
        return MessageAction.SET_USER_INFO;
    }
}
