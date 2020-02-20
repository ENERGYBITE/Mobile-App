package com.ecar.energybite.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class ConnectionManager {
    public static String TAG = ConnectionManager.class.getCanonicalName();

    public static boolean isConnected(Context context) {
        Log.i(TAG, "Checking Internet Connection");
        /*
         * boolean isDebuggable = ( 0 != ( context.getApplicationInfo().flags &
         * ApplicationInfo.FLAG_DEBUGGABLE ) ); if(isDebuggable) { return
         * isDebuggable; }
         */
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            Log.i(TAG, "Internet Connection Available");
            return true;
        }
        return false;
    }
}
