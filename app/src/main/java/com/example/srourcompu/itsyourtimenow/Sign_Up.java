package com.example.srourcompu.itsyourtimenow;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.srourcompu.itsyourtimenow.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuthActionCodeException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

import info.hoang8f.widget.FButton;

public class Sign_Up extends AppCompatActivity {

    Button choose_profile_botton, upload_button;
    ImageView profile_view;
    Uri image_path;
    final int PICK_IMAGE_REQUEST = 71;

    MaterialEditText editID, editUsername, editPhone, editBirth, editPwd;
    Button signUp;

    //Firebase
    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__up);

        //Initialize Firebase
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        FirebaseDatabase DB = FirebaseDatabase.getInstance();
        final DatabaseReference user_table = DB.getReference("User");

        // Initialize View
        choose_profile_botton = (Button) findViewById(R.id.choose_button);
        upload_button = (Button) findViewById(R.id.upload_button);
        profile_view = (ImageView) findViewById(R.id.profile_view);

        editID = (MaterialEditText)findViewById(R.id.clientID);
        editUsername = (MaterialEditText)findViewById(R.id.clientName);
        editPhone = (MaterialEditText)findViewById(R.id.clientPhone);
        editBirth = (MaterialEditText)findViewById(R.id.clientDateOfBirth);
        editPwd = (MaterialEditText)findViewById(R.id.clientPassword);
        signUp = (Button) findViewById(R.id.SignUpButton);

        choose_profile_botton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseImage();
            }
        });

        upload_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadImage();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog msg = new ProgressDialog(Sign_Up.this);
                msg.setMessage("Please wait while creating your profile...");
                msg.show();

                user_table.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(editID.getText().toString()).exists()){
                            msg.dismiss();
                            Toast.makeText(Sign_Up.this, "ID already exists", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            msg.dismiss();
                            User user = new User(editID.getText().toString(), editUsername.getText().toString(), editPhone.getText().toString(), editBirth.getText().toString(), editPwd.getText().toString());
                            user_table.child(editID.getText().toString()).setValue(user);
                            Toast.makeText(Sign_Up.this, "Congrats!! You are a member now :))", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    private void UploadImage() {
        if (image_path != null){
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading Profile...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
            ref.putFile(image_path).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    Toast.makeText(Sign_Up.this, "Profile Uploaded", Toast.LENGTH_SHORT).show();
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(Sign_Up.this, "Uploading Failed. SORRY:("+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            })
            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded"+(int)progress+"%");
                }
            });
        }
    }

    private void ChooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){

            image_path = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), image_path);
                profile_view.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
