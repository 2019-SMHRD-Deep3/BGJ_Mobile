package com.example.a3thproject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;
import android.widget.Toolbar;

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

import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import static java.sql.DriverManager.println;

public class MenuActivity extends AppCompatActivity {

    ImageView menu1, menu2, menu3;
    String id = "";
    Button Pop;
    Intent intent;
    static RequestQueue requestQueue;
    MenuInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        intent = getIntent();
        if(id!=null){
            id = intent.getStringExtra("id");
        }



        Pop = findViewById(R.id.btnOP);
        menu1 = findViewById(R.id.menu1);
        menu2 = findViewById(R.id.menu2);
        menu3 = findViewById(R.id.img3);
        //img1 = findViewById(R.id.img1);

        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        Log.v("mmm",""+intent.getStringExtra("idmm"));
        Log.v("mmm2",""+id);
        menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "http://172.30.1.17:8081/Podo/GetBookOfLibrary";


                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jObj = new JSONObject(response);
                            boolean error = jObj.getBoolean("error");

                            if (!error) {
                            } else {
                                Toast.makeText(getApplicationContext(),
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
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("id", id);
                        params.put("title","means");
                        return params;
                    }
                };
                request.setShouldCache(false);
                requestQueue.add(request);

            }


        });

        menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, Main5Activity.class);
                intent.putExtra("id",id);
                startActivityForResult(intent,101);

            }
        });

        menu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, AudioRecorder.class);
                startActivityForResult(intent,101);
            }
        });
    }

    // 메뉴 객체 사용연결
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // return은 반드시 true로 줄 것
        return true;
    }

    // 메뉴 객체 이벤트
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu1:
                Intent goIntent = new Intent(this, LoginActivity.class);
                startActivity(goIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    // 메뉴 이벤트 사용
    public void mpop(View v){

            PopupMenu popup = new PopupMenu(this, v); // 팝업 메뉴 객체 생성

            MenuInflater inflater = popup.getMenuInflater();  // XML 파일에 정의해둔 메뉴 전개자 선언
            Menu menu = popup.getMenu();

            if(id==null){
                Log.v("hhd","Login");
                inflater.inflate(R.menu.popmenu,menu);
            }else{
                Log.v("hhd","Logout");
                inflater.inflate(R.menu.logmenu,menu);
            }
//            inflater.inflate(R.menu.popmenu, menu); // XML의 메뉴 가져오기

        // 메뉴 안에서 클릭이벤트 발생시 처리
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch(item.getItemId()){
                    case R.id.login:

                            intent = new Intent(v.getContext(), LoginActivity.class);
                            startActivity(intent);

                        break;
                }
                return false;
            }
        });
        popup.show();
    }

    public void println(String data){
        if (!data.equals("false")){

            String resultData = null;
            try {
                resultData = URLDecoder.decode(data, "EUC-KR");
            }catch (IOException e){

            }

            Intent intent = new Intent(getApplicationContext(), myBookshelf.class);
            intent.putExtra("library",resultData);
            intent.putExtra("id",id);
            startActivityForResult(intent,101);
        }else{
            Toast.makeText(getApplicationContext(),
                    "로그인 실패 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
        }
    }

}
