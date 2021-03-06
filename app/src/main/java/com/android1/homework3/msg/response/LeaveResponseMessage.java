package com.android1.homework3.msg.response;

import android.os.Parcel;

import com.android1.homework3.msg.BaseMessage;
import com.android1.homework3.msg.MessageAction;
import com.android1.homework3.msg.Status;

/*{
    "action":"leave",
    "data":{
        "status":[0-9]+,
        "error":"TEXT_OF_ERROR",
    }
}*/
public class LeaveResponseMessage implements BaseMessage {
    public Status status;
    public String error;

    public LeaveResponseMessage() {
    }

    protected LeaveResponseMessage(Parcel in) {
        status = Status.values()[in.readInt()];
        error = in.readString();
    }

    public static final Creator<LeaveResponseMessage> CREATOR = new Creator<LeaveResponseMessage>() {
        @Override
        public LeaveResponseMessage createFromParcel(Parcel in) {
            return new LeaveResponseMessage(in);
        }

        @Override
        public LeaveResponseMessage[] newArray(int size) {
            return new LeaveResponseMessage[size];
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
        return MessageAction.LEAVE;
    }
}
