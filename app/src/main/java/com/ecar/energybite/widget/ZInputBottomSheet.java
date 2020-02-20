package com.ecar.energybite.widget;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

import com.ecar.energybite.R;
import com.ecar.energybite.util.KeyboardUtility;
import com.ecar.energybite.util.StringUtility;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
;

/**
 * Created by navin on 9/13/2019.
 */

public class ZInputBottomSheet extends BottomSheetDialog {
    private LinearLayout container;
    private ImageView btnCloseDialog;
    private TextView tvBottomSheetTitle;
    private TextView btnSubmitDialog;
    private ProgressBar pbValidating;
    private LinearLayout bottomSheetContainer;
    private CustomEditText etInput;

    private CharSequence mTitle;
    private int mTitleRes;
    private int mInputType;

    private String mValidationURL;
    private OnDismissListener m_onDismissListener;

    public ZInputBottomSheet(@NonNull Context context) {
        this(context, getDefaultThemeResId(context));
    }

    public ZInputBottomSheet(@NonNull Context context, @StyleRes int theme) {
        super(context, getThemeResId(context, theme));
    }

    public void initializeCVVView(int inputLength, OnDismissListener listener) {
        initializeView("CVV", "Enter CVV", inputLength, InputType.TYPE_CLASS_NUMBER, listener);
        if(etInput != null) {
            etInput.setPasswordType();
        }
    }

    public void initializeView(CharSequence inputHint, CharSequence dialogTitle, int maxInputLength, int inputType, OnDismissListener listener) {
        initializeView(inputHint, dialogTitle, maxInputLength, inputType, listener, null);
    }

    public void initializeView(CharSequence inputHint, CharSequence dialogTitle, int maxInputLength, int inputType, OnDismissListener listener, String validationURL ) {
        if(StringUtility.isNonEmpty(validationURL)) {
            setCanceledOnTouchOutside(false);
            mValidationURL = validationURL;
        }
        mInputType = inputType;
        m_onDismissListener = listener;
        container = (LinearLayout) View.inflate (getContext (),
                R.layout.layout_input_bottomsheet, null);
        tvBottomSheetTitle = (TextView) container.findViewById (R.id.tvBottomSheetTitle);
        btnCloseDialog = (ImageView) container.findViewById (R.id.btnCloseDialog);
        btnSubmitDialog = (TextView) container.findViewById (R.id.btnSubmitDialog);
        pbValidating = (ProgressBar) container.findViewById(R.id.pbValidating);
        bottomSheetContainer = (LinearLayout) container.findViewById (R.id.bottomSheetContainer);
        etInput = (CustomEditText) container.findViewById(R.id.etInput);

        setTitle(dialogTitle);
        if(inputHint != null && StringUtility.isNonEmpty(inputHint.toString())) {
            etInput.setHint(inputHint);
        }
        if(inputType > 0) {
            etInput.setInputType(inputType);
        }
        btnCloseDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ZInputBottomSheet.this.dismiss();
            }
        });
        btnSubmitDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtility.hide(EasyBite.getCurrentBaseActivity());
                if(StringUtility.isNonEmpty(mValidationURL) && StringUtility.isNonEmpty(etInput.getText().toString())) {
                    InputValidator validator = new InputValidator(etInput.getText().toString());
                    validator.execute();
                }
                if(m_onDismissListener != null) {
                    if(m_onDismissListener.isValid(etInput.getText().toString())) {
                        m_onDismissListener.onDismiss(etInput.getText().toString());
                        ZInputBottomSheet.this.dismiss();
                    } else {
                        etInput.setError("Please Enter Valid Value");
                    }
                } else {
                    ZInputBottomSheet.this.dismiss();
                }
            }
        });
        setContentView(container);
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

    public void setCloseButtonVisibility(int visibility) {
        if(btnCloseDialog != null)
            btnCloseDialog.setVisibility(visibility);
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

    public interface OnDismissListener {
        void onDismiss(String value);

        boolean isValid(String value);
    }

    public class InputValidator extends AsyncTask<String, Void, Boolean> {

        private String inputString;

        public InputValidator(String inputString) {
            super();
            this.inputString = inputString;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(pbValidating != null) {
                pbValidating.setVisibility(View.VISIBLE);
                btnCloseDialog.setVisibility(View.GONE);
                etInput.setInputType(InputType.TYPE_NULL);
            }
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            if (inputString != null && inputString.length() >= 3) {
                try {
//                    Uri.Builder builder = WebServiceURL.CHECK_VPA_VALIDITY.getURLBuilder();
//                    builder.appendPath(inputString);
//                    ZilliousHttpClient webClient = new ZilliousHttpClient();
//                    ZilliousHttpResponse zilliousResponse = webClient.execute(builder.toString(),
//                            ZilliousHttpClient.RequestMethod.POST, new StringEntity(""), null);
//                    if (zilliousResponse!= null && zilliousResponse.isValidResponse() && zilliousResponse.getResponseJson() != null) {
//                        String response = zilliousResponse.getResponseJson();
//                    }
                } catch (Exception e) {
                    Log.e("LocationFetcherExp", "Exp", e);
                }
            }
            return Boolean.FALSE;
        }

        @Override
        protected void onPostExecute(Boolean isValid) {
            super.onPostExecute(isValid);
            pbValidating.setVisibility(View.GONE);
            if(Boolean.TRUE.equals(isValid)) {
                if(m_onDismissListener != null) {
                    m_onDismissListener.onDismiss(inputString);
                }
                ZInputBottomSheet.this.dismiss();
            } else  {
                etInput.setError("Invalid VPA Address.");
                etInput.setInputType(mInputType);
            }

        }

        public String getInputString() {
            return inputString;
        }
    }


}
