package com.example.a3thproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    EditText ID, PW;
    Button Login, Join;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ID = findViewById(R.id.tx_UserID);
        PW = findViewById(R.id.tx_UserPW);
        Login = findViewById(R.id.btn_M_Login);
        Join = findViewById(R.id.btn_M_Join);

    }
}
