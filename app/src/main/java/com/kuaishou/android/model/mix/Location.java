package com.kuaishou.android.model.mix;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import org.parceler.Parcel;

@Parcel
public class Location implements Serializable {
    private static final long serialVersionUID = -1;
    @SerializedName("latitude")
    public double latitude;
    @SerializedName("longitude")
    public double longitude;
    @SerializedName("address")
    public String mAddress;
    @SerializedName("city")
    public String mCity;
    @SerializedName("id")
    public long mId;
    @SerializedName("title")
    public String mTitle;

    public String getTitle() {
        return this.mTitle;
    }

    public String getCity() {
        return this.mCity;
    }

    public String getAddress() {
        return this.mAddress;
    }

    public long getId() {
        return this.mId;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }
}
