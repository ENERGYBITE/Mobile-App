package com.ecar.energybite.widget;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ecar.energybite.R;
import com.ecar.energybite.util.ResourceUtility;
import com.ecar.energybite.util.StringUtility;

/**
 * Created by navin on 9/19/2019.
 */
public class ZSpinnerViewHolder extends RecyclerView.ViewHolder {

	private IZSpinnerItem item;

	protected ImageView ivIcon;
	protected TextView tvTitle;
	private TextView tvSubtitle;
	private CheckBox cbSelectItem;
	private ImageView ivDeselect;
	private boolean isDeselectable = false;

	public ZSpinnerViewHolder(View itemView, View.OnClickListener deselectListener) {
		super(itemView);
		ivIcon = (ImageView) itemView.findViewById (R.id.ivIcon);
		tvTitle = (TextView) itemView.findViewById (R.id.tvTitle);
		tvSubtitle = (TextView) itemView.findViewById (R.id.tvSubtitle);
		cbSelectItem = (CheckBox) itemView.findViewById (R.id.cbSelectItem);
		ivDeselect = (ImageView) itemView.findViewById(R.id.iv_btn_deselect);
		if(deselectListener != null){
			isDeselectable = true;
			ivDeselect.setOnClickListener(deselectListener);
		}
	}




	public IZSpinnerItem getItem() {
		return item;
	}

	public void setItem(IZSpinnerItem item) {
		this.item = item;
	}

	public void bindView(IZSpinnerItem item, boolean isSelected) {
		setItem(item);
		if(item.getDrawable() != null && ivIcon != null) {
			ivIcon.setBackground(item.getDrawable());
		} else {
			ivIcon.setBackground(null);
		}
		tvTitle.setText (item.getDisplayString());
		setSelected(isSelected);
		if(StringUtility.trimAndEmptyIsNull (item.getSubtitle ()) != null) {
			tvSubtitle.setText (item.getSubtitle ());
		} else {
			tvSubtitle.setVisibility (View.GONE);
		}
		if(isDeselectable){
			ivDeselect.setVisibility(View.VISIBLE);
		}else {
			ivDeselect.setVisibility(View.GONE);
		}

	}

	public void setSelected(boolean isSelected) {
		tvTitle.setTextColor (isSelected ? ResourceUtility.getColorByAttr(EasyBite.getCurrentBaseActivity(), R.attr.colorPrimary) :ResourceUtility.getColor (R.color.colorIcon));
		ivIcon.setImageDrawable (isSelected ? item.getSelectedDrawable (false) : item.getDrawable ());
		ivDeselect.setVisibility(isSelected ? View.VISIBLE : View.GONE);
	}
}
