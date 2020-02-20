package com.ecar.energybite.activity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.ecar.energybite.R;


/**
 * Created by navin.ketu on 04-09-2019.
 */

public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SettingsActivity.this, MapActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }
}