package com.example.a3thproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.UiThread;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class Firstpage extends AppCompatActivity {
    ImageView img1;
    //TextView text;
    Boolean isRunning = true;
    int cnt = 3;
    Thread splashTread;

    Animation animation_text;
    Animation animation_img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstpage);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

       // text = findViewById(R.id.title);
        img1 = findViewById(R.id.menu2);
        splashAnmation();

//        CntRun cntRun = new CntRun();
//        Thread thread1 = new Thread(cntRun);
//        thread1.start();


    }

    @UiThread
    private void splashAnmation() {
        animation_text = AnimationUtils.loadAnimation(this,R.anim.splash_textview);
        //animation_text.reset();
        //text.startAnimation(animation_text);
        animation_img = AnimationUtils.loadAnimation(this, R.anim.splash_imageview);
        //animation_img.reset();
        img1.startAnimation(animation_img);

        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    // Splash screen pause time
                    while (waited < 4000) {
                        sleep(100);
                        waited += 100;
                    }

                } catch (InterruptedException e) {
                    // do nothing
                } finally {

                }

            }
        };
        splashTread.start();

        animation_img.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.splash_out_top,R.anim.splash_in_down);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


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
