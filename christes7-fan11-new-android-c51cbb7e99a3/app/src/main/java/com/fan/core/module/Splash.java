package com.fan.core.module;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.fan.core.R;
import com.fan.core.module_fragment.MainFragmentActivity;
import com.fan.core.util.AppConstants;
import com.fan.core.util.SharedPrefUtility;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import static com.fan.core.util.AppConstants.getCountryJson;


public class Splash extends BaseActivity {
    public static final String TAG = Register.class.getSimpleName();
    private final int SPLASH_TIME_OUT = 2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT > 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "fan_11_channel";
            String channelName = "Fan 11";
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        /*serviceTask();*/
        getCountryJson(this);
        new AppConstants(this);

       /*if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d(TAG, "Key: " + key + " Value: " + value);
            }
        }*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    SharedPrefUtility prefUtility = new SharedPrefUtility(Splash.this);
                    boolean access = prefUtility.getAccess();
                    boolean landing = prefUtility.getlanding();
                    if (landing) {
                        prefUtility.setlanding(false);
                        intentActivity(LandingActivity.class);
                    } else {
                        if (access) {
                            intentActivity(MainFragmentActivity.class);
                        } else
                            intentActivity(Login.class);
                    }

                } catch (NullPointerException e) {
                }
            }
        }, SPLASH_TIME_OUT);
    }

    @Override
    public void onResponseListener(int requestCode, Object responseModel) {

    }

    @Override
    public void onErrorListener(int requestCode, String error) {

    }

    /**
     * start new activity
     *
     * @param splashClass
     */
    private void intentActivity(Class<?> splashClass) {
        Intent intent = new Intent(Splash.this, splashClass);
        startActivity(intent);
        finish();
    }

    /*public void serviceTask() {
        AppConstants.isServiceRunning(this.getApplicationContext(),CLASSES);
        for (int i = 0; i < AppConstants.SERVICE_CLASS.size(); i++) {
            boolean status = AppConstants.SERVICE_CLASS.get(AppConstants.CLASSES[i]);
            if (!status) {
                Intent serviceIntent = new Intent(this,NotificationServ.class);
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                    Splash.this.startForegroundService(serviceIntent);
//                } else {
                    startService(serviceIntent);
//                }
            }
        }
    }*/
}
