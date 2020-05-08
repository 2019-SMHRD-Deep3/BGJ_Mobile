package com.example.a3thproject.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a3thproject.R;
import com.example.a3thproject.RecyclerViewAdapter;
import com.example.a3thproject.titleDTO;

import java.util.ArrayList;

public class Fragment_booklistAll extends Fragment {

    private ArrayList<titleDTO> items;
    private String[] arr;
    private String path;
    private String id = "";
    private ArrayList<titleDTO> items2 = new ArrayList<>();
    private String where;
    private  String data;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booklistall, container, false);


        // initDataset();

        data = getArguments().getString("library");
        where = "library";
        arr = data.split("#");

        items = new ArrayList<>();

        items.clear();
        path = "http://172.30.1.19:8081/Podo/library/l";
        for (int n = 0; n < arr.length; n++) {
            items.add(new titleDTO(arr[n], path + n + "/0.jpg"));
            Log.v("dataa", arr[n]);
            Log.v("path", path + n + "/0.jpg");
        }

        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_library);
        recyclerView.setHasFixedSize(true);

        //LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        //layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //recyclerView.setLayoutManager(layoutManager);

        RecyclerView.LayoutManager layoutManager1
                = new GridLayoutManager(context, 1,
                LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layoutManager1);

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(context, items, id, where);
        recyclerView.setAdapter(adapter);


        return view;
    }
}
