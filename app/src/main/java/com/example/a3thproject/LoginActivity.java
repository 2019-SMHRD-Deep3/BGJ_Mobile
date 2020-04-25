package com.example.a3thproject;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText id,pw;
    Button join;
    static RequestQueue requestQueue;
    StringRequest request;

    public class MyAsyncTask extends AsyncTask<Void, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(Void... strings){
            if(requestQueue == null){
                requestQueue = Volley.newRequestQueue(getApplicationContext());
            }

            join.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    testJson();
                }
            });
            return true;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected void onPostExecute(Boolean s) {
            super.onPostExecute(s);
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
        @Override
        protected void onCancelled(Boolean s) {
            super.onCancelled(s);
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jjjj);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        getAppKeyHash();

        id = findViewById(R.id.text);
        pw = findViewById(R.id.pw);
        join = findViewById(R.id.join);
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testJson();
            }
        });

        MyAsyncTask asyncTask = new MyAsyncTask();
        asyncTask.execute();

//        if(requestQueue == null){
//            requestQueue = Volley.newRequestQueue(getApplicationContext());
//        }
//
//        join.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                testJson();
//
//            }
//        });

    }

    public void testJson(){
        String url = "http://192.168.56.1:8081/Podo/Loginserice";



        request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {


                    } else {
                        Toast.makeText(LoginActivity.this,
                                "요청에 실패했습니다 : 서버 오류", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                println(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", id.getText().toString());
                params.put("pw", pw.getText().toString());
                return params;
            }
        };

        request.setShouldCache(false);
        requestQueue.add(request);
    }

    public void println(String data){

        if (data.equals("true")){
            Log.v("son",data);
            Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(LoginActivity.this,
                    "로그인 실패 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
        }

    }
    // 해시 키 수집 코드
    private void getAppKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(),
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                Log.e("Hash key", something);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e("name not found", e.toString());
        }
    }
}
