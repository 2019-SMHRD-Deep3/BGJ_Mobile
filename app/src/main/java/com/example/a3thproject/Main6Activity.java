package com.example.a3thproject;


import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Main6Activity extends AppCompatActivity {

    ImageView imageView = null;
    Button button6 = null;
    private final int REQ_CODE_SELECT_IMAGE = 100;
    private String img_path = "";
    private String serverURL = "http://172.30.1.9:8081/Podo/Audio";  //<<서버주소
    private Bitmap image_bitmap_copy = null;
    private Bitmap image_bitmap = null;
    private String imageName = null;

    private ArrayList<Uri> images_uri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);

        images_uri = getIntent().getParcelableArrayListExtra("imagelist");
        Log.v("listuri",  String.valueOf(images_uri));

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .permitDiskReads()
                .permitDiskWrites()
                .permitNetwork().build());

        button6 = (Button) findViewById(R.id.button6);
        //이미지 전송 버튼
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                for (int i=0; i<images_uri.size();i++){
                    Log.v("1차","1");
                    DoFileUpload(serverURL, images_uri.get(i).getPath());
                    Log.v("1차", serverURL);
                    Log.v("1차",images_uri.get(i).toString().substring(39));
                    Toast.makeText(getApplicationContext(), "이미지 전송 성공", Toast.LENGTH_SHORT).show();
                    Log.d("Send", "Success");
                }


            }

        });
    }

//    public String getPathFromUri(Uri uri){
//
//        Log.v("2차","하이");
//        Cursor cursor = getContentResolver().query(uri, null, null, null, null );
//        cursor.moveToNext();
//        String path = cursor.getString( cursor.getColumnIndex( "_data" ) );
//        cursor.close();
//
//        return path;
//    }


    public void DoFileUpload(String apiUrl, String absolutePath) {
        Log.v("1차-1", apiUrl);
        Log.v("1차-1", absolutePath);
        HttpFileUpload(apiUrl, "", absolutePath);
    }

    String lineEnd = "\r\n";
    String twoHyphens = "--";
    String boundary = "*****";



    public void HttpFileUpload(String urlString, String params, String fileName) {
        try {

            Log.v("2차","하이1");
            FileInputStream mFileInputStream = new FileInputStream(fileName);
            Log.v("2차","하이2");
            URL connectUrl = new URL(urlString);
            Log.v("2차","하이3");
            Log.d("Test", "mFileInputStream  is " + mFileInputStream);

            // HttpURLConnection 통신
            HttpURLConnection conn = (HttpURLConnection) connectUrl.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

            // write data
            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + fileName + "\"" + lineEnd);
            dos.writeBytes(lineEnd);

            int bytesAvailable = mFileInputStream.available();
            int maxBufferSize = 1024;
            int bufferSize = Math.min(bytesAvailable, maxBufferSize);

            byte[] buffer = new byte[bufferSize];
            int bytesRead = mFileInputStream.read(buffer, 0, bufferSize);

            Log.d("Test", "image byte is " + bytesRead);

            // read image
            while (bytesRead > 0) {

                dos.write(buffer, 0, bufferSize);
                bytesAvailable = mFileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = mFileInputStream.read(buffer, 0, bufferSize);
            }

            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            // close streams
            Log.e("Test", "File is written");
            mFileInputStream.close();
            dos.flush();
            // finish upload...

            // get response
            InputStream is = conn.getInputStream();

            StringBuffer b = new StringBuffer();
            for (int ch = 0; (ch = is.read()) != -1; ) {
                b.append((char) ch);
            }
            is.close();
            Log.e("Test", b.toString());


        } catch (Exception e) {
            Log.d("Test", "exception " + e.getMessage());
            // TODO: handle exception
        }
    } // end of HttpFileUpload()\



}