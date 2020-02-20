package com.ecar.energybite.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.ecar.energybite.R;
import com.ecar.energybite.widget.EasyBite;


/**
 * Created by navin on 9/19/2019.
 */
public class DrawableUtility {

    public static Drawable getDefaultDrawable(int drawableResId) {
        if(drawableResId > 0) {
            return getDrawableWithCustomColorString(drawableResId, R.color.colorIcon);
        }
        return null;
    }

    public static Drawable getSelectedDrawable(int drawableResId) {
        if(drawableResId > 0) {
            return getDrawableWithCustomColorString(drawableResId, R.color.colorIconSelected);
        }
        return null;
    }

    public static Drawable getSelectedDrawable(Drawable drawableRes) {
        if(drawableRes != null) {
            Drawable selectedDrawable = getDrawableWithCustomColor(drawableRes, R.color.colorIconSelected);
            selectedDrawable.setBounds(0, 0, 48, 48);
            return selectedDrawable;
        }
        return null;
    }

    public static Drawable getSelectedDrawableIcon(Drawable drawableRes) {
        if(drawableRes != null) {
            return getDrawableWithCustomColor(drawableRes, R.color.colorIconSelected);
        }
        return null;
    }

    public static Drawable getCustomSelectedDrawableIcon(int drawableRes) {
        return getCustomSelectedDrawableIcon(ResourceUtility.getDrawable(drawableRes));
    }

    public static Drawable getCustomSelectedDrawableIcon(Drawable drawableRes) {
        if(drawableRes != null) {
            return getDrawableWithCustomColorAttr(drawableRes, R.attr.colorSelectedIcon);
        }
        return null;
    }


    public static Drawable getDrawableWithCustomColorString(int drawableResId, int colorResId) {
        Drawable sourceDrawable = ResourceUtility.getDrawable (drawableResId);
        return getDrawableWithCustomColorString(sourceDrawable, colorResId);
    }

    public static Drawable getDrawableWithCustomColorString(Drawable sourceDrawable, int colorResId) {
        int color = Color.parseColor (ResourceUtility.getString(colorResId));
        sourceDrawable.setColorFilter (color, PorterDuff.Mode.SRC_IN);
        return sourceDrawable;
    }

    public static Drawable getDrawableWithCustomColor(Drawable sourceDrawable, int colorResId) {
        int color = ResourceUtility.getColor(colorResId);
        sourceDrawable.setColorFilter (color, PorterDuff.Mode.SRC_IN);
        return sourceDrawable;
    }

    public static Drawable getDrawableWithCustomColorAttr(Drawable sourceDrawable, int colorResId) {
        int color = ResourceUtility.getColorByAttr(EasyBite.getContext(),colorResId);
        sourceDrawable.mutate();
        sourceDrawable.setColorFilter (color, PorterDuff.Mode.SRC_IN);
        return sourceDrawable;
    }

    public static Bitmap changeImageColor(int colorResId, int sourceDrawableId) {
        return changeImageColor (ResourceUtility.getString (colorResId), ResourceUtility.getDrawable (sourceDrawableId));
    }

    public static Bitmap changeImageColor(String colorRGBString, int sourceDrawableId) {
        return changeImageColor (colorRGBString, ResourceUtility.getDrawable (sourceDrawableId));
    }

    public static Bitmap changeImageColor(String colorRGBString, Drawable sourceDrawable) {
        int color = Color.parseColor(colorRGBString);

        Bitmap sourceBitmap = convertDrawableToBitmap(sourceDrawable);
        Bitmap resultBitmap = Bitmap.createBitmap(sourceBitmap, 0, 0, sourceBitmap.getWidth() - 1, sourceBitmap.getHeight() - 1);
        Paint p = new Paint();
        ColorFilter filter = new LightingColorFilter(color, 1);
        p.setColorFilter(filter);

        Canvas canvas = new Canvas(resultBitmap);
        canvas.drawBitmap(resultBitmap, 0, 0, p);
        return resultBitmap;
    }


    public static Drawable covertBitmapToDrawable(Context context, Bitmap bitmap) {
        Drawable d = new BitmapDrawable(context.getResources(), bitmap);
        return d;
    }

    public static Bitmap convertDrawableToBitmap(int drawable, int tintColor) {
        return convertDrawableToBitmap(getDrawableWithCustomColor(ResourceUtility.getDrawable(drawable), tintColor));
    }

    public static Bitmap convertDrawableToBitmapWithTintAttr(int drawable, int tintColor) {
        return convertDrawableToBitmap(getDrawableWithCustomColorAttr(ResourceUtility.getDrawable(drawable), tintColor));
    }

    public static Bitmap convertDrawableToBitmap(int drawable) {
        return convertDrawableToBitmap(ResourceUtility.getDrawable(drawable));
    }

    public static Bitmap getBitmap(int drawable) {
        return BitmapFactory.decodeResource(EasyBite.getContext().getResources(), drawable);
    }

    public static Bitmap convertDrawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static int getDominantColor(int drawableResId) {
        Drawable drawable = ResourceUtility.getDrawable (drawableResId);
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap, 1, 1, true);
        final int color = newBitmap.getPixel(0, 0);
        newBitmap.recycle();
        return color;
    }


    public static Drawable getCircularBitmap(Context ctx, String text) {
        Bitmap bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);

//        if (bitmap.getWidth() > bitmap.getHeight()) {
//            output = Bitmap.createBitmap(bitmap.getHeight(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
//        } else {
//            output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getWidth(), Bitmap.Config.ARGB_8888);
//        }

        Canvas canvas = new Canvas(bitmap);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        float r = 0;

        if (bitmap.getWidth() > bitmap.getHeight()) {
            r = bitmap.getHeight() / 2;
        } else {
            r = bitmap.getWidth() / 2;
        }

        paint.setAntiAlias(true);
        canvas.drawColor(Color.parseColor("#BAB399"));
        paint.setColor(color);
        canvas.drawCircle(r, r, r, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        paint.setColor(Color.WHITE);
        canvas.drawText(text, 10, 10, paint);
        return covertBitmapToDrawable(ctx, bitmap);
    }

    public static Drawable getMirrorDrawable(Context ctx, Drawable drawable) {
        if(drawable instanceof BitmapDrawable) {
            Bitmap bmp = ((BitmapDrawable)drawable).getBitmap();
            Matrix matrix = new Matrix();
            matrix.preScale(-1.0f, 1.0f);
            Bitmap mirroredBitmap = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(),  matrix, false);
            return covertBitmapToDrawable(ctx, mirroredBitmap);
        }
        return null;
    }
}
