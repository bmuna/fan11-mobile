package com.fan.core.module;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.core.content.ContextCompat;

import com.fan.core.R;
import com.fan.core.model.forget.ForgetBean;
import com.fan.core.model.forget.Responsedata;
import com.fan.core.util.AppConstants;
import com.fan.core.util.NetworkAPI;
import com.fan.core.util.SharedPrefUtility;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.loopj.android.http.RequestParams;

/**
 * Created by mohit.soni @ 02-Oct-19.
 */
public class MobileNumber extends BaseActivity {
    public static final String TAG = Register.class.getSimpleName();
    Typeface typeface;
    Button bt_mobile_number;
    EditText et_mobile_number;

    SharedPrefUtility prefUtility;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mobile_number);

        if(Build.VERSION.SDK_INT >= 21){
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.black));
        }

        typeface = AppConstants.getPoppinsLight(this);

        et_mobile_number = (EditText) findViewById(R.id.et_mobile_number);
        bt_mobile_number = (Button) findViewById(R.id.bt_mobile_number);

        et_mobile_number.setTypeface(typeface);
        bt_mobile_number.setTypeface(typeface);

        bt_mobile_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(AppConstants.isOnline(MobileNumber.this)){
                    if(checkField()){
                        RequestParams params = new RequestParams();
                        params.put("mobile_no",et_mobile_number.getText().toString());
                        postService(NetworkAPI.FORGET_PASSWORD, params, AppConstants.FORGET_PASSWORD);
                        showDialog("Sending OTP",true);
                    }
                }else{
                    showToast(getResources().getString(R.string.no_internet_connection));
                }

            }
        });
    }

    @Override
    public void onResponseListener(int requestCode, Object responseModel) {
        try {
            log(TAG, responseModel.toString());
            hideProgress();
            if (requestCode == AppConstants.FORGET_PASSWORD) {
                Gson gson = new Gson();
                ForgetBean bean = gson.fromJson((String) responseModel, ForgetBean.class);
                if (bean.getResponseCode() == 1) {
                    Responsedata responsedata = bean.getResponsedata();
                    Intent intent = new Intent(MobileNumber.this, OTP_Activity.class);
                    intent.putExtra("otp", responsedata.getForgetPassOtp().toString());
                    intent.putExtra("user_id", responsedata.getUserId().toString());
                    startActivity(intent);
                    finish();
                } else {
                    showToast(bean.getResponseMessage());
                }
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            log(TAG, e.getMessage());
        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorListener(int requestCode, String error) {

    }

    public boolean checkField(){
        if(et_mobile_number.getText().toString().equals("")){
            showToast("enter mobile number or email id");
            return false;
        }
        return true;
    }

}
