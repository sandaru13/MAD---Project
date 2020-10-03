package com.example.mad_adzz;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyAdapterViewHolder> {

    public Context c;
    public ArrayList<Products> arrayList;
    public MyAdapter(Context c, ArrayList<Products> arrayList){

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
        Products prods = arrayList.get(position) ;
        holder.des.setText(prods.getDescription());
        holder.tit.setText(prods.getTitle());
        holder.pri.setText(prods.getPrice());
        holder.own.setText(prods.getOwner());
        holder.imgv.setImageURI(prods.getImg());
        holder.pho.setText(prods.getPhone());

    }

    public class MyAdapterViewHolder extends RecyclerView.ViewHolder{

        public TextView des;
        public TextView tit;
        public TextView pri;
        public TextView own;
        public ImageView imgv;
        public TextView pho;

        public MyAdapterViewHolder(View itemView)  {
            super(itemView);
            tit = (TextView) itemView.findViewById(R.id.title);
            des = (TextView) itemView.findViewById(R.id.description);
            pri = (TextView) itemView.findViewById(R.id.price);
            own = (TextView) itemView.findViewById(R.id.owner);
            imgv = (ImageView) itemView.findViewById(R.id.imageProduct);
            pho = (TextView) itemView.findViewById(R.id.phone);
        }
    }



}
