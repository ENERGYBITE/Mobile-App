package com.ecar.energybite.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.ecar.energybite.R;
import com.google.android.material.textfield.TextInputLayout;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * Created by navin on 11/16/2019.
 */

public class CustomEditText extends TextInputLayout {
    private Object collapsingTextHelper;
    private Rect bounds;
    private Method recalculateMethod;
    private int       hintTextAppearance;
   // private int       errorTextAppearance;
    private boolean isRequired;

    protected ZEditText editText;

    public CustomEditText(Context context) {
        this(context, null);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomEditText);
        try {
//            hintTextAppearance = a.getResourceId(R.styleable.CustomEditText_hintTextAppearance,
//                    R.style.TextLabelAppearance);
//            errorTextAppearance = a.getResourceId(R.styleable.CustomEditText_errorTextAppearance,
//                    R.style.ErrorLabelAppearance);
        } finally {
            a.recycle();
        }
        editText = new ZEditText(context, attrs);
        LinearLayout.LayoutParams editTextParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        editText.setHint(null);
        addView(editText, 0, editTextParams);
        init();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        adjustBounds();
    }

    private void init() {
        try {
            Field cthField = TextInputLayout.class.getDeclaredField("collapsingTextHelper");
            cthField.setAccessible(true);
            collapsingTextHelper = cthField.get(this);

            Field boundsField = collapsingTextHelper.getClass().getDeclaredField("collapsedBounds");
            boundsField.setAccessible(true);
            bounds = (Rect) boundsField.get(collapsingTextHelper);

            recalculateMethod = collapsingTextHelper.getClass().getDeclaredMethod("recalculate");

//            setHintTextAppearance(hintTextAppearance);
//            Field errorTextAppearanceField = TextInputLayout.class.getDeclaredField("mErrorTextAppearance");
//            errorTextAppearanceField.setAccessible(true);
//            errorTextAppearanceField.setInt(this, R.style.ErrorLabelAppearance);
        } catch (NoSuchFieldException e) {
            collapsingTextHelper = null;
            bounds = null;
            recalculateMethod = null;
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            collapsingTextHelper = null;
            bounds = null;
            recalculateMethod = null;
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            collapsingTextHelper = null;
            bounds = null;
            recalculateMethod = null;
            e.printStackTrace();
        }
    }

    private void adjustBounds() {
        if (collapsingTextHelper == null) {
            return;
        }

        try {
            bounds.set(getEditText().getLeft() + getEditText().getPaddingLeft(), bounds.top, bounds.right,
                    bounds.bottom);
            recalculateMethod.invoke(collapsingTextHelper);
        } catch (InvocationTargetException | IllegalAccessException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public void setMinLength(int minLength) {
        editText.setMinLength(minLength);
    }

    public void setMaxLength(int maxLength) {
        editText.setMaxLength(maxLength);
    }

    public void setRegex(String regex) {
        editText.setRegex(regex);
    }

    public boolean validate() {
        return editText.validate();
    }

    public void setInputType(int inputType) {
        editText.setInputType(inputType);
    }

    public void setPasswordType() {
        editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }

    public void setTextType() {
        editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
    }

    public void addDrawableLeft(int iconId) {
        editText.addDrawableLeft(iconId);
    }

    public void addDrawableLeft(Drawable icon) {
        editText.addDrawableLeft(icon);
    }

    public Editable getText() {
        return editText.getText();
    }

    public void setText(CharSequence text, TextView.BufferType type) {
        editText.setText(text, type != null ? type : TextView.BufferType.EDITABLE);
    }

    public final void setText(CharSequence text) {
        editText.setText(text);
    }

    public void addTextChangedListener(TextWatcher watcher) {
        editText.addTextChangedListener(watcher);
    }

    public void setOnEditorActionListener(TextView.OnEditorActionListener l) {
        editText.setOnEditorActionListener(l);
    }

    public void setClickListener(View.OnClickListener listener) {
        editText.setOnClickListener(listener);
    }

    public void setTouchListener(View.OnTouchListener listener) {
        editText.setOnTouchListener(listener);
    }

    public void setFocusChangeListener(View.OnFocusChangeListener listener){
        editText.setOnFocusChangeListener(listener);
    }

    public boolean isRequired() {
        return isRequired;
    }

    public void setRequired(boolean required) {
        isRequired = required;
        setError(getError());
    }

    @Override
    public void setError(@Nullable CharSequence error) {
        if(error == null && isRequired) {
            error = "*required";
        }
        super.setError(error);
    }

    public void setUseIconTintAttr(boolean isUseIconTintAttr){
        editText.setUseIconTintAttr(isUseIconTintAttr);
    }

    public ZEditText getZEditText(){
        return editText;
    }

}
