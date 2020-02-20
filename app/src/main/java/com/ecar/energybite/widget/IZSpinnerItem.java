package com.ecar.energybite.widget;

import android.graphics.drawable.Drawable;

/**
 * Created by anoop.gupta on 9/20/2016.
 */
public interface IZSpinnerItem extends IRecyclerItem {

	String getItemId();

	String getDisplayString();

	String getSubtitle();

	Drawable getDrawable();

	Drawable getSelectedDrawable(boolean isUseIconTintAttr);

	boolean isSelected();

}
