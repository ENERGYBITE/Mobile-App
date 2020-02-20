package com.ecar.energybite.eVehicle;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ecar.energybite.BookingResponse;
import com.ecar.energybite.R;
import com.ecar.energybite.activity.BaseActivity;
import com.ecar.energybite.activity.BookingStatusActivity;
import com.ecar.energybite.booking.BookingModel;
import com.ecar.energybite.http.APIClient;
import com.ecar.energybite.http.EBWebservice;
import com.ecar.energybite.widget.EasyBite;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class PrefAdapter extends RecyclerView.Adapter<PrefAdapter.PrefItemViewHolder> {

    private List<AppPrefModel> mDataList;
    EBWebservice apiInterface;
    String chargerName = "";

    public PrefAdapter(List<AppPrefModel> mDataList) {
        this.mDataList = mDataList;
    }

    @NonNull
    @Override
    public PrefItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.pref_item_layout, parent, false);
        apiInterface = APIClient.getClient().create(EBWebservice.class);
        return new PrefItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PrefItemViewHolder holder, int position) {
        holder.tvPrefTitle.setText(mDataList.get(position).getTitle());
        holder.tvPrefData.setText(mDataList.get(position).getData());
        if (mDataList.get(position).getTitle().equalsIgnoreCase("Charger Name")) {
            chargerName = mDataList.get(position).getData();
        }
        if (mDataList.get(position).getData() != null && mDataList.get(position).getData().equalsIgnoreCase("Available")) {
            holder.tvBook.setVisibility(View.VISIBLE);
            holder.tvBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bookCharger(chargerName, "1");
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    public class PrefItemViewHolder extends RecyclerView.ViewHolder {

        //        private ImageView ivPrefIcon;
        private TextView tvPrefTitle;
        private TextView tvPrefData;
        private TextView tvBook;

        public PrefItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPrefTitle = itemView.findViewById(R.id.tv_pref_title);
            tvPrefData = itemView.findViewById(R.id.tv_pref_data);
            tvBook = itemView.findViewById(R.id.tvBook);
        }

    }

    private void bookCharger(final String chargerName, String chargingPoint) {
        final BookingResponse bookingResponse = new BookingResponse(chargerName, chargingPoint);
        Call<BookingResponse> call1 = apiInterface.book(bookingResponse);
        call1.enqueue(new Callback<BookingResponse>() {
            @Override
            public void onResponse(Call<BookingResponse> call, Response<BookingResponse> response) {
                BookingResponse bookingResponse = response.body();
                if (bookingResponse != null) {
                    String responseCode = bookingResponse.getResponseCode();
                    if (responseCode == null || responseCode.equals("404") || responseCode.equalsIgnoreCase("false")) {
                        Toast.makeText(EasyBite.getCurrentBaseActivity(), "Not able to connect the server. ", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent bookingStatusIntent = new Intent(EasyBite.getCurrentBaseActivity(), BookingStatusActivity.class);
                        bookingStatusIntent.putExtra(BaseActivity.BOOKING_RESPONSE, (BookingModel) bookingResponse.getBookingModel());
                        EasyBite.getCurrentBaseActivity().startActivity(bookingStatusIntent);
                        EasyBite.getCurrentBaseActivity().finish();
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


}
