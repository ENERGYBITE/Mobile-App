package com.ecar.energybite;

import android.os.Parcel;
import android.os.Parcelable;

import com.ecar.energybite.http.EVHttpResponse;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;


/**
 * Created by navin.ketu on 03-09-2019.
 */

public class UserRegisterResponse extends EVHttpResponse implements Parcelable, Serializable {

    private static final long serialVersionUID = 3920940778100554028L;
    private static final String TAG = UserRegisterResponse.class.getName();

    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("pincode")
    private String pincode;
    @JsonProperty("password")
    private String password;
    @JsonProperty("address")
    private String address;
    @JsonProperty("city")
    private String city;
    @JsonProperty("state")
    private String state;
    @JsonProperty("email")
    private String email;
    @JsonProperty("username")
    private String username;

    public UserRegisterResponse() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

//    public User getUser() {
//        return this.user;
//    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(null, flags);
    }

    public UserRegisterResponse(Parcel in) {
//        this.user = in.readParcelable(User.class.getClassLoader());
    }

    public static final Creator<UserRegisterResponse> CREATOR = new Creator<UserRegisterResponse>() {
        @Override
        public UserRegisterResponse createFromParcel(Parcel source) {
            return new UserRegisterResponse(source);
        }

        @Override
        public UserRegisterResponse[] newArray(int size) {
            return new UserRegisterResponse[size];
        }
    };
}
