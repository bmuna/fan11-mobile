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
import com.fan.core.model.update.UpdateBean;
import com.fan.core.util.AppConstants;
import com.fan.core.util.NetworkAPI;
import com.fan.core.util.SharedPrefUtility;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.loopj.android.http.RequestParams;

import static com.fan.core.util.AppConstants.updateNull;

/**
 * Created by mohit.soni @ 02-Oct-19.
 */
public class Update extends BaseActivity {
    public static final String TAG = Register.class.getSimpleName();
    Typeface typeface;
    Button bt_update;
    EditText et_update_new, et_update_new_confirm;

    Intent intent;
    String user_id;
    SharedPrefUtility prefUtility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update);


        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.black));
        }


        typeface = AppConstants.getPoppinsLight(this);

        et_update_new = (EditText) findViewById(R.id.et_update_new);
        et_update_new_confirm = (EditText) findViewById(R.id.et_update_new_confirm);

        bt_update = (Button) findViewById(R.id.bt_update);

        et_update_new.setTypeface(typeface);
        et_update_new_confirm.setTypeface(typeface);

        bt_update.setTypeface(typeface);

        bt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppConstants.isOnline(Update.this)) {
                    if (checkField()) {
                        RequestParams params = new RequestParams();
                        params.put("user_id", user_id);
                        params.put("password", et_update_new.getText().toString());
                        params.put("confirm_password", et_update_new_confirm.getText().toString());
                        postService(NetworkAPI.CHANGE_PASSWORD, params, AppConstants.CHANGE_PASSWORD);
                        showDialog("Updating", false);
                    }
                } else {
                    showToast(getResources().getString(R.string.no_internet_connection));
                }
            }
        });
        intent = getIntent();
        if (intent != null) {
            user_id = intent.getExtras().getString("user_id");
        }
    }

    @Override
    public void onResponseListener(int requestCode, Object responseModel) {
        try {
            log(TAG, responseModel.toString());
            hideProgress();
            if (requestCode == AppConstants.CHANGE_PASSWORD) {
                Gson gson = new Gson();
                UpdateBean bean = gson.fromJson((String) responseModel, UpdateBean.class);
                updateNull(bean.getResponsedata());
                if (bean.getResponseCode() == 1) {
                    prefUtility = new SharedPrefUtility(this);
                    prefUtility.updateUserData(bean);
                    Intent intent = new Intent(Update.this, Login.class);
                    startActivity(intent);
                    finish();
                } else {
                    showToast(bean.getResponseMessage());
                }
            }
        } catch (JsonSyntaxException e) {
            log(TAG, e.getMessage());
        }
    }

    @Override
    public void onErrorListener(int requestCode, String error) {
        showToast(requestCode + " : " + error);
    }

    public boolean checkField() {
        if (et_update_new.getText().toString().equals("")) {
            showToast("enter new password");
            return false;
        }
        if (et_update_new_confirm.getText().toString().equals("")) {
            showToast("enter confirm password");
            return false;
        }
        if (!et_update_new_confirm.getText().toString().equals("")) {
            if (!et_update_new.getText().toString().equals(et_update_new_confirm.getText().toString())) {
                showToast("password do not match");
                return false;
            }
        }

        return true;
    }
}
