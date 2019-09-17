package com.example.srourcompu.itsyourtimenow;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.srourcompu.itsyourtimenow.Access.Access;
import com.example.srourcompu.itsyourtimenow.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Sign_In extends AppCompatActivity {

    EditText userId, username, password;
    Button login_bttn, forget_pwd_bttn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__in);

        userId = (EditText)findViewById(R.id.userId);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);

        login_bttn = (Button)findViewById(R.id.login_bttn);
        forget_pwd_bttn = (Button)findViewById(R.id.btn_reset_password);

        // Initializing Firebase
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference user_table = db.getReference("User");

        login_bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog wait = new ProgressDialog(Sign_In.this);
                wait.setMessage("Please Wait while Loading.....");
                wait.show();

                user_table.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Checking the case where the user does not exist in Database
                        if (dataSnapshot.child(userId.getText().toString()).exists()) {

                            // First, Dismiss Progress Dialog
                            wait.dismiss();

                            // Get Info from User Table in the database
                            User user = dataSnapshot.child(userId.getText().toString()).getValue(User.class);
                            user.setUserId(userId.getText().toString());
                            if(user == null){
                                Toast.makeText(Sign_In.this, "USER NULL", Toast.LENGTH_SHORT).show();
                            }
                            if (user.getPassword() != null && user.getPassword().equalsIgnoreCase(password.getText().toString()) &&
                                    user.getUsername() != null && user.getUsername().equalsIgnoreCase(username.getText().toString())) {

                                Toast.makeText(Sign_In.this, "Welcome! Sign In Successfully :)", Toast.LENGTH_SHORT).show();

                               Intent home_intent = new Intent(Sign_In.this, Home.class);
                               Access.accessUser = user;
                               startActivity(home_intent);
                               finish();

                            } else {
                                Toast.makeText(Sign_In.this, "SORRY! Your Sign In Failed :(", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            wait.dismiss();
                            Toast.makeText(Sign_In.this, "SORRY !! You don't have an account", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        });

        forget_pwd_bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forget_pwd = new Intent(Sign_In.this, Forget_Password.class);
                startActivity(forget_pwd);
            }
        });

    }
}
