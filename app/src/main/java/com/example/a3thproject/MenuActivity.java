package com.example.a3thproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

public class MenuActivity extends AppCompatActivity {

    Button Pop;
    ImageView img1;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        intent = getIntent();
        Pop = findViewById(R.id.btnOP);
        img1 = findViewById(R.id.img1);

        // 뷰 선택
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, myBookshelf.class);
                startActivityForResult(intent,101);
            }
        });

    }

    // 메뉴 객체 사용연결
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu,menu);
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
//            case R.id.menu2:
//                Toast.makeText(this,"테스트중2",Toast.LENGTH_SHORT).show();
//                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // 일단 테스트
    public void mpop(View v){
        // 팝업 메뉴 객체 생성
        PopupMenu popup = new PopupMenu(this, v);
        // XML 파일에 정의해둔 메뉴 전개자 선언
        MenuInflater inflater = popup.getMenuInflater();
        Menu menu = popup.getMenu();
        // XML의 메뉴 가져오기
        inflater.inflate(R.menu.popmenu, menu);
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
}
