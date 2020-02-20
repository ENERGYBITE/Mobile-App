package com.ecar.energybite.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.ecar.energybite.R;
import com.ecar.energybite.gcm.GCMRegistrationWorker;
import com.ecar.energybite.http.ConnectionManager;
import com.ecar.energybite.util.Constants;
import com.ecar.energybite.util.MessageUtility;
import com.ecar.energybite.util.SharedPrefUtility;
import com.ecar.energybite.util.StringUtility;
import com.ecar.energybite.widget.EasyBite;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private boolean isReceiverRegistered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        generateView();
    }

    private void generateView() {
        if (ConnectionManager.isConnected(this)) {
            if (false && getIntent().getBooleanExtra(Constants.GCM.HAS_NOTIFICATION, false)) {  // added false to skip
                handleNotification();
            } else {
                if (checkPlayServices()) {
                    String registrationId = SharedPrefUtility.getGCMRegistrationId(this);
                    if (StringUtility.trimAndEmptyIsNull(registrationId) == null) {
                        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
                            @Override
                            public void onReceive(Context context, Intent intent) {
                                SharedPreferences sharedPreferences =
                                        PreferenceManager.getDefaultSharedPreferences(context);
                                boolean sentToken = sharedPreferences
                                        .getBoolean(Constants.GCM.SENT_TOKEN_TO_SERVER, false);
                                if (sentToken) {
//                                    Logger.d(TAG, "showNextActivity from onReceive");
                                    showLogin();
                                } else {
                                    MessageUtility.showErrorMessage(EasyBite.getCurrentBaseActivity(), R.string.error_device_registration);
                                }

                            }
                        };
                        registerReceiver();
//                        Intent intent = new Intent(this, GCMRegistrationIntentService.class);
//                        startService(intent);
                        WorkManager.getInstance().enqueue(OneTimeWorkRequest.from(GCMRegistrationWorker.class));
                    } else {
//                        Logger.d(TAG, "showNextActivity from else");
                        showLogin();
                    }
                } else {
//                    finish ();
                }
            }
        }
    }

    public void showLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        this.startActivity(intent);
        MainActivity.this.finish();
    }

    private void registerReceiver() {
        if (!isReceiverRegistered) {
            LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                    new IntentFilter(Constants.GCM.REGISTRATION_COMPLETE));
            isReceiverRegistered = true;
        }
    }

    public void handleNotification() {

    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

}
