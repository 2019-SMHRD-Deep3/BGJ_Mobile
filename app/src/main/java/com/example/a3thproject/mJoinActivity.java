package com.example.a3thproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import org.w3c.dom.Text;

import java.io.Console;
import java.util.HashMap;
import java.util.Map;

public class mJoinActivity extends AppCompatActivity {

    TextView pwcheck;
    EditText id, pw, pw2, name, email;
    Button btn;
    Button btn2;
    Button btncheck;

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
        pw2 = findViewById(R.id.pw2);
        pwcheck = findViewById(R.id.pwcheck);
        name = findViewById(R.id.name);
        btn = findViewById(R.id.btnjoin);
        btn2 = findViewById(R.id.btncancle);
        btncheck = findViewById(R.id.IdCheck);

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

         btncheck.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 testJson2();
             }
         });

        pw2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(pw.getText().toString().equals(pw2.getText().toString())) {
                    pwcheck.setText("일치");
                }else{
                    pwcheck.setText("불일치");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testJson();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mJoinActivity.this,  MenuActivity.class);
                startActivity(intent);
            }
        });

        MyAsyncTask asyncTask = new MyAsyncTask();
        asyncTask.execute();
    }

    public void testJson(){
        String url = "http://172.30.1.17:8081/Podo/JoinService";

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

    public void testJson2(){

        Log.v("asdf", "1차");
        String url = "http://172.30.1.17:8081/Podo/JoinIdCheck";

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
                println2(response);
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
                /*params.put("pw", pw.getText().toString());
                params.put("name", name.getText().toString());
                params.put("email", email.getText().toString());*/
                Log.v("asdf", "params : " + params);
                return params;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);
    }

    public void println(String data) {
        Log.v("son", data);
        if (data.equals("true")) {
            Toast.makeText(getApplicationContext(), "회원가입 성공", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(mJoinActivity.this,  LoginActivity.class);
            startActivity(intent);

        } else {
            Toast.makeText(getApplicationContext(), "회원가입 실패", Toast.LENGTH_SHORT).show();
        }

    }

    public void println2(String data){
        Log.v("son",data);
        if(data.equals("true")){
            Toast.makeText(getApplicationContext(), "중복된 아이디", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), "아이디 사용가능", Toast.LENGTH_SHORT).show();
        }

     }
}






