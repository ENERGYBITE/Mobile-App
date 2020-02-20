package com.ecar.energybite.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ecar.energybite.EVTemplateResponse;
import com.ecar.energybite.R;
import com.ecar.energybite.eVehicle.EVTemplate;
import com.ecar.energybite.eVehicle.UserVehicle;
import com.ecar.energybite.eVehicle.UserVehicleAdapter;
import com.ecar.energybite.http.APIClient;
import com.ecar.energybite.http.EBWebservice;
import com.ecar.energybite.widget.EasyBite;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by navin on 9/12/2019.
 */
public class UserVehicleActivity extends BaseActivity {
    private static final String TAG = UserVehicleActivity.class.getName();
    private RecyclerView rvMyVehicles;
    private TextView tvEmptyView;
    private UserVehicleAdapter userVehicleAdapter;
    private FloatingActionButton fabApprovalListAction;
    EBWebservice apiInterface;
    private Button btnSearchApprovalObjects;

    private List<UserVehicle> userVehicles;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiInterface = APIClient.getClient().create(EBWebservice.class);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_approvable_object_list, null, false);
        rvMyVehicles = (RecyclerView) contentView.findViewById(R.id.rvMyVehicles);
        tvEmptyView = (TextView) contentView.findViewById(R.id.tvEmptyView);
        tvEmptyView.setText(getEmptyString());
        userVehicles = getIntent().getParcelableArrayListExtra(BaseActivity.USER_VEHICLES);
        mFrameLayout.addView(contentView, 0);
        if (userVehicles != null && userVehicles.size() > 0) {
            rvMyVehicles.setVisibility(View.VISIBLE);
            tvEmptyView.setVisibility(View.GONE);
            userVehicleAdapter = new UserVehicleAdapter(userVehicles);
            rvMyVehicles.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            rvMyVehicles.setItemAnimator(null);
            rvMyVehicles.setAdapter(userVehicleAdapter);
        } else {
            rvMyVehicles.setVisibility(View.GONE);
            tvEmptyView.setVisibility(View.VISIBLE);
        }

        fabApprovalListAction = (FloatingActionButton) contentView.findViewById(R.id.fabApprovalListAction);
        fabApprovalListAction.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    if (view.getId() == R.id.fabApprovalListAction) {
                        getEVTemplates();
//                        Intent i = new Intent(UserVehicleActivity.this, AddVehicleActivity.class);
//                        UserVehicleActivity.this.startActivity(i);
                        // revealYellow(motionEvent.getRawX(),
                        // motionEvent.getRawY());
                        // transitionToActivity (TripCreationActivity.class,
                        // view, R.string.transition_reveal1);
                    }
                }
                return false;

            }
        });
    }

    public int getEmptyString() {
        return R.string.msg_empty;
    }

    private void getEVTemplates() {
        final EVTemplateResponse login = new EVTemplateResponse();
        Call<EVTemplateResponse> call1 = apiInterface.getEVTemplates();
        call1.enqueue(new Callback<EVTemplateResponse>() {
            @Override
            public void onResponse(Call<EVTemplateResponse> call, Response<EVTemplateResponse> response) {
                EVTemplateResponse evTemplateResponse = response.body();
                if (evTemplateResponse != null) {
                    String responseCode = evTemplateResponse.getResponseCode();
                    if (responseCode != null && responseCode.equals("404")) {
                        Toast.makeText(UserVehicleActivity.this, "Username or password is incorrect. ", Toast.LENGTH_SHORT).show();
                    } else {
                        List<EVTemplate> evTemplates = evTemplateResponse.getEVTemplates();
                        Intent evTemplateIntent = new Intent(EasyBite.getCurrentBaseActivity(), AddVehicleActivity.class);
                        evTemplateIntent.putParcelableArrayListExtra(BaseActivity.USER_TEMPLATES, (ArrayList<EVTemplate>) evTemplates);
                        evTemplateIntent.putParcelableArrayListExtra(BaseActivity.USER_VEHICLES, (ArrayList<UserVehicle>) userVehicles);
                        startActivity(evTemplateIntent);
                    }
                }
            }

            @Override
            public void onFailure(Call<EVTemplateResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Username or password is incorrect. ", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.getMessage());
                call.cancel();
            }
        });
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(UserVehicleActivity.this, MapActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }

}
