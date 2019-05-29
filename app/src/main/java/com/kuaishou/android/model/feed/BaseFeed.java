package com.kuaishou.android.model.feed;

import android.support.annotation.Nullable;

import org.parceler.Parcel;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public abstract class BaseFeed   implements Serializable{

    private static final long serialVersionUID = 6620173751547753318L;

    @Nullable
    public abstract String getId();


}
