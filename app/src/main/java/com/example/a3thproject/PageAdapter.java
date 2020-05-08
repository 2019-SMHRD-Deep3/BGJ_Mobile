package com.example.a3thproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.a3thproject.fragment.Fragment_booklistAll;
import com.example.a3thproject.fragment.Fragment_booklistMy;
import com.example.a3thproject.fragment.Fragment_nonlogin;
import com.example.a3thproject.pageview.SwingPage1;
import com.example.a3thproject.pageview.SwingPage2;

public class PageAdapter extends FragmentStatePagerAdapter {

    private int tabCount;
    private String data;
    private String id;
    String [] arr;
    public PageAdapter(@NonNull FragmentManager fm, int behavior, String dataa,String id2) {
        super(fm,behavior);
        tabCount = behavior;
        data = dataa;
        id = id2;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        arr = data.split("/");

        switch (position){
            case 0:

                Fragment_booklistAll all = new Fragment_booklistAll();
                Bundle bundle = new Bundle();
                bundle.putString("library", arr[0]);
                all.setArguments(bundle);
                return all;

            case 1:
                if(id.equals("")){
                    Fragment_booklistMy my = new Fragment_booklistMy();
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("iddd",id);
                    bundle1.putString("books",arr[1]);
                    my.setArguments(bundle1);
                    return my;
                }else {
                    if(id.equals("")){

                    }else {
                        Fragment_nonlogin nonlogin = new Fragment_nonlogin();
                        return nonlogin;
                    }

                }


            default:
                return null;

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
