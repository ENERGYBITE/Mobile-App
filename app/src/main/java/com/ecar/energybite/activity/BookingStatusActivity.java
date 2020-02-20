package com.ecar.energybite.activity;

import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;


import com.ecar.energybite.BookingResponse;
import com.ecar.energybite.R;
import com.ecar.energybite.booking.BookingModel;
import com.ecar.energybite.booking.BookingStatus;
import com.ecar.energybite.booking.PaymentModel;
import com.ecar.energybite.http.APIClient;
import com.ecar.energybite.http.EBWebservice;
import com.ecar.energybite.util.DateUtility;

import com.ecar.energybite.util.SavePreference;
import com.ecar.energybite.util.StringUtility;
import com.ecar.energybite.widget.EasyBite;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static com.ecar.energybite.activity.MyBookingActivity.BOOKING_STATUS_KEY;
import static com.ecar.energybite.booking.BookingStatus.COMPLETE;

public class BookingStatusActivity extends BaseActivity {

    TextView tvTransactionCode;
    TextView tvChargePointTitle;
    TextView tvChargingPoint;
    TextView tvChargingType;
    TextView tvBookingTime;
    ImageView ivStatusIcon;
    private BookingModel bookingModel;
    TextView tvStatus;
    ImageView ivRefreshStatus;
    TextView tvStopTimeTitle;
    TextView tvStopTime;
    TextView tvStartReadingTitle;
    TextView tvStartReading;
    TextView tvStopReadingTitle;
    TextView tvStopReading;
    TextView tvTotalUnitTitle;
    TextView tvTotalUnit;
    TextView tvPriceTitle;
    TextView tvPrice;
    TextView tvTotalAmountTitle;
    TextView tvTotalAmount;
    EBWebservice apiInterface;
    View contentView;
    TextView tvMyAccount;
    TextView tvNewBooking;
    TextView tvViewBookings;
    ImageView ivShareDetail;
    EditText edtDiscountCode;
    TextView tvPay;
    Handler handler = new Handler();
    TextView tvBookingTimeTitle;
    TextView tvChargingTypeTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        contentView = inflater.inflate(R.layout.activity_booking_status, null, false);
        initView(contentView);
        mFrameLayout.addView(contentView, 0);

        apiInterface = APIClient.getClient().create(EBWebservice.class);
        bookingModel = getIntent().getParcelableExtra(BaseActivity.BOOKING_RESPONSE);

        if (bookingModel != null) {

            if (bookingModel.getAmount() == null) {
                initValue();
                ivRefreshStatus.setOnClickListener(view -> getBookingStatus(bookingModel));
            }else{
                changeSuccess();
            }
        }

        tvNewBooking.setOnClickListener(view -> {
            Intent intent = new Intent(BookingStatusActivity.this, MapActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });

        tvMyAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookingStatusActivity.this, UserProfileActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        tvViewBookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookingStatusActivity.this, MyBookingActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        ivShareDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareBookingDetail();
            }
        });
        tvPay.setOnClickListener(view -> {
            if (edtDiscountCode.getText() == null || StringUtility.trimAndEmptyIsNull(edtDiscountCode.getText().toString()) == null) {
                Toast.makeText(BookingStatusActivity.this, "Please enter discount code", Toast.LENGTH_SHORT).show();
                return;
            }
            String discountCode = edtDiscountCode.getText().toString();
            PaymentModel paymentModel = new PaymentModel(discountCode, bookingModel.getPaymentId());
            makePayment(paymentModel);
        });
        handler.post(runnableCode);
    }


    private Runnable runnableCode = new Runnable() {
        @Override
        public void run() {

            if (SavePreference.getPayStatus(getApplicationContext())){
                changeSuccess();
                handler.removeCallbacks(runnableCode);
            }else{
                getBookingStatus(bookingModel);
                handler.postDelayed(runnableCode, 10000);
            }

        }
    };


    private void initValue() {

        if (bookingModel.getBookingId() != null) {
            tvTransactionCode.setText(bookingModel.getBookingId());
        }
        if (bookingModel.getChargingpoint() != null) {
            tvChargingPoint.setText(bookingModel.getChargingpoint());
        }

        if (bookingModel.getStatus() == null) {
            ivStatusIcon.setImageResource(R.drawable.ic_battery_unknown_black_48dp);
        } else if (bookingModel.getBookingStatus(bookingModel.getStatus().charAt(0)) == BookingStatus.NEW) {
            ivStatusIcon.setImageResource(R.drawable.ic_battery_unknown_black_48dp);
        } else if (bookingModel.getBookingStatus(bookingModel.getStatus().charAt(0)) == BookingStatus.PROGRESS) {
            ivStatusIcon.setImageResource(R.drawable.baseline_battery_charging_full_black_48);
        } else if (bookingModel.getBookingStatus(bookingModel.getStatus().charAt(0)) == COMPLETE) {
            ivStatusIcon.setImageResource(R.drawable.icon_payment_pending_48);
        }

        if (bookingModel.getStatus() == null) {
            tvStatus.setText("Waiting for charging.");
        } else if (bookingModel.getBookingStatus(bookingModel.getStatus().charAt(0)) == BookingStatus.NEW) {
            tvStatus.setText("Waiting for charging.");
        } else if (bookingModel.getBookingStatus(bookingModel.getStatus().charAt(0)) == BookingStatus.PROGRESS) {
            tvStatus.setText("Charging in progress.");
        } else if (bookingModel.getBookingStatus(bookingModel.getStatus().charAt(0)) == COMPLETE) {
            tvStatus.setText("Charging Completed. Waiting for payment.");
            if (SavePreference.getPayStatus(getApplicationContext())){
                changeSuccess();
            }else {
                tvStatus.setText("Charging Completed. Waiting for payment.");
                tvPay.setVisibility(View.VISIBLE);
                edtDiscountCode.setVisibility(View.VISIBLE);

                tvNewBooking.setGravity(Gravity.CENTER);

                tvStopTimeTitle.setVisibility(View.VISIBLE);
                tvStopTime.setVisibility(View.VISIBLE);

                tvStartReadingTitle.setVisibility(View.VISIBLE);
                tvStartReading.setVisibility(View.VISIBLE);

                tvStopReadingTitle.setVisibility(View.VISIBLE);
                tvStopReading.setVisibility(View.VISIBLE);
                //initValue();
            }
            /*if (bookingModel.getPaymentId() != null) {

                tvStopTimeTitle.setVisibility(View.VISIBLE);
                tvStopTime.setVisibility(View.VISIBLE);

                tvStartReadingTitle.setVisibility(View.VISIBLE);
                tvStartReading.setVisibility(View.VISIBLE);

                tvStopReadingTitle.setVisibility(View.VISIBLE);
                tvStopReading.setVisibility(View.VISIBLE);
            }else {
                changeSuccess();
            }*/
        }
        if (bookingModel.getStartTime() != null) {
            tvBookingTime.setText(DateUtility.getDateInDDMMYYYYTHHMMSS(DateUtility.convertServerTimeToDate(bookingModel.getStartTime())));
        }
        if (bookingModel.getStopTime() != null) {
            tvStopTime.setText(DateUtility.getDateInDDMMYYYYTHHMMSS(DateUtility.convertServerTimeToDate(bookingModel.getStopTime())));
            /*tvStopTimeTitle.setVisibility(View.VISIBLE);
            tvStopTime.setVisibility(View.VISIBLE);*/
        }
        if (bookingModel.getStartReading() != null) {

            tvStartReading.setText(bookingModel.getStartReading());
            /*tvStartReadingTitle.setVisibility(View.VISIBLE);
            tvStartReading.setVisibility(View.VISIBLE);*/
        }
        if (bookingModel.getStopReading() != null) {

            tvStopReading.setText(bookingModel.getStopReading());
            /*tvStopReadingTitle.setVisibility(View.VISIBLE);
            tvStopReading.setVisibility(View.VISIBLE);*/
        }
        if (bookingModel.getUnit() != null) {
            tvTotalUnitTitle.setVisibility(View.VISIBLE);
            tvTotalUnit.setVisibility(View.VISIBLE);
            tvTotalUnit.setText(bookingModel.getUnit());
        }
        if (bookingModel.getPrice() != null) {
            tvPriceTitle.setVisibility(View.VISIBLE);
            tvPrice.setVisibility(View.VISIBLE);
            tvPrice.setText(bookingModel.getPrice());
        }
        if (bookingModel.getAmount() != null) {
            tvTotalAmountTitle.setVisibility(View.VISIBLE);
            tvTotalAmount.setVisibility(View.VISIBLE);
            tvTotalAmount.setText(bookingModel.getAmount());
        }
    }

    private void changeSuccess() {
        SavePreference.setPayStatus(getApplicationContext(), true);

        ivStatusIcon.setImageResource(R.drawable.baseline_check_circle_outline_black_48);
        ivStatusIcon.setColorFilter(Color.GREEN);
        tvStatus.setText("Success");
        tvStatus.setTextColor(getResources().getColor(R.color.colorGreen));

        tvMyAccount.setVisibility(View.GONE);
        tvViewBookings.setVisibility(View.GONE);
        tvNewBooking.setVisibility(View.VISIBLE);
        tvNewBooking.setGravity(Gravity.CENTER);
        edtDiscountCode.setVisibility(View.GONE);
        tvChargePointTitle.setVisibility(View.GONE);
        tvChargingPoint.setVisibility(View.GONE);
        tvChargingTypeTitle.setVisibility(View.GONE);
        tvChargingType.setVisibility(View.GONE);
        tvPay.setVisibility(View.GONE);

        tvStopTimeTitle.setVisibility(View.GONE);
        tvStopTime.setVisibility(View.GONE);
        tvStartReadingTitle.setVisibility(View.GONE);
        tvStartReading.setVisibility(View.GONE);
        tvStopReadingTitle.setVisibility(View.GONE);
        tvStopReading.setVisibility(View.GONE);

        tvBookingTimeTitle.setVisibility(View.GONE);
        tvBookingTime.setVisibility(View.GONE);

        tvPriceTitle.setVisibility(View.VISIBLE);
        tvPrice.setVisibility(View.VISIBLE);

        tvTotalUnitTitle.setVisibility(View.VISIBLE);
        tvTotalUnit.setVisibility(View.VISIBLE);

    }

    private void initView(View contentView) {

        tvTransactionCode = contentView.findViewById(R.id.tvTransactionCode);
        tvChargePointTitle = contentView.findViewById(R.id.tvChargingPointTitle);
        tvChargingPoint = contentView.findViewById(R.id.tvChargingPoint);
        tvChargingTypeTitle = contentView.findViewById(R.id.tvChargingTypeTitle);
        tvChargingType = contentView.findViewById(R.id.tvChargingType);
        tvBookingTimeTitle = contentView.findViewById(R.id.tvBookingTimeTitle);
        tvBookingTime = contentView.findViewById(R.id.tvBookingTime);
        ivStatusIcon = contentView.findViewById(R.id.ivStatusIcon);
        tvStatus = contentView.findViewById(R.id.tvStatus);
        ivRefreshStatus = contentView.findViewById(R.id.ivRefreshStatus);
        tvStopTimeTitle = contentView.findViewById(R.id.tvStopTimeTitle);
        tvStopTime = contentView.findViewById(R.id.tvStopTime);
        tvStartReadingTitle = contentView.findViewById(R.id.tvStartReadingTitle);
        tvStartReading = contentView.findViewById(R.id.tvStartReading);
        tvStopReadingTitle = contentView.findViewById(R.id.tvStopReadingTitle);
        tvStopReading = contentView.findViewById(R.id.tvStopReading);
        tvTotalUnitTitle = contentView.findViewById(R.id.tvTotalUnitTitle);
        tvTotalUnit = contentView.findViewById(R.id.tvTotalUnit);
        tvPriceTitle = contentView.findViewById(R.id.tvPriceTitle);
        tvPrice = contentView.findViewById(R.id.tvPrice);
        tvTotalAmountTitle = contentView.findViewById(R.id.tvTotalAmountTitle);
        tvTotalAmount = contentView.findViewById(R.id.tvTotalAmount);
        tvViewBookings = contentView.findViewById(R.id.tvViewBookings);
        tvMyAccount = contentView.findViewById(R.id.tvMyAccount);
        tvNewBooking = contentView.findViewById(R.id.tvNewBooking);
        ivShareDetail = contentView.findViewById(R.id.ivShareDetail);
        edtDiscountCode = contentView.findViewById(R.id.edtDiscountCode);
        tvPay = contentView.findViewById(R.id.tvPay);
    }

    @Override
    public void onBackPressed() {
        handler.removeCallbacks(runnableCode);
        Intent intent = new Intent(BookingStatusActivity.this, MapActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }


    private void getBookingStatus(final BookingModel bookingModel) {
            Call<BookingResponse> call1 = apiInterface.getBookingStatus(bookingModel.getBookingId());
            call1.enqueue(new Callback<BookingResponse>() {
                @Override
                public void onResponse(Call<BookingResponse> call, Response<BookingResponse> response) {
                    BookingResponse bookingResponse = response.body();
                    if (bookingResponse != null) {
                        String responseCode = bookingResponse.getResponseCode();
                        if (responseCode == null || responseCode.equals("404") || responseCode.equalsIgnoreCase("false")) {
                            Toast.makeText(EasyBite.getCurrentBaseActivity(), "Not able to connect the server. ", Toast.LENGTH_SHORT).show();
                        } else {


                            BookingModel bookingModel = bookingResponse.getBookingModel();
                            updateData(bookingModel);

                            if (SavePreference.getPayStatus(getApplicationContext())){
                                changeSuccess();
                                handler.removeCallbacks(runnableCode);
                            }else{
                                initValue();
                            }
//
//                        Intent bookingStatusIntent = new Intent(EasyBite.getCurrentBaseActivity(), BookingStatusActivity.class);
//                        bookingStatusIntent.putExtra(BaseActivity.BOOKING_RESPONSE, bookingModel);
//                        EasyBite.getCurrentBaseActivity().startActivity(bookingStatusIntent);
//                        EasyBite.getCurrentBaseActivity().finish();
                        }
                    }
                }


                @Override
                public void onFailure(Call<BookingResponse> call, Throwable t) {
                    Toast.makeText(EasyBite.getContext(), "Not able to connect the server. ", Toast.LENGTH_SHORT).show();
                    Toast.makeText(EasyBite.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, t.getMessage());
                    call.cancel();
                }
            });
    }

    private void makePayment(final PaymentModel paymentModel) {
        Call<BookingResponse> call1 = apiInterface.makePayment(paymentModel);
        call1.enqueue(new Callback<BookingResponse>() {
            @Override
            public void onResponse(Call<BookingResponse> call, Response<BookingResponse> response) {
                BookingResponse bookingResponse = response.body();
                if (bookingResponse != null) {
                    String responseCode = bookingResponse.getResponseCode();
                    if (responseCode == null || responseCode.equals("404") || responseCode.equalsIgnoreCase("false")) {
                        Toast.makeText(EasyBite.getCurrentBaseActivity(), "Payment Failed. ", Toast.LENGTH_SHORT).show();
                        /*SavePreference.setPayStatus(getApplicationContext(),true);
                        changeSuccess();*/

                    } else {

                        /*BookingModel newBookingModel = (BookingModel) bookingResponse.getBookingModel();
                        updateData(newBookingModel);*/

                        SavePreference.setPayStatus(getApplicationContext(),true);
                        changeSuccess();
                        handler.removeCallbacks(runnableCode);
                        /*changeSuccess();
                        *//*SharedPreferences preferences = getSharedPreferences("PAY_SUCCESS", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putBoolean("P_SUCCESS", true);
                        editor.apply();*//*

                        SavePreference.setPayStatus(getApplicationContext(),true);*/

                        /*Bundle bundle = new Bundle();
                        bundle.putBoolean("P_SUCCESS", true);
                        onSaveInstanceState(bundle);*/
//
//                        Intent bookingStatusIntent = new Intent(EasyBite.getCurrentBaseActivity(), BookingStatusActivity.class);
//                        bookingStatusIntent.putExtra(BaseActivity.BOOKING_RESPONSE, bookingModel);
//                        EasyBite.getCurrentBaseActivity().startActivity(bookingStatusIntent);
//                        EasyBite.getCurrentBaseActivity().finish();
                    }
                }
            }


            @Override
            public void onFailure(Call<BookingResponse> call, Throwable t) {
                Toast.makeText(EasyBite.getContext(), "Not able to connect the server. ", Toast.LENGTH_SHORT).show();
                Toast.makeText(EasyBite.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.getMessage());
                call.cancel();
            }
        });
    }


    private void shareBookingDetail() {
        StringBuilder sharingContentBuilder = new StringBuilder("");
        try {

            sharingContentBuilder.append("TransactionId Id: " + bookingModel.getBookingId() + "\n");
            sharingContentBuilder.append("Charging Point: " + bookingModel.getChargingpoint() + "\n");
            if (bookingModel.getStartTime() != null) {
                sharingContentBuilder.append("Start Time: " + DateUtility.getDateInDDMMYYYYTHHMMSS(DateUtility.convertServerTimeToDate(bookingModel.getStartTime())) + "\n");
            }
            if (bookingModel.getStopTime() != null) {
                sharingContentBuilder.append("Stop Time: " + DateUtility.getDateInDDMMYYYYTHHMMSS(DateUtility.convertServerTimeToDate(bookingModel.getStopTime())) + "\n");
            }
            if (bookingModel.getStartReading() != null) {
                sharingContentBuilder.append("First Reading: " + bookingModel.getStartReading() + "\n");
            }
            if (bookingModel.getStopReading() != null) {
                sharingContentBuilder.append("Last Reading: " + bookingModel.getStopReading() + "\n");
            }
            if (bookingModel.getUnit() != null) {
                sharingContentBuilder.append("Total Unit: " + bookingModel.getUnit() + "\n");
            }
            if (bookingModel.getAmount() != null) {
                sharingContentBuilder.append("Total Amount: " + bookingModel.getAmount() + "\n");
            }
            shareData(sharingContentBuilder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void updateData(BookingModel newBookingModel) {

        if(bookingModel.getStatus() == null) {
            bookingModel.setStatus("S");
        }
        if (!(bookingModel.getStatus().equalsIgnoreCase(newBookingModel.getStatus()))) {
            bookingModel.setStatus(newBookingModel.getStatus());
        }
        if (newBookingModel.getAmount() != null) {
            bookingModel.setAmount(newBookingModel.getAmount());
        }
        if (newBookingModel.getPrice() != null) {
            bookingModel.setPrice(newBookingModel.getPrice());
        }
        if (newBookingModel.getUnit() != null) {
            bookingModel.setUnit(newBookingModel.getUnit());
        }
        if (newBookingModel.getStopReading() != null) {
            bookingModel.setStopReading(newBookingModel.getStopReading());
        }
        if (newBookingModel.getStartReading() != null) {
            bookingModel.setStartReading(newBookingModel.getStartReading());
        }
        if (newBookingModel.getDiscountcode() != null) {
            bookingModel.setDiscountcode(newBookingModel.getDiscountcode());
        }
        if (newBookingModel.getStartTime() != null) {
            bookingModel.setStartTime(newBookingModel.getStartTime());
        }
        if (newBookingModel.getStopTime() != null) {
            bookingModel.setStopTime(newBookingModel.getStopTime());
        }
        if (newBookingModel.getPaymentId() != null) {
            bookingModel.setPaymentId(newBookingModel.getPaymentId());
        }
    }

    private void shareData(String body) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
//        shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        shareIntent.putExtra(Intent.EXTRA_TEXT, body);
        startActivity(Intent.createChooser(shareIntent, "Share booking details"));
    }



    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(runnableCode);
    }

    protected void onStart() {
        super.onStart();
        handler.removeCallbacks(runnableCode);
    }

    protected void onResume() {
        super.onResume();

        handler.post(runnableCode);
        /*if (!SavePreference.getPayStatus(getApplicationContext())){

            handler.post(runnableCode);
        }else{
            handler.removeCallbacks(runnableCode);
        }*/

    }

}
