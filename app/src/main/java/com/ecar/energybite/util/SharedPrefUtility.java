package com.ecar.energybite.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.util.Log;

import java.util.Map;

/**
 * Created by anoop.gupta on 9/29/2016.
 */
public class SharedPrefUtility {

    private static final String TAG = SharedPrefUtility.class.getCanonicalName();
    public static final String USER_LOGIN_TYPE = "UserLoginType";

    public static void storeGCMRegistrationId(Context context, String gcmId) {
        SharedPreferences preferences = context.getSharedPreferences(Constants.GCM.SHARED_PREF_FILE, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.GCM.GCM_REG_ID, gcmId);
        editor.putInt(Constants.GCM.GCM_REG_VERSION, 1);
        editor.apply();
    }

    public static String getGCMRegistrationId(Context context) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(Constants.GCM.SHARED_PREF_FILE, Context.MODE_PRIVATE);
        if (1 == sharedpreferences.getInt(Constants.GCM.GCM_REG_VERSION, -1)) {
            return sharedpreferences.getString(Constants.GCM.GCM_REG_ID, null);
        }
        return null;
    }

    public static void storeToSharedPredrences(Context ctx, final String file, final String key, String value) {
        Log.i(TAG, "Saving " + key + " In Application");
        SharedPreferences preferences = ctx.getSharedPreferences(file, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void storeToSharedPredrences(Context ctx, final String file, Map<String, String> valuesMap) {
        Log.i(TAG, "Saving " + file + " In Application");
        SharedPreferences preferences = ctx.getSharedPreferences(file, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if (valuesMap != null && valuesMap.size() > 0) {
            for (Map.Entry<String, String> entry : valuesMap.entrySet()) {
                editor.putString(entry.getKey(), entry.getValue());
            }
        }
        editor.apply();
    }

    public static String getDataFromSharedPredrences(Context ctx, final String file, final String key) {
        Log.i(TAG, "Fetching " + key + " In Application");
        SharedPreferences sharedpreferences = ctx.getSharedPreferences(file, Context.MODE_PRIVATE);
        String value = sharedpreferences.getString(key, null);
        return value;
    }

    @SuppressWarnings("unchecked")
    public static Map<String, String> getAllDataFromSharedPredrences(Context ctx, final String file) {
        Log.i(TAG, "Fetching all data from " + file + " In Application");
        SharedPreferences sharedpreferences = ctx.getSharedPreferences(file, Context.MODE_PRIVATE);
        Map<String, String> valueMap = (Map<String, String>) sharedpreferences.getAll();
        return valueMap;
    }

    public static void removeFormSharedPredrences(Context ctx, final String file, final String key) {
        Log.i(TAG, "Deleting " + key + " from Application");
        if (ctx != null) {
            SharedPreferences preferences = ctx.getSharedPreferences(file, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove(key);
            editor.apply();
        }
    }
}
