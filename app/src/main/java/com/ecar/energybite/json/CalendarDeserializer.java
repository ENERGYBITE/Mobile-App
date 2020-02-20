package com.ecar.energybite.json;

import com.ecar.energybite.util.DateUtility;
import com.ecar.energybite.util.StringUtility;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.util.Calendar;

public class CalendarDeserializer extends JsonDeserializer<Calendar> {

    @Override
    public Calendar deserialize(JsonParser jsonparser, DeserializationContext deserializationcontext)
            throws IOException, JsonProcessingException {
        String date = StringUtility.trimAndEmptyIsNull (jsonparser.getText());
        if (StringUtility.isNonEmpty(date)) {
            return DateUtility.getParsedDateAsCalendar(date);
        }
        else {
            return null;
        }
    }



}
