package com.ecar.energybite.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;


import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.ecar.energybite.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

/**
 * Created by navin on 9/16/2019.
 */
public class ZSpinnerBottomSheetDialog extends BottomSheetDialog {

    public ZSpinnerBottomSheetDialog(@NonNull Context context) {
        this(context, 0);
    }

    public ZSpinnerBottomSheetDialog(@NonNull Context context, @StyleRes int theme) {
        super(context, getThemeResId(context, theme));
        // We hide the title bar for any style configuration. Otherwise, there
        // will be a gap
        // above the bottom sheet when it is expanded.
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    protected ZSpinnerBottomSheetDialog(@NonNull Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResId) {
        super.setContentView(wrapInBottomSheet(layoutResId, null, null));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(wrapInBottomSheet(0, view, null));
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(wrapInBottomSheet(0, view, params));
    }

    private View wrapInBottomSheet(int layoutResId, View view, ViewGroup.LayoutParams params) {
        final CoordinatorLayout coordinator = (CoordinatorLayout) View.inflate(getContext(),
                R.layout.design_bottom_sheet_dialog, null);
        if (layoutResId != 0 && view == null) {
            view = getLayoutInflater().inflate(layoutResId, coordinator, false);
        }
        FrameLayout bottomSheet = (FrameLayout) coordinator
                .findViewById(R.id.design_bottom_sheet);
        BottomSheetBehavior.from(bottomSheet).setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, @BottomSheetBehavior.State int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    dismiss();
                } else if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    coordinator.requestLayout();
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });
        if (params == null) {
            bottomSheet.addView(view);
        } else {
            bottomSheet.addView(view, params);
        }
        // We treat the CoordinatorLayout as outside the dialog though it is
        // technically inside
        if (shouldWindowCloseOnTouchOutside()) {
            coordinator.findViewById(R.id.touch_outside)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (isShowing()) {
                                cancel();
                            }
                        }
                    });
        }
        return coordinator;
    }

    private boolean shouldWindowCloseOnTouchOutside() {
        if (Build.VERSION.SDK_INT < 11) {
            return true;
        }
        TypedValue value = new TypedValue();
        // noinspection SimplifiableIfStatement
        if (getContext().getTheme().resolveAttribute(android.R.attr.windowCloseOnTouchOutside, value, true)) {
            return value.data != 0;
        }
        return false;
    }

    private static int getThemeResId(Context context, int themeId) {
        if (themeId == 0) {
            // If the provided theme is 0, then retrieve the dialogTheme from
            // our theme
            TypedValue outValue = new TypedValue();
            if (context.getTheme().resolveAttribute(R.attr.bottomSheetDialogTheme, outValue,
                    true)) {
                themeId = outValue.resourceId;
            } else {
                // bottomSheetDialogTheme is not provided; we default to our
                // light theme
                themeId = R.style.Theme_Design_Light_BottomSheetDialog;
            }
        }
        return themeId;
    }
}
