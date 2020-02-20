package com.ecar.energybite.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.ecar.energybite.util.DrawableUtility;
import com.ecar.energybite.util.ResourceUtility;
import com.google.android.material.textfield.TextInputEditText;



/**
 * Created by navin on 10/3/2019.
 */
public class ZEditText extends TextInputEditText {

    private CustomTextInputLayout textInputLayout;
    private int minLength;
    private int maxLength;
    private String regex;
    private boolean isUseIconTintAttr = true;

    public ZEditText(Context context) {
        this(context, null);
//		setTextColor (ResourceUtility.getColor (R.color.colorDefaultText));
//		setHintTextColor (ResourceUtility.getColor (R.color.colorHintText));
    }

    public ZEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
//		setTextColor (ResourceUtility.getColor (R.color.colorDefaultText));
//		setHintTextColor (ResourceUtility.getColor (R.color.colorHintText));
    }

    public ZEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//		setTextColor (ResourceUtility.getColor (R.color.colorDefaultText));
//		setHintTextColor (ResourceUtility.getColor (R.color.colorHintText));
    }

    public void setTextInputLayout(CustomTextInputLayout v) {
        textInputLayout = v;
    }

    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public boolean validate() {

        return true;
    }

    public void setUseIconTintAttr(boolean isUseIconTintAttr) {
        this.isUseIconTintAttr = isUseIconTintAttr;
    }

    public void addDrawableLeft(int iconId) {
        addDrawableLeft(ResourceUtility.getDrawable(iconId));
    }

    public void addDrawableLeft(Drawable icon) {
        Drawable selectedDrawable = isUseIconTintAttr ? DrawableUtility.getCustomSelectedDrawableIcon(icon) : DrawableUtility.getSelectedDrawable(icon);
        if (selectedDrawable != null) {
            selectedDrawable.setBounds(0, 0, (int) getTextSize(), (int) getTextSize());
            setCompoundDrawables(selectedDrawable, null, null, null);
        }
    }

}
