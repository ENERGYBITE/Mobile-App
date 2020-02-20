package com.ecar.energybite;

import android.os.Parcel;
import android.os.Parcelable;

import com.ecar.energybite.eVehicle.ChargerStation;
import com.ecar.energybite.http.EVHttpResponse;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by navin.ketu on 07-09-2019.
 */

public class ChargerStationResponse extends EVHttpResponse implements Parcelable, Serializable {

    @JsonProperty("result")
    public List<ChargerStation> chargerStations;

    @JsonProperty("stationId")
    private String stationId;

    public ChargerStationResponse(String stationId) {
        this.stationId = stationId;
    }

    public ChargerStationResponse() {

    }

    public String getStationId() {
        return this.stationId;
    }

    public List<ChargerStation> getChargerStations() {
        return this.chargerStations;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public ChargerStationResponse(Parcel in) {
    }

    public static final Creator<ChargerStationResponse> CREATOR = new Creator<ChargerStationResponse>() {
        @Override
        public ChargerStationResponse createFromParcel(Parcel source) {
            return new ChargerStationResponse(source);
        }

        @Override
        public ChargerStationResponse[] newArray(int size) {
            return new ChargerStationResponse[size];
        }
    };
}
