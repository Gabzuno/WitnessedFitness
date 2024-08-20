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
import com.first.menu.leg_workouts.activity_goblet_squat;

public class legg extends AppCompatActivity {

    Button startExercise;
    LinearLayout goblet,pendulum,jump,hip,jumping, singleLegD,pistol,curtsy,lateral,pause;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legg);

        startExercise = findViewById(R.id.startExercise);
        startExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(legg.this, activity_goblet_squat.class));
            }
        });

        //Dialog
        dialog = new Dialog(legg.this);

        goblet = findViewById(R.id.gobletsquat1_pose);
        goblet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGobletSquatDialog();
            }
        });

        pendulum = findViewById(R.id.pendulumlunge2_pose);
        pendulum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPendulumLungeDialog();
            }
        });

        jump = findViewById(R.id.jumpsquat3_pose);
        jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openJumpSquatDialog();
            }
        });

        hip = findViewById(R.id.hipbridge4_pose);
        hip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHipBridgeDialog();
            }
        });

        jumping = findViewById(R.id.jumpinglunge5_pose);
        jumping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openJumpingLungeDialog();
            }
        });

        singleLegD = findViewById(R.id.singlelegdeadlift6_pose);
        singleLegD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSingleLegDDialog();
            }
        });

        pistol = findViewById(R.id.pistolsquat7_pose);
        pistol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPistolSquat();
            }
        });

        curtsy = findViewById(R.id.curtsylunge8_pose);
        curtsy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCurtsyLunge();
            }
        });

        lateral = findViewById(R.id.laterallunge9_pose);
        lateral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLateralLunge();
            }
        });

        pause = findViewById(R.id.pausesquat10_pose);
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPauseSquat();
            }
        });
    }
    public void onBackPressed(){
        startActivity(new Intent(legg.this, MainActivity.class));
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    private void openPauseSquat() {
        dialog.setContentView(R.layout.pausesquat_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(true);

        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        dialog.show();
    }

    private void openLateralLunge() {
        dialog.setContentView(R.layout.laterallunge_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(true);

        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        dialog.show();
    }

    private void openCurtsyLunge() {
        dialog.setContentView(R.layout.curtsylunge_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(true);

        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        dialog.show();
    }

    private void openPistolSquat() {
        dialog.setContentView(R.layout.pistolsquat_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(true);

        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        dialog.show();
    }

    private void openSingleLegDDialog() {
        dialog.setContentView(R.layout.singlelegdeadlift_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(true);

        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        dialog.show();
    }

    private void openJumpingLungeDialog() {
        dialog.setContentView(R.layout.jumpinglunge_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(true);

        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        dialog.show();
    }

    private void openHipBridgeDialog() {
        dialog.setContentView(R.layout.hipbridge_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(true);

        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        dialog.show();
    }

    private void openJumpSquatDialog() {
        dialog.setContentView(R.layout.jumpsquat_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(true);

        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        dialog.show();
    }

    private void openPendulumLungeDialog() {
        dialog.setContentView(R.layout.pendulumlunge_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(true);

        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        dialog.show();
    }

    private void openGobletSquatDialog() {
        dialog.setContentView(R.layout.gobletsquat_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(true);

        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        dialog.show();
    }

}