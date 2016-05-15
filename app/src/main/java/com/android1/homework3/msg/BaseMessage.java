package com.android1.homework3.msg;

import android.os.Parcelable;

public interface BaseMessage extends Parcelable {
    SenderType getSenderType();
    String getAction();
}
