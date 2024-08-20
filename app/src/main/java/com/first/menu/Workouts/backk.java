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
import com.first.menu.backandshoulder_workouts.activity_pike_pushUp;

public class backk extends AppCompatActivity {

    Button startExercise;
    LinearLayout pike, tricepsBack, pushUpBack, inclineBack, mountainBack, pushBackPush, plankDD, plankUp,elevated, floorY;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backk);

        startExercise = findViewById(R.id.startExercise);
        startExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(backk.this, activity_pike_pushUp.class));
            }
        });

        //Dialog
        dialog = new Dialog(backk.this);

        pike = findViewById(R.id.pikepushup1_pose);
        pike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPikePushUpDialog();
            }
        });

        tricepsBack = findViewById(R.id.tricepdipbs2_pose);
        tricepsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTricepsBSDialog();
            }
        });

        pushUpBack = findViewById(R.id.pushupbs3_pose);
        pushUpBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPushUpBackDialog();
            }
        });

        inclineBack = findViewById(R.id.inclinepushupbs4_pose);
        inclineBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openInclineBackDialog();
            }
        });

        mountainBack = findViewById(R.id.mountainclimbersbs5_pose);
        mountainBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMountainClimbersBackDialog();
            }
        });

        pushBackPush = findViewById(R.id.pushback_pose);
        pushBackPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPushBackPushUpDialog();
            }
        });

        plankDD = findViewById(R.id.plankDown_pose);
        plankDD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPlankDownDogDialog();
            }
        });

        plankUp = findViewById(R.id.plankUP_pose);
        plankUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPlankUpDialog();
            }
        });

        elevated = findViewById(R.id.Elevated_pose);
        elevated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openElevatedBackDialog();
            }
        });

        floorY = findViewById(R.id.FloorY_pose);
        floorY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFloorYRaisesDialog();
            }
        });
    }
    public void onBackPressed(){
        startActivity(new Intent(backk.this, MainActivity.class));
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    private void openFloorYRaisesDialog() {
        dialog.setContentView(R.layout.flooryraises_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(true);

        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        dialog.show();
    }

    private void openElevatedBackDialog() {
        dialog.setContentView(R.layout.elevatedpike_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(true);

        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        dialog.show();
    }

    private void openPlankUpDialog() {
        dialog.setContentView(R.layout.plankup_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(true);

        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        dialog.show();
    }

    private void openPlankDownDogDialog() {
        dialog.setContentView(R.layout.plankdowndog_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(true);

        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        dialog.show();
    }

    private void openPushBackPushUpDialog() {
        dialog.setContentView(R.layout.pushbackpushup_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(true);

        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        dialog.show();
    }

    private void openMountainClimbersBackDialog() {
        dialog.setContentView(R.layout.mountainclimbersbs_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(true);

        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        dialog.show();
    }

    private void openInclineBackDialog() {
        dialog.setContentView(R.layout.inclinepushupbs_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(true);

        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        dialog.show();
    }

    private void openPushUpBackDialog() {
        dialog.setContentView(R.layout.pushupbs_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(true);

        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        dialog.show();
    }

    private void openTricepsBSDialog() {
        dialog.setContentView(R.layout.tricepdipsbs_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(true);

        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        dialog.show();
    }

    private void openPikePushUpDialog() {
        dialog.setContentView(R.layout.pikepushup_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(true);

        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        dialog.show();
    }
}