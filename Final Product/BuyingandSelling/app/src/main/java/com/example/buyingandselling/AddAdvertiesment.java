package com.example.buyingandselling;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.buyingandselling.Model.AddDetails;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

public class AddAdvertiesment extends AppCompatActivity {

    ImageView imageView;
    EditText e1;
    EditText e2;
    EditText e3;
    EditText e4;
    EditText e5;
    Button button;
    Uri FilePathUri;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    int Image_Request_Code = 7;
    ProgressDialog progressDialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_advertiesment);

        storageReference = FirebaseStorage.getInstance().getReference("Buying&Selling");
        databaseReference = FirebaseDatabase.getInstance().getReference("Buying&Selling");
        imageView=(ImageView)findViewById(R.id.imageView);
        e1=(EditText)findViewById(R.id.name);
        e2=(EditText)findViewById(R.id.contact);
        e3=(EditText)findViewById(R.id.address);
        e4=(EditText)findViewById(R.id.price);
        e5=(EditText)findViewById(R.id.discription);
        button=(Button)findViewById(R.id.submit);
        progressDialog = new ProgressDialog(AddAdvertiesment.this);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), Image_Request_Code);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                UploadImage();

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri = data.getData();

            try {
                Picasso.get().load(FilePathUri).into(imageView);
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);
                imageView.setImageBitmap(bitmap);
            }
            catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }

    public void UploadImage() {

        if (FilePathUri != null) {

            final String name = e1.getText().toString().trim();
            final String contact = e2.getText().toString().trim();
            final String address = e3.getText().toString().trim();
            final String price = e4.getText().toString().trim();
            final String discription = e5.getText().toString().trim();

            if(name.isEmpty()){
                e1.setError("Name is required");
            }else if(contact.isEmpty()){
                e2.setError("Contact number is required");
            }else if(address.isEmpty()){
                e3.setError("Address is required");
            }else if(price.isEmpty()){
                e4.setError("Price is required");
            }else if(discription.isEmpty()){
                e5.setError("Discription is required");
            }else {

            progressDialog.setTitle("Details are Uploading...");
            progressDialog.show();
            StorageReference storageReference2 = storageReference.child(System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));
            storageReference2.putFile(FilePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {



                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Advertisement uploaded Successfully ", Toast.LENGTH_LONG).show();
                            @SuppressWarnings("VisibleForTests")
                            AddDetails addDetails = new AddDetails(name,contact,address,price,discription,taskSnapshot.getUploadSessionUri().toString());
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            String userid = user.getUid();
                            databaseReference.child(userid).setValue(addDetails);

                            Intent intent = new Intent(AddAdvertiesment.this, AdvertiesmentList.class);
                            startActivity(intent);

                        }
                    });
        }
    }
        else {

            Toast.makeText(AddAdvertiesment.this, "Missed detail/details", Toast.LENGTH_LONG).show();
        }
        }
}
