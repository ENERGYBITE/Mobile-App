package com.ecar.energybite.widget;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ecar.energybite.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;


/**
 * Created by navin on 9/23/2019.
 */
public class ZBottomSheetDialog extends BottomSheetDialog {

	private LinearLayout container;
	private TextView tvBottomSheetTitle;
	private TextView btnCloseDialog;
	private LinearLayout bottomSheetContainer;

	private CharSequence mTitle;
	private int mTitleRes;

	public ZBottomSheetDialog(@NonNull Context context) {
		this(context, getDefaultThemeResId(context));
	}

	public ZBottomSheetDialog(@NonNull Context context, @StyleRes int theme) {
		super(context, getThemeResId(context, theme));
	}

	@Override
	public void setContentView(View view) {
		container = (LinearLayout) View.inflate (getContext (),
																		   R.layout.bottomsheetdialog_layout, null);
		tvBottomSheetTitle = (TextView) container.findViewById (R.id.tvBottomSheetTitle);
		btnCloseDialog = (TextView) container.findViewById (R.id.btnCloseDialog);
		btnCloseDialog.setVisibility(View.GONE);
		bottomSheetContainer = (LinearLayout) container.findViewById (R.id.bottomSheetContainer);
		bottomSheetContainer.addView (view);
		if(mTitle != null) {
			tvBottomSheetTitle.setText (mTitle);
		} else if (mTitleRes > 0) {
			tvBottomSheetTitle.setText (mTitleRes);
		}
		btnCloseDialog.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ZBottomSheetDialog.this.dismiss();
			}
		});
		super.setContentView(container);
	}

	@Override
	public void setTitle(CharSequence title) {
		if(tvBottomSheetTitle != null) {
			tvBottomSheetTitle.setText (title);
		}
		mTitle = title;
		super.setTitle(title);
	}

	@Override
	public void setTitle(int titleId) {
		if(tvBottomSheetTitle != null) {
			tvBottomSheetTitle.setText (titleId);
		}
		mTitleRes = titleId;
	}

	public BottomSheetBehavior getBottomSheetBehaviour() {
		return BottomSheetBehavior.from((View) container.getParent());
	}

	public  void setPadding(int left, int top, int right, int bottom) {
		bottomSheetContainer.setPadding (left, top, right, bottom);
	}

	public void setCloseButtonAsIcon() {
		if(btnCloseDialog != null) {
			btnCloseDialog.setText("");
			btnCloseDialog.setBackgroundResource(R.drawable.ic_close);
		}
	}

	public void setCloseButtonVisibility(int visibility) {
		if(btnCloseDialog != null)
			btnCloseDialog.setVisibility(visibility);
	}

	public void setCloseButtonOnClickListener(View.OnClickListener listener) {
		if(btnCloseDialog != null) {
			setCloseButtonVisibility(View.VISIBLE);
			btnCloseDialog.setOnClickListener(listener);
		}
	}

	private static int getThemeResId(Context context, int themeId) {
		if (themeId == 0) {
			// If the provided theme is 0, then retrieve the dialogTheme from
			// our theme
			TypedValue outValue = new TypedValue();
			if (context.getTheme().resolveAttribute(com.google.android.material.R.attr.bottomSheetDialogTheme, outValue,
					true)) {
				themeId = outValue.resourceId;
			} else {
				// bottomSheetDialogTheme is not provided; we default to our
				// light theme
				themeId = com.google.android.material.R.style.Theme_Design_Light_BottomSheetDialog;
			}
		}
		return themeId;
	}

	public static int getDefaultThemeResId(Context ctx) {
		TypedValue outValue = new TypedValue();
		ctx.getTheme().resolveAttribute(android.R.attr.theme, outValue, true);
		return outValue.resourceId;
	}
}
