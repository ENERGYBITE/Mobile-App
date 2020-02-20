package com.ecar.energybite.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static com.ecar.energybite.util.Constants.PAY_PREF;

public class SavePreference {

    public static SharedPreferences getPreferences(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setPayStatus(Context context, boolean loggedIn){
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean(PAY_PREF, loggedIn);
        editor.apply();
    }

    public static boolean getPayStatus(Context context){
        return  getPreferences(context).getBoolean(PAY_PREF, false);
    }
}
