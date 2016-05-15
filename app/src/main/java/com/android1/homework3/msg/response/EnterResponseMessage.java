package com.android1.homework3.msg.response;

import android.os.Parcel;

import com.android1.homework3.msg.BaseMessage;
import com.android1.homework3.msg.SenderType;
import com.android1.homework3.msg.Status;

import java.util.ArrayList;
import java.util.List;

/*{
    "action":"enter",
    "data":{
        "status":[0-9]+,
        "error":"TEXT_OF_ERROR",
        "users":[
            {
                "uid":"USER_ID",
                "nick":"NICKNAME",
            },...
        ],
        "last_msg": [
            {
                "mid":"MESSAGE_ID",
                "from":"USER_ID",
                "nick":"USERS_NICKNAME",
                "body":"TEXT_OF_MESSAGE",
                "time":UNIXTIMESTAMP_OF_MESSAGE,
            }, ...
        ]
    }
}*/
public class EnterResponseMessage implements BaseMessage {
    public Status status;
    public String error;
    public List<User> users;
    public List<Message> lastMsg;

    public EnterResponseMessage() {
        users = new ArrayList<>();
        lastMsg = new ArrayList<>();
    }

    protected EnterResponseMessage(Parcel in) {
        status = Status.values()[in.readInt()];
        error = in.readString();
        users = new ArrayList<>();
        in.readTypedList(users, User.CREATOR);
        lastMsg = new ArrayList<>();
        in.readTypedList(lastMsg, Message.CREATOR);
    }

    public static final Creator<EnterResponseMessage> CREATOR = new Creator<EnterResponseMessage>() {
        @Override
        public EnterResponseMessage createFromParcel(Parcel in) {
            return new EnterResponseMessage(in);
        }

        @Override
        public EnterResponseMessage[] newArray(int size) {
            return new EnterResponseMessage[size];
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
        out.writeTypedList(users);
        out.writeTypedList(lastMsg);
    }

    @Override
    public SenderType getSenderType() {
        return SenderType.SERVER;
    }

    @Override
    public String getAction() {
        return "enter";
    }
}
