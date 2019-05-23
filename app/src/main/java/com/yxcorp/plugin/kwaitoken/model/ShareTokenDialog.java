package com.yxcorp.plugin.kwaitoken.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class ShareTokenDialog implements Serializable {
    private static final long serialVersionUID = 1870533684415873508L;
    public String mAction;
    public String mActionUri;
    public int mAtlasType;
    public int mAvatarPlaceHolderImage;
    public String mAvatarUrl;
    public Objects[] mAvatarUrls;
    public int mCoverPlaceHolderColor;
    public String mCoverUrl;
    public Objects[] mCoverUrls;
    public String mDescription;
    public String mErrorMessage;
    public boolean mIsRich;
    public int mPhotoCount;
    public List<Objects[]> mPhotoCovers;
    public int mPhotoType;
    public String mPoiAddress;
    public List<Objects[]> mPoiCovers;
    public String mPoiTitle;
    public String mSource;
    public String mSourceUri;
    public String mTagName;
    public int mTagType;
    public String mTagTypeStr;
    public String mTitle;
    public int mType;
}