package com.ecar.energybite.eVehicle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ecar.energybite.R;
import com.ecar.energybite.widget.CustomTextView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserVehicleAdapter extends RecyclerView.Adapter<UserVehicleAdapter.UserVehicleItemViewHolder> {

    private List<UserVehicle> userVehicles;

    public UserVehicleAdapter(List<UserVehicle> userVehicles) {
        this.userVehicles = userVehicles;
    }

    @NonNull
    @Override
    public UserVehicleItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.user_vehicles_layout, parent, false);
        return new UserVehicleItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final UserVehicleItemViewHolder holder, int position) {
        UserVehicle userVehicle = userVehicles.get(position);
        holder.tvVehicle.setText(userVehicle.getMake());
        holder.tvModel.setText(userVehicle.getModel());
        holder.tvYear.setText(userVehicle.getYear() + "");
        holder.tvRegistrationNumber.setText(userVehicle.getEvRegNumber());
        holder.fabSubUserMetaDetails.setScaleType(ImageView.ScaleType.CENTER);
        holder.fabSubUserMetaDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.lvExtraInfo.getVisibility() == View.VISIBLE) {
                    holder.lvExtraInfo.setVisibility(View.GONE);
                    holder.fabSubUserMetaDetails.setImageResource(R.drawable.ic_caret_down);
                } else {
                    holder.lvExtraInfo.setVisibility(View.VISIBLE);
                    holder.fabSubUserMetaDetails.setImageResource(R.drawable.ic_caret_up);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return userVehicles == null ? 0 : userVehicles.size();
    }

    public class UserVehicleItemViewHolder extends RecyclerView.ViewHolder {

        //        private ImageView ivPrefIcon;
        private TextView tvVehicle;
        private CustomTextView tvModel;
        private CustomTextView tvYear;
        private CustomTextView tvRegistrationNumber;
        private CustomTextView tvUserEVId;
        private FloatingActionButton fabSubUserMetaDetails;
        private LinearLayout lvExtraInfo;

        public UserVehicleItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvVehicle = itemView.findViewById(R.id.tvVehicle);
            tvModel = itemView.findViewById(R.id.tvModel);
            tvYear = itemView.findViewById(R.id.tvYear);
            tvRegistrationNumber = itemView.findViewById(R.id.tvRegistrationNumber);
            tvUserEVId = itemView.findViewById(R.id.tvUserEVId);
            fabSubUserMetaDetails = itemView.findViewById(R.id.fabSubUserMetaDetails);
            lvExtraInfo = (LinearLayout) itemView.findViewById(R.id.lvExtraInfo);
        }

    }

}
