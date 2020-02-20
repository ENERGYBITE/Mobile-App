package com.ecar.energybite.booking;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentModel implements Parcelable {

    @JsonProperty("discountCode")
    private String discountCode;

    @JsonProperty("paymentId")
    private String paymentId;

    public String getDiscountCode() {
        return discountCode;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public PaymentModel() {

    }

    public PaymentModel(String discountCode, String paymentId) {
        this.discountCode = discountCode;
        this.paymentId = paymentId;
    }

    public final static Creator<PaymentModel> CREATOR = new Creator<PaymentModel>() {


        @SuppressWarnings({
                "unchecked"
        })
        public PaymentModel createFromParcel(Parcel in) {
            return new PaymentModel(in);
        }

        public PaymentModel[] newArray(int size) {
            return (new PaymentModel[size]);
        }

    };

    protected PaymentModel(Parcel in) {
        this.discountCode = ((String) in.readValue((String.class.getClassLoader())));
        this.paymentId = ((String) in.readValue((String.class.getClassLoader())));
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(discountCode);
        dest.writeValue(paymentId);
    }

    public int describeContents() {
        return 0;
    }

}
