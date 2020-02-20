package com.ecar.energybite.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ecar.energybite.AddVehicleResponse;
import com.ecar.energybite.EVData;
import com.ecar.energybite.R;
import com.ecar.energybite.eVehicle.EVTemplate;
import com.ecar.energybite.eVehicle.UserVehicle;
import com.ecar.energybite.http.APIClient;
import com.ecar.energybite.http.EBWebservice;
import com.ecar.energybite.util.CollectionUtility;
import com.ecar.energybite.util.StringUtility;
import com.ecar.energybite.widget.EasyBite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddVehicleActivity extends BaseActivity {
    private static final String TAG = AddVehicleActivity.class.getName();
    private Spinner spinmodel;
    private Spinner spinmake;
    private Spinner spinyear;
    private Spinner spincolor;
    private Spinner spinregno;
    private Button mSubmitBtn;
    private EditText mtextview;
    private List<EVTemplate> evTemplates;
    private List<UserVehicle> userVehicles;
    private Map<String, List<String>> makersAndModelMap = new HashMap<>();
    private List<String> makers = new ArrayList<>();
    EBWebservice apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        evTemplates = getIntent().getParcelableArrayListExtra(BaseActivity.USER_TEMPLATES);
        userVehicles = getIntent().getParcelableArrayListExtra(BaseActivity.USER_VEHICLES);
        apiInterface = APIClient.getClient().create(EBWebservice.class);
        processEVTemplates();
        View contentView = inflater.inflate(R.layout.activity_add_vehicle, null, false);

        spinmake = (Spinner) contentView.findViewById(R.id.make);
        spinmodel = (Spinner) contentView.findViewById(R.id.model);
        spinyear = (Spinner) contentView.findViewById(R.id.year);
        spincolor = (Spinner) contentView.findViewById(R.id.color);
        mSubmitBtn = (Button) contentView.findViewById(R.id.add_ev);
        mtextview = (EditText) contentView.findViewById(R.id.regno);

//        spinmake.setOnItemSelectedListener(this);
//        spinyear.setOnItemSelectedListener ( this );

        ArrayAdapter aa = new ArrayAdapter(EasyBite.getCurrentBaseActivity(), android.R.layout.simple_spinner_item, makers);
        aa.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
        spinmake.setAdapter(aa);
        ArrayAdapter aayear = new ArrayAdapter(EasyBite.getCurrentBaseActivity(), android.R.layout.simple_spinner_item, EVTemplate.getEVYearList());
        aayear.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
        spinyear.setAdapter(aayear);

        ArrayAdapter aacolor = new ArrayAdapter(EasyBite.getCurrentBaseActivity(), android.R.layout.simple_spinner_item, EVTemplate.getEVColorList());
        aacolor.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
        spincolor.setAdapter(aacolor);

        spinmake.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Object itm = adapterView.getItemAtPosition(i);
                Toast.makeText(EasyBite.getCurrentBaseActivity(), itm.toString(), Toast.LENGTH_LONG);
                List<String> models = makersAndModelMap.get(itm.toString());
                ArrayAdapter aamodel = new ArrayAdapter(EasyBite.getCurrentBaseActivity(), android.R.layout.simple_spinner_item, models);
                aamodel.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
                spinmodel.setAdapter(aamodel);

            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserVehicle userVehicle = new UserVehicle();
                if (spinmake != null && spinmake.getSelectedItem() != null) {
                    userVehicle.setMake((String) spinmake.getSelectedItem());
                } else {
                    Toast.makeText(getApplicationContext(), "Please select Vehicle type ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (spinmodel != null && spinmodel.getSelectedItem() != null) {
                    userVehicle.setModel((String) spinmodel.getSelectedItem());
                } else {
                    Toast.makeText(getApplicationContext(), "Please select Vehicle Model ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (spinyear != null && spinyear.getSelectedItem() != null) {
                    userVehicle.setYear((int) spinyear.getSelectedItem());
                } else {
                    Toast.makeText(getApplicationContext(), "Please select Year ", Toast.LENGTH_SHORT).show();
                    return;
                }
//                if (spincolor != null && spincolor.getSelectedItem() != null) {
//                    userVehicle.setC((String) spincolor.getSelectedItem());
//                } else {
//                    Toast.makeText(getApplicationContext(), "Please select Vehicle color ", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                if (mtextview != null && StringUtility.trimAndEmptyIsNull(mtextview.getText().toString()) != null) {
                    userVehicle.setEvRegNumber(mtextview.getText().toString());
                } else {
                    Toast.makeText(getApplicationContext(), "Please add reg number ", Toast.LENGTH_SHORT).show();
                    return;
                }
                userVehicle.setUserName(EVData.getUser().getUsername());
                addUserVehicle(userVehicle);
            }
        });
        mFrameLayout.addView(contentView, 0);
    }

    private void processEVTemplates() {
        for (EVTemplate evTemplate : evTemplates) {
            String make = evTemplate.getMake();
            List<String> values = makersAndModelMap.get(make);
            if (!CollectionUtility.isCollectionNullOrEmpty(values)) {
                values.add(evTemplate.getModel());
            } else {
                makers.add(make);
                List<String> newValues = new ArrayList<String>();
                newValues.add(evTemplate.getModel());
                makersAndModelMap.put(make, newValues);
            }
        }
    }

    private void addUserVehicle(final UserVehicle userVehicle) {
        final AddVehicleResponse addVehicleResponse = new AddVehicleResponse(userVehicle.getUserName(), userVehicle.getMake(), userVehicle.getModel(), userVehicle.getYear() + "", userVehicle.getEvRegNumber());
        Call<AddVehicleResponse> call1 = apiInterface.addUserEV(userVehicle);
        call1.enqueue(new Callback<AddVehicleResponse>() {
            @Override
            public void onResponse(Call<AddVehicleResponse> call, Response<AddVehicleResponse> response) {
                AddVehicleResponse addVehicleResponse = response.body();
                if (addVehicleResponse != null) {
                    String responseCode = addVehicleResponse.getResponseCode();
                    if (responseCode != null && responseCode.equals("404")) {
                        Toast.makeText(AddVehicleActivity.this, "Getting Error ", Toast.LENGTH_SHORT).show();
                    } else {
                        UserVehicle userVehicle = addVehicleResponse.getUserVehicle();
                        if(userVehicle == null) {
                            Toast.makeText(getApplicationContext(), addVehicleResponse.getResponsemessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            List<UserVehicle> userVehicles = new ArrayList<>();
                            userVehicles.add(userVehicle);
                            Intent userVehicleIntent = new Intent(EasyBite.getCurrentBaseActivity(), UserVehicleActivity.class);
                            userVehicleIntent.putParcelableArrayListExtra(BaseActivity.USER_VEHICLES, (ArrayList<UserVehicle>) userVehicles);
                            startActivity(userVehicleIntent);
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<AddVehicleResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Getting Error ", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.getMessage());
                call.cancel();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddVehicleActivity.this, UserVehicleActivity.class);
        intent.putParcelableArrayListExtra(BaseActivity.USER_VEHICLES, (ArrayList<UserVehicle>) userVehicles);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }
}
