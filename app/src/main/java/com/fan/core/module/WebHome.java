package com.fan.core.module;

import android.annotation.TargetApi;
import android.app.Activity;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.CookieManager;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.fan.core.R;


/**
 * Created by mohit.soni @ 16-Oct-19.
 */
public class WebHome extends Activity {
    private WebView mWebView;
    public static final String USER_AGENT = "Mozilla/5.0 (Linux; Android 4.1.1; Galaxy Nexus Build/JRO03C) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Mobile Safari/535.19";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_home);
        CookieManager.getInstance().setAcceptCookie(true);
        createWebView();
    }

    private void createWebView() {

        mWebView = (WebView) findViewById(R.id.wb);
        setUpWebViewDefaults(mWebView);
        mWebView.loadUrl("https://udwebs.xyz/telegram_login.php");
        /*mWebView.loadUrl("file:///android_asset/t_index.html");
        mWebView.getSettings().setUserAgentString(USER_AGENT);*/
        mWebView.setWebChromeClient(new WebChromeClient());
    }

    private void setUpWebViewDefaults(WebView webView) {

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setAllowContentAccess(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setMediaPlaybackRequiresUserGesture(false);

        webView.clearCache(true);
        webView.clearHistory();
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

                Uri s = request.getUrl();
                String f = s.getPath();
                return super.shouldOverrideUrlLoading(view, request);
            }
        });
    }
    /**
     * call google chrome
     * @param url
     */
   /*private void openBrowserWithURL(String url) {
        Uri uri = Uri.parse("googlechrome://navigate?url=" + url);
        Intent i = new Intent(Intent.ACTION_VIEW, uri);
        if (i.resolveActivity(WebHome.this.getPackageManager()) == null) {
            i.setData(Uri.parse(url));
        }

        WebHome.this.startActivity(i);
    }*/
}
