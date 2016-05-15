package com.android1.homework3.msg.request;

import android.os.Parcel;

import com.android1.homework3.msg.BaseMessage;
import com.android1.homework3.msg.SenderType;

/*{
    "action":"createchannel",
    "data":{
        "cid":"MY_USER_ID",
        "sid":"MY_SESSION_ID",
        "name":"NAME_OF_CHANNEL",
        "descr":"DESCRIPTION_OF_CHANNEL"
    }
}*/
public class CreateChannelRequestMessage implements BaseMessage {
    public String cid;  // client id
    public String sid;  // session id
    public String name;
    public String descr;

    protected CreateChannelRequestMessage(Parcel in) {
        cid = in.readString();
        sid = in.readString();
        name = in.readString();
        descr = in.readString();
    }

    public static final Creator<CreateChannelRequestMessage> CREATOR = new Creator<CreateChannelRequestMessage>() {
        @Override
        public CreateChannelRequestMessage createFromParcel(Parcel in) {
            return new CreateChannelRequestMessage(in);
        }

        @Override
        public CreateChannelRequestMessage[] newArray(int size) {
            return new CreateChannelRequestMessage[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(cid);
        out.writeString(sid);
        out.writeString(name);
        out.writeString(descr);
    }

    @Override
    public SenderType getSenderType() {
        return SenderType.CLIENT;
    }

    @Override
    public String getAction() {
        return "createchannel";
    }
}
