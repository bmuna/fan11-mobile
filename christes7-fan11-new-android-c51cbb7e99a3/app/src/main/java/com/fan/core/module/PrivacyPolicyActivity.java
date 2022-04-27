package com.fan.core.module;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.fan.core.R;
import com.fan.core.model.privacy.Privacy;
import com.fan.core.util.AppConstants;
import com.fan.core.util.CircleImageView;
import com.fan.core.util.NetworkAPI;
import com.fan.core.util.SharedPrefUtility;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.loopj.android.http.RequestParams;

import static com.fan.core.util.AppConstants.PRIVACY_POLICY;
import static com.fan.core.util.AppConstants.callLanguage;
import static com.fan.core.util.AppConstants.profile_pic;
import static com.fan.core.util.AppConstants.startTutorial;

/**
 * created by mohit.soni @ 29-Jan-20 _ 3:01 PM
 */
public class PrivacyPolicyActivity extends BaseActivity {

    public static final String TAG = Terms.class.getSimpleName();
    TextView tv_toolbar_head, tv_toolbar_language;
    CircleImageView iv_toolbar_profile_image;
    ImageView iv_toolbar_back, iv_toolbar_wallet_icon, iv_toolbar_icon_tutorial;
    Resources resources;
    LinearLayout ll_toolbar_language_change;
    TextView tv_toolbar_language_amheric, tv_toolbar_language_english;
    SharedPrefUtility prefUtility;


    private WebView wb_privacy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.privacy_policy);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.black));
        }
        resources = getResources();
        wb_privacy = (WebView) findViewById(R.id.wb_privacy);
        setUpWebViewDefaults(wb_privacy);
        wb_privacy.setWebViewClient(new Callback());
        wb_privacy.setBackgroundColor(Color.TRANSPARENT);

        iv_toolbar_back = (ImageView) findViewById(R.id.iv_toolbar_back);
        iv_toolbar_profile_image = (CircleImageView) findViewById(R.id.iv_toolbar_profile_image);
        tv_toolbar_head = (TextView) findViewById(R.id.tv_toolbar_head);
        tv_toolbar_language = (TextView) findViewById(R.id.tv_toolbar_language);
        iv_toolbar_wallet_icon = (ImageView) findViewById(R.id.iv_toolbar_wallet_icon);
        iv_toolbar_icon_tutorial = (ImageView) findViewById(R.id.iv_toolbar_icon_tutorial);

        tv_toolbar_language_amheric = (TextView) findViewById(R.id.tv_toolbar_language_amheric);
        tv_toolbar_language_english = (TextView) findViewById(R.id.tv_toolbar_language_english);
        ll_toolbar_language_change = (LinearLayout) findViewById(R.id.ll_toolbar_language_change);


        iv_toolbar_icon_tutorial.setVisibility(View.VISIBLE);
        iv_toolbar_wallet_icon.setVisibility(View.VISIBLE);
        tv_toolbar_head.setText(getResources().getString(R.string.privacy_policy));
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

        iv_toolbar_wallet_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PrivacyPolicyActivity.this, MyBalanceActivity.class));
            }
        });
        iv_toolbar_icon_tutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTutorial(PrivacyPolicyActivity.this);
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
                callLanguage(PrivacyPolicyActivity.this, PrivacyPolicyActivity.this, PrivacyPolicyActivity.class, resources, 1);
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
                callLanguage(PrivacyPolicyActivity.this, PrivacyPolicyActivity.this, PrivacyPolicyActivity.class, resources, 0);
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
        startActivity(new Intent(PrivacyPolicyActivity.this, Profile.class));
        finish();
    }

    @Override
    public void onResponseListener(int requestCode, Object responseModel) {
        try {
            log(TAG, responseModel.toString());
            Gson gson = new Gson();
            if (requestCode == PRIVACY_POLICY) {
                Privacy bean = gson.fromJson((String) responseModel, Privacy.class);
                wb_privacy.loadData(bean.getResponsedata(), "text/html; charset=utf-8", "UTF-8");
                hideProgress();
            }
        } catch (JsonSyntaxException e) {
            log(TAG, e.getMessage());
        }
    }

    @Override
    public void onErrorListener(int requestCode, String error) {
        showToast(requestCode + " : " + error);
    }

    public void sendRequest() {
        if (AppConstants.isOnline(this)) {
            showDialog("Getting Privacy Policy", true);
            RequestParams params = new RequestParams();
            postService(NetworkAPI.PRIVACY_POLICY, params, PRIVACY_POLICY);
        } else {
            showToast(getResources().getString(R.string.no_internet_connection));
        }
    }

    private void setUpWebViewDefaults(WebView webView) {

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }

        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setAllowContentAccess(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setMediaPlaybackRequiresUserGesture(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        webView.clearCache(true);
        webView.clearHistory();
        webView.setWebViewClient(new WebViewClient());
    }

    private class Callback extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return (false);
        }

        public void onPageFinished(WebView view, String url) {
            view.loadUrl(
                    "javascript:document.body.style.setProperty(\"color\", \"white\");"
            );
        }
    }

}