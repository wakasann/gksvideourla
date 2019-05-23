package com.kuaishou.android.model.mix;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import org.parceler.Parcel;

@Parcel
public class Distance implements Serializable {
    private static final long serialVersionUID = 8606192738710884187L;
    @SerializedName("distance")
    public double mDistance;
    @SerializedName("lat")
    public double mLatitude;
    @SerializedName("lon")
    public double mLongtitude;
    @SerializedName("name")
    public String mName;

}
