package com.ecar.energybite;

import android.os.Parcel;
import android.os.Parcelable;

import com.ecar.energybite.booking.BookingModel;
import com.ecar.energybite.http.EVHttpResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;
/**
 * Created by navin.ketu on 07-09-2019.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingListResponse extends EVHttpResponse implements Parcelable, Serializable {

    @JsonProperty("result")
    private List<BookingModel> bookingModels;

    public List<BookingModel> getBookingModels() {
        return bookingModels;
    }

    public BookingListResponse() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public BookingListResponse(Parcel in) {
    }

    public static final Creator<BookingListResponse> CREATOR = new Creator<BookingListResponse>() {
        @Override
        public BookingListResponse createFromParcel(Parcel source) {
            return new BookingListResponse(source);
        }

        @Override
        public BookingListResponse[] newArray(int size) {
            return new BookingListResponse[size];
        }
    };
}
