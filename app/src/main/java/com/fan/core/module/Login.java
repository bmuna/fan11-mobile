package com.fan.core.module;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginBehavior;
import com.facebook.login.widget.LoginButton;
import com.fan.core.R;
import com.fan.core.model.telegram.TelegramBean;
import com.fan.core.model.user.Responsedata;
import com.fan.core.model.user.UserBean;
import com.fan.core.module_fragment.MainFragmentActivity;
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

import org.telegram.passport.TelegramLoginButton;
import org.telegram.passport.TelegramPassport;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.fan.core.util.AppConstants.API_TELEGRAM;
import static com.fan.core.util.AppConstants.LOGIN;
import static com.fan.core.util.AppConstants.LOGIN_WITH_SOCIAL_MEDIA;
import static com.fan.core.util.AppConstants.updateNull;

/**
 * Created by mohit.soni @ 02-Oct-19.
 */
public class Login extends BaseActivity {
    public static final String TAG = Login.class.getSimpleName();
    Button bt_login, bt_login_register;
    EditText et_login_mobile_number, et_login_password;
    TextView tv_login_forget;

    SharedPrefUtility prefUtility;
    TelegramLoginButton bt_login_telegram;
    LoginButton bt_login_facebook;
    CallbackManager callbackManager;
    String name = "", id = "";
    private static final String EMAIL = "email";

    private static final int TG_RESULT = 352;
    TelegramPassport.AuthRequest req;
    Gson gson = new Gson();
    public UserBean bean;


    Responsedata responsedata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.login);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.black));
        }

        tv_login_forget = (TextView) findViewById(R.id.tv_login_forget);
        et_login_mobile_number = (EditText) findViewById(R.id.et_login_mobile_number);
        et_login_password = (EditText) findViewById(R.id.et_login_password);

        bt_login = (Button) findViewById(R.id.bt_login);
        bt_login_register = (Button) findViewById(R.id.bt_login_register);
        bt_login_telegram = (TelegramLoginButton) findViewById(R.id.bt_login_telegram);


        bt_login_facebook = (LoginButton) findViewById(R.id.bt_login_facebook);
        callbackManager = CallbackManager.Factory.create();
        bt_login_facebook.setLoginBehavior(LoginBehavior.WEB_VIEW_ONLY);
        /*bt_login_facebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            name = object.getString("name");
                            id = object.getString("id");
//                            String f_name = name.substring(0, name.indexOf(" "));
//                            String l_name = name.substring(name.indexOf(" ") + 1, name.length());
                            if (AppConstants.isOnline(Login.this)) {
                                RequestParams params = new RequestParams();
                                params.put("type", "facebook");
                                params.put("ac_id", id);
                                params.put("first_name", name);
                                params.put("last_name", "");
                                postService(NetworkAPI.LOGIN_WITH_SOCIAL_MEDIA, params, AppConstants.LOGIN_WITH_SOCIAL_MEDIA);
                                showDialog("Login In",false);
                            } else {
                                showToast(getResources().getString(R.string.no_internet_connection));
                            }
                        } catch (JSONException e) {
                        }
                    }
                }).executeAsync();
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {

            }
        });*/
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.fan.core", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {
        }
        /*bt_login_telegram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                req = new TelegramPassport.AuthRequest();
                req.botID = TELEGRAM_BOT_ID;
                req.publicKey = TELEGRAM_PUBLIC_KEY;
                req.nonce = TELEGRAM_NONCE;
                req.scope = new PassportScope(PassportScope.PHONE_NUMBER);
                TelegramPassport.request(Login.this, req, TG_RESULT);

            }
        });*/
        bt_login_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Register.class));
                finish();
            }
        });

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppConstants.isOnline(Login.this)) {
                    if (checkField()) {
                        getToken();
                    }
                } else {
                    showToast(getResources().getString(R.string.no_internet_connection));
                }
            }
        });
        tv_login_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, MobileNumber.class));

            }
        });
        /*AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));*/

        ((Button) findViewById(R.id.login_facebook)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bt_login_facebook.performClick();
            }
        });
        ((Button) findViewById(R.id.login_telegram)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bt_login_telegram.performClick();
            }
        });
        /*et_login_mobile_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                et_login_mobile_number.setText("+251 " + s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/
        prefUtility = new SharedPrefUtility(this);
    }
    @Override
    public void onResponseListener(int requestCode, Object responseModel) {
        try {
            log(TAG, responseModel.toString());
            final File root = new File(Environment.getExternalStorageDirectory() + File.separator +
                    "Fan11" + File.separator);
            root.delete();
            if (requestCode == LOGIN) {
                UserBean bean = gson.fromJson((String) responseModel, UserBean.class);
                updateNull(bean.getResponsedata());
                if (bean.getResponsedata().getMyBalance().equals("")) {
                    bean.getResponsedata().setMyBalance("0.0");
                }
                if (bean.getResponseCode() == 1) {
                    if (prefUtility != null) {
                        prefUtility.setUserData(bean);
                        launchActivity();
                    }
                } else {
                    showToast(bean.getResponsedata().getErrorMessage());
                    hideProgress();
                }
            }
            if (requestCode == LOGIN_WITH_SOCIAL_MEDIA) {
                Gson gson = new Gson();
                UserBean bean = gson.fromJson((String) responseModel, UserBean.class);
                updateNull(bean.getResponsedata());
                if (bean.getResponseCode() == 1) {
                    if (prefUtility != null) {
                        prefUtility.setUserData(bean);
                        launchActivity();
                    }
                } else {
                    showToast(bean.getResponsedata().getErrorMessage());
                    hideProgress();
                }
            }
            if (requestCode == API_TELEGRAM) {
                Gson gson = new Gson();
                TelegramBean bean = gson.fromJson((String) responseModel, TelegramBean.class);
                if (bean.getOk()) {
                    showToast("Success");
                }
            }
        }catch (JsonSyntaxException e){
            log(TAG, e.getMessage());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(this, getString(R.string.facebook_app_id));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TG_RESULT && resultCode == RESULT_OK) {
            getTelegramData();
        }
    }

    @Override
    public void onBackPressed() {
        showDoubleButtonAlertDialog(this, "Exit", "You want to exit app", false);
    }

    @Override
    public void onErrorListener(int requestCode, String error) {
        showToast(requestCode + " : " + error);
    }

    public boolean checkField() {
        if (et_login_mobile_number.getText().toString().equals("")) {
            showToast("enter mobile number");
            return false;
        }
        if (et_login_password.getText().toString().equals("")) {
            showToast("enter password");
            return false;
        }
        return true;
    }

    public void getTelegramData() {
        if (AppConstants.isOnline(Login.this)) {
            RequestParams params = new RequestParams();
            params.put("username", et_login_mobile_number.getText().toString());
            params.put("password", et_login_password.getText().toString());
            getService(NetworkAPI.API_TELEGRAM, params, API_TELEGRAM);
            showDialog("Please wait connecting to telegram", false);
        } else {
            showToast(getResources().getString(R.string.no_internet_connection));
        }
    }

    public void launchActivity() {
        hideProgress();
        startActivity(new Intent(Login.this, MainFragmentActivity.class));

        finish();
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
        showDialog("Login In", false);
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
                        params.put("username", et_login_mobile_number.getText().toString());
                        params.put("password", et_login_password.getText().toString());
                        params.put("unique_device_id", token);
                        postService(NetworkAPI.LOGIN, params, LOGIN);
                    }
                });
        return msg;
    }
}
