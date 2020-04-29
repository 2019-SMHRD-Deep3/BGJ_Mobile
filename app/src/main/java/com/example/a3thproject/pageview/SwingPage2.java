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

public class SwingPage2 extends Fragment {

    RecyclerView tab2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View tabView = inflater.inflate(R.layout.swingpage2,container,false);

            // 가져와야 할 레이아웃
            tab2 = tabView.findViewById(R.id.swingR2);

        return tabView;
    }
}
