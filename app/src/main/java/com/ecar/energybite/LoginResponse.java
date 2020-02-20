package com.ecar.energybite;

import android.os.Parcel;
import android.os.Parcelable;

import com.ecar.energybite.http.EVHttpResponse;
import com.ecar.energybite.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by navin.ketu on 03-09-2019.
 */

public class LoginResponse extends EVHttpResponse implements Parcelable, Serializable {

    private static final long serialVersionUID = 3920940778100554028L;
    private static final String TAG = LoginResponse.class.getName();

    @JsonProperty("result")
    public User user;

    @JsonProperty("username")
    private String username;
    @JsonProperty("password")
    private String password;

    public LoginResponse() {

    }
    public LoginResponse(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public User getUser(){
        return this.user;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.user, flags);
    }

    public LoginResponse(Parcel in) {
        this.user = in.readParcelable(User.class.getClassLoader());
    }

    public static final Creator<LoginResponse> CREATOR = new Creator<LoginResponse>() {
        @Override
        public LoginResponse createFromParcel(Parcel source) {
            return new LoginResponse(source);
        }

        @Override
        public LoginResponse[] newArray(int size) {
            return new LoginResponse[size];
        }
    };
}
