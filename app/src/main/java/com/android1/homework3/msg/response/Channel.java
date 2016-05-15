package com.android1.homework3.msg.response;

import android.os.Parcel;
import android.os.Parcelable;

/*{
    "chid":"NEED_CHANNEL_ID",
    "name":"NAME_OF_CHANNEL",
    "descr":"DESCRIPTION_OF_CHANNEL",
    "online":ONLINE_NUM,
}*/
public class Channel implements Parcelable {
    public String chid; // channel id
    public String name;
    public String descr;
    public int online; // online users count

    protected Channel(Parcel in) {
        chid = in.readString();
        name = in.readString();
        descr = in.readString();
        online = in.readInt();
    }

    public static final Creator<Channel> CREATOR = new Creator<Channel>() {
        @Override
        public Channel createFromParcel(Parcel in) {
            return new Channel(in);
        }

        @Override
        public Channel[] newArray(int size) {
            return new Channel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(chid);
        out.writeString(name);
        out.writeString(descr);
        out.writeInt(online);
    }
}
