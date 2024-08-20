package com.first.menu.backandshoulder_workouts;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.first.menu.Credentials.MainActivity;
import com.first.menu.Credentials.RecordBMI;
import com.first.menu.CustomAlertExercise;
import com.first.menu.R;
import com.first.menu.Workouts.backk;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import io.github.muddz.styleabletoast.StyleableToast;

public class activity_floorY_raises extends AppCompatActivity {

    ImageView btnTutorial;

    Button btnDone;

    TextView timer, NextExercise, youtubeIndicator,demoIndicator;

    MediaPlayer mediaPlayer;

    CountDownTimer countDownTimer;

    ViewPager viewPager;

    activity_floorY_slideAdapter adapter;

    LinearLayout indicatorLayout;

    FirebaseUser user;

    DatabaseReference reference;

    String Uid;
    boolean status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floory_raises);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        status = false;

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("users");
        Uid = user.getUid();

        viewPager = findViewById(R.id.viewPager_id);
        adapter = new activity_floorY_slideAdapter(this);
        viewPager.setAdapter(adapter);

        demoIndicator = findViewById(R.id.demo_indicator);
        youtubeIndicator = findViewById(R.id.youtube_indicator);
        indicatorLayout = findViewById(R.id.indicator_layout);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                // highlight the current indicator and unhighlight the others
                switch (position) {
                    case 0:
                        demoIndicator.setBackgroundColor(getResources().getColor(R.color.crimson2));
                        youtubeIndicator.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                        break;
                    case 1:
                        demoIndicator.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                        youtubeIndicator.setBackgroundColor(getResources().getColor(R.color.crimson2));
                        break;
                    // add more cases for additional slides
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });

        //Audio
        mediaPlayer = MediaPlayer.create(this, R.raw.whistle);

        /*Link for tutorial
        btnTutorial = findViewById(R.id.floorYRaises_link);
        btnTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://youtu.be/lUGi7NilqWA");
            }
        });

         */

        timer = findViewById(R.id.timer_flooryraises);
        btnDone = findViewById(R.id.flooryraises_btndone);
        NextExercise = findViewById(R.id.lastexercise_flooryraises);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                mediaPlayer.start();
                btnDone.setVisibility(View.GONE);
                NextExercise.setText("Workout Complete");
                StyleableToast.makeText(activity_floorY_raises.this, "Workout Complete: CONGRATULATIONS!", R.style.Succeeded).show();

                //COMPUTES CALORIE BURNED//
                Query bmiRef = reference.child(Uid+"/BMI").orderByChild("recordKey").limitToLast(1);
                bmiRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            // Get the latest BMI record
                            RecordBMI currentRecord = snapshot.getChildren().iterator().next().getValue(RecordBMI.class);
                            if (currentRecord != null) {
                                // Extract the weight from the BMI record
                                double weight = Double.parseDouble(currentRecord.weight);
                                // Set exercise duration to 1 minute
                                double duration = 0.017;
                                // MET value for knee push-ups
                                double metValue = 3.3;
                                // Calculate calorie burn per minute
                                double calorieBurn = (metValue * weight * duration * 10) / 10;
                                // Format calorie burn to 2 decimal places
                                @SuppressLint("DefaultLocale") String formattedCalorieBurn = String.format("%.2f", calorieBurn);
                                // Store the calorie burn in the database
                                reference.child(Uid).child("Calorie Burn/Back and Shoulder/Floor Y Raises").setValue(formattedCalorieBurn);
                            } else {
                                // BMI record is null
                                Toast.makeText(activity_floorY_raises.this,"No record found!",Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // No BMI records found for the user
                            Toast.makeText(activity_floorY_raises.this,"No record found!",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle database error
                        Toast.makeText(activity_floorY_raises.this,"Something went wrong!", Toast.LENGTH_LONG).show();
                    }
                });

                countDownTimer = new CountDownTimer(15000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        // Update the TextView with the remaining seconds
                        int secondsLeft = (int) millisUntilFinished / 1000;
                        timer.setText("Rest: " + secondsLeft + " seconds");
                    }

                    public void onFinish() {
                        mediaPlayer.start();
                        startActivity(new Intent(activity_floorY_raises.this, backk.class));
                        btnDone.setVisibility(View.GONE);
                        timer.setVisibility(View.GONE);
                    }
                }; countDownTimer.start();


            }

        });

    }
    @Override
    public void onBackPressed() {
        CustomAlertExercise customAlertExercise;
        if(status) {
            customAlertExercise = new CustomAlertExercise(this, new CustomAlertExercise.CustomAlertExerciseListener() {
                @Override
                public void onYesClicked() {
                    startActivity(new Intent(activity_floorY_raises.this, MainActivity.class));
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    countDownTimer.cancel();
                }
            });
        } else {
            customAlertExercise = new CustomAlertExercise(this, new CustomAlertExercise.CustomAlertExerciseListener() {
                @Override
                public void onYesClicked() {
                    startActivity(new Intent(activity_floorY_raises.this, MainActivity.class));
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
            });
        }
        customAlertExercise.show();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void gotoUrl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

}