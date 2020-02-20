package com.ecar.energybite;

import android.os.Parcel;
import android.os.Parcelable;

import com.ecar.energybite.eVehicle.UserVehicle;
import com.ecar.energybite.http.EVHttpResponse;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;


/**
 * Created by navin.ketu on 07-09-2019.
 */

public class UserVehicleResponse extends EVHttpResponse implements Parcelable, Serializable {

    @JsonProperty("result")
    public List<UserVehicle> userVehicles;

    @JsonProperty("username")
    private String username;

    @JsonProperty("make")
    private String make;

    @JsonProperty("model")
    private String model;

    @JsonProperty("year")
    private String year;

    @JsonProperty("evRegNumber")
    private String evRegNumber;

    public UserVehicleResponse(String username) {
        this.username = username;
    }

    public UserVehicleResponse(String username, String make, String model, String year, String evRegNumber) {
        this.username = username;
        this.make = make;
        this.model = model;
        this.year = year;
        this.evRegNumber = evRegNumber;
    }

    public UserVehicleResponse() {

    }

    public String getUserName() {
        return this.username;
    }

    public List<UserVehicle> getUserVehicles() {
        return this.userVehicles;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public UserVehicleResponse(Parcel in) {
    }

    public static final Creator<UserVehicleResponse> CREATOR = new Creator<UserVehicleResponse>() {
        @Override
        public UserVehicleResponse createFromParcel(Parcel source) {
            return new UserVehicleResponse(source);
        }

        @Override
        public UserVehicleResponse[] newArray(int size) {
            return new UserVehicleResponse[size];
        }
    };
}
