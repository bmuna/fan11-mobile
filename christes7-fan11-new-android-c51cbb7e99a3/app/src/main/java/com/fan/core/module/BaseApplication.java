package com.fan.core.module;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.util.Log;

import com.fan.core.util.AppConstants;


/**
 * Created by mohit.soni @ 01-Oct-19.
 */

public class BaseApplication extends Application {
    public static final String TAG = BaseApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "app create");
        try {
            PackageManager packageManager = this.getPackageManager();
            PackageInfo info = packageManager.getPackageInfo(this.getPackageName(),0);
            Log.i("Package name : ",info.packageName);
            AppConstants.ROOT_PACKAGE = info.packageName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
