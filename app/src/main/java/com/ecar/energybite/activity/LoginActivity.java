package com.ecar.energybite.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ecar.energybite.EVData;
import com.ecar.energybite.LoginResponse;
import com.ecar.energybite.R;
import com.ecar.energybite.UserVehicleResponse;
import com.ecar.energybite.http.APIClient;
import com.ecar.energybite.http.EBWebservice;
import com.ecar.energybite.util.MessageUtility;
import com.ecar.energybite.util.StringUtility;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getName();

    EBWebservice apiInterface;
    @BindView(R.id.btn_sign_in)
    Button btnSignIn;

    @BindView(R.id.edt_user_name)
    EditText edtUserName;
    @BindView(R.id.edt_password)
    EditText edtPassword;
    @BindView(R.id.tv_sign_up)
    TextView tvSignUp;
    @BindView(R.id.txv_forgot_pass)
    TextView txvForgotPass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Window w = getWindow(); // in Activity's onCreate() for instance
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        apiInterface = APIClient.getClient().create(EBWebservice.class);
        if (getSupportActionBar() != null && getSupportActionBar().isShowing()) {
            getSupportActionBar().hide();
        }
        initViews();
    }

    public void initViews() {
        ButterKnife.bind(this);


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userName = edtUserName.getText().toString();
                String pass = edtPassword.getText().toString();
                if (isValid(userName, pass)) {
                    loginRetrofit2Api(userName, pass);
                }
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent signUpIntent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(signUpIntent);
                LoginActivity.this.finish();
            }
        });

        txvForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtUserName.getText() == null || StringUtility.trimAndEmptyIsNull(edtUserName.getText().toString()) == null) {
                    Toast.makeText(LoginActivity.this, "Username not valid", Toast.LENGTH_SHORT).show();
                    return;
                }
//                MessageUtility.showInfoMessage(view, "Password has been sent to mobile number " + edtUserName.getText().toString(), "");
                sendPasswordLink(edtUserName.getText().toString(), view);
            }
        });
    }

    private boolean isValid(String userName, String password) {
        if (StringUtility.trimAndEmptyIsNull(userName) == null || StringUtility.trimAndEmptyIsNull(password) == null) {
            Toast.makeText(LoginActivity.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void loginRetrofit2Api(final String userId, String password) {
        final LoginResponse login = new LoginResponse(userId, password);
        Call<LoginResponse> call1 = apiInterface.login(login);
        call1.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                if (loginResponse != null) {
                    String responseCode = loginResponse.getResponseCode();
                    if (responseCode == null || responseCode.equals("404") || responseCode.equalsIgnoreCase("false")) {
                        Toast.makeText(LoginActivity.this, "Username or password is incorrect. ", Toast.LENGTH_SHORT).show();
                    } else {
                        if (loginResponse.getUser() != null) {
                            EVData.setLoggedInUser(loginResponse.getUser());
                        }
//                        Intent homeIntent = new Intent(LoginActivity.this,
//                                HomeActivity.class);
//                        startActivity(homeIntent);
                        Intent mapIntent = new Intent(LoginActivity.this, MapActivity.class);
                        startActivity(mapIntent);
                        LoginActivity.this.finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Username or password is incorrect. ", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.getMessage());
                call.cancel();
            }
        });
    }

    private void sendPasswordLink(final String userName, final View view) {
        Call<UserVehicleResponse> call1 = apiInterface.getPasswordLink(userName);
        call1.enqueue(new Callback<UserVehicleResponse>() {
            @Override
            public void onResponse(Call<UserVehicleResponse> call, Response<UserVehicleResponse> response) {
                UserVehicleResponse userVehicleResponse = response.body();
                if (userVehicleResponse != null) {
                    String responseCode = userVehicleResponse.getResponseCode();
                    if (responseCode != null && responseCode.equals("404")) {
                        Toast.makeText(LoginActivity.this, "Not able to get results. ", Toast.LENGTH_SHORT).show();
                    } else {
                        MessageUtility.showInfoMessage(view, "Password has been sent to your registered email and password", "");
                    }
                }
            }

            @Override
            public void onFailure(Call<UserVehicleResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Not able to send password", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });
    }
}
