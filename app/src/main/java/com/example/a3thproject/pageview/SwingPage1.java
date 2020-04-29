package com.example.a3thproject.pageview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a3thproject.R;

public class SwingPage1 extends Fragment {

    RecyclerView tab1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View tabView = inflater.inflate(R.layout.swingpage1,container,false);
        tab1 = tabView.findViewById(R.id.swingR1);

        return tabView;
    }
}
