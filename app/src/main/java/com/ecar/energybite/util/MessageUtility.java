package com.ecar.energybite.util;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.ecar.energybite.activity.BaseActivity;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;


public class MessageUtility {

	public static void showInfoMessage(View view, String messageString, String actionString) {
		showInfoMessage(view,messageString, actionString, null, null);
	}
	public static void showInfoMessage(View view, String messageString, String actionString, View.OnClickListener onClickListener, Snackbar.Callback callback) {

		final Snackbar snackbar = Snackbar
				.make(view, messageString, Snackbar.LENGTH_LONG);

		if(onClickListener == null) {
			onClickListener = new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					snackbar.dismiss();
				}
			};
		}
		snackbar.setAction(actionString, onClickListener);
		if(callback != null) {
			snackbar.setCallback(callback);
		}
		// Changing message text color
		snackbar.setActionTextColor (Color.RED);

		// Changing action button text color
		View sbView = snackbar.getView();
//		CoordinatorLayout.LayoutParams params =(CoordinatorLayout.LayoutParams)sbView.getLayoutParams ();
//		params.gravity = Gravity.TOP;
//		sbView.setLayoutParams(params);
		TextView textView = (TextView) sbView.findViewById (com.google.android.material.R.id.snackbar_text);
		textView.setTextColor(Color.YELLOW);

		snackbar.show();
	}

	public static void showErrorMessage(BaseActivity activity, int messageString) {
		Snackbar snackbar = Snackbar
				.make(activity.getView (), messageString, Snackbar.LENGTH_LONG)
				.setAction("RETRY", new View.OnClickListener() {
					@Override
					public void onClick(View view) {
					}
				});

		// Changing message text color
		snackbar.setActionTextColor (Color.RED);

		// Changing action button text color
		View sbView = snackbar.getView();
//		CoordinatorLayout.LayoutParams params =(CoordinatorLayout.LayoutParams)sbView.getLayoutParams ();
//		params.gravity = Gravity.TOP;
//		sbView.setLayoutParams(params);
		TextView textView = (TextView) sbView.findViewById (com.google.android.material.R.id.snackbar_text);
		textView.setTextColor(Color.YELLOW);

		snackbar.show();
	}

	public static String getWelcomeMessage() {
		String greeting = "";
		Calendar c = Calendar.getInstance();
		int hours = c.get(Calendar.HOUR_OF_DAY);
		if (hours >= 1 && hours <= 12) {
			greeting = "Good Morning";
		} else if (hours >= 12 && hours <= 16) {
			greeting = "Good Afternoon";
		} else if (hours >= 16 && hours <= 21) {
			greeting = "Good Evening";
		} else if (hours >= 21 && hours <= 24) {
			greeting = "Good Night";
		}
		return greeting;
	}
}
