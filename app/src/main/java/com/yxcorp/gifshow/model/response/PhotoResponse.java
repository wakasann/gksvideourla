package com.yxcorp.gifshow.model.response;

import com.google.gson.annotations.SerializedName;
import com.yxcorp.gifshow.entity.QPhoto;
import com.yxcorp.gifshow.retrofit.c.CursorResponse;
import com.yxcorp.gifshow.retrofit.d.ApiTools;

import java.util.List;

public class PhotoResponse implements CursorResponse<QPhoto> {

    @SerializedName("pcursor")
    private String mCursor;
    @SerializedName("photos")
    private List<QPhoto> mPhotos;


    public String getCursor() {
        return this.mCursor;
    }

    public List<QPhoto> getItems() {
        return this.mPhotos;
    }

    public boolean hasMore() {
        return ApiTools.a();
    }

}
