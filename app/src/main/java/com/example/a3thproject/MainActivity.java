package com.example.a3thproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.a3thproject.R;
import com.kakao.auth.IApplicationConfig;
import com.kakao.auth.KakaoAdapter;
import com.kakao.auth.KakaoSDK;
import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    EditText ID, PW;
    Button Login, Join;
    boolean isId, isPw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        ID = findViewById(R.id.tx_UserID);
        PW = findViewById(R.id.tx_UserPW);
        Login = findViewById(R.id.btn_M_Login);
        Join = findViewById(R.id.btn_M_Join);

        // 카카오 SDK 테스트
        KakaoSDK.init(new KakaoAdapter() {

            @Override
            public IApplicationConfig getApplicationConfig() {
                return new IApplicationConfig() {
                    @Override
                    public Context getApplicationContext() {
                        return MainActivity.this;
                    }
                };
            }
        });

//        ID.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                String id = ID.getText().toString();
//                // id조건식 (정규식으로 조건표시)
//                isId = (id.length()>7 && Pattern.matches("^[a-zA-Z0-9]+$",id));
//                checkEditTextBackground(isId,ID);
//                Login.setEnabled(isId&&isPw);
//                return false;
//            }
//        });

//        PW.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                String pw = PW.getText().toString();
//                // pw조건식 (정규식으로 조건표시 :
//                isPw = (pw.length()>10 && Pattern.matches("^(?=.*[a-zA-Z0-9])" +
//                        "(?=.*[a-zA-Z!@#$%^&*])(?=.*[0-9!@#$%^&*]).{8,15}$",pw));
//                checkEditTextBackground(isPw,PW);
//                Login.setEnabled(isId&&isPw);
//                return false;
//            }
//        });

//        Login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if()
//            }
//        });

    }
//    private void checkEditTextBackground(boolean isCheck, EditText et) {
//        if (isCheck) {
//            et.setBackgroundResource(R.drawable.check_sucess);
//        } else {
//            et.setBackgroundResource(R.drawable.check_default);
//        }
//    }
}
