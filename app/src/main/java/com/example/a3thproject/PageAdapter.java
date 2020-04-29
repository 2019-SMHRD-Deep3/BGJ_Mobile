package com.example.a3thproject;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.a3thproject.pageview.SwingPage1;
import com.example.a3thproject.pageview.SwingPage2;

public class PageAdapter extends FragmentStatePagerAdapter {

    private int tabCount;

    public PageAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm,behavior);
        tabCount = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                SwingPage1 swingPage1 = new SwingPage1();
                return swingPage1;

            case 1:
                SwingPage2 swingPage2 = new SwingPage2();
                return swingPage2;

            default:
                return null;//ㅇㅇㅇ

        }

        // switch로 탭 전환코드
//        if(position == 0){
//            SwingPage1 swingPage1 = new SwingPage1();
//            return swingPage1;
//        }else if(position == 1){
//            return new SwingPage2();
//        }else {
//            return null;
//        }

    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
