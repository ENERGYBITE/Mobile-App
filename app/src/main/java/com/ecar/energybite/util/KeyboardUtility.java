package com.ecar.energybite.util;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by navin on 8/15/2019.
 */
public class KeyboardUtility {

	public static boolean isKeyboardShowing(Activity activity){
		InputMethodManager imm = (InputMethodManager) activity.getSystemService (Activity.INPUT_METHOD_SERVICE);
		return imm.isActive();
	}

	public static void hide(Activity activity){
		if(activity != null) {
			InputMethodManager imm = (InputMethodManager) activity.getSystemService (Activity.INPUT_METHOD_SERVICE);
			if (imm.isActive() && activity.getCurrentFocus () != null){
				View view = activity.getCurrentFocus ();
				imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
			}
		}
	}

	public static void toggle(Activity activity){
		InputMethodManager imm = (InputMethodManager) activity.getSystemService (Activity.INPUT_METHOD_SERVICE);
		if (imm.isActive()){
			imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0); // hide
		} else {
			imm.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY); // show
		}
	}//end method
}