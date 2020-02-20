package com.ecar.energybite.eVehicle;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by navin.ketu on 07-09-2019.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ChargerPort implements Parcelable {

    public ChargerPort() {
    }

    @JsonProperty("seqNumber")
    private int seqNumber;

    @JsonProperty("identificationNumber")
    private String identificationNumber;

    @JsonProperty("qrCodeFilePath")
    private String qrCodeFilePath;

    @JsonProperty("isBusy")
    private boolean isBusy;

    public final static Creator<ChargerStation> CREATOR = new Creator<ChargerStation>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ChargerStation createFromParcel(Parcel in) {
            return new ChargerStation(in);
        }

        public ChargerStation[] newArray(int size) {
            return (new ChargerStation[size]);
        }

    };

    protected ChargerPort(Parcel in) {
//
    }

    public void writeToParcel(Parcel dest, int flags) {
    }

    public int describeContents() {
        return 0;
    }
}
