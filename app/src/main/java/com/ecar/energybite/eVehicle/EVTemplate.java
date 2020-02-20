package com.ecar.energybite.eVehicle;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by navin.ketu on 03-09-2019.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class EVTemplate implements Parcelable {

    @JsonProperty("evTemplateId")
    private int evTemplateId;
    @JsonProperty("make")
    private String make;
    @JsonProperty("model")
    private String model;

    public EVTemplate() {
    }

    public int getEvTemplateId() {
        return evTemplateId;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public final static Creator<EVTemplate> CREATOR = new Creator<EVTemplate>() {


        @SuppressWarnings({
                "unchecked"
        })
        public EVTemplate createFromParcel(Parcel in) {
            return new EVTemplate(in);
        }

        public EVTemplate[] newArray(int size) {
            return (new EVTemplate[size]);
        }

    };

    protected EVTemplate(Parcel in) {
        this.evTemplateId = in.readInt();
        this.make = ((String) in.readValue((String.class.getClassLoader())));
        this.model = ((String) in.readValue((String.class.getClassLoader())));
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(evTemplateId);
        dest.writeValue(make);
        dest.writeValue(model);
    }

    public int describeContents() {
        return 0;
    }

    public static List<Number> getEVYearList() {
        List<Number> evYear =new ArrayList<Number> (  );
        int currentYear = Calendar.getInstance ().get ( Calendar.YEAR );
        for(int i=currentYear; i>=2010;i--) {
            evYear.add ( i );
        }
        return evYear;
    }


    public static List<String> getEVColorList() {
        List<String> evcolors=new ArrayList<String>(  );
        evcolors.add ( "-" );
        evcolors.add ( "Red" );
        evcolors.add ( "Green" );
        evcolors.add ( "Black" );
        evcolors.add ( "White" );
        evcolors.add ( "Orange" );
        return evcolors;
    }

}
