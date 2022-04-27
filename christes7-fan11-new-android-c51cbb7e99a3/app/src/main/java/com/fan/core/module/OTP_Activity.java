package com.fan.core.module;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.core.content.ContextCompat;

import com.fan.core.R;
import com.fan.core.model.otp.OTPBean;
import com.fan.core.model.otp.Responsedata;
import com.fan.core.util.AppConstants;
import com.fan.core.util.NetworkAPI;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.loopj.android.http.RequestParams;

/**
 * Created by mohit.soni @ 04-Oct-19.
 */
public class OTP_Activity extends BaseActivity {
    public static final String TAG = OTP_Activity.class.getSimpleName();
    EditText et_otp_1, et_otp_2, et_otp_3, et_otp_4, et_otp_5, et_otp_6;
    Button bt_otp_verify;

    Intent intent;
    String otp = "", user_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.black));
        }
        et_otp_1 = (EditText) findViewById(R.id.et_otp_1);
        et_otp_2 = (EditText) findViewById(R.id.et_otp_2);
        et_otp_3 = (EditText) findViewById(R.id.et_otp_3);
        et_otp_4 = (EditText) findViewById(R.id.et_otp_4);
        et_otp_5 = (EditText) findViewById(R.id.et_otp_5);
        et_otp_6 = (EditText) findViewById(R.id.et_otp_6);

        bt_otp_verify = (Button) findViewById(R.id.bt_otp_verify);

        bt_otp_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppConstants.isOnline(OTP_Activity.this)) {
                    if (checkField()) {
                        try {
                            String string = et_otp_1.getText().toString() + et_otp_2.getText().toString() +
                                    et_otp_3.getText().toString() + et_otp_4.getText().toString() +
                                    et_otp_5.getText().toString() + et_otp_6.getText().toString();
                            log(TAG, string);
                            if (string.equals(otp)) {
                                RequestParams params = new RequestParams();
                                params.put("user_id", user_id);
                                params.put("forget_pass_otp", otp);
                                params.put("otp", string);
                                /*params.put("otp", otp);*/
                                postService(NetworkAPI.VERIFY_OTP, params, AppConstants.VERIFY_OTP);
                                showDialog("Please Wait", false);
                            } else {
                                showToast("OTP do no match");
                            }
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    showToast(getResources().getString(R.string.no_internet_connection));
                }

            }
        });

        et_otp_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!et_otp_1.getText().toString().equals("")) {
                    et_otp_2.requestFocus();
                } else {
                    et_otp_1.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        et_otp_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!et_otp_2.getText().toString().equals("")) {
                    et_otp_3.requestFocus();
                } else {
                    et_otp_1.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        et_otp_3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!et_otp_3.getText().toString().equals("")) {
                    et_otp_4.requestFocus();
                } else {
                    et_otp_2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        et_otp_4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!et_otp_4.getText().toString().equals("")) {
                    et_otp_5.requestFocus();
                } else {
                    et_otp_3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        et_otp_5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!et_otp_5.getText().toString().equals("")) {
                    et_otp_6.requestFocus();
                } else {
                    et_otp_4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        et_otp_6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!et_otp_6.getText().toString().equals("")) {
                    et_otp_6.requestFocus();
                } else {
                    et_otp_5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        intent = getIntent();
        if (intent != null) {
            otp = intent.getExtras().getString("otp");
            user_id = intent.getExtras().getString("user_id");
        }
    }

    @Override
    public void onResponseListener(int requestCode, Object responseModel) {
        try {
            log(TAG, responseModel.toString());
            if (requestCode == AppConstants.VERIFY_OTP) {
                Gson gson = new Gson();
                OTPBean bean = gson.fromJson((String) responseModel, OTPBean.class);
                if (bean.getResponseCode() == 1) {
                    Responsedata responsedata = bean.getResponsedata();
                    Intent intent = new Intent(OTP_Activity.this, Update.class);
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
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        hideProgress();
    }

    @Override
    public void onErrorListener(int requestCode, String error) {

    }

    private String err_msg = "otp must be 6 digit";

    public boolean checkField() {
        if (et_otp_1.getText().toString().equals("")) {
            showToast(err_msg);
            return false;
        }
        if (et_otp_2.getText().toString().equals("")) {
            showToast(err_msg);
            return false;
        }
        if (et_otp_3.getText().toString().equals("")) {
            showToast(err_msg);
            return false;
        }
        if (et_otp_4.getText().toString().equals("")) {
            showToast(err_msg);
            return false;
        }
        if (et_otp_5.getText().toString().equals("")) {
            showToast(err_msg);
            return false;
        }
        if (et_otp_6.getText().toString().equals("")) {
            showToast(err_msg);
            return false;
        }
        return true;
    }
}
