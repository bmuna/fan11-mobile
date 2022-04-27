package com.fan.core.module.facebook;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.fan.core.R;
import com.fan.core.module.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by mohit.soni @ 07-Oct-19.
 */
public class MasterFacebook extends BaseActivity {

    LoginButton signup;
    CallbackManager callbackManager;
    String name = "",id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        /*setContentView(R.layout.facebook_plus_login_layout);
        signup = (LoginButton) findViewById(R.id.login_button);

        callbackManager = CallbackManager.Factory.create();
        signup.setLoginBehavior(LoginBehavior.WEB_ONLY);
        signup.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            name = object.getString("name");
                            id = object.getString("id");
                            Intent intent = new Intent(MasterFaceBook.this, FacebookLogout.class);
                            intent.putExtra("name", name);
                            intent.putExtra("id", id);
                            startActivity(intent);
                            finish();
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
        });
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.mohit.program.facebook_login", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {
        }*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(this);
    }

    @Override
    public void onResponseListener(int requestCode, Object responseModel) {

    }

    @Override
    public void onErrorListener(int requestCode, String error) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
