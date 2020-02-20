package com.ecar.energybite.user;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by navin.ketu on 03-09-2019.
 */


@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Parcelable, Serializable {

    private static final long serialVersionUID = 3920940778100554027L;
    private static final String TAG = User.class.getName();

    public User() {

    }

    @JsonProperty("username")
    private String username;

    @JsonProperty("name")
    private String name;

    @JsonProperty("userEmail")
    private String userEmail;

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getUserEmail() {
        return userEmail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.username);
        dest.writeString(this.userEmail);
    }

    protected User(Parcel in) {
        this.name = in.readString();
        this.username = in.readString();
        this.userEmail = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
