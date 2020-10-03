package com.example.mad_adzz;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewConfig {
    private Context mcontext;
    private productsAdapter mProdAdapter;


    public void setConfig(RecyclerView recyclerView,Context context, List<Products> product, List<String> keys){
        mcontext = context;
        mProdAdapter = new productsAdapter(product, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mProdAdapter);
    }

    class productlistView extends RecyclerView.ViewHolder{

        private TextView mdescription;
        private TextView mtitle;
        private TextView mprice;
        private TextView mowner;
        private ImageView mimg;
        private String key;
        private TextView mphone;


        public productlistView(ViewGroup parent){
            super(LayoutInflater.from(mcontext).inflate(R.layout.product_list_item,parent, false));

            mtitle = (TextView) itemView.findViewById(R.id.title);
            mdescription = (TextView) itemView.findViewById(R.id.description);
            mprice= (TextView) itemView.findViewById(R.id.price);
            mowner = (TextView) itemView.findViewById(R.id.owner);
            mimg = (ImageView) itemView.findViewById(R.id.imageProduct);
            mphone = (TextView) itemView.findViewById(R.id.phone);

        }



        public void bind(Products pro,String key){
            mtitle.setText(pro.getTitle());
            mdescription.setText(pro.getDescription());
            mprice.setText(pro.getPrice());
            mowner.setText(pro.getOwner());
            mimg.setImageURI(pro.getImg());
            mphone.setText(pro.getPhone());
            this.key = key;
        }

    }


    class productsAdapter extends RecyclerView.Adapter<productlistView>{
        private List<Products> mprodlist;
        private List<String> mkeys;

        public productsAdapter(List<Products> mprodlist, List<String> mkeys) {
            this.mprodlist = mprodlist;
            this.mkeys = mkeys;
        }

        @NonNull
        @Override
        public productlistView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new productlistView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull productlistView holder, int position) {
            holder.bind(mprodlist.get(position), mkeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mprodlist.size();
        }
    }
}


