package com.exercise.ace;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyAdapterViewHolder> {


    public Context c;
    public ArrayList<Ad> arrayList;
    public MyAdapter(Context c, ArrayList<Ad> arrayList){

        this.c = c;
        this.arrayList = arrayList;

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public MyAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_item,parent,false);

        return new MyAdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterViewHolder holder, int position) {
        Ad ads = arrayList.get(position) ;
        holder.des.setText(ads.getDescription());
        holder.tit.setText(ads.getTitle());
        holder.pri.setText(ads.getPrice());

        //String img = ads.getImg();
        //Uri image = Uri.parse(img);

        //holder.imgv.setImageURI(ads.sandaru());
        holder.pho.setText(ads.getPhone());

    }

    public class MyAdapterViewHolder extends RecyclerView.ViewHolder{

        public TextView des;
        public TextView tit;
        public TextView pri;
        public ImageView imgv;
        public TextView pho;

        public MyAdapterViewHolder(View itemView)  {
            super(itemView);
            tit = (TextView) itemView.findViewById(R.id.title);
            des = (TextView) itemView.findViewById(R.id.description);
            pri = (TextView) itemView.findViewById(R.id.price);
            imgv = (ImageView) itemView.findViewById(R.id.imageProduct);
            pho = (TextView) itemView.findViewById(R.id.phone);
        }
    }



}
