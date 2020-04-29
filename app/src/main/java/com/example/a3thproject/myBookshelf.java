package com.example.a3thproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a3thproject.R;
import com.example.a3thproject.fragment.Fragment_booklistAll;
import com.example.a3thproject.fragment.Fragment_booklistMy;
import com.google.android.material.tabs.TabLayout;

public class myBookshelf extends AppCompatActivity {

//    Button shelfAll, shelfMy;
//    TextView userName;
//    FrameLayout bookList;

    TabLayout tab;
    ViewPager bookList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bookshelf);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Intent intent = getIntent();

        tab = findViewById(R.id.tabLayout);
        bookList = findViewById(R.id.vBook);

        PageAdapter pageAdapter = new PageAdapter(getSupportFragmentManager(),
                tab.getTabCount());

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

        // <!-- 해당 선 아래는 사용하지 않는 코드 --!>
//        [
//        shelfAll = findViewById(R.id.bookShelf_all);
//        shelfMy = findViewById(R.id.bookShelf_my);
//        userName = findViewById(R.id.textView);
//
//        shelfAll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Fragment_booklistAll fragment_booklistAll = new Fragment_booklistAll();
//                getSupportFragmentManager().beginTransaction().
//                        replace(R.id.bookShelf_list,fragment_booklistAll).commit();
//            }
//        });
//
//        shelfMy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Fragment_booklistMy fragment_booklistmy = new Fragment_booklistMy();
//                getSupportFragmentManager().beginTransaction().
//                        replace(R.id.bookShelf_list,fragment_booklistmy).commit();
//            }
//        });]
    }
//    // 메뉴 객체 사용연결
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.mainmenu,menu);
//        // return은 반드시 true로 줄 것
//        return true;
//    }
//
//    // 메뉴 객체 이벤트
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.menu1:
//                Toast.makeText(this,"테스트중1",Toast.LENGTH_SHORT).show();
//                return true;
////            case R.id.menu2:
////                Toast.makeText(this,"테스트중2",Toast.LENGTH_SHORT).show();
////                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    // intent를 통해 액티비티 전환

}
