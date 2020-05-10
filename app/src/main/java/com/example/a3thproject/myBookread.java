package com.example.a3thproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;


public class myBookread extends AppCompatActivity {
    WebView webView;
    WebSettings webSettings;
    String id, where;
    ImageView img1;
    int num;
    boolean check;
    WebChromeClient chromeClient;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bookread);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        num = intent.getIntExtra("num",999);
        where = intent.getStringExtra("where");
        Log.v("aaaaaaa", id );
        Log.v( "bbbbbb", num + "" );
        Log.v("ccccc", where );


        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);   // 화면 세로고정 코드
        //     가로고정 코드 -> SCREEN_ORIENTATION_LANDSCAPE

        Display display = getWindowManager().getDefaultDisplay();

        Point size = new Point();

        display.getSize(size);

        webView = (WebView) findViewById(R.id.webvw);



        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);   // 웹뷰 자바스크립트 true
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);


        // 웹뷰 멀티 터치 가능하게 (줌기능)
        webSettings.setBuiltInZoomControls(true);   // 줌 아이콘 사용
        webSettings.setSupportZoom(true);


        webView.setWebViewClient(new WebViewClient());

//        ) {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                return true;
//            }
//        });
        chromeClient = new WebChromeClient();
        webView.setWebChromeClient(chromeClient);


        //webView.loadUrl("http://172.30.1.17:8081/Podo/Book.jsp?num="+num);
        webView.loadUrl("http://172.30.1.17:8081/Podo/Book2.jsp?id="+id+ "&num="+num + "&where="+where);







    }


}