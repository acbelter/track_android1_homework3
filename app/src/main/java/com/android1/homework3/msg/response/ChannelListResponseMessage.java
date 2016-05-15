package com.android1.homework3.msg.response;

import android.os.Parcel;

import com.android1.homework3.msg.BaseMessage;
import com.android1.homework3.msg.SenderType;
import com.android1.homework3.msg.Status;

import java.util.ArrayList;
import java.util.List;

/*{
    "action":"channellist",
    "data":{
        "status":[0-9]+,
        "error":"TEXT_OF_ERROR",
        "channels":[
            {
            "chid":"NEED_CHANNEL_ID",
            "name":"NAME_OF_CHANNEL",
            "descr":"DESCRIPTION_OF_CHANNEL",
            "online":ONLINE_NUM,
            }, ....
        ]
    }
}*/
public class ChannelListResponseMessage implements BaseMessage {
    public Status status;
    public String error;
    public List<Channel> channels;

    public ChannelListResponseMessage() {
        channels = new ArrayList<>();
    }

    protected ChannelListResponseMessage(Parcel in) {
        status = Status.values()[in.readInt()];
        error = in.readString();
        channels = new ArrayList<>();
        in.readTypedList(channels, Channel.CREATOR);
    }

    public static final Creator<ChannelListResponseMessage> CREATOR = new Creator<ChannelListResponseMessage>() {
        @Override
        public ChannelListResponseMessage createFromParcel(Parcel in) {
            return new ChannelListResponseMessage(in);
        }

        @Override
        public ChannelListResponseMessage[] newArray(int size) {
            return new ChannelListResponseMessage[size];
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
        out.writeTypedList(channels);
    }

    @Override
    public SenderType getSenderType() {
        return SenderType.SERVER;
    }

    @Override
    public String getAction() {
        return "channellist";
    }
}
