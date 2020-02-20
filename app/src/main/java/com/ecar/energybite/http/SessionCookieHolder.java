package com.ecar.energybite.http;

/**
 * Created by navin.ketu on 7/27/2016.
 */
public enum SessionCookieHolder {
    INSTANCE;

    private Object data;

    public static boolean hasData() {
        return INSTANCE.data != null;
    }

    public static void setData(final Object data) {
        INSTANCE.data = data;
    }

    public static Object getData() {
        final Object retList = INSTANCE.data;
        INSTANCE.data = null;
        return retList;
    }
}