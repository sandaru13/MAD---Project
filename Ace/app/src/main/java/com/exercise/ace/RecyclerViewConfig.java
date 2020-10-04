package com.exercise.ace;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.util.List;

public class RecyclerViewConfig {
    private Context mcontext;
    private productsAdapter mProdAdapter;


    public void setConfig(RecyclerView recyclerView,Context context, List<Ad> ad, List<String> keys){
        mcontext = context;
        mProdAdapter = new productsAdapter(ad, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mProdAdapter);
    }

    class productlistView extends RecyclerView.ViewHolder{

        private TextView mdescription;
        private TextView mtitle;
        private TextView mprice;
        private ImageView mimg;
        private String key;
        private TextView mphone;


        public productlistView(ViewGroup parent){
            super(LayoutInflater.from(mcontext).inflate(R.layout.product_list_item,parent, false));

            mtitle = (TextView) itemView.findViewById(R.id.title);
            mdescription = (TextView) itemView.findViewById(R.id.description);
            mprice= (TextView) itemView.findViewById(R.id.price);
            mimg = (ImageView) itemView.findViewById(R.id.imageProduct);
            mphone = (TextView) itemView.findViewById(R.id.phone);

        }



        public void bind(Ad ado,String key){
            mtitle.setText(ado.getTitle());
            mdescription.setText(ado.getDescription());
            mprice.setText(ado.getPrice());

            //String img = ado.getImg();
            //Uri image = Uri.parse(img);

            //mimg.setImageURI(ado.sandaru());
            mphone.setText(ado.getPhone());
            this.key = key;
        }

    }


    class productsAdapter extends RecyclerView.Adapter<productlistView>{
        private List<Ad> mprodlist;
        private List<String> mkeys;

        public productsAdapter(List<Ad> mprodlist, List<String> mkeys) {
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
