package com.ecar.energybite.json;

import android.util.Log;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;

/**
 * Created by ravish.garg on 11/1/2017.
 */

public class LatLangDeserializer extends JsonDeserializer<LatLng> {


    @Override
    public LatLng deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        try {
            double latitude  = 0;
            double longitude = 0;
            if(p.getCurrentToken() == JsonToken.START_OBJECT) {
                if (p.nextFieldName().equalsIgnoreCase("Latitude")) {
                    p.nextToken();
                    latitude = (Double)p.getNumberValue();
                }
                if (p.nextFieldName().equalsIgnoreCase("Longitude")) {
                    p.nextToken();
                    longitude = (Double)p.getNumberValue();

                }
            }
//            while(p.getCurrentToken() != JsonToken.END_OBJECT) {
                p.nextToken();
//            }

            return new LatLng(latitude,longitude);

        }catch (Exception e){
            Log.e(LatLangDeserializer.class.toString(),e.toString());
        }

        return null;
    }
}
