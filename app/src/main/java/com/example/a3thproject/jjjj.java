package com.example.a3thproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class jjjj extends AppCompatActivity {

    EditText text;
    EditText pw;
    Button join;
    static RequestQueue requestQueue;
   StringRequest request;
   String id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jjjj);

        text = findViewById(R.id.text);
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

                        Toast.makeText(jjjj.this, "요청에 실패했습니다 : 서버 오류", Toast.LENGTH_SHORT).show();

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
                params.put("id", text.getText().toString());
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

            Intent intent = new Intent(jjjj.this, MenuActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(jjjj.this, "로그인 실패 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
        }

    }
}