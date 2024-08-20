package com.first.menu.Workouts;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.first.menu.Credentials.MainActivity;
import com.first.menu.R;
import com.first.menu.core_workouts.activity_mountain_climber;

public class coree extends AppCompatActivity {

    Button startExercise;
    LinearLayout mountain, sidePlankLift,crunches,russian, sidePlankDips,reverse,bicycle,plank;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coree);

        startExercise = findViewById(R.id.startExercise);
        startExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(coree.this, activity_mountain_climber.class));
            }
        });

        //Dialog
        dialog = new Dialog(coree.this);

        mountain = findViewById(R.id.mountainclimber1_pose);
        mountain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMountainClimbersDialog();
            }
        });

        sidePlankLift = findViewById(R.id.sideplankleftleg2_pose);
        sidePlankLift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSidePlankLiftDialog();
            }
        });

        crunches = findViewById(R.id.crunches3_pose);
        crunches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCrunchesDialog();
            }
        });

        russian = findViewById(R.id.russiantwist4_pose);
        russian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRussianTwist();
            }
        });

        sidePlankDips = findViewById(R.id.sideplankhip5_pose);
        sidePlankDips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSidePlankDipsDialog();
            }
        });

        reverse = findViewById(R.id.reversecrunch6_pose);
        reverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openReverseCrunchDialog();
            }
        });

        bicycle = findViewById(R.id.bicyclecrunch7_pose);
        bicycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBicycleCrunchDialog();
            }
        });

        plank = findViewById(R.id.plank8_pose);
        plank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPlankDialog();
            }
        });
    }
    public void onBackPressed(){
        startActivity(new Intent(coree.this, MainActivity.class));
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    private void openPlankDialog() {
        dialog.setContentView(R.layout.plank_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(true);

        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        dialog.show();
    }

    private void openBicycleCrunchDialog() {
        dialog.setContentView(R.layout.bicyclecrunch_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(true);

        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        dialog.show();
    }

    private void openReverseCrunchDialog() {
        dialog.setContentView(R.layout.reversecrunch_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(true);

        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        dialog.show();
    }

    private void openSidePlankDipsDialog() {
        dialog.setContentView(R.layout.sideplankdips_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(true);

        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        dialog.show();
    }

    private void openRussianTwist() {
        dialog.setContentView(R.layout.russiantwist_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(true);

        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        dialog.show();
    }

    private void openCrunchesDialog() {
        dialog.setContentView(R.layout.crunches_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(true);

        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        dialog.show();
    }

    private void openSidePlankLiftDialog() {
        dialog.setContentView(R.layout.sideplanklift_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(true);

        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        dialog.show();
    }

    private void openMountainClimbersDialog() {
        dialog.setContentView(R.layout.mountainclimbers_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(true);

        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        dialog.show();
    }
}