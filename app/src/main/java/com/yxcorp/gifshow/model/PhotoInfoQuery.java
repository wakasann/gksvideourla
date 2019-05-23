package com.yxcorp.gifshow.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class PhotoInfoQuery implements Serializable {
    private static final long serialVersionUID = 3839423431547401709L;
    @SerializedName("photoId")
    public String mPhotoId;
    @SerializedName("serverExpTag")
    public String mServerExpTag;

    public PhotoInfoQuery(String str, String str2) {
        this.mPhotoId = str;
        this.mServerExpTag = str2;
    }
}