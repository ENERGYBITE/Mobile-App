package com.ecar.energybite.widget;
import android.content.Context;
import android.text.InputType;
import android.util.AttributeSet;

/**
 * Created by navin on 13-9-2017.
 */

public class CustomTextView extends CustomEditText {

    public CustomTextView(Context context) {
        this(context, null);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context,attrs, defStyleAttr);
        if(editText != null) {
            editText.setBackgroundResource(android.R.color.transparent);
            editText.setInputType(InputType.TYPE_NULL);
            editText.setFocusable(false);
            editText.setClickable(false);
        }
        setClickable(false);
        setFocusable(false);
    }


}
