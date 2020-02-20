package com.ecar.energybite.eVehicle;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by navin.ketu on 07-09-2019.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ChargerStation implements Parcelable {
    public ChargerStation() {
    }

    @JsonProperty("chargerId")
    private int chargerId;

    @JsonProperty("chargerName")
    private String chargerName;

    @JsonProperty("chargerType")
    private String chargerType;

    @JsonProperty("category")
    private String category;

    @JsonProperty("outputType")
    private String outputType;

    @JsonProperty("ratedVoltages")
    private String ratedVoltages;

    @JsonProperty("number_of_chargingpoints")
    private int number_of_chargingpoints;

    @JsonProperty("chargerCapacity")
    private Double chargerCapacity;

    @JsonProperty("connector")
    private String connector;

    @JsonProperty("status")
    private String status;

    @JsonProperty("stationId")
    private String stationId;

    @JsonProperty("chargerPort")
    private List<ChargerPort> chargerPorts;

    public int getChargerId() {
        return chargerId;
    }

    public String getChargerName() {
        return chargerName;
    }

    public String getChargerType() {
        return chargerType;
    }

    public String getCategory() {
        return category;
    }

    public String getOutputType() {
        return outputType;
    }

    public String getRatedVoltages() {
        return ratedVoltages;
    }

    public int getNumber_of_chargingpoints() {
        return number_of_chargingpoints;
    }

    public Double getChargerCapacity() {
        return chargerCapacity;
    }

    public String getConnector() {
        return connector;
    }

    public String getStatus() {
        return status;
    }

    public String getStationId() {
        return stationId;
    }

    public List<ChargerPort> getChargerPorts() {
        return this.chargerPorts;
    }

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

    protected ChargerStation(Parcel in) {
        this.chargerId = in.readInt ();
        this.chargerName = ((String) in.readValue((String.class.getClassLoader())));
        this.chargerType = ((String) in.readValue((String.class.getClassLoader())));
        this.category = ((String) in.readValue((String.class.getClassLoader())));
        this.outputType = ((String) in.readValue((String.class.getClassLoader())));
        this.ratedVoltages = ((String) in.readValue((String.class.getClassLoader())));
        this.number_of_chargingpoints = in.readInt ();
        this.chargerCapacity = ((Double) in.readValue((Double.class.getClassLoader())));
        this.connector = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(chargerId);
        dest.writeValue(chargerName);
        dest.writeValue(chargerType);
        dest.writeValue(category);
        dest.writeValue(outputType);
        dest.writeValue(ratedVoltages);
        dest.writeInt(number_of_chargingpoints);
        dest.writeDouble(chargerCapacity);
        dest.writeValue(connector);
        dest.writeValue(status);
    }

    public int describeContents() {
        return 0;
    }
}
