package com.snow.app.smartsmsfilter.util;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2016.03.08.
 */
public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getGlobalContext() {
        return context;
    }
}
