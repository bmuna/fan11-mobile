package com.fan.core.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

/**
 * Created by mohit.soni on 15-Jun-20.11:53 AM
 */
public class NotificationServ extends Service {
    /*private static final String TAG = "[NotificationServ]";
    private Timer timer;
    private AsyncHttpClient client;
    public int NOT = 101;
    public final String BASE_URL = "https://udwebs.in/restapi/login";

    static Context context;*/

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

   /* @Override
    public void onCreate() {
        super.onCreate();
        context = this.getApplicationContext();
        startRequest();
    }*/

    /**
     * sent notification that service is running
     */
    /*private void startForeground() {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel("default", "Fan 11", NotificationManager.IMPORTANCE_DEFAULT);
                channel.setDescription("Fan 11 league");
                mNotificationManager.createNotificationChannel(channel);
            }
            mBuilder = new NotificationCompat.Builder(getApplicationContext(), "default")
                    .setAutoCancel(true);
        } else {
            mBuilder = new NotificationCompat.Builder(this);
            mBuilder.setAutoCancel(true);
        }
        Notification notification = mBuilder.setOngoing(true)
                .setTicker(getResources().getString(R.string.app_name))
                .setContentText("Fan 11 league")
                .setSmallIcon(R.mipmap.app_icon_new)
                .setContentIntent(null)
                .setOngoing(true)
                .build();
        this.startForeground(923465, notification);
        startRequest();
    }*/

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    /**
     * first_name_change page periodically
     */
   /* protected void startRequest() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
               new RT().execute();

            }
        }, 1000, 8000);
    }*/

  /*  @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Service destroyed");
        timer.cancel();
    }
*/
   /* public void onResponseListener(int requestCode, Object responseModel) {
        log(TAG, responseModel.toString());
        Gson gson = new Gson();
        if (requestCode == NOT) {
            UserBean bean = gson.fromJson((String) responseModel, UserBean.class);
            if (bean.getResponsedata().getMyBalance().equals("")) {
                bean.getResponsedata().setMyBalance("0.0");
            }
            if (bean.getResponseCode() == 1) {
            }
        }
    }*/

    /**
     * log to console
     *
     * @param TAG activity name
     * @param msg message to print
     */
    /*public static void log(String TAG, String msg) {
        Log.i(TAG, msg);
    }*/

    /**
     * send data to post
     *
     * @param params
     * @param requestCode
     */
   /* public void postService(String url, RequestParams params, final int requestCode) {
        client = new AsyncHttpClient();
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    log(TAG, "onSuccess" + " : " + statusCode + "");
                    onResponseListener(requestCode, new String(responseBody));
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody,
                                  Throwable error) {
                try {
                    log(TAG, "requestCode" + " : " + statusCode + "");
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onStart() {
            }

            @Override
            public void onRetry(int retryNo) {
                log(TAG, "onRetry" + " : " + retryNo + "");
            }

        });
    }*/

   /* public void getService(String url, RequestParams params, final int requestCode) {
        client = new AsyncHttpClient();
        AsyncHttpResponseHandler asyncHttpResponseHandler = new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                log(TAG, "onSuccess" + " : " + statusCode + "");
                onResponseListener(requestCode, new String(responseBody));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody,
                                  Throwable error) {
                log(TAG, "requestCode" + " : " + statusCode + "");
            }

            @Override
            public void onStart() {
            }

            @Override
            public void onRetry(int retryNo) {
                log(TAG, "onRetry" + " : " + retryNo + "");
            }

        };
        get(url, params, asyncHttpResponseHandler);
    }*/

   /* public static boolean isOnline(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable()
                    && cm.getActiveNetworkInfo().isConnected()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public  class RT extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            if (isOnline(context)) {
                RequestParams params = new RequestParams();
                params.put("username", "1234567891");
                params.put("password", "123456");
                postService(BASE_URL, params, NOT);
            }
            return null;
        }
    }*/
}
