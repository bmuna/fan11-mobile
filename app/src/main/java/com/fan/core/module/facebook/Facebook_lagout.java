package com.fan.core.module.facebook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.fan.core.R;
import com.fan.core.module.BaseActivity;

/**
 * Created by mohit.soni @ 07-Oct-19.
 */
public class Facebook_lagout extends BaseActivity {
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
       /* setContentView(R.layout.google_logout_layout);
        logout = (Button) findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                Intent intent = new Intent(FacebookLogout.this, MasterFaceBook.class);
                startActivity(intent);
                finish();
            }
        });*/
    }

    @Override
    public void onResponseListener(int requestCode, Object responseModel) {

    }

    @Override
    public void onErrorListener(int requestCode, String error) {

    }
}
