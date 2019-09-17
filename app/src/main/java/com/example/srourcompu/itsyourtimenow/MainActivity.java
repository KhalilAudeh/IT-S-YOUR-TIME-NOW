package com.example.srourcompu.itsyourtimenow;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button facebook, instagram, twitter;
    Button SignIn_button, SignUp_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        facebook = (Button) findViewById(R.id.fb_button);
        instagram = (Button) findViewById(R.id.instagram_button);
        twitter = (Button) findViewById(R.id.twitter_button);

        SignIn_button = (Button)findViewById(R.id.signIn_button);
        SignUp_button = (Button)findViewById(R.id.signUp_button);

        SignIn_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signIn = new Intent(MainActivity.this, Sign_In.class);
                startActivity(signIn);
            }
        });

        SignUp_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUp = new Intent(MainActivity.this, Sign_Up.class);
                startActivity(signUp);
            }
        });

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent fb_intent = goToFacebook(MainActivity.this);
                startActivity(fb_intent);

            }
        });

        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent instagram_intent = goToInstagram(MainActivity.this);
                startActivity(instagram_intent);

            }
        });

        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent twitter_intent = goToTwitter(MainActivity.this);
                startActivity(twitter_intent);

            }
        });
    }

    public static Intent goToFacebook(Context c){
//        try {
//            return new Intent(Intent.ACTION_VIEW, Uri.parse("fb://group/232624060226227"));
//        }
        //catch (Exception e){
            return new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/khalil.audeh"));
       // }
    }

    public static Intent goToInstagram(Context c){
//        try {
//            return new Intent(Intent.ACTION_VIEW, Uri.parse("fb://group/232624060226227"));
//        }
        //catch (Exception e){
        return new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/khalil_audeh/"));
        // }
    }

    public static Intent goToTwitter(Context c){
//        try {
//            return new Intent(Intent.ACTION_VIEW, Uri.parse("fb://group/232624060226227"));
//        }
        //catch (Exception e){
        return new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/?lang=en"));
        // }
    }
}
