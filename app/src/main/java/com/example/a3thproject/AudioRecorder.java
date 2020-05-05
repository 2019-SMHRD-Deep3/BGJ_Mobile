package com.example.a3thproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class AudioRecorder extends AppCompatActivity {
    MediaRecorder recorder;
    String filename;
    MediaPlayer player;

    boolean playCheck = true;
    boolean recordCheck = true;
    boolean release = true;

    int position = 0; // 다시 시작 기능을 위한 현재 재생 위치 확인 변수

    //Button iplay,ipause,irestart,istop,irecord,irecordStop;
    Button Oplay, Ocheck, Orecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_recorder);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        permissionCheck();

        File sdcard = Environment.getExternalStorageDirectory();
        File file = new File(sdcard, "recorded.mp4");
        filename = file.getAbsolutePath();
        Log.d("TTST", "저장할 파일 명 : " + filename);

//        iplay = findViewById(R.id.Iplay);
//        ipause = findViewById(R.id.Ipause);
//        irestart = findViewById(R.id.Irestart);
//        istop = findViewById(R.id.Istop);
//        irecord = findViewById(R.id.Irecord);
//        irecordStop = findViewById(R.id.IrecordStop);
        Oplay = findViewById(R.id.oPlaying);
        Ocheck = findViewById(R.id.oChack);
        Orecord = findViewById(R.id.oRecord);

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


        // 녹음 실행 / 녹음 중단
        Orecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recordCheck){
                    recordAudio();
                }else{
                    stopRecording();
                }
                recordCheck = !recordCheck;
            }
        });

        // 일시 중단 / 재실행
        Ocheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(release){
                    pauseAudio();
                }else{
                    resumeAudio();
                }
            }
        });

        // 재생 실행 / 재생 중단
        Oplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(playCheck){
                    playAudio();
                }else{
                    stopAudio();
                }
                playCheck = !playCheck;
            }
        });
    }

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

    private void stopRecording() {
        if (recorder != null) {
            recorder.stop();
            recorder.release();
            recorder = null;
            Toast.makeText(this, "녹음 중지됨.", Toast.LENGTH_SHORT).show();
        }
    }

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

    private void pauseAudio() {
        if (player != null) {
            position = player.getCurrentPosition();
            player.pause();

            Toast.makeText(this, "일시정지됨.", Toast.LENGTH_SHORT).show();
        }
    }

    private void resumeAudio() {
        if (player != null && !player.isPlaying()) {
            player.seekTo(position);
            player.start();
            Toast.makeText(this, "재시작됨.", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopAudio() {
        if (player != null && player.isPlaying()) {
            player.stop();
            Toast.makeText(this, "중지됨.", Toast.LENGTH_SHORT).show();
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
}
