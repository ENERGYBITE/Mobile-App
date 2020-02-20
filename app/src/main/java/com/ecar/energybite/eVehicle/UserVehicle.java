package com.ecar.energybite.eVehicle;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by navin.ketu on 08-09-2019.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserVehicle implements Parcelable {
    public UserVehicle() {
    }

    @JsonProperty("userEVId")
    private int userEVId;

    @JsonProperty("username")
    private String userName;

    @JsonProperty("make")
    private String make;

    @JsonProperty("model")
    private String model;

    @JsonProperty("year")
    private int year;

    @JsonProperty("evRegNumber")
    private String evRegNumber;

    public int getUserEVId() {
        return userEVId;
    }

    public String getUserName() {
        return userName;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public String getEvRegNumber() {
        return evRegNumber;
    }

    public void setUserEVId(int userEVId) {
        this.userEVId = userEVId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setEvRegNumber(String evRegNumber) {
        this.evRegNumber = evRegNumber;
    }

    public final static Creator<UserVehicle> CREATOR = new Creator<UserVehicle>() {


        @SuppressWarnings({
                "unchecked"
        })
        public UserVehicle createFromParcel(Parcel in) {
            return new UserVehicle(in);
        }

        public UserVehicle[] newArray(int size) {
            return (new UserVehicle[size]);
        }

    };

    protected UserVehicle(Parcel in) {
        this.userEVId = in.readInt();
        this.userName = ((String) in.readValue((String.class.getClassLoader())));
        this.make = ((String) in.readValue((String.class.getClassLoader())));
        this.model = ((String) in.readValue((String.class.getClassLoader())));
        this.year = in.readInt();
        this.evRegNumber = ((String) in.readValue((String.class.getClassLoader())));
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(userEVId);
        dest.writeValue(userName);
        dest.writeValue(make);
        dest.writeValue(model);
        dest.writeInt(year);
        dest.writeValue(evRegNumber);
    }

    public int describeContents() {
        return 0;
    }
}
