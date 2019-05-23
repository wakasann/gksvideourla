package com.kuaishou.android.model.mix;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import org.parceler.Parcel;

@Parcel
public class QRecoTag implements Serializable {
    private static final long serialVersionUID = 8028584265950725036L;
    @SerializedName("id")
    public String mId;
    @SerializedName("name")
    public String mName;
}
