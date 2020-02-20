package com.ecar.energybite.booking;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ecar.energybite.R;
import com.ecar.energybite.util.DateUtility;
import com.ecar.energybite.widget.IRecyclerItem;

public class MyBookingAdapter extends RecyclerView.Adapter<MyBookingAdapter.MyBookingViewHolder> {

    private List<BookingModel> mDataList;
    protected OnItemSelectedListener m_itemClickListener;

    public MyBookingAdapter(List<BookingModel> dataList, OnItemSelectedListener itemClickListener) {
        mDataList = dataList;
        this.m_itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MyBookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.booking_item_layout, parent, false);
        return new MyBookingViewHolder(view, m_itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyBookingViewHolder holder, int position) {
        BookingModel bookingModel = mDataList.get(position);
        holder.setItem(bookingModel);
        holder.tvTitle.setText(bookingModel.getChargerName());
        holder.tvBookingId.setText(bookingModel.getBookingId());
        if (bookingModel.getStartTime() != null) {
            Date date = DateUtility.convertServerTimeToDate(bookingModel.getStartTime());
            holder.tvSampleCollectionDate.setText(DateUtility.getDateInDDMMMYYYY(date));
            holder.tvOrderDate.setText(DateUtility.getDateInHHMM(date));
        }
        holder.tvTotalAmount.setText(bookingModel.getAmount());
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    public class MyBookingViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private TextView tvBookingId;
        private TextView tvSampleCollectionDate;
        private TextView tvOrderDate;
        private TextView tvTotalAmount;
        protected OnItemSelectedListener onItemSelectedListener;
        protected IRecyclerItem item;

        public MyBookingViewHolder(@NonNull View itemView, OnItemSelectedListener itemSelectedListener) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_label);
            tvBookingId = itemView.findViewById(R.id.tv_booking_id);
            tvSampleCollectionDate = itemView.findViewById(R.id.tv_collection_date);
            tvOrderDate = itemView.findViewById(R.id.tv_order_date);
            tvTotalAmount = itemView.findViewById(R.id.tv_total_amount);
            this.onItemSelectedListener = itemSelectedListener;
            if (onItemSelectedListener != null) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (onItemSelectedListener != null) {
                            onItemSelectedListener.onItemSelected(view, item);
                        }
                    }
                });
            }
        }

        public IRecyclerItem getItem() {
            return item;
        }

        public void setItem(IRecyclerItem item) {
            this.item = item;
        }

    }

    public interface OnItemSelectedListener {
        void onItemSelected(View v, IRecyclerItem item);
    }

}
