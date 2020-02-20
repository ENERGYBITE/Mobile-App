package com.ecar.energybite.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ecar.energybite.R;
import com.ecar.energybite.booking.BookingModel;
import com.ecar.energybite.booking.BookingStatus;
import com.ecar.energybite.booking.MyBookingAdapter;
import com.ecar.energybite.booking.PaymentModel;
import com.ecar.energybite.widget.IRecyclerItem;

import java.util.List;

public class MyBookingActivity extends BaseActivity {

    public static final String BOOKING_STATUS_KEY = "booking_status";
    private RecyclerView rvMyBookings;
    private MyBookingAdapter mMyBookingAdapter;
    private List<BookingModel> bookingModels;
    private MyBookingAdapter.OnItemSelectedListener m_onItemSelectedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_my_booking, null, false);
        initView(contentView);
        bookingModels = getIntent().getParcelableArrayListExtra(BaseActivity.USER_VEHICLES);
        init();
        mFrameLayout.addView(contentView, 0);

    }

    private void initView(View contentView) {
        rvMyBookings = contentView.findViewById(R.id.rv_my_bookings);
    }

    private void init() {
        m_onItemSelectedListener = (v, item) -> {
            BookingModel bookingModel = (BookingModel) item;
            Intent bookingStatusIntent = new Intent(MyBookingActivity.this, BookingStatusActivity.class);
            bookingStatusIntent.putExtra(BaseActivity.BOOKING_RESPONSE, bookingModel);
            startActivity(bookingStatusIntent);
            MyBookingActivity.this.finish();
        };
        setOrNotifyAdapter();
    }

    private void setOrNotifyAdapter() {
        if (mMyBookingAdapter == null) {
            mMyBookingAdapter = new MyBookingAdapter(bookingModels, m_onItemSelectedListener);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(RecyclerView.VERTICAL);
            rvMyBookings.setLayoutManager(layoutManager);
            rvMyBookings.setAdapter(mMyBookingAdapter);
        } else {
            mMyBookingAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Intent intent = new Intent(MyBookingActivity.this, MapActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }

}
