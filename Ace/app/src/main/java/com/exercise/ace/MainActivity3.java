package com.exercise.ace;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class MainActivity3 extends AppCompatActivity {
    EditText title, description, price, phone;
    private Button button3, ch, up;
    ImageView img;
    StorageReference mStorageRef;
    DatabaseReference reff;
    Ad ad;
    Spinner sp1;
    public Uri imguri;
    long maxid=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        title = (EditText)findViewById(R.id.editTextTextPersonName2);
        description = (EditText)findViewById(R.id.editTextTextPersonName3);
        sp1 = (Spinner) findViewById(R.id.spinner);
        price = (EditText)findViewById(R.id.editTextNumber);
        phone = (EditText)findViewById(R.id.editTextPhone);
        button3 = (Button) findViewById(R.id.button5);
        ad = new Ad();
        reff = FirebaseDatabase.getInstance().getReference().child("Ad");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    maxid = (snapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewAd();
            }
        });

        mStorageRef = FirebaseStorage.getInstance().getReference("Images");
        ch = (Button)findViewById(R.id.button14);
        up = (Button)findViewById(R.id.button);
        img = (ImageView)findViewById(R.id.imageView2);
        ch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Filechooser();
            }
        });
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fileuploader();
            }
        });
    }

    public String getExtension(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }

    public void Fileuploader(){
        StorageReference Ref = mStorageRef.child(System.currentTimeMillis()+"."+getExtension(imguri));

        Ref.putFile(imguri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        Toast.makeText(MainActivity3.this, "Image Uploaded Successfully", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });
    }

    public void Filechooser(){
        Intent intent = new Intent();
        intent.setType("image/'");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //if(requestCode==1 && requestCode==RESULT_OK && data!=null && data.getData()!=null){
            imguri = data.getData();
            img.setImageURI(imguri);
        //}
    }

    public void viewAd() {
        String img = imguri.toString();

        ad.setTitle(title.getText().toString().trim());
        ad.setDescription(description.getText().toString().trim());
        ad.setCategory(sp1.getSelectedItem().toString().trim());
        ad.setPrice(price.getText().toString().trim());
        ad.setPhone(phone.getText().toString().trim());
        ad.setImg(img);

        reff.child(String.valueOf(maxid+1)).setValue(ad);
        Toast.makeText(MainActivity3.this, "Advertisement Successfully Posted", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, MainActivity4.class);
        startActivity(intent);
    }
}
