package com.fan.core.module;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.fan.core.R;
import com.fan.core.model.referral.Referral;
import com.fan.core.model.user.Responsedata;
import com.fan.core.util.AppConstants;
import com.fan.core.util.CircleImageView;
import com.fan.core.util.NetworkAPI;
import com.fan.core.util.SharedPrefUtility;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.loopj.android.http.RequestParams;

import static com.fan.core.util.AppConstants.MY_REFERRALS;
import static com.fan.core.util.AppConstants.callLanguage;
import static com.fan.core.util.AppConstants.getUserSaved;
import static com.fan.core.util.AppConstants.profile_pic;
import static com.fan.core.util.AppConstants.startTutorial;

/**
 * Created by mohit.soni @ 19-Oct-19.
 */
public class ReferralActivity extends BaseActivity {
    public static final String TAG = ReferralActivity.class.getSimpleName();

    TextView tv_toolbar_head, tv_toolbar_language, tv_my_referral_amount_free, tv_my_referral_code;
    ImageView iv_toolbar_back, iv_toolbar_icon, iv_toolbar_wallet_icon, iv_toolbar_icon_tutorial, iv_my_referral_code;
    CircleImageView iv_toolbar_profile_image;

    Responsedata responsedata;
    Resources resources;
    LinearLayout ll_toolbar_language_change;
    TextView tv_toolbar_language_amheric, tv_toolbar_language_english;
    SharedPrefUtility prefUtility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_referrals);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.black));
        }
        resources = getResources();
        iv_toolbar_icon = (ImageView) findViewById(R.id.iv_toolbar_wallet_icon);
        iv_toolbar_profile_image = (CircleImageView) findViewById(R.id.iv_toolbar_profile_image);
        iv_toolbar_wallet_icon = (ImageView) findViewById(R.id.iv_toolbar_wallet_icon);
        iv_toolbar_icon_tutorial = (ImageView) findViewById(R.id.iv_toolbar_icon_tutorial);
        tv_toolbar_head = (TextView) findViewById(R.id.tv_toolbar_head);
        iv_toolbar_back = (ImageView) findViewById(R.id.iv_toolbar_back);
        iv_my_referral_code = (ImageView) findViewById(R.id.iv_my_referral_code);
        tv_toolbar_language = (TextView) findViewById(R.id.tv_toolbar_language);
        tv_my_referral_amount_free = (TextView) findViewById(R.id.tv_my_referral_amount_free);
        tv_my_referral_code = (TextView) findViewById(R.id.tv_my_referral_code);

        tv_toolbar_language_amheric = (TextView) findViewById(R.id.tv_toolbar_language_amheric);
        tv_toolbar_language_english = (TextView) findViewById(R.id.tv_toolbar_language_english);
        ll_toolbar_language_change = (LinearLayout) findViewById(R.id.ll_toolbar_language_change);

        iv_toolbar_icon_tutorial.setVisibility(View.VISIBLE);
        iv_toolbar_wallet_icon.setVisibility(View.VISIBLE);
        tv_toolbar_head.setText(getResources().getString(R.string.referral_codes));
        if (profile_pic != null) {
            iv_toolbar_profile_image.setImageBitmap(profile_pic);
        }
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
        try {
            responsedata = getUserSaved(this);
        } catch (NullPointerException e) {
        }
        iv_toolbar_wallet_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReferralActivity.this, MyBalanceActivity.class));
            }
        });
        iv_toolbar_icon_tutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTutorial(ReferralActivity.this);
            }
        });
        iv_my_referral_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("PhoneNumber", tv_my_referral_code.getText());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(ReferralActivity.this, "Code copied successfully", Toast.LENGTH_SHORT).show();

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
                callLanguage(ReferralActivity.this, ReferralActivity.this, ReferralActivity.class, resources, 1);
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
                callLanguage(ReferralActivity.this, ReferralActivity.this, ReferralActivity.class, resources, 0);
            }
        });
        /*tv_toolbar_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callLanguage(MainFragmentActivity.this,MainFragmentActivity.this,MainFragmentActivity.class,resources);
            }
        });*/

        /*tv_toolbar_language.setText(CURRENT_LANGUAGE);*/
        sendRequest();
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
        startActivity(new Intent(ReferralActivity.this, Profile.class));
        finish();
    }

    @Override
    public void onResponseListener(int requestCode, Object responseModel) {
        try {
            log(TAG, responseModel.toString());
            Gson gson = new Gson();
            if (requestCode == MY_REFERRALS) {
                Referral bean = gson.fromJson((String) responseModel, Referral.class);
                com.fan.core.model.referral.Responsedata responsedata = bean.getResponsedata();
                tv_my_referral_amount_free.setText(responsedata.getReferComm() + " Free");
                tv_my_referral_code.setText(responsedata.getReferralCode());
                hideProgress();
            }
        } catch (JsonSyntaxException e) {
            log(TAG, e.getMessage());
        }
    }

    @Override
    public void onErrorListener(int requestCode, String error) {
        showToast(requestCode + " : " + error);
        hideProgress();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void sendRequest() {
        if (AppConstants.isOnline(this)) {
            RequestParams params = new RequestParams();
            params.put("user_id", responsedata.getId());
            postService(NetworkAPI.MY_REFERRALS, params, MY_REFERRALS);
            showDialog("Getting players", false);
        } else {
            showToast(getResources().getString(R.string.no_internet_connection));
        }
    }
}
