package com.fan.core.module;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.fan.core.R;
import com.fan.core.model.user.UserBean;
import com.fan.core.model.verify.VerifyBean;
import com.fan.core.util.AppConstants;
import com.fan.core.util.NetworkAPI;
import com.fan.core.util.SharedPrefUtility;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.loopj.android.http.RequestParams;

import static com.fan.core.util.AppConstants.updateNull;

/**
 * Created by mohit.soni @ 02-Oct-19.
 */
public class Register extends BaseActivity {
    public static final String TAG = Register.class.getSimpleName();

    TextView tv_register_date;
    /*tv_register_mobile_number_verify,
    tv_register_captcha,
    tv_register_country,
    tv_register_gender;*/
    Button bt_register_login, bt_register, bt_register_mobile_number_verify;
    EditText et_register_first_name, et_register_last_name,
            et_register_mobile_number, et_register_password,
            et_register_password_confirm, et_register_mobile_number_verify_text,
    /*et_register_captcha_confirm,*/
    et_register_referral;

    RelativeLayout rl_register_mobile_number_verify_text;
    SharedPrefUtility prefUtility;

    /* public static String captcha = "";*/
    public static int verify = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.black));
        }

        et_register_first_name = (EditText) findViewById(R.id.et_register_first_name);
        et_register_last_name = (EditText) findViewById(R.id.et_register_last_name);
        et_register_mobile_number = (EditText) findViewById(R.id.et_register_mobile_number);
        et_register_password = (EditText) findViewById(R.id.et_register_password);
        et_register_password_confirm = (EditText) findViewById(R.id.et_register_password_confirm);
        /* et_register_captcha_confirm = (EditText) findViewById(R.id.et_register_captcha_confirm);*/
        et_register_mobile_number_verify_text = (EditText) findViewById(R.id.et_register_mobile_number_verify_text);
        et_register_referral = (EditText) findViewById(R.id.et_register_referral);

        /*tv_register_mobile_number_verify = (TextView) findViewById(R.id.tv_register_mobile_number_verify);*/
        tv_register_date = (TextView) findViewById(R.id.tv_register_date);
        /*tv_register_country = (TextView) findViewById(R.id.tv_register_country);
        tv_register_captcha = (TextView) findViewById(R.id.tv_register_captcha);
        tv_register_gender = (TextView) findViewById(R.id.tv_register_gender);*/

        bt_register_login = (Button) findViewById(R.id.bt_register_login);
        bt_register = (Button) findViewById(R.id.bt_register);
        bt_register_mobile_number_verify = (Button) findViewById(R.id.bt_register_mobile_number_verify);

        rl_register_mobile_number_verify_text = (RelativeLayout) findViewById(R.id.rl_register_mobile_number_verify_text);

        bt_register_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this, Login.class));
                finish();
            }
        });

        bt_register_mobile_number_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (!et_register_mobile_number.getText().toString().equals("")) {
                        if (et_register_mobile_number.getText().toString().length() > 8) {
                            if (AppConstants.isOnline(Register.this)) {
                                RequestParams params = new RequestParams();
                                params.put("username", et_register_mobile_number.getText().toString());
                                postService(NetworkAPI.VERIFY_ACCOUNT, params, AppConstants.VERIFY_ACCOUNT);
                                showDialog(getResources().getString(R.string.sending_verification_code_to_email), true);
                            } else {
                                showToast(getResources().getString(R.string.no_internet_connection));
                            }
                        } else {
                            showToast("Mobile number not valid.");
                        }
                    } else {
                        showToast("Invalid Mobile no");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        tv_register_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppConstants.showDatePicker(tv_register_date, Register.this);
            }
        });
        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppConstants.isOnline(Register.this)) {
                    if (checkField()) {
                        getToken();
                    }
                } else {
                    showToast(getResources().getString(R.string.no_internet_connection));
                }
            }
        });
        /*tv_register_country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppConstants.country_data.size() > 0) {
                    callCountry(tv_register_country, Register.this, Register.this);
                } else {
                    AppConstants.getCountryJson(Register.this);
                }
            }
        });*/
        /*tv_register_gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callGender(tv_register_gender, Register.this, Register.this);
            }
        });*/
        /*captcha = getCaptcha();*/
        /* tv_register_captcha.setText(captcha + "");*/
    }

    @Override
    public void onBackPressed() {
        showDoubleButtonAlertDialog(this, "Exit", "You want to exit app", false);
    }

    @Override
    public void onResponseListener(int requestCode, Object responseModel) {
        try {
            log(TAG, responseModel.toString());
            hideProgress();
            /* captcha = "";*/
            if (requestCode == AppConstants.REGISTER) {
                Gson gson = new Gson();
                UserBean bean = gson.fromJson((String) responseModel, UserBean.class);
                updateNull(bean.getResponsedata());
                if (bean.getResponseCode() == 1) {
                    prefUtility = new SharedPrefUtility(this);
                    prefUtility.setUserData(bean);
                    startActivity(new Intent(Register.this, Login.class));
                } else {
                    showToast(bean.getResponseMessage());
                }
            }
            if (requestCode == AppConstants.VERIFY_ACCOUNT) {
                Gson gson = new Gson();
                VerifyBean bean = gson.fromJson((String) responseModel, VerifyBean.class);
                if (bean.getResponseCode() == 1) {
                    showToast("Verify code sent to your mobile number");
                    verify = bean.getResponsedata().getVerifyCode();
                    rl_register_mobile_number_verify_text.setVisibility(View.VISIBLE);
                    bt_register_mobile_number_verify.setVisibility(View.GONE);
                } else {
                    showToast(bean.getResponseMessage());
                }
            }
        }catch (JsonSyntaxException e){
            log(TAG,  e.getMessage());
            /*showToast("Service error");*/
        }
    }

    @Override
    public void onErrorListener(int requestCode, String error) {
        showToast(requestCode + " : " + error);
    }

    public boolean checkField() {
        if (et_register_first_name.getText().toString().equals("")) {
            showToast("enter first name");
            return false;
        }
        if (et_register_last_name.getText().toString().equals("")) {
            showToast("enter last name");
            return false;
        }
        if (et_register_mobile_number.getText().toString().equals("")) {
            showToast("enter mobile number");
            return false;
        }
        if (tv_register_date.getText().toString().equals("")) {
            showToast("select date");
            return false;
        }
        /*if (tv_register_country.getText().toString().equals("")) {
            showToast("select country");
            return false;
        }
        if (tv_register_gender.getText().toString().equals("")) {
            showToast("select gender");
            return false;
        }*/
        if (et_register_password.getText().toString().equals("")) {
            showToast("set password");
            return false;
        }
        if (et_register_password_confirm.getText().toString().equals("")) {
            showToast("set confirm password");
            return false;
        }
        if (!et_register_password_confirm.getText().toString().equals("")) {
            if (!et_register_password.getText().toString().equals(et_register_password_confirm.getText().toString())) {
                showToast("password do not match");
                /*et_register_password.setText("");
                et_register_password_confirm.setText("");*/
                return false;
            }
        }
        /*if (et_register_captcha_confirm.getText().toString().equals("")) {
            showToast("captcha can not black");
            return false;
        }
        if (!et_register_captcha_confirm.getText().toString().equals(captcha)) {
            showToast("captcha do not match");
            return false;
        }*/
        if (et_register_mobile_number_verify_text.getText().toString().isEmpty()) {
            showToast("please enter verify code sent to your mobile entered");
            return false;
        }
       /* if (et_register_referral.getText().toString().isEmpty()) {
            showToast("please enter referral code");
            return false;
        }*/
        if (!et_register_mobile_number_verify_text.getText().toString().equals(Integer.toString(verify))) {
            showToast("verify code do not match");
            return false;
        }
        return true;
    }

    public void subcribe() {
        FirebaseMessaging.getInstance().subscribeToTopic("weather")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Subscribed";
                        if (!task.isSuccessful()) {
                            msg = "Subscribed Failed";
                        }
                        Log.d(TAG, msg);
                    }
                });
    }

    String msg = "";

    public String getToken() {
        showDialog("Registering", false);
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }
                        String token = task.getResult().getToken();
                        msg = String.format("InstanceID Token: %s", token);
                        Log.d(TAG, msg);
                        RequestParams params = new RequestParams();
                        params.put("first_name", et_register_first_name.getText().toString());
                        params.put("last_name", et_register_last_name.getText().toString());
                        params.put("mobile_no", et_register_mobile_number.getText().toString());
                        params.put("dob", tv_register_date.getText().toString());
                        params.put("referral_code",et_register_referral.getText().toString());
                        /* params.put("country", tv_register_country.getText().toString());*/
                        params.put("password", et_register_password.getText().toString());
                        params.put("confirm_password", et_register_password_confirm.getText().toString());
                        params.put("unique_device_id", token);
                        /*params.put("otp", et_register_captcha_confirm.getText().toString());
                        params.put("session_otp", et_register_captcha_confirm.getText().toString());*/
                        postService(NetworkAPI.REGISTER, params, AppConstants.REGISTER);
                    }
                });
        return msg;
    }
    /**
     * ganerate captcha charater
     * @return
     */
   /* public String getCaptcha() {
        String alpha = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String numeric = "0123456789";
        String[] beta = alpha.split("");
        String[] numeric_beta = numeric.split("");
        int alpha_length = alpha.length();
        int numeric_length = numeric.length();
        while (captcha.length() < 6) {
            int a = new Random().nextInt(alpha_length);
            captcha = captcha + beta[a];
            int n = new Random().nextInt(numeric_length);
            captcha = captcha + numeric_beta[n];
        }
        return captcha.substring(0, 6);
    }*/
}
