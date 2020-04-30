package com.example.a3thproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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

import java.util.HashMap;
import java.util.Map;

public class mJoinActivity extends AppCompatActivity {

    EditText id, pw, name, email;
    Button btn;
    static RequestQueue requestQueue;
    StringRequest request;

    public class MyAsyncTask extends AsyncTask<Void, Integer, Boolean> {
        @Override
        protected Boolean doInBackground(Void... strings){
            if(requestQueue == null){
                requestQueue = Volley.newRequestQueue(getApplicationContext());
            }
           btn.setOnClickListener(new View.OnClickListener() {
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
        setContentView(R.layout.activity_mjoin);

        Intent intent = getIntent();

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        id = findViewById(R.id.id);
        pw = findViewById(R.id.pw);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        btn = findViewById(R.id.btn);

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }


        MyAsyncTask asyncTask = new MyAsyncTask();
        asyncTask.execute();
    }

    public void testJson(){
        String url = "http://172.30.1.17:8081/Podo/Joinservice";



        request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                    } else {
                        Toast.makeText(mJoinActivity.this,
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
                params.put("name", name.getText().toString());
                params.put("email", email.getText().toString());
                return params;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);
    }

    public void println(String data){
        if (data.equals("true")){
            Log.v("son",data);
            Intent intent = new Intent(mJoinActivity.this, LoginActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(mJoinActivity.this,
                    "회원가입 실패 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
        }
    }





    }
