package com.fan.core.module_fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.fan.core.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

/**
 * Created by mohit.soni @ 05-Oct-19.
 */
public abstract class BaseFragment extends Fragment {
    private static final String TAG = BaseFragment.class.getName();
    private static AsyncHttpClient client;
    Context context;
    ProgressDialog progressDialog;
    Dialog dialog = null;
    ImageView iv_football;
    RelativeLayout rl_football;
    Animation iv_animation;
    TextView tv_football_progress;
    public void init(Context context) {
        this.context = context;

        progressDialog = new ProgressDialog(context);
        progressDialog.setIndeterminate(false);
        progressDialog.setTitle("Please Wait..");
        progressDialog.setMessage("Getting Content");
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(true);
        callDialog();
    }

    public void callDialog() {
        dialog = new Dialog(this.context);
        dialog.setContentView(R.layout.football_progress);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        rl_football = (RelativeLayout) dialog.findViewById(R.id.rl_football);
        iv_football = (ImageView) dialog.findViewById(R.id.iv_football);
        tv_football_progress = (TextView) dialog.findViewById(R.id.tv_football_progress);
        iv_animation = AnimationUtils.loadAnimation(context,R.anim.rotate);
    }

    public void showDialog(String message, boolean isCancelable) {
        tv_football_progress.setText("");
        dialog.setCancelable(isCancelable);
        iv_football.setAnimation(iv_animation);
        dialog.show();
    }
    public void showProgress(String message, boolean isCancelable, boolean isProgress) {
        progressDialog.setMessage(message);
        progressDialog.setCancelable(isCancelable);
        progressDialog.show();
    }

    public void showDialog() {
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


    public abstract void onResponseListener(int requestCode, Object responseModel);

    public abstract void onErrorListener(int requestCode, String error);

    public void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
    public void log(String TAG, String msg) {
        Log.i(TAG, msg);
    }

    /**
     * send data to post
     *
     * @param params
     * @param requestCode
     */
    public void getService(String url, RequestParams params, final int requestCode) {
        client = new AsyncHttpClient();
        client.setTimeout(90000);
        AsyncHttpResponseHandler asyncHttpResponseHandler = new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                log(TAG,"onSuccess" + " : " + statusCode + "");
                onResponseListener(requestCode, new String(responseBody));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody,
                                  Throwable error) {
                log(TAG,"requestCode" + " : " + statusCode + "");
                onErrorListener(requestCode, getResources().getString(R.string.there_is_some_technical_issue));
            }

            @Override
            public void onStart() {
            }

            @Override
            public void onRetry(int retryNo) {
                log(TAG,"onRetry" + " : " + retryNo + "");
            }

        };
        get(url, params, asyncHttpResponseHandler);
    }

    /**
     * send data to post
     *
     * @param params
     * @param requestCode
     */
    public void postService(String url, RequestParams params, final int requestCode) {
        client = new AsyncHttpClient();
        client.setTimeout(90000);

        AsyncHttpResponseHandler asyncHttpResponseHandler = new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                log(TAG,"onSuccess" + " : " + statusCode + "");
                onResponseListener(requestCode, new String(responseBody));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody,
                                  Throwable error) {
                log(TAG,"requestCode" + " : " + statusCode + "");
                onErrorListener(requestCode, getResources().getString(R.string.there_is_some_technical_issue));
            }

            @Override
            public void onStart() {
            }

            @Override
            public void onRetry(int retryNo) {
                log(TAG,"onRetry" + " : " + retryNo + "");
            }

        };
        post(url, params, asyncHttpResponseHandler);
    }

    /**
     * send data to post
     *
     * @param params
     * @param requestCode
     */
    public void getServicewithHeader(String url, RequestParams params, final int requestCode) {
        client = new AsyncHttpClient();
        client.setTimeout(90000);

        client.addHeader("x-rapidapi-host","api-football-v1.p.rapidapi.com");
        client.addHeader("x-rapidapi-key","95895bae65mshd03a25a332bee01p1572d4jsnd7648dcf9cb6");
//        client.addHeader("x-rapidapi-key","5d33a8a895msh43362997c535ba5p16e394jsne09094c22624");
        AsyncHttpResponseHandler asyncHttpResponseHandler = new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                log(TAG,"onSuccess" + " : " + statusCode + "");
                onResponseListener(requestCode, new String(responseBody));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody,
                                  Throwable error) {
                log(TAG,"requestCode" + " : " + statusCode + "");
                onErrorListener(requestCode, getResources().getString(R.string.there_is_some_technical_issue));
            }

            @Override
            public void onStart() {
            }

            @Override
            public void onRetry(int retryNo) {
                log(TAG,"onRetry" + " : " + retryNo + "");
            }

        };
        client.get(url,params,asyncHttpResponseHandler);
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
        client.setTimeout(90000);

    }

    public static void get(String url, RequestParams params,
                           AsyncHttpResponseHandler responseHandler) {
        client.get(url, params, responseHandler);
        client.setTimeout(90000);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],
                                           int[] grantResults) {
    }


}
