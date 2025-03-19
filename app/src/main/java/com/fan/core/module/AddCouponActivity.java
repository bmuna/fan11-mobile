package com.fan.core.module;

import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.fan.core.R;
import com.fan.core.model.account_otp.AccountOtp;
import com.fan.core.model.add_balance.AddBalance;
import com.fan.core.model.user.Responsedata;
import com.fan.core.util.AppConstants;
import com.fan.core.util.CircleImageView;
import com.fan.core.util.NetworkAPI;
import com.fan.core.util.SharedPrefUtility;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.loopj.android.http.RequestParams;

import static com.fan.core.util.AppConstants.ADD_BALANCE;
import static com.fan.core.util.AppConstants.SEND_OTP_FOR_PAYMENT_ON_AMOLE;
import static com.fan.core.util.AppConstants.callLanguage;
import static com.fan.core.util.AppConstants.getUserSaved;
import static com.fan.core.util.AppConstants.profile_pic;
import static com.fan.core.util.AppConstants.startTutorial;

/**
 * Created by mohit.soni @ 01-Nov-19.
 */
public class AddCouponActivity extends BaseActivity {
    public static final String TAG = AddCouponActivity.class.getSimpleName();
    TextView tv_toolbar_head, tv_toolbar_language, tv_add_balance_amount,
            tv_add_balance_amount_1, tv_add_balance_amount_2, tv_add_balance_amount_3;
    ImageView iv_toolbar_wallet_icon, iv_toolbar_icon_tutorial, iv_toolbar_back;
    CircleImageView iv_toolbar_profile_image;
    LinearLayout ll_amount_otp;
    Button bt_add_balance;
    Responsedata responsedata;
    Resources resources;
    EditText et_add_balance_amount_add, et_add_balance_amount_add_otp;
    LinearLayout ll_toolbar_language_change;
    TextView tv_toolbar_language_amheric, tv_toolbar_language_english;
    SharedPrefUtility prefUtility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_coupon);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.black));
        }
        resources = getResources();
        iv_toolbar_wallet_icon = (ImageView) findViewById(R.id.iv_toolbar_wallet_icon);
        iv_toolbar_icon_tutorial = (ImageView) findViewById(R.id.iv_toolbar_icon_tutorial);
        ll_amount_otp = (LinearLayout) findViewById(R.id.ll_amount_otp);
        iv_toolbar_profile_image = (CircleImageView) findViewById(R.id.iv_toolbar_profile_image);
        et_add_balance_amount_add_otp = (EditText) findViewById(R.id.et_add_balance_amount_add_otp);
        tv_toolbar_head = (TextView) findViewById(R.id.tv_toolbar_head);
        iv_toolbar_back = (ImageView) findViewById(R.id.iv_toolbar_back);
        tv_toolbar_language = (TextView) findViewById(R.id.tv_toolbar_language);
        tv_add_balance_amount = (TextView) findViewById(R.id.tv_add_balance_amount);
        et_add_balance_amount_add = (EditText) findViewById(R.id.et_add_balance_amount_add);
        tv_add_balance_amount_1 = (TextView) findViewById(R.id.tv_add_balance_amount_1);
        tv_add_balance_amount_2 = (TextView) findViewById(R.id.tv_add_balance_amount_2);
        tv_add_balance_amount_3 = (TextView) findViewById(R.id.tv_add_balance_amount_3);

        tv_toolbar_language_amheric = (TextView) findViewById(R.id.tv_toolbar_language_amheric);
        tv_toolbar_language_english = (TextView) findViewById(R.id.tv_toolbar_language_english);
        ll_toolbar_language_change = (LinearLayout) findViewById(R.id.ll_toolbar_language_change);

        bt_add_balance = (Button) findViewById(R.id.bt_add_balance);

        tv_toolbar_head.setText(getResources().getString(R.string.add_balance));
        iv_toolbar_icon_tutorial.setVisibility(View.VISIBLE);
        iv_toolbar_wallet_icon.setVisibility(View.GONE);
        if (profile_pic != null) {
            iv_toolbar_profile_image.setImageBitmap(profile_pic);
        }
        try {
            responsedata = getUserSaved(this);
        } catch (NullPointerException e) {
        }

        iv_toolbar_icon_tutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTutorial(AddCouponActivity.this);
            }
        });
        iv_toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        iv_toolbar_profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tv_toolbar_language_amheric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (prefUtility != null) {
                    prefUtility.setLanguage("ar");
                }
                ll_toolbar_language_change.setBackground(resources.getDrawable(R.drawable.round_button_toolbar_language_amheric));
                tv_toolbar_language_amheric.setTextColor(resources.getColor(R.color.black));
                tv_toolbar_language_english.setTextColor(resources.getColor(R.color.grey));
                callLanguage(AddCouponActivity.this, AddCouponActivity.this, AddCouponActivity.class, resources, 1);
            }
        });
        tv_toolbar_language_english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (prefUtility != null) {
                    prefUtility.setLanguage("en");
                }
                ll_toolbar_language_change.setBackground(resources.getDrawable(R.drawable.round_button_toolbar_language_english));
                tv_toolbar_language_english.setTextColor(resources.getColor(R.color.black));
                tv_toolbar_language_amheric.setTextColor(resources.getColor(R.color.grey));
                callLanguage(AddCouponActivity.this, AddCouponActivity.this, AddCouponActivity.class, resources, 0);
            }
        });
        /*tv_toolbar_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callLanguage(MainFragmentActivity.this,MainFragmentActivity.this,MainFragmentActivity.class,resources);
            }
        });*/

        tv_add_balance_amount.setText(responsedata.getMyBalance());
        et_add_balance_amount_add.setText("100");
        tv_add_balance_amount_1.setText("100");
        tv_add_balance_amount_2.setText("200");
        tv_add_balance_amount_3.setText("500");

        tv_add_balance_amount_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_add_balance_amount_add.setText("100");
            }
        });
        tv_add_balance_amount_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_add_balance_amount_add.setText("200");
            }
        });
        tv_add_balance_amount_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_add_balance_amount_add.setText("500");
            }
        });
        bt_add_balance.setText(getResources().getString(R.string.request_OTP));
        bt_add_balance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ll_amount_otp.getVisibility() == View.VISIBLE) {
                    if (!et_add_balance_amount_add_otp.getText().toString().equals("")) {
                        sendRequest();
                    } else {
                        showToast("Enter OTP sent on mobile");
                    }
                } else {
                    sendRequestOTP();
                }
            }
        });
        /*tv_toolbar_language.setText(CURRENT_LANGUAGE);*/
        prefUtility = new SharedPrefUtility(this);
        if (prefUtility != null) {
            String lng = prefUtility.getLanguage();
            if (lng.equals("en")) {
                ll_toolbar_language_change.setBackground(resources.getDrawable(R.drawable.round_button_toolbar_language_english));
                tv_toolbar_language_english.setTextColor(resources.getColor(R.color.black));
                tv_toolbar_language_amheric.setTextColor(resources.getColor(R.color.grey));
            } else {
                ll_toolbar_language_change.setBackground(resources.getDrawable(R.drawable.round_button_toolbar_language_amheric));
                tv_toolbar_language_amheric.setTextColor(resources.getColor(R.color.black));
                tv_toolbar_language_english.setTextColor(resources.getColor(R.color.grey));
            }
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        /*startActivity(new Intent(AddCouponActivity.this, Profile.class));*/
        finish();
    }

    @Override
    public void onResponseListener(int requestCode, Object responseModel) {
        try {
            log(TAG, responseModel.toString());
            Gson gson = new Gson();
            if (requestCode == ADD_BALANCE) {
                AddBalance bean = gson.fromJson((String) responseModel, AddBalance.class);
                Toast.makeText(this, bean.getResponseMessage(), Toast.LENGTH_SHORT).show();
            /*if (bean.getResponseMessage().equals("Success")) {
            } else {
            }*/
            }
            if (requestCode == SEND_OTP_FOR_PAYMENT_ON_AMOLE) {
                AccountOtp bean = gson.fromJson((String) responseModel, AccountOtp.class);
                if (bean.getResponseMessage().equals("Success")) {
                    ll_amount_otp.setVisibility(View.VISIBLE);
                    bt_add_balance.setText(getResources().getString(R.string.add_balance));
                } else {
                    Toast.makeText(this, bean.getResponseMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        } catch (JsonSyntaxException e) {
            log(TAG, e.getMessage());
        }

        hideProgress();
    }

    @Override
    public void onErrorListener(int requestCode, String error) {

    }

    public void sendRequest() {
        if (AppConstants.isOnline(this)) {
            showDialog("Adding Balance", true);
            RequestParams params = new RequestParams();
            String am = et_add_balance_amount_add.getText().toString();
            params.put("user_id", responsedata.getId());
            params.put("amount", am);
            params.put("payment_otp", et_add_balance_amount_add_otp.getText().toString());
            postService(NetworkAPI.ADD_BALANCE, params, ADD_BALANCE);
        } else {
            showToast(getResources().getString(R.string.no_internet_connection));
        }
    }

    public void sendRequestOTP() {
        if (AppConstants.isOnline(this)) {
            showDialog("Requesting OTP", true);
            RequestParams params = new RequestParams();
            params.put("mobile_no", responsedata.getMobileNo());
            postService(NetworkAPI.SEND_OTP_FOR_PAYMENT_ON_AMOLE, params, SEND_OTP_FOR_PAYMENT_ON_AMOLE);
        } else {
            showToast(getResources().getString(R.string.no_internet_connection));
        }
    }

}
