package com.example.a3thproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.QuickContactBadge;

public class myBookread extends AppCompatActivity {
    WebView webView;
    Button btn1, btn2;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bookread);




        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);   // 화면 세로고정 코드
                                                                             //     가로고정 코드 -> SCREEN_ORIENTATION_LANDSCAPE


        webView = (WebView) findViewById(R.id.webvw);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // 웹뷰 멀티 터치 가능하게 (줌기능)
        webSettings.setBuiltInZoomControls(true);   // 줌 아이콘 사용
        webSettings.setSupportZoom(true);


        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        webView.loadUrl("http://172.30.1.17:8081/Podo/Book.jsp");






    }
}
