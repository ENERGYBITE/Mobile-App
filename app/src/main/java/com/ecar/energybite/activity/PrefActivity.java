package com.ecar.energybite.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.ecar.energybite.R;
import com.ecar.energybite.eVehicle.AppPrefModel;
import com.ecar.energybite.eVehicle.ChargerStation;
import com.ecar.energybite.eVehicle.PrefAdapter;
import com.ecar.energybite.map.PlaceAdapter;
import com.ecar.energybite.util.StringUtility;

import java.util.ArrayList;
import java.util.List;

public class PrefActivity extends BaseActivity {

    private RecyclerView rvPreferences;
    private PrefAdapter mPrefAdapter;
    private ChargerStation chargerStation;

    private List<AppPrefModel> mPrefList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chargerStation = (ChargerStation) getIntent().getExtras().get(PlaceAdapter.CHARGER_STATION);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_pref, null, false);
        rvPreferences = contentView.findViewById(R.id.rv_preference);
        initView();
        init();
        mFrameLayout.addView(contentView, 0);
    }

    private void initView() {

    }

    private void init() {
        notifyOrSetApdater();
    }

    private void notifyOrSetApdater() {
        if (mPrefAdapter == null) {
            mPrefAdapter = new PrefAdapter(getAppPrefModels());
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(RecyclerView.VERTICAL);
            rvPreferences.setLayoutManager(layoutManager);
            rvPreferences.setAdapter(mPrefAdapter);
        } else {
            mPrefAdapter.notifyDataSetChanged();
        }
    }

    private List<AppPrefModel> getAppPrefModels() {
        List<AppPrefModel> appPrefModels = new ArrayList<AppPrefModel>();
        if (chargerStation != null) {
            AppPrefModel appPrefModel = new AppPrefModel();
            appPrefModel.setTitle("Station Id");
            appPrefModel.setData(chargerStation.getStationId());
            appPrefModels.add(appPrefModel);

            if (StringUtility.trimAndEmptyIsNull(chargerStation.getChargerName()) != null) {
                appPrefModel = new AppPrefModel();
                appPrefModel.setTitle("Charger Name");
                appPrefModel.setData(chargerStation.getChargerName());
                appPrefModels.add(appPrefModel);
            }

            if (StringUtility.trimAndEmptyIsNull(chargerStation.getChargerType()) != null) {
                appPrefModel = new AppPrefModel();
                appPrefModel.setTitle("Charger Type");
                appPrefModel.setData(chargerStation.getChargerType());
                appPrefModels.add(appPrefModel);
            }

            if (StringUtility.trimAndEmptyIsNull(chargerStation.getCategory()) != null) {
                appPrefModel = new AppPrefModel();
                appPrefModel.setTitle("Charger Category");
                appPrefModel.setData(chargerStation.getCategory());
                appPrefModels.add(appPrefModel);
            }

            if (StringUtility.trimAndEmptyIsNull(chargerStation.getOutputType()) != null) {
                appPrefModel = new AppPrefModel();
                appPrefModel.setTitle("Output Type");
                appPrefModel.setData(chargerStation.getOutputType());
                appPrefModels.add(appPrefModel);
            }

            if (StringUtility.trimAndEmptyIsNull(chargerStation.getRatedVoltages()) != null) {
                appPrefModel = new AppPrefModel();
                appPrefModel.setTitle("Rated Voltage");
                appPrefModel.setData(chargerStation.getRatedVoltages());
                appPrefModels.add(appPrefModel);
            }

            appPrefModel = new AppPrefModel();
            appPrefModel.setTitle("No. of Charging Points");
            appPrefModel.setData(chargerStation.getNumber_of_chargingpoints() + "");
            appPrefModels.add(appPrefModel);


            if (chargerStation.getChargerCapacity() != null) {
                appPrefModel = new AppPrefModel();
                appPrefModel.setTitle("Charger Capacity");
                appPrefModel.setData(chargerStation.getChargerCapacity() + "");
                appPrefModels.add(appPrefModel);
            }

            if (chargerStation.getConnector() != null) {
                appPrefModel = new AppPrefModel();
                appPrefModel.setTitle("Connector");
                appPrefModel.setData(chargerStation.getConnector());
                appPrefModels.add(appPrefModel);
            }

            if (chargerStation.getStatus() != null) {
                appPrefModel = new AppPrefModel();
                appPrefModel.setTitle("Availability Status");
                if(chargerStation.getStatus().equalsIgnoreCase("A")) {
                    appPrefModel.setData("Available");
                }
                appPrefModels.add(appPrefModel);
            }

        }

        return appPrefModels;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(PrefActivity.this, MapActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }

}
