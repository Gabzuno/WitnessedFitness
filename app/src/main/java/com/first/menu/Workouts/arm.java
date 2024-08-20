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
import com.first.menu.arm_workouts.activity_sidearm_raise;

public class arm extends AppCompatActivity {

    Button startExercise;
    LinearLayout sidearm, armCircle, pushUp, Triceps, sidePlank,superman,inchworm, plankTap,decline,floor;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_armm);

        //Dialog
        dialog = new Dialog(arm.this);

        startExercise = findViewById(R.id.startExercise);
        startExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPreExerciseDialog();
            }
        });

        sidearm = findViewById(R.id.sidearmraise1_pose);
        sidearm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSideArmRaiseDialog();
            }
        });

        armCircle = findViewById(R.id.armcircle2_pose);
        armCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openArmCircleDialog();
            }
        });

        pushUp = findViewById(R.id.pushuparm3_pose);
        pushUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPushUpArmDialog();
            }
        });

        Triceps = findViewById(R.id.tricepdips4_pose);
        Triceps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTricepsDipsDialog();
            }
        });

        sidePlank = findViewById(R.id.sideplankrotation5_pose);
        sidePlank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSidePlankDialog();
            }
        });

        superman = findViewById(R.id.superman6_pose);
        superman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSupermanDialog();
            }
        });

        inchworm = findViewById(R.id.inchworm7_pose);
        inchworm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openInchwormDialog();
            }
        });

        plankTap = findViewById(R.id.planktap8_pose);
        plankTap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPlankTapDialog();
            }
        });

        decline = findViewById(R.id.declinearmpushup9_pose);
        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDeclineArm();
            }
        });

        floor = findViewById(R.id.floortricepdip10_pose);
        floor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFloorArm();
            }
        });
    }
    public void onBackPressed(){
        startActivity(new Intent(arm.this, MainActivity.class));
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    private void openFloorArm() {
        dialog.setContentView(R.layout.floortricepdips_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(true);

        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        dialog.show();
    }

    private void openDeclineArm() {
        dialog.setContentView(R.layout.declinearm_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(true);

        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        dialog.show();
    }

    private void openPlankTapDialog() {
        dialog.setContentView(R.layout.planktap_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(true);

        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        dialog.show();
    }

    private void openInchwormDialog() {
        dialog.setContentView(R.layout.inchworm_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(true);

        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        dialog.show();
    }

    private void openSupermanDialog() {
        dialog.setContentView(R.layout.superman_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(true);

        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        dialog.show();
    }

    private void openSidePlankDialog() {
        dialog.setContentView(R.layout.sideplank_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(true);

        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        dialog.show();
    }

    private void openTricepsDipsDialog() {
        dialog.setContentView(R.layout.tricepdips_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(true);

        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        dialog.show();
    }

    private void openPushUpArmDialog() {
        dialog.setContentView(R.layout.pushuparm_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(true);

        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        dialog.show();
    }

    private void openArmCircleDialog() {
        dialog.setContentView(R.layout.armcircle_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(true);

        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        dialog.show();
    }

    private void openSideArmRaiseDialog() {
        dialog.setContentView(R.layout.sidearmraise_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(true);

        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        dialog.show();
    }

    private void openPreExerciseDialog() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.pre_exercise_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(true);

        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        Button startButton = dialog.findViewById(R.id.beginButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the desired activity when the button in the dialog is clicked
                startActivity(new Intent(arm.this, activity_sidearm_raise.class));
                dialog.dismiss(); // Close the dialog if needed
            }
        });

        dialog.show();
    }
}