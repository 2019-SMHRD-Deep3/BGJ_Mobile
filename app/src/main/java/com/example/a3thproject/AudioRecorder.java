package com.example.a3thproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class AudioRecorder extends AppCompatActivity {
    boolean mStartRecording = true;
    boolean mStartPlaying = true;

    private static final String TAG = "AudioRecorder";
    private static String filename = null;

    MediaRecorder recorder;

    MediaPlayer player;

    boolean playCheck = true;
    boolean recordCheck = true;
    boolean release = true;
    boolean Rcheck = true;

    int cnt = 0;
    int position = 0; // 다시 시작 기능을 위한 현재 재생 위치 확인 변수
    String id;
    Intent intent;
    // 뷰어 요소
    ImageView btnleft, btnright, checkOn,Iplay;
    Button onRecord, onPlay, aStop;
    TextView voicetext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_recorder);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        intent = getIntent();
        id = intent.getStringExtra("id");
        permissionCheck();
/*
        File sdcard = Environment.getExternalStorageDirectory();
        File file = new File(sdcard, "AudioRecordTest.mp3");
        filename = file.getAbsolutePath();*/
        filename = getExternalCacheDir().getAbsolutePath();
        filename += "/AudioRecordTest.mp3";
        Log.d("TTST", "저장할 파일 명 : " + filename);

//        Oplay = findViewById(R.id.oPlaying);
//        Ocheck = findViewById(R.id.oCheck);
//        Orecord = findViewById(R.id.oRecord);

        // 기능요소
        btnleft = findViewById(R.id.before);
        btnright = findViewById(R.id.after);
        voicetext = findViewById(R.id.voice);

        onRecord = findViewById(R.id.recordOn); //
        Iplay = findViewById(R.id.iPlay);     // 재생버튼
        aStop = findViewById(R.id.allStop);     // 정지버튼
        checkOn = findViewById(R.id.Ocheck);    // 일시정지, 녹음



        ArrayList<String> voice = new ArrayList<>();

        voice.add("첫번째");
        voice.add("세상은 세가지로 이루어져 있습니다.");
        voice.add("하늘, 바다, 땅");
        voice.add("여기, 저기, 거기");
        voice.add("동그라미, 세모, 네모");
        voice.add("철수, 영희, 바둑이 (읭?)");
        voice.add("... 그렇다고요");
        voice.add("");
        voice.add("로그 메소드의 첫번째 인자로 사용할 상수 TAG를 정의합니다." +
                "메세지들을 구분하는 구분값으로 사용되어집니다. 보통 현재 클래스의 이름을 많이 사용합니다.");

        voicetext.setText(voice.get(0));

        // 이전 페이지로
        btnleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cnt != 0) {
                    cnt--;
                    voicetext.setText(voice.get(cnt));
                    btnright.setImageResource(R.drawable.right_f);
                }else{
                    btnleft.setImageResource(R.drawable.left);
                    btnright.setImageResource(R.drawable.right_f);
                    Toast.makeText(AudioRecorder.this,
                            "첫 문장입니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 다음 페이지로
        btnright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cnt != voice.size()-1){
                    cnt++;
                    voicetext.setText(voice.get(cnt));
                    btnleft.setImageResource(R.drawable.left_f);
                }else{
                    btnleft.setImageResource(R.drawable.left_f);
                    btnright.setImageResource(R.drawable.right);
                    Toast.makeText(AudioRecorder.this,
                            "마지막 문장입니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 재생
        Iplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(playCheck){
                    playAudio();
                    Iplay.setImageResource(R.drawable.play_n);
                }
                playCheck = !playCheck;
            }
        });

        // (일회용) 녹음 시작과 동시에 버튼 보이기
        onRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Iplay.setVisibility(View.VISIBLE);
                aStop.setVisibility(View.VISIBLE);
                checkOn.setImageResource(R.drawable.minstop1);
                recordAudio();
                onRecord.setEnabled(false);
                onRecord.setVisibility(View.GONE);
                recordCheck = !recordCheck;
            }

        });

        // 정지
        aStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recordCheck==false&&playCheck==true){
                    stopRecording();
                    checkOn.setImageResource(R.drawable.norecord_t);
                    Intent intent1 = new Intent(AudioRecorder.this, SendAudioActivity.class);
                    intent1.putExtra("id",id);
                    startActivityForResult(intent1,101);
                }else if(recordCheck==true&&playCheck==false){
                    stopAudio();
                    Iplay.setImageResource(R.drawable.play_t);
                }
                recordCheck = !recordCheck;
                release = !release;
                playCheck = !playCheck;
            }
        });

        // 일시정지 및 재실행에 대한 기능적 고찰
        checkOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("myTest", "result");
                if(recordCheck==true&&release==true){
                    checkOn.setImageResource(R.drawable.minstop1);
                    recordAudio();
                }else if(recordCheck==false&&release==true){
                    Log.v("myTest", "test1");
                    checkOn.setImageResource(R.drawable.norecord_t);
                    pauseAudio();
                }else if(recordCheck==false&&release==false){
                    Log.v("myTest", "test2");
                    checkOn.setImageResource(R.drawable.minstop1);
                    resumeAudio();
                }
                release = !release;
            }
        });

    }

    // 녹음 실행
    private void recordAudio() {
        recorder = new MediaRecorder();
        /* 그대로 저장하면 용량이 크다.
         * 프레임 : 한 순간의 음성이 들어오면, 음성을 바이트 단위로 전부 저장하는 것
         * 초당 15프레임 이라면 보통 8K(8000바이트) 정도가 한순간에 저장됨
         * 따라서 용량이 크므로, 압축할 필요가 있음 */
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC); // 어디에서 음성 데이터를 받을 것인지
            recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4); // 압축 형식 설정
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
            recorder.setOutputFile(filename);

            try {
            recorder.prepare();
            recorder.start();

            Toast.makeText(this, "녹음 시작됨.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //녹음 중단
    private void stopRecording() {
        if (recorder != null) {
            recorder.stop();
            recorder.release();
            recorder = null;
            Toast.makeText(this, "녹음 중지됨.", Toast.LENGTH_SHORT).show();
        }
    }

    // 파일 재생
    private void playAudio() {
        try {
            closePlayer();
            player = new MediaPlayer();
            player.setDataSource(filename);
            player.prepare();
            player.start();
            Toast.makeText(this, "재생 시작됨.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 재생 중단
    private void stopAudio() {
        if (player != null && player.isPlaying()) {
            player.stop();
            Toast.makeText(this, "중지됨.", Toast.LENGTH_SHORT).show();
        }
    }

    // 녹음 일시중단
    private void pauseAudio() {
        if (player != null) {
            position = player.getCurrentPosition();
            player.pause();

            Toast.makeText(this, "일시정지됨.", Toast.LENGTH_SHORT).show();
        }
    }

    // 녹음 재시작
    private void resumeAudio() {
        if (player != null && !player.isPlaying()) {
            player.seekTo(position);
            player.start();
            Toast.makeText(this, "재시작됨.", Toast.LENGTH_SHORT).show();
        }
    }


    public void closePlayer() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

    public void permissionCheck(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 1);
        }
    }




    public void serverplay(){
        FileUploadUtils  f= new FileUploadUtils();
    }

    // 사용하지 않는 코드
    // 수정 이전 녹음기능 코드
//        iplay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                playAudio();
//            }
//        });
//
//        ipause.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                pauseAudio();
//            }
//        });
//
//        irestart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                resumeAudio();
//            }
//        });
//
//        istop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                stopAudio();
//            }
//        });
//
//        irecord.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                recordAudio();
//            }
//        });
//
//        irecordStop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                stopRecording();
//            }
//        });
    // 수정 이전 녹음 코드
    //        iplay = findViewById(R.id.Iplay);
//        ipause = findViewById(R.id.Ipause);
//        irestart = findViewById(R.id.Irestart);
//        istop = findViewById(R.id.Istop);
//        irecord = findViewById(R.id.Irecord);
//        irecordStop = findViewById(R.id.IrecordStop);
    // (사운드) 버튼별 기능
    // 녹음 실행 / 녹음 중단
//        Orecord.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(recordCheck){
//                    recordAudio();
//                }else{
//                    T.setEnabled(false);
//                    stopRecording();
//                }
//                recordCheck = !recordCheck;
//            }
//        });
//
//        t3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(recordCheck&&Rcheck){
//                    recordAudio();
//                    T.setVisibility(View.VISIBLE);
//                    t2.setVisibility(View.VISIBLE);
//                    loo.setImageResource(R.drawable.minstop1);
//                    Rcheck=!Rcheck;
//                }else if(recordCheck&&Rcheck==false){
//                        recordAudio();
//                    recordCheck = !recordCheck;
//                }else{
//                    stopRecording();
//                }
//            }
//        });

    // 일시 중단 / 재실행
//        Ocheck.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(release){
//                    pauseAudio();
//                }else{
//                    resumeAudio();
//                }
//            }
//        });
//        T.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(release){
//                    pauseAudio();
//                }else{
//                    resumeAudio();
//                }
//            }
//        });

    // 재생 실행 / 재생 중단
//        Oplay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(playCheck){
//                    playAudio();
//                }else{
//                    stopAudio();
//                }
//                playCheck = !playCheck;
//            }
//        });


//        LinearLayout ll = new LinearLayout(this);
//        recordButton = new RecordButton(this);
//        ll.addView(recordButton,
//                new LinearLayout.LayoutParams(
//                        ViewGroup.LayoutParams.WRAP_CONTENT,
//                        ViewGroup.LayoutParams.WRAP_CONTENT,
//                        0));
//        playButton = new PlayButton(this);
//        ll.addView(playButton,
//                new LinearLayout.LayoutParams(
//                        ViewGroup.LayoutParams.WRAP_CONTENT,
//                        ViewGroup.LayoutParams.WRAP_CONTENT,
//                        0));
//        setContentView(ll);
}
