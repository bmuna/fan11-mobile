package com.fan.core.module;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.fan.core.R;
import com.fan.core.adapter.GameList_Adapter;
import com.fan.core.model.add_balance.AddBalance;
import com.fan.core.model.my_balance.MyBalance;
import com.fan.core.model.user.Responsedata;
import com.fan.core.model.user.UserBean;
import com.fan.core.module_fragment.MainFragmentActivity;
import com.fan.core.util.AppConstants;
import com.fan.core.util.CircleImageView;
import com.fan.core.util.NetworkAPI;
import com.fan.core.util.SharedPrefUtility;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.loopj.android.http.RequestParams;

import static com.fan.core.util.AppConstants.CURRENT_LANGUAGE;
import static com.fan.core.util.AppConstants.MY_BALANCE;
import static com.fan.core.util.AppConstants.callLanguage;
import static com.fan.core.util.AppConstants.getUserSaved;
import static com.fan.core.util.AppConstants.profile_pic;
import static com.fan.core.util.AppConstants.startTutorial;
import static com.fan.core.util.AppConstants.updateNull;

/**
 * Created by mohit.soni @ 01-Nov-19.
 */
public class MyBalanceActivity extends BaseActivity {
    public static final String TAG = MyBalanceActivity.class.getSimpleName();

    TextView tv_toolbar_head, tv_toolbar_language, tv_my_balance_amount;
    ImageView iv_toolbar_back;
    ImageView iv_toolbar_icon, iv_toolbar_wallet_icon, iv_toolbar_icon_tutorial;
    CircleImageView iv_toolbar_profile_image;

    TextView tv_my_balance_deposited, tv_my_balance_winning, tv_my_balance_bonus;
    TextView tv_my_balance_my_recent_transaction, tv_my_balance_my_withdrawal, tv_my_balance_my_deposite, tv_my_balance_my_coupon;

    Responsedata responsedata;
    Resources resources;


    LinearLayout ll_toolbar_language_change;
    TextView tv_toolbar_language_amheric, tv_toolbar_language_english;
    SharedPrefUtility prefUtility;
    private final String PREF_LOGIN = "login_pref";
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_balance);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.black));
        }
        resources = getResources();
        iv_toolbar_icon = (ImageView) findViewById(R.id.iv_toolbar_wallet_icon);
        iv_toolbar_profile_image = (CircleImageView) findViewById(R.id.iv_toolbar_profile_image);
        tv_toolbar_head = (TextView) findViewById(R.id.tv_toolbar_head);
        iv_toolbar_back = (ImageView) findViewById(R.id.iv_toolbar_back);
        tv_toolbar_language = (TextView) findViewById(R.id.tv_toolbar_language);
        tv_my_balance_amount = (TextView) findViewById(R.id.tv_my_balance_amount);

        tv_my_balance_deposited = (TextView) findViewById(R.id.tv_my_balance_deposited);
        tv_my_balance_winning = (TextView) findViewById(R.id.tv_my_balance_winning);
        tv_my_balance_bonus = (TextView) findViewById(R.id.tv_my_balance_bonus);

        tv_my_balance_my_recent_transaction = (TextView) findViewById(R.id.tv_my_balance_my_recent_transaction);
        tv_my_balance_my_withdrawal = (TextView) findViewById(R.id.tv_my_balance_my_withdrawal);
        tv_my_balance_my_deposite = (TextView) findViewById(R.id.tv_my_balance_my_deposite);
        tv_my_balance_my_coupon = (TextView) findViewById(R.id.tv_my_balance_my_coupon);

        tv_toolbar_language_amheric = (TextView) findViewById(R.id.tv_toolbar_language_amheric);
        tv_toolbar_language_english = (TextView) findViewById(R.id.tv_toolbar_language_english);
        ll_toolbar_language_change = (LinearLayout) findViewById(R.id.ll_toolbar_language_change);
        iv_toolbar_wallet_icon = (ImageView) findViewById(R.id.iv_toolbar_wallet_icon);
        iv_toolbar_icon_tutorial = (ImageView) findViewById(R.id.iv_toolbar_icon_tutorial);

        iv_toolbar_icon_tutorial.setVisibility(View.VISIBLE);
        iv_toolbar_wallet_icon.setVisibility(View.GONE);
        tv_toolbar_head.setText(getResources().getString(R.string.my_balance));

        try {
            responsedata = getUserSaved(this);
        } catch (NullPointerException e) {

        }
        tv_my_balance_my_deposite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyBalanceActivity.this, AddBalanceActivity.class));
            }
        });
        tv_my_balance_my_coupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                hideProgress();
                startActivity(new Intent(MyBalanceActivity.this, AddCouponNew.class));
//                finish();
//                startActivity(new Intent(MyBalanceActivity.this, Add.class));

            }
        });
        tv_my_balance_my_withdrawal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyBalanceActivity.this, WithDrawBalanceActivity.class));
            }
        });

        if (profile_pic != null) {
            iv_toolbar_profile_image.setImageBitmap(profile_pic);
        }
        iv_toolbar_icon_tutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTutorial(MyBalanceActivity.this);
            }
        });
        iv_toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onBackPressed();
                startActivity(new Intent(MyBalanceActivity.this, MainFragmentActivity.class));

            }
        });
        iv_toolbar_profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        iv_toolbar_icon.setVisibility(View.GONE);

        tv_toolbar_language_amheric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (prefUtility != null) {
                    prefUtility.setLanguage("ar");
                }
                ll_toolbar_language_change.setBackground(resources.getDrawable(R.drawable.round_button_toolbar_language_amheric));
                tv_toolbar_language_amheric.setTextColor(resources.getColor(R.color.black));
                tv_toolbar_language_english.setTextColor(resources.getColor(R.color.grey));
                callLanguage(MyBalanceActivity.this, MyBalanceActivity.this, MyBalanceActivity.class, resources, 1);
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
                callLanguage(MyBalanceActivity.this, MyBalanceActivity.this, MyBalanceActivity.class, resources, 0);
            }
        });
        /*tv_toolbar_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callLanguage(MainFragmentActivity.this,MainFragmentActivity.this,MainFragmentActivity.class,resources);
            }
        });*/


//        balance = responsedata.getMyBalance();

        tv_my_balance_amount.setText(getResources().getString(R.string.birr) + " " + responsedata.getMyBalance());
        tv_my_balance_deposited.setText(getResources().getString(R.string.birr) + "  0 ");
        tv_my_balance_winning.setText(getResources().getString(R.string.birr) + " 0 ");
        tv_my_balance_bonus.setText(getResources().getString(R.string.birr) + " 0 ");
        tv_my_balance_my_recent_transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyBalanceActivity.this, RecentTransactionActivity.class));
            }
        });
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
        /* startActivity(new Intent(MyBalanceActivity.this, Profile.class));*/
        finish();
    }
    @Override
    public void onResponseListener(int requestCode, Object responseModel) {
        try {
            log(TAG, responseModel.toString());
            Gson gson = new Gson();
            if (requestCode == MY_BALANCE) {
                MyBalance bean = gson.fromJson((String) responseModel, MyBalance.class);
                if (bean.getResponseMessage().equals("Success")) {
                    tv_toolbar_language.setText(CURRENT_LANGUAGE);
                    tv_my_balance_amount.setText(getResources().getString(R.string.birr) + " " + bean.getResponsedata().getCurrentBalance());
                    prefUtility.updateUserData("my_balance", bean.getResponsedata().getCurrentBalance());
                    tv_my_balance_deposited.setText(getResources().getString(R.string.birr) + " " + bean.getResponsedata().getTotalDeposited());
                    tv_my_balance_winning.setText(getResources().getString(R.string.birr) + " " + bean.getResponsedata().getTotalWinnings());
                    tv_my_balance_bonus.setText(getResources().getString(R.string.birr) + " " + bean.getResponsedata().getTotalBonus());
                } else {
                    showToast(bean.getResponsedata().getErrorMessage());
                }
                hideProgress();
            }
        } catch (JsonSyntaxException e) {
            log(TAG, e.getMessage());
        }

    }

//    @Override
//    public void onResponseListener(int requestCode, Object responseModel) {
//        try {
//            log(TAG, responseModel.toString());
//            Gson gson = new Gson();
//            if (requestCode == MY_BALANCE) {
//                MyBalance bean = gson.fromJson((String) responseModel, MyBalance.class);
//                    tv_toolbar_language.setText(CURRENT_LANGUAGE);
//                   tv_my_balance_amount.setText(getResources().getString(R.string.birr) + " " + bean.getResponsedata().getCurrentBalance());
//                    prefUtility.updateUserData("my_balance", bean.getResponsedata().getCurrentBalance());
//                   tv_my_balance_deposited.setText(getResources().getString(R.string.birr) + " " + bean.getResponsedata().getTotalDeposited());
//                    tv_my_balance_winning.setText(getResources().getString(R.string.birr) + " " + bean.getResponsedata().getTotalWinnings());
//                    tv_my_balance_bonus.setText(getResources().getString(R.string.birr) + " " + bean.getResponsedata().getTotalBonus());
//                } else {
//                    showToast(bean.getResponsedata().getErrorMessage());
//                }
//                hideProgress();
//            }
//        } catch (JsonSyntaxException e) {
//            log(TAG, e.getMessage());
//        }
//
//    }

    @Override
    public void onErrorListener(int requestCode, String error) {

    }

    public void sendRequest() {
        if (AppConstants.isOnline(this)) {
            showDialog("Getting Balance", true);
            RequestParams params = new RequestParams();
            params.put("user_id", responsedata.getId());
            postService(NetworkAPI.MY_BALANCE, params, MY_BALANCE);
        } else {
            showToast(getResources().getString(R.string.no_internet_connection));
        }
    }
}
