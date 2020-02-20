package com.ecar.energybite.widget;

import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialog;

import com.ecar.energybite.R;
import com.ecar.energybite.activity.BaseActivity;

/**
 * Created by anoop.gupta on 9/28/2016.
 */
public class ZDialog extends AppCompatDialog {

    private LinearLayout container;
    private TextView tvDialogTitle;
    private TextView btnCloseDialog;
    private LinearLayout dialogContainer;

    private CharSequence mTitle;
    private int mTitleRes;

    public ZDialog(BaseActivity activity) {
        super(activity);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    public void setContentView(View view) {
        container = (LinearLayout) View.inflate(getContext(), R.layout.bottomsheetdialog_layout, null);
        tvDialogTitle = (TextView) container.findViewById(R.id.tvBottomSheetTitle);
        btnCloseDialog = (TextView) container.findViewById(R.id.btnCloseDialog);
        btnCloseDialog.setVisibility(View.GONE);
        dialogContainer = (LinearLayout) container.findViewById(R.id.bottomSheetContainer);
        dialogContainer.addView(view);
        if (mTitle != null) {
            tvDialogTitle.setText(mTitle);
        } else if (mTitleRes > 0) {
            tvDialogTitle.setText(mTitleRes);
        }
        super.setContentView(container);
    }

    @Override
    public void setTitle(CharSequence title) {
        if (tvDialogTitle != null) {
            tvDialogTitle.setText(title);
        }
        mTitle = title;
        super.setTitle(title);
    }

    @Override
    public void setTitle(int titleId) {
        if (tvDialogTitle != null) {
            tvDialogTitle.setText(titleId);
        }
        mTitleRes = titleId;
    }

    public void setPadding(int left, int top, int right, int bottom) {
        dialogContainer.setPadding(left, top, right, bottom);
    }

    public void setCloseButtonVisibility(int visibility) {
        if (btnCloseDialog != null)
            btnCloseDialog.setVisibility(visibility);
    }

    public void setCloseButtonOnClickListener(View.OnClickListener listener) {
        if (btnCloseDialog != null)
            btnCloseDialog.setOnClickListener(listener);
    }
}
