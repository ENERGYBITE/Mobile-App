package com.ecar.energybite.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ecar.energybite.R;
import com.ecar.energybite.UserRegisterResponse;
import com.ecar.energybite.http.APIClient;
import com.ecar.energybite.http.EBWebservice;
import com.ecar.energybite.util.StringUtility;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = SignUpActivity.class.getName();
    public static final String REGEX_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    EBWebservice apiInterface;
    @BindView(R.id.btn_register)
    Button btn_register;

    @BindView(R.id.edt_user_name)
    EditText edtUserName;
    @BindView(R.id.edt_email)
    EditText edtEmail;
    @BindView(R.id.edt_password)
    EditText edtPassword;
    @BindView(R.id.edt_confirm_password)
    EditText edtConfirmPassword;
    @BindView(R.id.edt_first_name)
    EditText edtFirstName;
    @BindView(R.id.edt_last_name)
    EditText edtLastName;
    @BindView(R.id.edt_address)
    EditText edtAddress;
    @BindView(R.id.edt_pincode)
    EditText edtPincode;
    @BindView(R.id.edt_city)
    EditText edtCity;
    @BindView(R.id.edt_state)
    EditText edtState;
    @BindView(R.id.tv_sign_in)
    TextView tvSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        apiInterface = APIClient.getClient().create(EBWebservice.class);
        if (getSupportActionBar() != null && getSupportActionBar().isShowing()) {
            getSupportActionBar().hide();
        }
        initViews();
    }

    public void initViews() {
        ButterKnife.bind(this);


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserRegisterResponse userRegisterResponse = new UserRegisterResponse();
                validateData(userRegisterResponse);
                registerUserRetrofit2Api(userRegisterResponse);
            }
        });

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchLoginPage();
            }
        });
    }

    private void validateData(UserRegisterResponse userRegisterResponse) {
        if (edtFirstName.getText() == null || StringUtility.trimAndEmptyIsNull(edtFirstName.getText().toString()) == null) {
            Toast.makeText(SignUpActivity.this, "First name not valid", Toast.LENGTH_SHORT).show();
            return;
        } else {
            userRegisterResponse.setFirstName(StringUtility.trimAndEmptyIsNull(edtFirstName.getText().toString()));
        }

        if (edtLastName.getText() == null || StringUtility.trimAndEmptyIsNull(edtLastName.getText().toString()) == null) {
            Toast.makeText(SignUpActivity.this, "Last name not valid", Toast.LENGTH_SHORT).show();
            return;
        } else {
            userRegisterResponse.setLastName(StringUtility.trimAndEmptyIsNull(edtLastName.getText().toString()));
        }

        if (edtUserName.getText() == null || StringUtility.trimAndEmptyIsNull(edtUserName.getText().toString()) == null || StringUtility.trimAndEmptyIsNull(edtUserName.getText().toString()).length() != 10) {
            Toast.makeText(SignUpActivity.this, "Mobile number not valid", Toast.LENGTH_SHORT).show();
            return;
        } else {
            userRegisterResponse.setUsername(StringUtility.trimAndEmptyIsNull(edtUserName.getText().toString()));
        }

        if (edtEmail.getText() == null || StringUtility.trimAndEmptyIsNull(edtEmail.getText().toString()) == null || !isValidEmail(StringUtility.trimAndEmptyIsNull(edtEmail.getText().toString()))) {
            Toast.makeText(SignUpActivity.this, "Email is not valid", Toast.LENGTH_SHORT).show();
            return;
        } else {
            userRegisterResponse.setEmail(StringUtility.trimAndEmptyIsNull(edtEmail.getText().toString()));
        }

        if (edtPassword.getText() == null || StringUtility.trimAndEmptyIsNull(edtPassword.getText().toString()) == null) {
            Toast.makeText(SignUpActivity.this, "Password should not be empty.", Toast.LENGTH_SHORT).show();
            return;
        } else {
            userRegisterResponse.setPassword(StringUtility.trimAndEmptyIsNull(edtPassword.getText().toString()));
        }

        if (edtConfirmPassword.getText() == null || StringUtility.trimAndEmptyIsNull(edtConfirmPassword.getText().toString()) == null || !userRegisterResponse.getPassword().equals(StringUtility.trimAndEmptyIsNull(edtConfirmPassword.getText().toString()))) {
            Toast.makeText(SignUpActivity.this, "Password does not match. ", Toast.LENGTH_SHORT).show();
            return;
        }

        if (edtAddress.getText() == null || StringUtility.trimAndEmptyIsNull(edtAddress.getText().toString()) == null) {
            Toast.makeText(SignUpActivity.this, "Address not valid", Toast.LENGTH_SHORT).show();
            return;
        } else {
            userRegisterResponse.setAddress(StringUtility.trimAndEmptyIsNull(edtAddress.getText().toString()));
        }

        if (edtPincode.getText() == null || StringUtility.trimAndEmptyIsNull(edtPincode.getText().toString()) == null || StringUtility.trimAndEmptyIsNull(edtPincode.getText().toString()).length() != 6) {
            Toast.makeText(SignUpActivity.this, "Pincode is not valid", Toast.LENGTH_SHORT).show();
            return;
        } else {
            userRegisterResponse.setPincode(StringUtility.trimAndEmptyIsNull(edtPincode.getText().toString()));
        }

        if (edtCity.getText() == null || StringUtility.trimAndEmptyIsNull(edtCity.getText().toString()) == null) {
            Toast.makeText(SignUpActivity.this, "City name is not valid", Toast.LENGTH_SHORT).show();
            return;
        } else {
            userRegisterResponse.setCity(StringUtility.trimAndEmptyIsNull(edtCity.getText().toString()));
        }
        if (edtState.getText() == null || StringUtility.trimAndEmptyIsNull(edtState.getText().toString()) == null) {
            Toast.makeText(SignUpActivity.this, "State is not valid", Toast.LENGTH_SHORT).show();
            return;
        } else {
            userRegisterResponse.setState(StringUtility.trimAndEmptyIsNull(edtState.getText().toString()));
        }
    }

    private void launchLoginPage() {
        Intent loginIntent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        SignUpActivity.this.finish();
    }

    private void registerUserRetrofit2Api(final UserRegisterResponse registerResponse) {
//        final UserRegisterResponse userRegisterResponse = new UserRegisterResponse();
        Call<UserRegisterResponse> call1 = apiInterface.registerUser(registerResponse);
        call1.enqueue(new Callback<UserRegisterResponse>() {
            @Override
            public void onResponse(Call<UserRegisterResponse> call, Response<UserRegisterResponse> response) {
                UserRegisterResponse loginResponse = response.body();
                if (loginResponse != null) {
                    String responseCode = loginResponse.getResponseCode();
                    if (responseCode != null && responseCode.equals("404")) {
                        Toast.makeText(SignUpActivity.this, "Username or password is incorrect. ", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "User registered successfully. ", Toast.LENGTH_SHORT).show();
                        Intent loginIntent = new Intent(SignUpActivity.this, LoginActivity.class);
                        startActivity(loginIntent);
                        SignUpActivity.this.finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserRegisterResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error while register user. Please contact admin ", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.getMessage());
                call.cancel();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        launchLoginPage();
    }

    public static boolean isValidEmail(String email) {
        if (email != null) {
            return email.matches(REGEX_EMAIL);
        }
        return false;
    }
}
