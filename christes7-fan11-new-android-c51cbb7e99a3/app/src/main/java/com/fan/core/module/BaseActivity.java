package com.fan.core.module;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fan.core.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

/**
 * Created by mohit.soni @ 01-Oct-19.
 */

public abstract class BaseActivity extends Activity {

    private static final String TAG = BaseActivity.class.getName();
    private static AsyncHttpClient client;
    ProgressDialog progressDialog;
    private boolean isCancelable = true;
    Dialog dialog = null;
    TextView tv_football_progress;
    ImageView iv_football;
    RelativeLayout rl_football;
    Animation iv_animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(false);
        progressDialog.setTitle("Please Wait..");
        progressDialog.setMessage("Getting Content");
        progressDialog.setCancelable(isCancelable);
        progressDialog.setCanceledOnTouchOutside(true);
        callDialog();
    }

    public void callDialog() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.football_progress);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        rl_football = (RelativeLayout) dialog.findViewById(R.id.rl_football);
        iv_football = (ImageView) dialog.findViewById(R.id.iv_football);
        tv_football_progress = (TextView) dialog.findViewById(R.id.tv_football_progress);
        iv_animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate);

    }

    public void showDialog(String message, boolean isCancelable) {
        tv_football_progress.setText("");
        dialog.setCancelable(isCancelable);
        iv_football.setAnimation(iv_animation);
        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * show alert with message
     *
     * @param context
     * @param title
     * @param message
     * @param status
     */
    public void showSingleButtonAlertDialog(Context context, String title, String message,
                                            Boolean status) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(status);
        builder.setPositiveButton(getString(android.R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        onBackPressed();
                    }
                });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * show alert with message
     *
     * @param activity
     * @param title
     * @param message
     * @param status
     */
    public void showDoubleButtonAlertDialog(final Activity activity, String title, String message,
                                            Boolean status) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(status);
        builder.setPositiveButton(getString(android.R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        activity.finish();
                    }
                });
        builder.setNegativeButton(getString(android.R.string.cancel),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public abstract void onResponseListener(int requestCode, Object responseModel);

    public abstract void onErrorListener(int requestCode, String error);

    public void showProgress(String message, boolean isCancelable, boolean isProgress) {
        progressDialog.setMessage(message);
        progressDialog.setCancelable(isCancelable);
        progressDialog.show();
    }

    public void shoProgress() {
        progressDialog.show();
    }

    public void hideProgress(boolean isProgress) {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public void hideProgress() {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public void showToast(String message) {
        Toast.makeText(BaseActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * send data to post
     *
     * @param params
     * @param requestCode
     */
    public void postService(String url, RequestParams params, final int requestCode) {
        client = new AsyncHttpClient();
        AsyncHttpResponseHandler asyncHttpResponseHandler = new AsyncHttpResponseHandler() {
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
                    onErrorListener(requestCode, getResources().getString(R.string.there_is_some_technical_issue));
                    hideProgress();
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

        };
        post(url, params, asyncHttpResponseHandler);
    }

    /**
     * send data to get
     *
     * @param params
     * @param requestCode
     */
    public void getService(String url, RequestParams params, final int requestCode) {
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
                onErrorListener(requestCode, getResources().getString(R.string.there_is_some_technical_issue));
                hideProgress();
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
    }

    /**
     * create post hit
     *
     * @param params
     * @param responseHandler
     */
    public static void post(String url, RequestParams params,
                            AsyncHttpResponseHandler responseHandler) {
        client.post(url, params, responseHandler);
    }

    public static void get(String url, RequestParams params,
                           AsyncHttpResponseHandler responseHandler) {
        client.get(url, params, responseHandler);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],
                                           int[] grantResults) {
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void log(String TAG, String msg) {
        Log.i(TAG, msg);
    }
}
