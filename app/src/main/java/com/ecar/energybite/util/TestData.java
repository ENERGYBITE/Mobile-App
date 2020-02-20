package com.ecar.energybite.util;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import com.ecar.energybite.widget.IZSpinnerItem;


/**
 * Created by navin.ketu on 08-09-2019.
 */

public class TestData implements IZSpinnerItem, Parcelable {

    public TestData() {

    }

    @Override
    public String getSubtitle() {
        return "title";
    }

    public String getItemId() {
        return "test";
    }

    @Override
    public String getDisplayString() {
        return "test";
    }


    @Override
    public Drawable getDrawable() {
        return null;
    }

    @Override
    public Drawable getSelectedDrawable(boolean isUseIconTintAttr) {
        return null;
    }

    @Override
    public boolean isSelected() {
        return false;
    }

    public final static Creator<TestData> CREATOR = new Creator<TestData>() {


        @SuppressWarnings({
                "unchecked"
        })
        public TestData createFromParcel(Parcel in) {
            return new TestData(in);
        }

        public TestData[] newArray(int size) {
            return (new TestData[size]);
        }

    };

    protected TestData(Parcel in) {
//
    }

    public void writeToParcel(Parcel dest, int flags) {
    }

    public int describeContents() {
        return 0;
    }


}
