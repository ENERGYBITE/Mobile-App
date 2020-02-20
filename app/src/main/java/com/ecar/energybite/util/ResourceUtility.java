package com.ecar.energybite.util;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.ecar.energybite.widget.EasyBite;

public class ResourceUtility {

    public static float getDimens(int dimResId) {
        return EasyBite.getContext().getResources().getDimension(dimResId);
    }

    public static float getFloat(int dimResId) {
        TypedValue typedValue = new TypedValue();
        EasyBite.getContext().getResources().getValue(dimResId, typedValue, true);
        return typedValue.getFloat();
    }

    public static boolean getBool(int boolResId) {
        return EasyBite.getContext().getResources().getBoolean(boolResId);
    }

    public static int getInt(int intResId) {
        return EasyBite.getContext().getResources().getInteger(intResId);
    }

    public static String getString(int strResId) {
        return EasyBite.getContext().getResources().getString(strResId);
    }

    public static String getString(int strResId, Object... options) {
        return EasyBite.getContext().getResources().getString(strResId, options);
    }

    public static String getQuantityString(int strResId, int quantity) {
        return EasyBite.getContext().getResources().getQuantityString(strResId, quantity);
    }

    public static String getPluralQuantityString(int strResId, int quantity, int count) {
        return EasyBite.getContext().getResources().getQuantityString(strResId, quantity, count);
    }

    public static String[] getStringArray(int strArrId) {
        return EasyBite.getContext().getResources().getStringArray(strArrId);
    }

    public static Drawable getDrawable(int id) {
        return EasyBite.getContext().getResources().getDrawable(id, EasyBite.getContext().getTheme());
    }

    public static int dpToPx(int dp) {
        DisplayMetrics displayMetrics = EasyBite.getContext().getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    public static int pxToDp(int px) {
        DisplayMetrics displayMetrics = EasyBite.getContext().getResources().getDisplayMetrics();
        int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }

    public static Drawable resize(Drawable image, int height, int width) {
        Bitmap b = ((BitmapDrawable) image).getBitmap();
        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, width, height, false);
        return new BitmapDrawable(EasyBite.getContext().getResources(), bitmapResized);
    }

    public static int getColor(int colorResId) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            return EasyBite.getContext().getResources().getColor(colorResId, EasyBite.getContext().getTheme());
        } else {
            return EasyBite.getContext().getResources().getColor(colorResId);
        }
    }

    public static String getStringByAttr(Context context, int strResId) {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(strResId, typedValue, true);
        TypedArray arr =
                context.obtainStyledAttributes(typedValue.data, new int[]{
                        strResId});
        String primaryColor = arr.getString(0);
        return primaryColor;
    }

    public static int getColorByAttr(Context context, int colorResId) {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(colorResId, typedValue, true);
        TypedArray arr =
                context.obtainStyledAttributes(typedValue.data, new int[]{
                        colorResId});
        int primaryColor = arr.getColor(0, -1);
        return primaryColor;
    }

    public static Drawable getDrawableByAttr(Context context, int drawableId) {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(drawableId, typedValue, true);
        TypedArray arr =
                context.obtainStyledAttributes(typedValue.data, new int[]{
                        drawableId});
        Drawable drawable = arr.getDrawable(0);
        return drawable;
    }

    public static int getDrawableIdByAttr(Context context, int drawableId) {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(drawableId, typedValue, true);
        TypedArray arr =
                context.obtainStyledAttributes(typedValue.data, new int[]{
                        drawableId});
        return arr.getResourceId(0, 0);
    }

    public static int[] getIntArray(int intArrId) {
        return EasyBite.getContext().getResources().getIntArray(intArrId);
    }

    public static int getDrawableByName(String iconName) {
        return EasyBite.getContext().getResources().getIdentifier(iconName, "drawable",
                EasyBite.getRootPackageName());
    }

    public static String getStringByName(String stringName) {
        return getString(EasyBite.getContext().getResources().getIdentifier(stringName, "string",
                EasyBite.getRootPackageName()));
    }

    public static int getCountryFlag(String countryCode) {
        if (StringUtility.isNonEmpty(countryCode)) {
            return getDrawableByName("country_" + countryCode.toLowerCase());
        }
        return 0;
    }

    public static int getScreenWidthInPx() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        EasyBite.getCurrentBaseActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        return width;
    }

    public static int getScreenHeightInPx() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        EasyBite.getCurrentBaseActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        return height;
    }

    public static int getScreenWidthInDp() {
        return pxToDp(getScreenWidthInPx());
    }

    public static int getScreenHeightInDp() {
        return pxToDp(getScreenHeightInPx());
    }

    public static int spToPx(float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, EasyBite.getCurrentBaseActivity().getResources().getDisplayMetrics());
    }

    public static int dpToSp(float dp) {
        return (int) (dpToPx((int) dp) / EasyBite.getCurrentBaseActivity().getResources().getDisplayMetrics().scaledDensity);
    }

}
