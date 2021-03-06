package com.android1.homework3.msg.response;

import android.os.Parcel;

import com.android1.homework3.msg.BaseMessage;
import com.android1.homework3.msg.MessageAction;
import com.android1.homework3.msg.Status;

/*{
    "action":"createchannel",
    "data":{
        "status":[0-9]+,
        "error":"TEXT_OF_ERROR",
        "chid":"CHANNEL_ID"
    }
}*/
public class CreateChannelResponseMessage implements BaseMessage {
    public Status status;
    public String error;
    public String chid;

    public CreateChannelResponseMessage() {
    }

    protected CreateChannelResponseMessage(Parcel in) {
        status = Status.values()[in.readInt()];
        error = in.readString();
        chid = in.readString();
    }

    public static final Creator<CreateChannelResponseMessage> CREATOR = new Creator<CreateChannelResponseMessage>() {
        @Override
        public CreateChannelResponseMessage createFromParcel(Parcel in) {
            return new CreateChannelResponseMessage(in);
        }

        @Override
        public CreateChannelResponseMessage[] newArray(int size) {
            return new CreateChannelResponseMessage[size];
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
        out.writeString(chid);
    }

    @Override
    public String getAction() {
        return MessageAction.CREATE_CHANNEL;
    }
}
