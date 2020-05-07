package com.example.a3thproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;

public class testttttt extends AppCompatActivity {

    Button btnleft, btnright;
    TextView voicetext;
    int cnt = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testttttt);

        btnleft = findViewById(R.id.btnleft);
        btnright = findViewById(R.id.btnright);
        voicetext = findViewById(R.id.voice);


        ArrayList<String> voice = new ArrayList<>();

        voice.add("f");
        voice.add("ff");
        voice.add("fff");
        voice.add("ffff");
        voice.add("fffff");
        voice.add("ffffff");
        voice.add("fffffff");

        voicetext.setText(voice.get(0));

        init();



                btnleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cnt != 0) {
                    cnt--;
                    voicetext.setText(voice.get(cnt));
                }else{
                    Toast.makeText(testttttt.this,
                            "첫 문장입니다.", Toast.LENGTH_SHORT).show();

                }
            }
        });

        btnright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(cnt != voice.size()-1){
                    cnt++;
                    voicetext.setText(voice.get(cnt));

                }else{

                    Toast.makeText(testttttt.this,
                            "마지막 문장입니다.", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private void init(){

        //Calendar를 이용하여 년, 월, 일, 시간, 분을 PICKER에 넣어준다.
        final Calendar cal = Calendar.getInstance();


        //DATE PICKER DIALOG



        //TIME PICKER DIALOG
        findViewById(R.id.btn_date_picker_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimePickerDialog dialog = new TimePickerDialog(testttttt.this, new TimePickerDialog.OnTimeSetListener() {

                    public void onTimeSet(TimePicker timePicker, int hour, int min) {

                        String msg = String.format("%d 시 %d 분", hour, min);
                        Toast.makeText(testttttt.this, msg, Toast.LENGTH_SHORT).show();
                    }
                }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true);  //마지막 boolean 값은 시간을 24시간으로 보일지 아닐지

                dialog.show();

            }
        });
    }


}
