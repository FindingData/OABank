package com.findingdata.oabank.ui;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.findingdata.oabank.R;
import com.findingdata.oabank.base.BaseActivity;
import com.findingdata.oabank.utils.TokenUtils;

import java.util.HashMap;
import java.util.Map;

public class AchievementDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement_detail);

        WebView webView = (WebView) findViewById(R.id.web_view);
        String url = getIntent().getExtras().getString("web_url");
        Map<String, String > map = new HashMap<String, String>() ;
        map.put( "Cookie" , "Token="+ TokenUtils.getToken()) ;
        webView.loadUrl(url,map);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Map<String, String > map = new HashMap<String, String>() ;
                map.put( "Cookie" , "Token="+ TokenUtils.getToken()) ;
                view.loadUrl(url,map);
                return true;
            }
        });
    }
}
