package com.example.a3thproject;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

        ID = findViewById(R.id.tx_UserID);
        PW = findViewById(R.id.tx_UserPW);
        Login = findViewById(R.id.btn_M_Login);
        Join = findViewById(R.id.btn_M_Join);

        ID.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                String id = ID.getText().toString();
                // id조건식 (정규식으로 조건표시)
                isId = (id.length()>7 && Pattern.matches("^[a-zA-Z0-9]+$",id));
                checkEditTextBackground(isId,ID);
                Login.setEnabled(isId&&isPw);
                return false;
            }
        });

        PW.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                String pw = PW.getText().toString();
                // pw조건식 (정규식으로 조건표시 :
                isPw = (pw.length()>10 && Pattern.matches("^(?=.*\\d)(?=.*[~`!@#$%&*()-])" +
                        "(?=.*[a-z])(?=.*[A-Z]).{9,12}$",pw));
                return false;
            }
        });

    }
    private void checkEditTextBackground(boolean isCheck, EditText et) {
        if (isCheck) {
            et.setBackgroundResource(R.drawable.check_sucess);
        } else {
            et.setBackgroundResource(R.drawable.check_default);
        }
    }
}
