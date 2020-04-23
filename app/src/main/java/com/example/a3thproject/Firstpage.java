package com.example.a3thproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class Firstpage extends AppCompatActivity {

    TextView text;
    Boolean isRunning = true;
    int cnt = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstpage);

        text = findViewById(R.id.title);

        CntRun cntRun = new CntRun();
        Thread thread1 = new Thread(cntRun);
        thread1.start();
//        ff


    }

    public class CntRun implements Runnable{

        @Override
        public void run() {
            while(isRunning){
                if(cnt<=1){

                    Intent intent = new Intent(Firstpage.this, MenuActivity.class);
                    startActivity(intent);
                    break;
                }

                try {
                    Thread.sleep(1000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                run_handler.post(new Runnable() {
                    @Override
                    public void run() {
                        --cnt;



                    }
                });
            }


        }
    }

    Handler run_handler = new Handler();
}
