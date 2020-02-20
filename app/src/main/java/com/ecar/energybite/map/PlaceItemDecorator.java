package com.ecar.energybite.map;

import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ecar.energybite.widget.EasyBite;

public class PlaceItemDecorator extends RecyclerView.ItemDecoration {

    private final int MARGIN = 30;

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int itemCount = state.getItemCount();
        int itemPosition = parent.getChildAdapterPosition(view);

        if(itemPosition == RecyclerView.NO_POSITION){
            return;
        }

        if(itemCount == 1){
            int singleItemSideMargin = (getScreenWidthInPx()-view.getWidth())/2;
            outRect.set(singleItemSideMargin, MARGIN, singleItemSideMargin, MARGIN);
            return;
        }

        if(itemPosition == 0){
            outRect.set(2*MARGIN, MARGIN, MARGIN, MARGIN);
        }else if(itemPosition == itemCount - 1){
            outRect.set(MARGIN, MARGIN, MARGIN, MARGIN);
        }else {
            outRect.set(MARGIN, MARGIN, MARGIN, MARGIN);
        }

    }

    public static int getScreenWidthInPx(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        EasyBite.getCurrentBaseActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        return width;
    }

    public static int dpToPx(int dp) {
        DisplayMetrics displayMetrics = EasyBite.getCurrentBaseActivity().getResources().getDisplayMetrics();
        int px = Math.round (dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    public static int pxToDp(int px) {
        DisplayMetrics displayMetrics = EasyBite.getCurrentBaseActivity().getResources ().getDisplayMetrics ();
        int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }

}
