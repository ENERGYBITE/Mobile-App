package com.ecar.energybite.widget;

import android.view.View;

import com.ecar.energybite.R;
import com.ecar.energybite.util.ResourceUtility;


/**
 * Created by anoop.gupta on 9/20/2016.
 */
public class ZSpinnerGridViewHolder extends ZSpinnerViewHolder {

    public ZSpinnerGridViewHolder(View itemView) {
        super(itemView, null);
    }

    public ZSpinnerGridViewHolder(View itemView, View.OnClickListener deselectListener) {
        super(itemView, null);
    }

    @Override
    public void bindView(IZSpinnerItem item, boolean isSelected) {
        tvTitle.setText(item.getDisplayString());
        tvTitle.setTextColor(ResourceUtility.getColor(isSelected ? R.color.colorIconSelected : R.color.colorIcon));
        ivIcon.setImageDrawable(isSelected ? item.getSelectedDrawable(false) : item.getDrawable());
    }
}
