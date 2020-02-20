package com.ecar.energybite;

import android.os.Parcel;
import android.os.Parcelable;

import com.ecar.energybite.eVehicle.EVTemplate;
import com.ecar.energybite.http.EVHttpResponse;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by navin.ketu on 03-09-2019.
 */

public class EVTemplateResponse extends EVHttpResponse implements Parcelable, Serializable {

    private static final long serialVersionUID = 3920940778100554029L;
    private static final String TAG = EVTemplateResponse.class.getName();

    @JsonProperty("result")
    public List<EVTemplate> evTemplates;

    public EVTemplateResponse() {

    }

    public List<EVTemplate> getEVTemplates() {
        return this.evTemplates;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
//        dest.write(this.evLocations, flags);
    }

    public EVTemplateResponse(Parcel in) {
//        this.evLocations = in.readParcelable(User.class.getClassLoader());
    }

    public static final Creator<EVTemplateResponse> CREATOR = new Creator<EVTemplateResponse>() {
        @Override
        public EVTemplateResponse createFromParcel(Parcel source) {
            return new EVTemplateResponse(source);
        }

        @Override
        public EVTemplateResponse[] newArray(int size) {
            return new EVTemplateResponse[size];
        }
    };
}
