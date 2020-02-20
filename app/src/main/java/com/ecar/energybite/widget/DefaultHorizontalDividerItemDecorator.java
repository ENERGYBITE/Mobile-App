package com.ecar.energybite.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by navin on 1/19/2019.
 */

public class DefaultHorizontalDividerItemDecorator extends RecyclerView.ItemDecoration {

    private Drawable mDivider;

    public DefaultHorizontalDividerItemDecorator(Context context, int resId) {
        mDivider = ContextCompat.getDrawable(context, resId);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin +
            Math.round(ViewCompat.getTranslationY(child));
            final int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }
}
