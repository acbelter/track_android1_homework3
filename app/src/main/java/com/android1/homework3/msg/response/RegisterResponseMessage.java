package com.android1.homework3.msg.response;

import android.os.Parcel;

import com.android1.homework3.msg.BaseMessage;
import com.android1.homework3.msg.MessageAction;
import com.android1.homework3.msg.Status;

/*{
    "action":"register",
    "data":{
        "status":[0-9]+,
        "error":"TEXT_OF_ERROR"
    }
}*/
public class RegisterResponseMessage implements BaseMessage {
    public Status status;
    public String error;

    public RegisterResponseMessage() {
    }

    protected RegisterResponseMessage(Parcel in) {
        status = Status.values()[in.readInt()];
        error = in.readString();
    }

    public static final Creator<RegisterResponseMessage> CREATOR = new Creator<RegisterResponseMessage>() {
        @Override
        public RegisterResponseMessage createFromParcel(Parcel in) {
            return new RegisterResponseMessage(in);
        }

        @Override
        public RegisterResponseMessage[] newArray(int size) {
            return new RegisterResponseMessage[size];
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
    }

    @Override
    public String getAction() {
        return MessageAction.REGISTER;
    }
}
