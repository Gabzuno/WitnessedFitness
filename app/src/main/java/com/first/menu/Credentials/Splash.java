package com.first.menu.Credentials;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.first.menu.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Splash extends AppCompatActivity {

    VideoView view;
    private static final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        boolean isFirstTime = prefs.getBoolean("isFirstTime",true);


        view = findViewById(R.id.logo);
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.new_splash);
        view.setVideoURI(uri);
        view.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(isFirstTime){
                    //Start the Slide Main Activity
                    startActivity(new Intent(Splash.this,BirthdateActivityDialog.class));
                    overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                    //Save the "isFirstTime" flag to false
                    prefs.edit().putBoolean("isFirstTime",false).apply();
                } else {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if(user != null){
                        //User is loged in, start the Main Activity
                        startActivity(new Intent(Splash.this, MainActivity.class));
                        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                    } else {
                        startActivity(new Intent(Splash.this, LogIn.class));
                        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                    }
                }
                // Close the Splash
                finish();
            }
        },2500);
    }
}