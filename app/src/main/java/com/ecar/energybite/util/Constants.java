package com.ecar.energybite.util;


public class Constants {
    public static class GCM {
        public static final String SHARED_PREF_FILE      = "Energy_Byte_GCM";
        public static final String GCM_REG_VERSION = "RegistrationVersion";
        public static final String GCM_REG_ID = "RegistrationId";
        public static final String HAS_NOTIFICATION = "HasNotification";
        public static final String CART_BOOKING_ID = "cartId";
        public static final String TRIP_BOOKING_ID = "tripId";
        public static final String GCM_MESSAGE_TYPE = "type";
        public static final String GCM_VALID_USERS = "validUsers";
        public static final String SENT_TOKEN_TO_SERVER = "SentGCMTokenToServer";
        public static final String REGISTRATION_COMPLETE = "GCMRegistrationCompleted";
    }


    public static class LOCATION {
        public static final String SEARCH_HINT = "LOC_SEARCH_HINT";
        public static final String TRAVEL_TYPE = "LOC_TRAVEL_TYPE";
        public static final String LOCATION_TYPE = "LOC_TYPE";
        public static final String LOC_PRODUCT = "LOC_PRODUCT";
    }

    public static class LOGIN {
        public static final String KEY_LOGIN_FORM_DATA = "LoginFormData";
        public static final String KEY_LOGIN_EMAIL_DATA = "LoginEmailData";
        public static final String LOGIN_DATA_USER_ID = "LoginUserId";
    }

    public static class NAV {
        public static final String SELECTED_NAV_ITEM = "SELECTED_NAV_ITEM";
    }

    public static final String PAY_PREF = "p_success";
}
