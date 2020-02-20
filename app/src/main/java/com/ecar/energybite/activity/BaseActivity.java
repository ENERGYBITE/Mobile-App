package com.ecar.energybite.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ecar.energybite.BookingListResponse;
import com.ecar.energybite.EVData;
import com.ecar.energybite.R;
import com.ecar.energybite.UserVehicleResponse;
import com.ecar.energybite.booking.BookingModel;
import com.ecar.energybite.booking.BookingStatus;
import com.ecar.energybite.eVehicle.UserVehicle;
import com.ecar.energybite.http.APIClient;
import com.ecar.energybite.http.EBWebservice;
import com.ecar.energybite.user.User;
import com.ecar.energybite.widget.EasyBite;
import com.google.android.material.navigation.NavigationView;


import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    protected DrawerLayout mDrawer;
    protected FrameLayout mFrameLayout;
    private EBWebservice apiInterface;
    public static final String USER_VEHICLES = "userVehicles";
    public static final String USER_TEMPLATES = "userTemplates";
    public static final String BOOKING_RESPONSE = "bookingResponse";

    public static final String PAYMENT_RESPONSE = "paymentResponse";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        apiInterface = APIClient.getClient().create(EBWebservice.class);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        User loggedInUser = EVData.getUser();
        if (loggedInUser != null) {
            View header = navigationView.getHeaderView(0);
            TextView tvUserName = (TextView) header.findViewById(R.id.tvUserName);
            TextView tvUserEmail = (TextView) header.findViewById(R.id.tvUserEmail);
            tvUserName.setText(loggedInUser.getName());
            tvUserEmail.setText(loggedInUser.getUserEmail());
        }
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            BaseActivity.super.onBackPressed();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();

        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Intent intent = null;
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            intent = new Intent(this, HomeActivity.class);
        } else if (id == R.id.nav_station) {
            intent = new Intent(this, MapActivity.class);
        } else if (id == R.id.nav_setting) {
            intent = new Intent(this, SettingsActivity.class);
        } else if (id == R.id.nav_ev) {
            if (EVData.getUser() != null) {
                getUserVehicles(EVData.getUser().getUsername(), this);
            }
        } else if (id == R.id.nav_booking) {
            getBookings(EVData.getUser().getUsername(), this);
        } else if (id == R.id.nav_my_location) {
            intent = new Intent(this, MyLocationActivity.class);
        } else if (id == R.id.nav_profile) {
            intent = new Intent(this, UserProfileActivity.class);
        } else if (id == R.id.nav_logout) {
            intent = new Intent(this, LoginActivity.class);
        }

        if (intent != null) {
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        EasyBite.setCurrentBaseActivity(this);
    }

    private void getUserVehicles(final String userName, final BaseActivity baseActivity) {
        final UserVehicleResponse userVehicleResponse = new UserVehicleResponse(userName);
        Call<UserVehicleResponse> call1 = apiInterface.getUserVehicles(userName);
        call1.enqueue(new Callback<UserVehicleResponse>() {
            @Override
            public void onResponse(Call<UserVehicleResponse> call, Response<UserVehicleResponse> response) {
                UserVehicleResponse userVehicleResponse = response.body();
                if (userVehicleResponse != null) {
                    String responseCode = userVehicleResponse.getResponseCode();
                    if (responseCode != null && responseCode.equals("404")) {
                        Toast.makeText(BaseActivity.this, "Not able to get results. ", Toast.LENGTH_SHORT).show();
                    } else {
                        List<UserVehicle> userVehicles = userVehicleResponse.getUserVehicles();
                        Intent userVehicleIntent = new Intent(baseActivity, UserVehicleActivity.class);
                        userVehicleIntent.putParcelableArrayListExtra(BaseActivity.USER_VEHICLES, (ArrayList<UserVehicle>) userVehicles);
                        startActivity(userVehicleIntent);
                    }
                }
            }

            @Override
            public void onFailure(Call<UserVehicleResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Not able to get locations", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });
    }

    private void getBookings(final String userName, final BaseActivity baseActivity) {
        Call<BookingListResponse> call1 = apiInterface.getBookingList(userName);
        call1.enqueue(new Callback<BookingListResponse>() {
            @Override
            public void onResponse(Call<BookingListResponse> call, Response<BookingListResponse> response) {
                BookingListResponse bookingListResponse = response.body();
                if (bookingListResponse != null) {
                    String responseCode = bookingListResponse.getResponseCode();
                    if (responseCode != null && responseCode.equals("404")) {
                        Toast.makeText(BaseActivity.this, "Not able to get results. ", Toast.LENGTH_SHORT).show();
                    } else {
                        List<BookingModel> bookingModels = bookingListResponse.getBookingModels();
                        Intent myBookingListIntent = new Intent(baseActivity, MyBookingActivity.class);
                        myBookingListIntent.putParcelableArrayListExtra(BaseActivity.USER_VEHICLES, (ArrayList<BookingModel>) bookingModels);
                        startActivity(myBookingListIntent);
                    }
                }
            }

            @Override
            public void onFailure(Call<BookingListResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error in getting my bookings. ", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });
    }

    public View getView() {
        final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        return viewGroup;
    }
}
