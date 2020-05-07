package com.example.a3thproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;


public class myBookread extends AppCompatActivity {
    WebView webView;
    Button btn1, btn2;
    int num = 0;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bookread);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);   // 화면 세로고정 코드
        //     가로고정 코드 -> SCREEN_ORIENTATION_LANDSCAPE


        webView = (WebView) findViewById(R.id.webvw);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setJavaScriptEnabled(true);   // 웹뷰 자바스크립트 true

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


        //webView.loadUrl("http://172.30.1.17:8081/Podo/Book.jsp?num="+num);
        webView.loadUrl("http://172.30.1.17:8081/Podo/test2.jsp");
        WebChromeClient chromeClient = new WebChromeClient();

        webView.setWebChromeClient(chromeClient);
        btn1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Toast.makeText(getApplicationContext(), "왼쪽 클릭", Toast.LENGTH_LONG).show();
                    num -= 1;
                    if(num<0) {
                        num = 0;
                    }
                    //webView.loadUrl("http://172.30.1.17:8081/Podo/Book.jsp?num=" + num);

                } return false;
            }
        });

        btn2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Toast.makeText(getApplicationContext(), "오른쪽 클릭", Toast.LENGTH_LONG).show();
                    num += 1;
                    //webView.loadUrl("http://172.30.1.17:8081/Podo/test.html?num=" + num);

                }
                return false;
            }

        });


    }


}