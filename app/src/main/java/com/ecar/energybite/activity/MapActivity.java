package com.ecar.energybite.activity;

/**
 * Created by navin.ketu on 03-09-2019.
 */

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ecar.energybite.ChargerStationResponse;
import com.ecar.energybite.EVLocationResponse;
import com.ecar.energybite.R;
import com.ecar.energybite.eVehicle.ChargerStation;
import com.ecar.energybite.evLocation.EVLocation;
import com.ecar.energybite.http.APIClient;
import com.ecar.energybite.http.EBWebservice;
import com.ecar.energybite.map.CustomInfoWindowAdapter;
import com.ecar.energybite.map.PlaceAdapter;
import com.ecar.energybite.map.PlaceItemDecorator;
import com.ecar.energybite.util.CollectionUtility;
import com.ecar.energybite.widget.EasyBite;
import com.google.android.gms.location.FusedLocationProviderClient;
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
import com.google.android.material.bottomsheet.BottomSheetBehavior;


import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MapActivity extends BaseActivity implements LocationListener, OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener {

    private static final String TAG = MapActivity.class.getName();
    private GoogleMap mGoogleMap;
    private LinearLayout llMainBottomSheet;
    private BottomSheetBehavior mBottomSheetBehaviour;
    private RecyclerView rvPlaces;
    private Location mCurrentLocation;
    private PlaceAdapter mPlaceAdapter;
    private List<ChargerStation> mDataList = new ArrayList<ChargerStation>();
    private CustomInfoWindowAdapter mCustomInfoWindowAdapter;
    private LocationManager locationManager;
    private EBWebservice apiInterface;

    public static final int PERMISSION_CODE_REQUEST_LOCATION = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_map, null, false);
        mDrawer.addView(contentView, 0);
        apiInterface = APIClient.getClient().create(EBWebservice.class);
        EasyBite.setCurrentBaseActivity(this);

        initView();
        init();
        setBottomSheet();
    }

    private void initView() {
        rvPlaces = findViewById(R.id.rv_places);
        llMainBottomSheet = findViewById(R.id.ll_main_bottomsheet);
        mBottomSheetBehaviour = BottomSheetBehavior.from(llMainBottomSheet);
    }

    private void init() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mCustomInfoWindowAdapter = new CustomInfoWindowAdapter(this);
        rvPlaces.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        getCurrentLocation();
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.setOnMarkerClickListener(this);
        googleMap.setInfoWindowAdapter(mCustomInfoWindowAdapter);
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
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_CODE_REQUEST_LOCATION);
        }
    }

    private void handleCurrentLocationResult(Location location) {
        mCurrentLocation = location;
        markCurrentLocation(location);
        getLocation(location.getLatitude() + "", location.getLongitude() + "");

    }

    private void setBottomSheet() {
//        mBottomSheetBehaviour.setHideable(false);
        mBottomSheetBehaviour.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    private void markCurrentLocation(Location location) {
        mGoogleMap.clear();
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        mGoogleMap.addMarker(new MarkerOptions()
                .position(latLng)
                .anchor(.5f, .5f)).showInfoWindow();
        CameraPosition camPosition = new CameraPosition.Builder()
                .target(latLng)
                .zoom(18)
                .bearing(location.getBearing())
                .build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(camPosition);
        mGoogleMap.animateCamera(cameraUpdate);
    }

    private void markAllPlaces(List<EVLocation> evLocations) {
        if(CollectionUtility.isCollectionNullOrEmpty(evLocations)) {
            return;
        }
        mGoogleMap.clear();
        LatLngBounds.Builder latLngBoundsBuilder = new LatLngBounds.Builder();
        for (EVLocation evLocation : evLocations) {
            latLngBoundsBuilder.include(
                    new LatLng((evLocation.getLat()), evLocation.getLongitude()));
            createLocationMarker(evLocation);
        }

        LatLngBounds latLngBounds = latLngBoundsBuilder.build();

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(latLngBounds, 100);
        mGoogleMap.animateCamera(cameraUpdate);
        mGoogleMap.setOnInfoWindowClickListener(this);
    }

    private void createLocationMarker(EVLocation evLocation) {
        if (mGoogleMap != null) {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(evLocation.getLat(), evLocation.getLongitude()));
            markerOptions.anchor(.5f, .5f);
            markerOptions.title(evLocation.getStationname());
            Marker marker = mGoogleMap.addMarker(markerOptions);
            marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.sharp_ev_station_black_36));
            marker.setTag(evLocation);
            marker.showInfoWindow();
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        mBottomSheetBehaviour.setState(BottomSheetBehavior.STATE_EXPANDED);
        mDataList.clear();
        EVLocation evLocation = (EVLocation) marker.getTag();
        if(evLocation != null) {
            getChargerStationDetail(evLocation.getStationId());
        }
        return false;
    }

    private void setOrNotifyAdapter() {
        if (mPlaceAdapter == null) {
            mPlaceAdapter = new PlaceAdapter(mDataList);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(RecyclerView.HORIZONTAL);
            rvPlaces.setLayoutManager(layoutManager);
            rvPlaces.setAdapter(mPlaceAdapter);
            rvPlaces.addItemDecoration(new PlaceItemDecorator());
        } else {
            mPlaceAdapter.notifyDataSetChanged();
        }
    }

    void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        getLocation(location.getLatitude() + "", location.getLongitude() + "");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(MapActivity.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }


    private void getLocation(final String lattitude, String longitude) {
        final EVLocationResponse evLocationResponse = new EVLocationResponse(lattitude, longitude);
        Call<EVLocationResponse> call1 = apiInterface.getEVLocations(lattitude, longitude);
        call1.enqueue(new Callback<EVLocationResponse>() {
            @Override
            public void onResponse(Call<EVLocationResponse> call, Response<EVLocationResponse> response) {
                EVLocationResponse evLocationResponse = response.body();
                if (evLocationResponse != null) {
                    String responseCode = evLocationResponse.getResponseCode();
                    if (responseCode != null && responseCode.equals("404")) {
                        Toast.makeText(MapActivity.this, "Not able to get results. ", Toast.LENGTH_SHORT).show();
                    } else {
                        markAllPlaces(evLocationResponse.getEVLocations());
                    }
                }
            }

            @Override
            public void onFailure(Call<EVLocationResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Not able to get locations", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.getMessage());
                call.cancel();
            }
        });
    }

    private void getChargerStationDetail(final String stationId) {
        final ChargerStationResponse chargerStationResponse = new ChargerStationResponse(stationId);
        Call<ChargerStationResponse> call1 = apiInterface.getChargerStationDetail(stationId);
        call1.enqueue(new Callback<ChargerStationResponse>() {
            @Override
            public void onResponse(Call<ChargerStationResponse> call, Response<ChargerStationResponse> response) {
                ChargerStationResponse chargerStationResponse = response.body();
                if (chargerStationResponse != null) {
                    String responseCode = chargerStationResponse.getResponseCode();
                    if (responseCode != null && responseCode.equals("404")) {
                        Toast.makeText(MapActivity.this, "Not able to get results. ", Toast.LENGTH_SHORT).show();
                    } else {
                        mDataList.clear();
                        mDataList.addAll(chargerStationResponse.getChargerStations());
                        setOrNotifyAdapter();
                    }
                }
            }

            @Override
            public void onFailure(Call<ChargerStationResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Not able to get locations", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.getMessage());
                call.cancel();
            }
        });
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        EVLocation evLocation = (EVLocation)marker.getTag();
        getLocationAndOpenGoogleMap(evLocation);
    }

    private void getLocationAndOpenGoogleMap(EVLocation evLocation) {
        String query = getLocation(evLocation.getLat(), evLocation.getLongitude());
        openGoogleMap(query);
    }

    private String getLocation(Double lat, Double lng) {
        String googleMapQuery = "";
        if (lat != null && lng != null) {
            googleMapQuery = "destination=" + lat + " , " + lng;
        }
        return googleMapQuery;
    }

    private void openGoogleMap(String query) {
//        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + query);
        Uri gmmIntentUri = Uri.parse("https://www.google.com/maps/dir/?api=1&" + query);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        EasyBite.getCurrentBaseActivity().startActivity(mapIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater findMenuItems = getMenuInflater();
        findMenuItems.inflate(R.menu.main_action, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.scan_qr_code:
                Intent scannQRCodeIntent = new Intent(MapActivity.this, ScannedBarcodeActivity.class);
                startActivity(scannQRCodeIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}



