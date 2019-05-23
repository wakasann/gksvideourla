package com.kuaishou.android.model.mix;

import com.google.gson.annotations.SerializedName;
//import com.yxcorp.utility.TextUtils;
import java.io.Serializable;
import java.util.List;

public class FansTopDisplayStyle implements Serializable {
    public static final int FANSTOP_STATUS_ADVERTISING = 2;
    public static final int FANSTOP_STATUS_COMPLETED = 3;
    public static final int FANSTOP_STATUS_UNDER_REVIEW = 1;
    private static final long serialVersionUID = -3038753522303012108L;
    @SerializedName("extData")
    public FansTopExtData mExtData;
    @SerializedName("recommendUsers")
    public List<FansTopRecommendUsers> mFansTopRecommendUsers;
    @SerializedName("showDeliveryIcon")
    public boolean mShowDeliveryIcon;

    public class FansTopExtData implements Serializable {
        private static final long serialVersionUID = -3672940487540162946L;
        @SerializedName("fans_top_comment_count")
        public String mFansTopCommentCount;
        @SerializedName("fans_top_like_count")
        public String mFansTopLikeCount;
        @SerializedName("fans_top_play_count")
        public String mFansTopPlayCount;
        @SerializedName("fans_top_status")
        public int mFansTopStatus;
        @SerializedName("fans_top_boost_running")
        public boolean mFanstopBoostRunning;
        @SerializedName("need_alert_for_operation")
        public boolean mNeedAlertForOperation;
        @SerializedName("supporter_style")
        public String mSupporter_style;
    }

    public class FansTopRecommendUsers implements Serializable {
        private static final long serialVersionUID = 6330920257207965344L;
        @SerializedName("user_id")
        public long mFansTopRecommendUserId;
    }

    public boolean shouldShowFansTopOwnnerStyle() {
        return this.mExtData != null && (this.mExtData.mFansTopStatus == 1 || this.mExtData.mFansTopStatus == 2 || this.mExtData.mFansTopStatus == 3);
    }

    public boolean shouldShowFansTopWatchIcon() {
        return this.mExtData != null && (this.mExtData.mFansTopStatus == 2 || (this.mExtData.mFansTopStatus == 3 && this.mExtData.mFanstopBoostRunning));
    }

    public boolean isFansTopBoostRunning() {
        return this.mExtData != null && this.mExtData.mFanstopBoostRunning;
    }

    public boolean isFansTopNeedAlertForOperation() {
        return this.mExtData != null && this.mExtData.mNeedAlertForOperation;
    }

    public long getFansTopCommentCount() {
//        if (this.mExtData == null || TextUtils.Reference(this.mExtData.mFansTopCommentCount)) {
//            return -1;
//        }
        return Long.parseLong(this.mExtData.mFansTopCommentCount);
    }

    public int getFansTopStatus() {
        return this.mExtData != null ? this.mExtData.mFansTopStatus : -1;
    }
}
