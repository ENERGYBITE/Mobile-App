package com.ecar.energybite.eVehicle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataSource {

    private static Map<String, List<String>> locationMap = new HashMap<>();
    private static List<AppPrefModel> appPrefList = new ArrayList<>();

    static {
        List<String> placeList = new ArrayList<>();

        placeList.add("Advant Navis Business Park 1");
        locationMap.put("28.5008_77.4100", placeList);

        placeList = new ArrayList<>();
        placeList.add("Samsung Research Institute 1");
        placeList.add("Samsung Research Institute 2");
        locationMap.put("28.6126_77.3656", placeList);

        placeList = new ArrayList<>();
        placeList.add("Oracle 1");
        placeList.add("Oracle 2");
        placeList.add("Oracle 3");
        locationMap.put("28.5336_77.3479", placeList);

        placeList = new ArrayList<>();
        placeList.add("Axtria India Pvt. Ltd. 1");
        placeList.add("Axtria India Pvt. Ltd. 2");
        placeList.add("Axtria India Pvt. Ltd. 3");
        placeList.add("Axtria India Pvt. Ltd. 4");
        locationMap.put("28.4988_77.4046", placeList);
//        locationMap.put("", "");
//        locationMap.put("", "");

        AppPrefModel appPrefModel = new AppPrefModel();
        appPrefModel.setTitle("Network & Internet");
        appPrefModel.setData("Wi-Fi, mobile, data usage, hotspot");
//        appPrefModel.setIconId(R.drawable.baseline_signal_wifi_4_bar_black_48);
        appPrefList.add(appPrefModel);

        appPrefModel = new AppPrefModel();
        appPrefModel.setTitle("Connected devices");
        appPrefModel.setData("Bluetooth, Cast");
//        appPrefModel.setIconId(R.drawable.baseline_bluetooth_connected_black_48);
        appPrefList.add(appPrefModel);

        appPrefModel = new AppPrefModel();
        appPrefModel.setTitle("Apps & notifications");
        appPrefModel.setData("Permissions, default apps");
//        appPrefModel.setIconId(R.drawable.baseline_apps_black_48);
        appPrefList.add(appPrefModel);

        appPrefModel = new AppPrefModel();
        appPrefModel.setTitle("Battery");
        appPrefModel.setData("81% - 3d 20h 8m left");
//        appPrefModel.setIconId(R.drawable.baseline_battery_full_black_48);
        appPrefList.add(appPrefModel);

    }

    public static Map<String, List<String>> getLocationMap(){
        return locationMap;
    }

    public static List<AppPrefModel> getAppPref(){
        return appPrefList;
    }

}
