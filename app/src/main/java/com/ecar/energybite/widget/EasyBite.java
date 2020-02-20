package com.ecar.energybite.widget;

import android.app.Application;
import android.content.Context;

import com.ecar.energybite.activity.BaseActivity;


public class EasyBite extends Application {

    private static Context sApplicationContext   = null;
    private static BaseActivity currentBaseActivity;

    @Override
    public void onCreate() {
        super.onCreate();
        sApplicationContext = getApplicationContext();
    }

    public static void setCurrentBaseActivity(BaseActivity baseActivity){
        currentBaseActivity = baseActivity;
    }

    public static BaseActivity getCurrentBaseActivity(){
        return currentBaseActivity;
    }

    public static Context getContext() {
        return currentBaseActivity != null ? currentBaseActivity : sApplicationContext;
    }

    public static String getRootPackageName() {
        return getContext() != null ? getContext().getPackageName() : "";
    }
}
