package com.ecar.energybite.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataSource {

    private static Map<String, List<String>> locationMap = new HashMap<>();

    static {
        List<String> placeList = new ArrayList<>();

        placeList.add("54321");
        locationMap.put("28.5008_77.4100", placeList);

        placeList = new ArrayList<>();
        placeList.add("74321");
        placeList.add("74322");
        locationMap.put("28.6126_77.3656", placeList);

        placeList = new ArrayList<>();
        placeList.add("54323");
        placeList.add("543218");
        placeList.add("54329");
        locationMap.put("28.5336_77.3479", placeList);

        placeList = new ArrayList<>();
        placeList.add("54378");
        placeList.add("54379");
        placeList.add("54377");
        placeList.add("543789");
        locationMap.put("28.4988_77.4046", placeList);
//        locationMap.put("", "");
//        locationMap.put("", "");
    }

    public static Map<String, List<String>> getLocationMap(){
        return locationMap;
    }

}
