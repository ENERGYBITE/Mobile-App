package com.ecar.energybite.json;

import com.ecar.energybite.util.DateUtility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.android.gms.maps.model.LatLng;
import java.io.IOException;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;

public class JsonUtility {

    public static final String YES           = "Y";
    public static final String NO            = "N";
    private static ObjectMapper sObjectMapper = new ObjectMapper();

    public static String serialize(Boolean bool) {
        return bool == null ? "" : ((bool == Boolean.TRUE) ? YES : NO);
    }


    public static ObjectMapper defaultMapper() {
        return sObjectMapper;
    }

    public static ObjectMapper getJsonObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(MapperFeature.AUTO_DETECT_CREATORS, MapperFeature.AUTO_DETECT_FIELDS,
                MapperFeature.AUTO_DETECT_GETTERS, MapperFeature.AUTO_DETECT_IS_GETTERS);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        mapper.enable (DeserializationFeature.READ_ENUMS_USING_TO_STRING);
        SimpleModule booleanModule = new SimpleModule("BooleanModule", new Version(1, 0, 0, null, null, null))
                .addDeserializer(Boolean.class, new BooleanDeserializer<Boolean>())
                .addDeserializer(Boolean.TYPE, new BooleanDeserializer<Boolean>());
        mapper.registerModule(booleanModule);

        SimpleModule calendarModule = new SimpleModule("CalendarModule", new Version(1, 0, 0, null, null, null))
                .addDeserializer(Calendar.class, new CalendarDeserializer());
        mapper.registerModule(calendarModule);

        SimpleModule latLangModule = new SimpleModule("LatLangModule", new Version(1, 0, 0, null, null, null))
                .addDeserializer (LatLng.class, new LatLangDeserializer());
        mapper.registerModule(latLangModule);
        return mapper;
    }

    private static class BooleanDeserializer<T extends Object> extends JsonDeserializer<Boolean> {
        final protected Class< ? > _valueClass = Boolean.class;

        @Override
        public Boolean deserialize(JsonParser jp, DeserializationContext ctxt)
                throws IOException, JsonProcessingException {
            // TODO Auto-generated method stub
            return _parseBooleanPrimitive2(jp, ctxt);
        }

        protected final boolean _parseBooleanPrimitive2(JsonParser jp, DeserializationContext ctxt)
                throws IOException, JsonProcessingException {
            JsonToken t = jp.getCurrentToken ();
            if (t == JsonToken.VALUE_TRUE) {
                return true;
            }
            if (t == JsonToken.VALUE_FALSE) {
                return false;
            }
            if (t == JsonToken.VALUE_NULL) {
                return false;
            }
            if (t == JsonToken.VALUE_NUMBER_INT) {
                return (jp.getIntValue() != 0);
            }
            if (t == JsonToken.VALUE_STRING) {
                String text = jp.getText().trim();
                if ("true".equals(text)) {
                    return true;
                }
                if ("false".equals(text) || text.length() == 0) {
                    return Boolean.FALSE;
                }

                if ("n".equals(text) || text.length() == 0) {
                    return Boolean.FALSE;
                }

                if ("y".equals(text)) {
                    return Boolean.TRUE;
                }

                if ("N".equals(text) || text.length() == 0) {
                    return Boolean.FALSE;
                }

                if ("Y".equals(text)) {
                    return Boolean.TRUE;
                }
                throw ctxt.weirdStringException(text, _valueClass, "only \"true\" or \"false\" recognized");
            }
            // Otherwise, no can do:
            throw ctxt.mappingException(_valueClass);
        }
    }

    /*
     * public static <T extends Enum> T getEnumValueWithCharId(JsonNode
     * jsonNode, T enumClass, T defaultValue) { if(jsonNode == null) { return
     * defaultValue; } for(T enumObj : T) return defaultValue; }
     */
    public static char getCharValue(JsonNode jsonNode, char defaultVal) {
        if (jsonNode == null) {
            return defaultVal;
        }
        return jsonNode.textValue().trim().charAt(0);
    }

    public static Calendar getDateValue(JsonNode jsonNode) {
        if (jsonNode == null) {
            return null;
        }
        String dateString = jsonNode.textValue();
        if (dateString.length() < 10) {
            return null;
        } else if (dateString.length() == 10) {
            Date date = null;
            try {
                date = DateUtility.parseDateInDDMMYYYY (dateString);
                date = DateUtility.getDateWithZeroTimefields (date);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return DateUtility.dateToCalendar (date);
        } else {
            Date date = DateUtility.parseDateInDDMYYYYHHMMNullIfError (dateString);
            return DateUtility.dateToCalendar (date);
        }

    }

    public static int getIntValueWithDefault(JsonNode jsonNode, int defaultVal) {
        if (jsonNode == null) {
            return defaultVal;
        }
        try {
            return jsonNode.asInt();
        } catch (Exception e) {
            return defaultVal;
        }
    }

    public static String getStringValueWithDefault(JsonNode jsonNode, String defaultVal) {
        if (jsonNode == null) {
            return defaultVal;
        }
        return jsonNode.textValue();
    }

    public static boolean getBooleanValue(JsonNode jsonNode) {
        if (jsonNode == null) {
            return false;
        }
        String val = jsonNode.textValue();
        return val.equalsIgnoreCase(YES) ? true : false;
    }

    public static boolean getBooleanValueWithDefault(JsonNode jsonNode, boolean defaultVal) {
        if (jsonNode == null) {
            return defaultVal;
        }
        String val = jsonNode.textValue();
        if(val == null){
            return defaultVal;
        }
        return val.equalsIgnoreCase(YES) ? true : (val.equalsIgnoreCase(NO) ? false : defaultVal);
    }

    public static Currency getCurrencyValue(JsonNode jsonNode) {
        if (jsonNode == null) {
            return Currency.getInstance("INR");
        }
        String curr = jsonNode.textValue();
        return Currency.getInstance(curr);
    }

    public static <T> T parseJson(JsonNode node, Class<T> clazz) {
        try {
            return JsonUtility.defaultMapper ().treeToValue (node, clazz);
        } catch (IOException e) {
            // Log.e(TAG, "Failed to parse JSON entity " +
            // clazz.getSimpleName(), e);
            throw new RuntimeException(e);
        }
    }

    public static Date getDateValueFromLongValue(JsonNode modificationTSNode) {
        Date dateVal = null;
        try {
            if (modificationTSNode.textValue() != null && modificationTSNode.textValue().trim().length() > 0) {
                long ts = Long.parseLong(modificationTSNode.textValue());
                dateVal = new Date(ts);
            }
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        return dateVal;
    }

    public static String parseRegexString(JsonNode node) {
        if (node != null) {
            String val = node.asText();
            String regex = "\\\\";
            String toReplace = "\\";
            while (val.indexOf(regex) >= 0) {
                val = val.replace(regex, toReplace);
            }
            return val;
        }
        return null;
    }
}
