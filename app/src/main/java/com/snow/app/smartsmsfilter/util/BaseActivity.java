package com.snow.app.smartsmsfilter.util;

import android.app.Activity;
import android.os.Bundle;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016.03.08.
 */
public class BaseActivity extends Activity {
    private static List<Activity> activityList = new ArrayList<Activity>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeActivity(this);
    }

    public static void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activityList.remove(activity);
    }

    public static void exitApplication() {
        for (Activity activity : activityList) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
