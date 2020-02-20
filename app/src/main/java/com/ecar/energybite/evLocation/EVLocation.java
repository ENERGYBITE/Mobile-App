package com.ecar.energybite.evLocation;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by navin.ketu on 03-09-2019.
 */


import java.util.HashMap;
import java.util.Map;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "lattitude",
        "longitude"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class EVLocation implements Parcelable {

    @JsonProperty("lattitude")
    private Double lattitude;
    @JsonProperty("longitude")
    private Double longitude;
    @JsonProperty("stationId")
    private String stationId;
    @JsonProperty("stationname")
    private String stationname;
    @JsonProperty("address")
    private String address;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public EVLocation() {
    }

    @JsonProperty("lattitude")
    public Double getLat() {
        return lattitude;
    }

    @JsonProperty("lattitude")
    public void setLat(Double lattitude) {
        this.lattitude = lattitude;
    }

    @JsonProperty("longitude")
    public Double getLongitude() {
        return longitude;
    }

    @JsonProperty("longitude")
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getStationname() {
        return stationname;
    }

    public String getStationId() {
        return stationId;
    }

    public String getAddress() {
        return address;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public final static Creator<EVLocation> CREATOR = new Creator<EVLocation>() {


        @SuppressWarnings({
                "unchecked"
        })
        public EVLocation createFromParcel(Parcel in) {
            return new EVLocation(in);
        }

        public EVLocation[] newArray(int size) {
            return (new EVLocation[size]);
        }

    };

    protected EVLocation(Parcel in) {
        this.lattitude = ((Double) in.readValue((Double.class.getClassLoader())));
        this.longitude = ((Double) in.readValue((Double.class.getClassLoader())));
        this.stationId = ((String) in.readValue((String.class.getClassLoader())));
        this.stationname = ((String) in.readValue((String.class.getClassLoader())));
        this.address = ((String) in.readValue((String.class.getClassLoader())));
        this.additionalProperties = ((Map<String, Object>) in.readValue((Map.class.getClassLoader())));
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(lattitude);
        dest.writeValue(longitude);
        dest.writeValue(stationId);
        dest.writeValue(stationname);
        dest.writeValue(address);
        dest.writeValue(additionalProperties);
    }

    public int describeContents() {
        return 0;
    }

}
