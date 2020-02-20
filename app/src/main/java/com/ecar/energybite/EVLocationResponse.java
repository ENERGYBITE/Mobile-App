package com.ecar.energybite;

import android.os.Parcel;
import android.os.Parcelable;

import com.ecar.energybite.evLocation.EVLocation;
import com.ecar.energybite.http.EVHttpResponse;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by navin.ketu on 03-09-2019.
 */

public class EVLocationResponse extends EVHttpResponse implements Parcelable, Serializable {

    private static final long serialVersionUID = 3920940778100554029L;
    private static final String TAG = EVLocationResponse.class.getName();

    @JsonProperty("result")
    public List<EVLocation> evLocations;

    @JsonProperty("longitude")
    private String longitude;
    @JsonProperty("lattitude")
    private String lattitude;

    public EVLocationResponse() {

    }

    public EVLocationResponse(String longitude, String lattitude) {
        this.longitude = longitude;
        this.lattitude = lattitude;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public String getLattitude() {
        return this.lattitude;
    }

    public List<EVLocation> getEVLocations() {
        return this.evLocations;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
//        dest.write(this.evLocations, flags);
    }

    public EVLocationResponse(Parcel in) {
//        this.evLocations = in.readParcelable(User.class.getClassLoader());
    }

    public static final Creator<EVLocationResponse> CREATOR = new Creator<EVLocationResponse>() {
        @Override
        public EVLocationResponse createFromParcel(Parcel source) {
            return new EVLocationResponse(source);
        }

        @Override
        public EVLocationResponse[] newArray(int size) {
            return new EVLocationResponse[size];
        }
    };
}
