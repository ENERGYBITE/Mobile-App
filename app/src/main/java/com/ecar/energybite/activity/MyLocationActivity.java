package com.ecar.energybite.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.ecar.energybite.R;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

public class MyLocationActivity extends BaseActivity implements LocationListener, OnMapReadyCallback {

    private GoogleMap mGoogleMap;
    private Location mlocation;
    private Marker mCurrLocationMarker;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest locationRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_current_location, null, false);
        mFrameLayout.addView(contentView, 0);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.currentmap);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        getCurrentLocation();
    }

    private void markCurrentLocation(Location location) {
        mGoogleMap.clear();
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        mGoogleMap.addMarker(new MarkerOptions()
                .position(latLng)
                .anchor(.5f, .5f)).showInfoWindow();
        CameraPosition camPosition = new CameraPosition.Builder()
                .target(latLng)
                .zoom(8)
                .bearing(location.getBearing())
                .build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(camPosition);
        mGoogleMap.animateCamera(cameraUpdate);
    }

    protected void getCurrentLocation() {
        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        handleCurrentLocationResult(location);
                        String longlat = "";
                        String lat = String.valueOf(location.getLatitude());
                        String lon = String.valueOf(location.getLongitude());

                        longlat = "lat=" + lat + "&lon=" + lon;
                        Log.d("longlat", longlat);
                    }
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("Error", "Couldn't get location");
                            e.printStackTrace();
                        }
                    });
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MapActivity.PERMISSION_CODE_REQUEST_LOCATION);
        }
    }

    private void handleCurrentLocationResult(Location location) {
        mlocation = location;
        markCurrentLocation(location);

    }



    @Override
    public void onLocationChanged(Location location) {
        mlocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
        final LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Location");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        mGoogleMap.clear();
        LatLngBounds.Builder latLngBoundsBuilder = new LatLngBounds.Builder();
        LatLngBounds latLngBounds = latLngBoundsBuilder.build();

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(latLngBounds, 100);
        mGoogleMap.animateCamera(cameraUpdate);
//        if (mGoogleApiClient != null) {
//            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
//

    }


    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(MyLocationActivity.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MyLocationActivity.this, MapActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }
}
