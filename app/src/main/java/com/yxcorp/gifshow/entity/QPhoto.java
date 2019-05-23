package com.yxcorp.gifshow.entity;

import com.kuaishou.android.feed.b.FeedExt;
import com.kuaishou.android.model.feed.BaseFeed;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class QPhoto implements  Serializable{
    public static final int CHANNEL_DEFAULT = 0;
    public static final int CHANNEL_FOLLOWING = 6;
    public static final int CHANNEL_HOT = 7;
    public static final int CHANNEL_LOCAL = 10;
    public static final int CHANNEL_PROFILE = 5;
    public static final int CHANNEL_SEARCH = 20;
    public static final int CHANNEL_SEARCH_PYMK = 21;
    public static final int ILLEGAL_POSITION = -1;
    public static final float MAX_ASPECT_RATIO = 1.7777778f;
    private static final long serialVersionUID = -9188926462089199605L;
    public boolean isChecked;
    public BaseFeed mEntity;

    public String getPhotoId() {
        return this.mEntity.getId();
    }

    public String getServerExpTag() {
        return "feed_photo|5246975051577396128|633919396|1_i/0_sp0";
    }


}
