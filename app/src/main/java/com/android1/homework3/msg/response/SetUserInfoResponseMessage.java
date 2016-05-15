package com.android1.homework3.msg.response;

import android.os.Parcel;

import com.android1.homework3.msg.BaseMessage;
import com.android1.homework3.msg.SenderType;
import com.android1.homework3.msg.Status;

/*{
    "action":"setuserinfo",
    "data":{
        "status":[0-9]+,
        "error":"TEXT_OF_ERROR"
    }
}*/
public class SetUserInfoResponseMessage implements BaseMessage {
    public Status status;
    public String error;

    protected SetUserInfoResponseMessage(Parcel in) {
        status = Status.values()[in.readInt()];
        error = in.readString();
    }

    public static final Creator<SetUserInfoResponseMessage> CREATOR = new Creator<SetUserInfoResponseMessage>() {
        @Override
        public SetUserInfoResponseMessage createFromParcel(Parcel in) {
            return new SetUserInfoResponseMessage(in);
        }

        @Override
        public SetUserInfoResponseMessage[] newArray(int size) {
            return new SetUserInfoResponseMessage[size];
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
    public SenderType getSenderType() {
        return SenderType.SERVER;
    }

    @Override
    public String getAction() {
        return "setuserinfo";
    }
}
