package com.example.a3thproject.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.a3thproject.PickPictureActivity;
import com.example.a3thproject.R;

public class Fragment_nonresister extends Fragment {

    String id;

    public Fragment_nonresister(String id) {
        this.id = id;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.nonresister, container, false);



        view.findViewById(R.id.sendResister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), PickPictureActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);


            }
        });


        return view;





    }
}
