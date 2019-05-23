package com.kuaishou.android.model.mix;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.parceler.Parcel;

@Parcel
public class CommonMeta implements Mockable<CommonMeta>,Serializable{
    public static final int REAL_RELATION_TYPE_FRIEND = 1;
    public static final int RELATION_TYPE_FRIEND = 1;
    public static final int UNITS_METRIC = 0;
    public static final int Units_IMPERIAL = 1;
    private static final long serialVersionUID = 3107088071177803449L;

    @SerializedName("caption")
    public String mCaption;
    public int mColor;
    @SerializedName("timestamp")
    public long mCreated;
    public String mCurrentNetwork = "WIFI";
    public int mCurrentPosition = -1;
    public int mDirection;
    @SerializedName("display_reco_reason")
    public String mDisplayRecoReason;
    @SerializedName("location")
    public Distance mDistance = new Distance();
    @SerializedName("enablePaidQuestion")
    public boolean mEnablePaidQuestion;
    @SerializedName("exp_tag")
    public String mExpTag = "";
    @SerializedName("expectFreeTraffic")
    public boolean mExpectFreeTraffic;
    @SerializedName("fansTopDisplayStyle")
    public FansTopDisplayStyle mFansTopDisplayStyle;
    @SerializedName("forward_stats_params")
    public Map<String, String> mForwardStatsParams = new HashMap();
    @SerializedName("h")
    public int mHeight;
    @SerializedName("hosts")
    public List<String> mHosts = new ArrayList();
    @SerializedName("mId")
    public String mId;
    @SerializedName("llsid")
    public String mListLoadSequenceID;
    @SerializedName("poi")
    public Location mLocation;
    public String mLocationDistanceStr;
    public transient boolean mNeedRetryFreeTraffic;
    @SerializedName("position")
    public int mPosition;
    public transient boolean mProductsNeedBoostFansTop;
    public transient boolean mProfileTimeLine;
    @SerializedName("realRelationType")
    public int mRealRelationType;
    @SerializedName("reco_reason")
    public String mRecoReason;
    @SerializedName("recoTags")
    public List<QRecoTag> mRecoTags;
    @SerializedName("regionText")
    public String mRegionText;
    @SerializedName("relationType")
    public int mRelationType;
    @SerializedName("relationTypeText")
    public String mRelationTypeText;
    @SerializedName("serverExpTag")
    public String mServerExpTag;
    @SerializedName("share_info")
    public String mShareInfo;
    public boolean mShowed;
    @SerializedName("source")
    public String mSource = "";
    @SerializedName("time")
    public String mTime;
    public int mTopFeedIndex;
    @SerializedName("type")
    public int mType;
    @SerializedName("ussid")
    public String mUssId;
    public boolean mVerticalShowed;
    @SerializedName("w")
    public int mWidth;


    public void updateWithServer(CommonMeta commonMeta) {
        this.mTime = commonMeta.mTime;
        this.mDistance = commonMeta.mDistance;
        this.mExpTag = commonMeta.mExpTag;
        this.mServerExpTag = commonMeta.mServerExpTag;
        this.mLocation = commonMeta.mLocation;
        this.mListLoadSequenceID = commonMeta.mListLoadSequenceID;
        this.mDisplayRecoReason = commonMeta.mDisplayRecoReason;
        this.mCreated = commonMeta.mCreated;
        this.mFansTopDisplayStyle = commonMeta.mFansTopDisplayStyle;
        this.mCaption = commonMeta.mCaption;
    }

}
