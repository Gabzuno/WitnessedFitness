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
import com.first.menu.chest_workouts.activity_knee_pushUp;

public class chestt extends AppCompatActivity{

    Button startExercise;
    LinearLayout knee,diamond, pushUp,wide,clap,incline,decline,hindu,medicine,burpees;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chestt);


        startExercise = findViewById(R.id.startExercise);
        startExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPreExerciseDialog();
            }
        });

        knee = findViewById(R.id.kneepushup1_pose);
            knee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openKneePushUpDialog();
            }
        });

        diamond = findViewById(R.id.diamondpushup2_pose);
        diamond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDiamondPushUpDialog();
            }
        });

        pushUp = findViewById(R.id.pushup3_pose);
        pushUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPushUpDialog();
            }
        });

        wide = findViewById(R.id.widepushup4_pose);
        wide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWideArmPushUpDialog();
            }
        });

        clap = findViewById(R.id.clappushup5_pose);
        clap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openClapPushUpDialog();
            }
        });

        incline = findViewById(R.id.inclinepushup6_pose);
        incline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openInclinePushUpDialog();
            }
        });

        decline = findViewById(R.id.declinepushup7_pose);
        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDeclinePushUpDialog();
            }
        });

        hindu = findViewById(R.id.hindupushup8_pose);
        hindu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHinduPushUpDialog();
            }
        });

        medicine = findViewById(R.id.medicinepushup9_pose);
        medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMedicineBallPushUpDialog();
            }
        });

        burpees = findViewById(R.id.burpees10_pose);
        burpees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBurpeesDialog();
            }
        });

    }

    public void onBackPressed(){
        startActivity(new Intent(chestt.this, MainActivity.class));
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    private void openBurpeesDialog() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.burpees_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(true);

        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        dialog.show();
    }

    private void openMedicineBallPushUpDialog() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.medicineballpushup_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(true);

        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        dialog.show();
    }

    private void openHinduPushUpDialog() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.hindupushup_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(true);

        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        dialog.show();
    }

    private void openDeclinePushUpDialog() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.declinepushup_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(true);

        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        dialog.show();
    }

    private void openInclinePushUpDialog() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.inclinepushup_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(true);

        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        dialog.show();
    }

    private void openClapPushUpDialog() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.clappushup_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(true);

        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        dialog.show();
    }

    private void openWideArmPushUpDialog() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.widearmpushup_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(true);

        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        dialog.show();
    }

    private void openPushUpDialog() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.pushup_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(true);

        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        dialog.show();
    }

    private void openDiamondPushUpDialog() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.diamondpushup_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(true);

        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        dialog.show();
    }

    private void openKneePushUpDialog() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.kneepushup_dialog);
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
                startActivity(new Intent(chestt.this, activity_knee_pushUp.class));
                dialog.dismiss(); // Close the dialog if needed
            }
        });

        dialog.show();
    }
}