package com.ecar.energybite.gcm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import android.util.Log;

import com.ecar.energybite.util.Constants;
import com.ecar.energybite.util.SharedPrefUtility;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;



public class GCMRegistrationWorker extends Worker {

    private static final String TAG = GCMRegistrationWorker.class.getCanonicalName ();
    private Context mContext;

    public GCMRegistrationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        mContext = getApplicationContext();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences (mContext);

        try {
            // [START register_for_gcm]
            // Initially this call goes out to the network to retrieve the token, subsequent calls
            // are local.
            // R.string.gcm_defaultSenderId (the Sender ID) is typically derived from google-services.json.
            // See https://developers.google.com/cloud-messaging/android/start for details on this file.
            // [START get_token]
            InstanceID instanceID = InstanceID.getInstance (mContext);
            String token = instanceID.getToken ("833209582449",
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            // [END get_token]
            Log.i(TAG, "GCM Registration Token: " + token);

            // TODO: Implement this method to send any registration to your app's servers.
            SharedPrefUtility.storeGCMRegistrationId (mContext, token);
            sendRegistrationToServer(token);

            // You should store a boolean that indicates whether the generated token has been
            // sent to your server. If the boolean is false, send the token to your server,
            // otherwise your server should have already received the token.
            sharedPreferences.edit().putBoolean (Constants.GCM.SENT_TOKEN_TO_SERVER, true).apply ();
            // [END register_for_gcm]
        } catch (Exception e) {
            Log.d (TAG, "Failed to complete token refresh", e);
            // If an exception happens while fetching the new token or updating our registration data
            // on a third-party server, this ensures that we'll attempt the update at a later time.
            sharedPreferences.edit().putBoolean(Constants.GCM.SENT_TOKEN_TO_SERVER, false).apply();
            return Result.failure();
        }
        // Notify UI that registration has completed, so the progress indicator can be hidden.
        Intent registrationComplete = new Intent(Constants.GCM.REGISTRATION_COMPLETE);
        LocalBroadcastManager.getInstance (mContext).sendBroadcast (registrationComplete);

        return Result.failure();
    }

    private void sendRegistrationToServer(String token) {
        // Add custom implementation, as needed.
    }

}
