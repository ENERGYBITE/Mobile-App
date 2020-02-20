package com.ecar.energybite.map;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ecar.energybite.R;
import com.ecar.energybite.activity.PrefActivity;
import com.ecar.energybite.eVehicle.ChargerStation;
import com.ecar.energybite.widget.EasyBite;


public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder> {

    public static String CHARGER_STATION = "chargerStation";
    private List<ChargerStation> chargerStations;
    TextView bookButton;

    public PlaceAdapter(List<ChargerStation> chargerStations) {
        this.chargerStations = chargerStations;
    }

    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.place_item_layout, parent, false);
        return new PlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceViewHolder holder, int position) {
        final ChargerStation chargerStation = chargerStations.get(position);
        holder.tvChargerName.setText(chargerStation.getChargerName());
        holder.tvChargerType.setText(chargerStation.getChargerType());
        holder.tvChargerCurrentType.setText(chargerStation.getOutputType());
        if (chargerStation.getStatus().equalsIgnoreCase("A")) {
            holder.tvAvailable.setVisibility(View.VISIBLE);
            holder.tvNotAvailable.setVisibility(View.GONE);
        } else {
            holder.tvNotAvailable.setVisibility(View.VISIBLE);
            holder.tvAvailable.setVisibility(View.GONE);
        }

        holder.tvBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent prefActivity = new Intent(EasyBite.getCurrentBaseActivity(),
                        PrefActivity.class);
                prefActivity.putExtra(CHARGER_STATION, chargerStation);
                EasyBite.getCurrentBaseActivity().startActivity(prefActivity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return chargerStations == null ? 0 : chargerStations.size();
    }

    public class PlaceViewHolder extends RecyclerView.ViewHolder {
        TextView tvChargerName;
        TextView tvChargerType;
        TextView tvAvailable;
        TextView tvNotAvailable;
        TextView tvBook;
        TextView tvChargerCurrentType;

        public PlaceViewHolder(@NonNull View itemView) {
            super(itemView);
            tvChargerName = itemView.findViewById(R.id.tv_charger_name);
            tvChargerType = itemView.findViewById(R.id.tv_charger_type);
            tvAvailable = itemView.findViewById(R.id.tvAvailable);
            tvNotAvailable = itemView.findViewById(R.id.tvNotAvailable);
            tvBook = itemView.findViewById(R.id.tvBook);
            tvChargerCurrentType = itemView.findViewById(R.id.tvChargerCurrentType);
        }

    }

}
