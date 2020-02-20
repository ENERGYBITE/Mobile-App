package com.ecar.energybite;

import android.os.Parcel;
import android.os.Parcelable;

import com.ecar.energybite.booking.BookingModel;
import com.ecar.energybite.http.EVHttpResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by navin.ketu on 07-09-2019.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingResponse extends EVHttpResponse implements Parcelable, Serializable {


    @JsonProperty("chargerName")
    private String chargerName;

    @JsonProperty("chargingpoint")
    private String chargingpoint;

    @JsonProperty("username")
    private String username;

    @JsonProperty("paymentId")
    private String paymentId;

    @JsonProperty("discountCode")
    private String discountCode;


    @JsonProperty("result")
    private BookingModel bookingModel;

    public BookingModel getBookingModel() {
        return bookingModel;
    }

    public BookingResponse() {

    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public BookingResponse(String chargerName, String chargingpoint) {
        this.chargerName = chargerName;
        this.chargingpoint = chargingpoint;
        this.username = EVData.getUser().getUsername();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public BookingResponse(Parcel in) {
    }

    public static final Creator<BookingResponse> CREATOR = new Creator<BookingResponse>() {
        @Override
        public BookingResponse createFromParcel(Parcel source) {
            return new BookingResponse(source);
        }

        @Override
        public BookingResponse[] newArray(int size) {
            return new BookingResponse[size];
        }
    };
}
