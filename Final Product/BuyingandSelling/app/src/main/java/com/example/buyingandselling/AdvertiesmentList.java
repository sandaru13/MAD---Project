package com.example.buyingandselling;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.buyingandselling.Model.AddDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AdvertiesmentList extends AppCompatActivity {

    Button button;
    List<AddDetails> user;
    DatabaseReference ref;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertiesment_list);


        listView = (ListView) findViewById(R.id.listview);
        button=(Button)findViewById(R.id.addadvertise);

        user = new ArrayList<>();

        ref = FirebaseDatabase.getInstance().getReference("Buying&Selling");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user.clear();

                for (DataSnapshot userDatasnap : dataSnapshot.getChildren()) {

                    AddDetails addDetails = userDatasnap.getValue(AddDetails.class);
                    user.add(addDetails);
                }

                MyAdapter adapter = new MyAdapter(AdvertiesmentList.this, R.layout.list, (ArrayList<AddDetails>) user);
                listView.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdvertiesmentList.this, AddAdvertiesment.class);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                AlertDialog.Builder builder1 = new AlertDialog.Builder(AdvertiesmentList.this);
                builder1.setMessage("Do you want to take a call?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Update",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                Intent intent = new Intent(AdvertiesmentList.this, UpdateDetails.class);
                                startActivity(intent);
                            }

                        });

                builder1.setNeutralButton(
                        "Delete",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                AlertDialog.Builder builder1 = new AlertDialog.Builder(AdvertiesmentList.this);
                                builder1.setMessage("Are you sure about this?");
                                builder1.setCancelable(true);

                                builder1.setPositiveButton(
                                        "Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {

                                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                                String userid = user.getUid();
                                                FirebaseDatabase.getInstance().getReference("Buying&Selling").child(userid).removeValue();
                                                Toast.makeText(AdvertiesmentList.this, "Data deleted successfully!", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(AdvertiesmentList.this, AdvertiesmentList.class);
                                                startActivity(intent);
                                            }
                                        });

                                builder1.setNegativeButton(
                                        "No",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });

                                AlertDialog alert11 = builder1.create();
                                alert11.show();

                            }

                        });

                builder1.setNegativeButton(
                        "Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();

                return false;
            }
        });

    }

    static class ViewHolder {

        TextView COL1;
        TextView COL2;
        TextView COL3;
        TextView COL4;
        TextView COL5;
        ImageView imageView;
    }


    class MyAdapter extends ArrayAdapter<AddDetails> {
        LayoutInflater inflater;
        Context myContext;
        List<AddDetails> user;


        public MyAdapter(Context context, int resource, ArrayList<AddDetails> objects) {
            super(context, resource, objects);
            myContext = context;
            user = objects;
            inflater = LayoutInflater.from(context);
            int y;
            String barcode;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public View getView(int position, View view, ViewGroup parent) {
            final ViewHolder holder;
            if (view == null) {
                holder = new ViewHolder();
                view = inflater.inflate(R.layout.list, null);

                holder.COL1 = (TextView) view.findViewById(R.id.namee);
                holder.COL2 = (TextView) view.findViewById(R.id.contactt);
                holder.COL3 = (TextView) view.findViewById(R.id.addresss);
                holder.COL4 = (TextView) view.findViewById(R.id.pricee);
                holder.COL5 = (TextView) view.findViewById(R.id.discriptionn);
                holder.imageView = (ImageView) view.findViewById(R.id.imageView2);


                view.setTag(holder);
            } else {

                holder = (ViewHolder) view.getTag();
            }
            final AddDetails addDetails = user.get(position);

            Picasso.get().load(user.get(position).getImageId()).into(holder.imageView);
            holder.COL1.setText(user.get(position).getName());
            holder.COL2.setText(user.get(position).getContact());
            holder.COL3.setText(user.get(position).getAddress());
            holder.COL4.setText(user.get(position).getPrice());
            holder.COL5.setText(user.get(position).getDiscription());
            System.out.println(holder);

            return view;
        }

    }
}