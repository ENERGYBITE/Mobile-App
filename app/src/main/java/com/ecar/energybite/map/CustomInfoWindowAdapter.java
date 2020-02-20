package com.ecar.energybite.map;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.ecar.energybite.R;
import com.ecar.energybite.evLocation.EVLocation;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by henu.kumar on 12-07-2018.
 */

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private Context mContext;
    private View view;
    private TextView tvPlace;
    private TextView tvCharges;
    private View lvGetDirection;

    public CustomInfoWindowAdapter(Context context) {
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) mContext.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.marker_custom_info_window, null);
        initView();
    }



    private void initView() {
        tvCharges = view.findViewById(R.id.tv_charges);
        tvPlace = view.findViewById(R.id.tv_place);
        lvGetDirection = view.findViewById(R.id.ll_get_direction);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        final EVLocation evLocation = (EVLocation) marker.getTag();
        if (evLocation != null) {
            tvPlace.setText(evLocation.getStationname());
            tvCharges.setText(evLocation.getAddress());
        }
        return view;
    }

    @Override
    public View getInfoContents(Marker marker) {
        if (marker != null
                && marker.isInfoWindowShown()) {
            marker.hideInfoWindow();
            marker.showInfoWindow();
        }
        return null;
    }
}
