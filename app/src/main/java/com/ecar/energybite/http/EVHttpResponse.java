package com.ecar.energybite.http;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by navin.ketu on 03-09-2019.
 */

public class EVHttpResponse {

    @JsonProperty("success")
    public String responseCode;
    @JsonProperty("message")
    public String responsemessage;

    public String getResponseCode() {
        return responseCode;
    }

    public String getResponsemessage() {
        return responsemessage;
    }
}
