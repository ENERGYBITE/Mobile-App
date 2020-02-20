package com.ecar.energybite.booking;

import android.os.Parcel;
import android.os.Parcelable;

import com.ecar.energybite.widget.IRecyclerItem;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingModel implements Parcelable, IRecyclerItem {

    @JsonProperty("chargerName")
    private String chargerName;

    @JsonProperty("chargingpoint")
    private String chargingpoint;

    @JsonProperty("userEVId")
    private String userEVId;

    @JsonProperty("discountcode")
    private String discountcode;

    @JsonProperty("bookingId")
    private String bookingId;

    @JsonProperty("start_time")
    private String startTime;

    @JsonProperty("status")
    private String status;

    @JsonProperty("amount")
    private String amount;

    @JsonProperty("startReading")
    private String startReading;

    @JsonProperty("stopReading")
    private String stopReading;

    @JsonProperty("unit")
    private String unit;

    @JsonProperty("price")
    private String price;

    @JsonProperty("stopTime")
    private String stopTime;

    @JsonProperty("paymentId")
    private String paymentId;

    public String getStartReading() {
        return startReading;
    }

    public String getStopReading() {
        return stopReading;
    }

    public String getUnit() {
        return unit;
    }

    public String getPrice() {
        return price;
    }

    public String getStopTime() {
        return stopTime;
    }

    public String getChargerName() {
        return chargerName;
    }

    public String getChargingpoint() {
        return chargingpoint;
    }

    public String getUserEVId() {
        return userEVId;
    }

    public String getDiscountcode() {
        return discountcode;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getStatus() {
        return status;
    }

    public String getAmount() {
        return amount;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setChargerName(String chargerName) {
        this.chargerName = chargerName;
    }

    public void setChargingpoint(String chargingpoint) {
        this.chargingpoint = chargingpoint;
    }

    public void setUserEVId(String userEVId) {
        this.userEVId = userEVId;
    }

    public void setDiscountcode(String discountcode) {
        this.discountcode = discountcode;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setStartReading(String startReading) {
        this.startReading = startReading;
    }

    public void setStopReading(String stopReading) {
        this.stopReading = stopReading;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setStopTime(String stopTime) {
        this.stopTime = stopTime;
    }

    public BookingStatus getBookingStatus(char status) {
        return BookingStatus.getBookingStatus(status);
    }

    public String getStartTimeInIST() {

        return "NA";
    }

    public BookingModel() {

    }

    public final static Creator<BookingModel> CREATOR = new Creator<BookingModel>() {


        @SuppressWarnings({
                "unchecked"
        })
        public BookingModel createFromParcel(Parcel in) {
            return new BookingModel(in);
        }

        public BookingModel[] newArray(int size) {
            return (new BookingModel[size]);
        }

    };

    protected BookingModel(Parcel in) {
        this.chargerName = ((String) in.readValue((String.class.getClassLoader())));
        this.chargingpoint = ((String) in.readValue((String.class.getClassLoader())));
        this.userEVId = ((String) in.readValue((String.class.getClassLoader())));
        this.bookingId = ((String) in.readValue((String.class.getClassLoader())));
        this.discountcode = ((String) in.readValue((String.class.getClassLoader())));
        this.startTime = ((String) in.readValue((String.class.getClassLoader())));
        this.stopTime = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.startReading = ((String) in.readValue((String.class.getClassLoader())));
        this.stopReading = ((String) in.readValue((String.class.getClassLoader())));
        this.unit = ((String) in.readValue((String.class.getClassLoader())));
        this.price = ((String) in.readValue((String.class.getClassLoader())));
        this.amount = ((String) in.readValue((String.class.getClassLoader())));
        this.paymentId = ((String) in.readValue((String.class.getClassLoader())));
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(chargerName);
        dest.writeValue(chargingpoint);
        dest.writeValue(userEVId);
        dest.writeValue(bookingId);
        dest.writeValue(discountcode);
        dest.writeValue(startTime);
        dest.writeValue(stopTime);
        dest.writeValue(status);
        dest.writeValue(startReading);
        dest.writeValue(stopReading);
        dest.writeValue(unit);
        dest.writeValue(price);
        dest.writeValue(amount);
        dest.writeValue(paymentId);
    }

    public int describeContents() {
        return 0;
    }

}
