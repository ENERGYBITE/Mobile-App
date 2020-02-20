package com.ecar.energybite;

import android.os.Parcel;
import android.os.Parcelable;

import com.ecar.energybite.eVehicle.UserVehicle;
import com.ecar.energybite.http.EVHttpResponse;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
/**
 * Created by navin.ketu on 07-09-2019.
 */

public class AddVehicleResponse extends EVHttpResponse implements Parcelable, Serializable {

    @JsonProperty("result")
    public UserVehicle userVehicle;

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

    public AddVehicleResponse(String username) {
        this.username = username;
    }

    public AddVehicleResponse(String username, String make, String model, String year, String evRegNumber) {
        this.username = username;
        this.make = make;
        this.model = model;
        this.year = year;
        this.evRegNumber = evRegNumber;
    }

    public AddVehicleResponse() {

    }

    public String getUserName() {
        return this.username;
    }

    public UserVehicle getUserVehicle() {
        return this.userVehicle;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public AddVehicleResponse(Parcel in) {
    }

    public static final Creator<AddVehicleResponse> CREATOR = new Creator<AddVehicleResponse>() {
        @Override
        public AddVehicleResponse createFromParcel(Parcel source) {
            return new AddVehicleResponse(source);
        }

        @Override
        public AddVehicleResponse[] newArray(int size) {
            return new AddVehicleResponse[size];
        }
    };
}
