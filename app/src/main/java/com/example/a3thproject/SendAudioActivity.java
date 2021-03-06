package com.example.a3thproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SendAudioActivity extends FragmentActivity {



//    Button btnImageSend;
//    File tempSelectFile;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_send_audio);
//
//        btnImageSend = findViewById(R.id.button6);
//        btnImageSend.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
//                        .permitDiskReads()
//                        .permitDiskWrites()
//                        .permitNetwork().build());
//
//
//                tempSelectFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/내장 메모리/Android/AudioRecordTest.mp3");
//                try {
//                    OutputStream out = new FileOutputStream(tempSelectFile);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//
//                FileUploadUtils.send2Server(tempSelectFile);
//
//            }
//        });
//
//
//
//
//
//    }
////
////    @Override
////    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
////        super.onActivityResult(requestCode, resultCode, data);
////        Uri dataUri = data.getData();
////
////        try {
////            InputStream in = getContentResolver().openInputStream(dataUri);
////            in.close();
////
////
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
////    }

        TextView messageText;
        ImageView sendingAudioImg;
    Button uploadButton;
    int serverResponseCode = 0;
    ProgressDialog dialog = null;
    String upLoadServerUri = null;
    Intent intent;
    String id;


    /**********  File Path *************/

   String uploadFilePath ="";//경로를 모르겠으면, 갤러리 어플리케이션 가서 메뉴->상세 정보

    final String uploadFileName = "AudioRecordTest.mp3"; //전송하고자하는 파일 이름

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
              setContentView(R.layout.activity_send_audio);

        messageText  = (TextView)findViewById(R.id.messageText);
        sendingAudioImg = (ImageView)findViewById(R.id.sendingAudioImg) ;
        uploadButton = findViewById(R.id.button6);
        intent = getIntent();
        id = intent.getStringExtra("id");
        Log.v("iddd",id);


        messageText.setText("목소리를 전송하시겠습니까?");



        /************* Php script path ****************/

        upLoadServerUri = "http://172.30.1.17:8081/Podo/Camera?id="+id;//서버컴퓨터의 ip주소

        uploadButton.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {



                dialog = ProgressDialog.show(SendAudioActivity.this, "", "Uploading file...", true);
                                new Thread(new Runnable() {

                                    public void run() {

                                        runOnUiThread(new Runnable() {

                                            public void run() {

                                                messageText.setText("전송하는 중...");

                            }

                        });
                        uploadFilePath = getExternalCacheDir().getAbsolutePath();
                        uploadFile(uploadFilePath + "/" + uploadFileName);

                        Log.v("song",uploadFileName);
                        Log.v("song",uploadFilePath);
                    }
                }).start();
            }
        });

    }



    public int uploadFile(String sourceFileUri) {
        String fileName = sourceFileUri;
        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);
        if (!sourceFile.isFile()) {
            dialog.dismiss();
            Log.e("uploadFile", "Source File not exist :"
                    +uploadFilePath + "" + uploadFileName);
            runOnUiThread(new Runnable() {
                public void run() {
                    messageText.setText("Source File not exist :"
                            +uploadFilePath + "" + uploadFileName);

                }
            });



            return 0;

        }else{

            try {
                // open a URL connection to the Servlet
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                URL url = new URL(upLoadServerUri);

                // Open a HTTP  connection to  the URL

                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy

                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", fileName);
                dos = new DataOutputStream(conn.getOutputStream());
                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                        + fileName + "\"" + lineEnd);

                dos.writeBytes(lineEnd);

                // create a buffer of  maximum size

                bytesAvailable = fileInputStream.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);

                buffer = new byte[bufferSize];

                // read file and write it into form...

                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {

                    dos.write(buffer, 0, bufferSize);

                    bytesAvailable = fileInputStream.available();

                    bufferSize = Math.min(bytesAvailable, maxBufferSize);

                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                }

                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                // Responses from the server (code and message)

                serverResponseCode = conn.getResponseCode();

                String serverResponseMessage = conn.getResponseMessage();

                Log.i("uploadFile", "HTTP Response is : "

                        + serverResponseMessage + ": " + serverResponseCode);

                if(serverResponseCode == 200){
                    runOnUiThread(new Runnable() {
                        public void run() {
                           /* String msg = "File Upload Completed.\n\n See uploaded file here : \n\n"

                                    +uploadFileName;*/

                            //messageText.setText("전송이 완료되었습니다");

                            Intent intent1 = new Intent(SendAudioActivity.this,MenuActivity.class);
                            intent1.putExtra("id",id);
                            intent1.putExtra("request",222);
                            startActivity(intent1);

                            //Toast.makeText(SendAudioActivity.this, "File Upload Complete.", Toast.LENGTH_SHORT).show();




                        }
                    });
                }

                //close the streams //

                fileInputStream.close();
                dos.flush();
                dos.close();

            } catch (MalformedURLException ex) {
                dialog.dismiss();
                ex.printStackTrace();
                runOnUiThread(new Runnable() {
                    public void run() {
                        messageText.setText("MalformedURLException Exception : check script url.");
                        Toast.makeText(SendAudioActivity.this, "MalformedURLException",
                                Toast.LENGTH_SHORT).show();
                    }
                });

                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);

            } catch (Exception e) {
                dialog.dismiss();
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    public void run() {
                        messageText.setText("Got Exception : see logcat ");
                        Toast.makeText(SendAudioActivity.this, "Got Exception : see logcat ",
                                Toast.LENGTH_SHORT).show();
                    }

                });
                Log.e("file2Server Exception", "Exception : "   + e.getMessage(), e);

            }
            dialog.dismiss();
            return serverResponseCode;
        }
    }
}
