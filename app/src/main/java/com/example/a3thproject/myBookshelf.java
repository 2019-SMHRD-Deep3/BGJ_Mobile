package com.example.a3thproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a3thproject.R;
import com.example.a3thproject.fragment.Fragment_booklistAll;
import com.example.a3thproject.fragment.Fragment_booklistMy;
import com.google.android.material.tabs.TabLayout;

public class myBookshelf extends AppCompatActivity {

    TabLayout tab;
    ViewPager bookList;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bookshelf);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        intent = getIntent();

        tab = findViewById(R.id.tabLayout);
        bookList = findViewById(R.id.vBook);

        PageAdapter pageAdapter = new PageAdapter(getSupportFragmentManager(),
                tab.getTabCount(),intent.getStringExtra("library"),intent.getStringExtra("id"));

        bookList.setAdapter(pageAdapter);
        bookList.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));

        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                bookList.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    // 메뉴 객체 사용연결
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.popmenu,menu);
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

    // 메뉴 이벤트 사용
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
