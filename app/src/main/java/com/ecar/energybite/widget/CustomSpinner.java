package com.ecar.energybite.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.ecar.energybite.R;
import com.ecar.energybite.util.CollectionUtility;
import com.ecar.energybite.util.StringUtility;
import com.google.android.material.textfield.TextInputLayout;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by navin on 11/17/2016.
 */
public class CustomSpinner extends TextInputLayout {
    private Object collapsingTextHelper;
    private Rect bounds;
    private Method recalculateMethod;
    private int        hintTextAppearance;
    private boolean    isRequired;

    protected ZSpinner m_zSpinner;

    public CustomSpinner(Context context) {
        this(context, null);
    }

    public CustomSpinner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, true);
    }

    public CustomSpinner(Context context, AttributeSet attrs, int defStyleAttr, boolean isInitView) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomSpinner);
        try {
            hintTextAppearance = a.getResourceId(R.styleable.CustomSpinner_hintTextAppearance,
                    R.style.TextLabelAppearance);
        } finally {
            a.recycle();
        }
        if (isInitView) {
            m_zSpinner = new ZSpinner(context, attrs);
            LinearLayout.LayoutParams editTextParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            m_zSpinner.setHint(null);
            addView(m_zSpinner, 0, editTextParams);
            // m_zSpinner.setBackground(null);
            init();
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        adjustBounds();
    }

    protected void init() {
        try {
            Field cthField = TextInputLayout.class.getDeclaredField("collapsingTextHelper");
            cthField.setAccessible(true);
            collapsingTextHelper = cthField.get(this);

            Field boundsField = collapsingTextHelper.getClass().getDeclaredField("collapsedBounds");
            boundsField.setAccessible(true);
            bounds = (Rect) boundsField.get(collapsingTextHelper);

            recalculateMethod = collapsingTextHelper.getClass().getDeclaredMethod("recalculate");

            setHintTextAppearance(hintTextAppearance);

//            Field errorTextAppearance = TextInputLayout.class.getDeclaredField("mErrorTextAppearance");
//            errorTextAppearance.setAccessible(true);
//            errorTextAppearance.setInt(this, R.style.ErrorLabelAppearance);

            m_zSpinner.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
//                    if (hasFocus && getHint() != null) {
//                        setHint(getHint().toString().toUpperCase());
//                    } else {
//                        setHint(getHint());
//                    }
                }
            });
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

    public ZSpinner getSpinner() {
        return m_zSpinner;
    }

    public void setOnItemSelectedListener(ZSpinner.OnItemSelectedListener onItemSelectedListener) {
        m_zSpinner.setOnItemSelectedListener(onItemSelectedListener);
    }

    public int getItemPosition(IZSpinnerItem item) {
        return m_zSpinner.getItemPosition(item);
    }

    public IZSpinnerItem getItemByPosition(int pos) {
        return m_zSpinner.getItemByPosition(pos);
    }

    public int getGridColumn() {
        return m_zSpinner.getGridColumn();
    }

    public void setGridColumn(int gridColumn) {
        m_zSpinner.setGridColumn(gridColumn);
    }

    public int getItemsOrientation() {
        return m_zSpinner.getOrientation();
    }

    public void setItemsOrientation(int orientation) {
        m_zSpinner.setOrientation(orientation);
    }

    public int getMaxRowCountForFullscreen() {
        return m_zSpinner.getMaxRowCountForFullscreen();
    }

    public void setMaxRowCountForFullscreen(int maxRowCountForFullscreen) {
        m_zSpinner.setMaxRowCountForFullscreen(maxRowCountForFullscreen);
    }

    public CharSequence getTitle() {
        return m_zSpinner.getTitle();
    }

    public void setTitle(CharSequence title) {
        m_zSpinner.setTitle(title);
    }

    public List< ? extends IZSpinnerItem> getItems() {
        return m_zSpinner.getmItems();
    }

    public <T extends IZSpinnerItem> void setItems(List<T> mItems) {
        setItems(mItems, false);
    }

    public <T extends IZSpinnerItem> void setItems(List<T> items, boolean isHideOnSingleElem) {
        setItems(items, isHideOnSingleElem, false);
    }

    public <T extends IZSpinnerItem> void setItems(List<T> items, boolean isHideOnSingleElem,
                                                   boolean isAutoSelectFirstElement) {
        m_zSpinner.setItems(items, isHideOnSingleElem, isAutoSelectFirstElement);
        setDefaultSelection();
    }

    public IZSpinnerItem getSelectedItem() {
        return m_zSpinner.getSelectedItem();
    }

    public void setSelectedItem(IZSpinnerItem selectedItem) {
        m_zSpinner.setSelectedItem(selectedItem);
    }

    public void setSelectedItemById(String selectedItemId) {
        if (!CollectionUtility.isCollectionNullOrEmpty(m_zSpinner.getmItems())
                && StringUtility.isNonEmpty(selectedItemId)) {
            for (IZSpinnerItem item : m_zSpinner.getmItems()) {
                if (item != null && item.getItemId().equals(selectedItemId)) {
                    setSelectedItem(item);
                    return;
                }
            }
        }
    }

    public void setTextSize(int textSize) {
        m_zSpinner.setTextSize(textSize);
    }

    public void setViewHint(String hint) {
        m_zSpinner.setHint(hint);
    }

    public void setEnabled(boolean isEnabled) {
        m_zSpinner.setEnabled(isEnabled);
    }

    public void clearSelection() {
        m_zSpinner.clearSelection();
    }

    public boolean isRequired() {
        return isRequired;
    }

    public void setRequired(boolean required) {
        isRequired = required;
        setDefaultSelection();
        setError(getError());
    }

    @Override
    public void setError(@Nullable CharSequence error) {
        if (error == null && isRequired) {
            error = "*required";
        }
        super.setError(error);
    }

    public void setDefaultSelection() {
        if(isRequired && !CollectionUtility.isCollectionNullOrEmpty(getItems()) && getItems().size() == 1 && getSelectedItem() == null) {
            setSelectedItem(getItems().get(0));
        }
    }

    public void showDropDown(boolean showDropDown){
        m_zSpinner.showDropDown(showDropDown);
    }

    public void setUseIconTintAttr(boolean isUseIconTintAttr){
        m_zSpinner.setUseIconTintAttr(isUseIconTintAttr);
    }

    public void setAjacentDrawable(Drawable drawableLeft, Drawable drawableTop, Drawable drawableRight, Drawable drawableBottom){
        m_zSpinner.setAjacentDrawable(drawableLeft, drawableTop, drawableRight, drawableBottom);
    }

    public boolean isDeselectable(){
        return m_zSpinner.isDeselectable();
    }

    public void setIsDeselectable(boolean isDeselectable){
        m_zSpinner.setIsDeselectable(isDeselectable);
    }

}
