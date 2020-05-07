package com.example.a3thproject;
import android.content.Context;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import io.reactivex.annotations.NonNull;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private ArrayList<titleDTO> mPersons;
    private LayoutInflater mInflate;
    private Context mContext;
    private String id;
    private String where;

    public RecyclerViewAdapter(Context context, ArrayList<titleDTO> persons, String id, String where) {
        this.mContext = context;
        this.mInflate = LayoutInflater.from(context);
        this.mPersons = persons;
        this.id = id;
        this.where = where;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflate.inflate(R.layout.title, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        //데이터오 뷰를 바인딩
        String url = mPersons.get(position).path;
        holder.title.setText(mPersons.get(position).title);
        Glide.with(mContext)
                .load(url)
                .centerCrop()
                .into(holder.imageView)
        ;


    }

    @Override
    public int getItemCount() {
        return mPersons.size();
    }


    //ViewHolder
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView imageView;


        public MyViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.tv_title);
            imageView = (ImageView) itemView.findViewById(R.id.pic);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Intent intent = new Intent(v.getContext(), myBookread.class);
                    intent.putExtra("id", id);
                    intent.putExtra("num", position);
                    intent.putExtra("where",where);
                    v.getContext().startActivity(intent);
                }
            });
        }

    }
}