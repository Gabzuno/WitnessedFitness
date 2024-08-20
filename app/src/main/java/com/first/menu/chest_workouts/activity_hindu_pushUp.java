package com.first.menu.chest_workouts;

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

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.first.menu.Credentials.MainActivity;
import com.first.menu.CustomAlertExercise;
import com.first.menu.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import io.github.muddz.styleabletoast.StyleableToast;

public class activity_hindu_pushUp extends AppCompatActivity {

    ImageView btnTutorial;

    Button btnDone,btnSkip;

    TextView timer, NextExercise, youtubeIndicator,demoIndicator;

    MediaPlayer mediaPlayer, mediaPlayer2, hindupushup_done;

    CountDownTimer countDownTimer;

    ViewPager viewPager;

    activity_hindu_slideAdapter adapter;

    LinearLayout indicatorLayout;

    boolean status;

    FirebaseUser user;

    DatabaseReference reference;

    String Uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hindu_pushup);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        status = false;

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("users");
        Uid = user.getUid();

        viewPager = findViewById(R.id.viewPager_id);
        adapter = new activity_hindu_slideAdapter(this);
        viewPager.setAdapter(adapter);

        demoIndicator = findViewById(R.id.demo_indicator);
        youtubeIndicator = findViewById(R.id.youtube_indicator);
        indicatorLayout = findViewById(R.id.indicator_layout);

        //Audio
        mediaPlayer2 = MediaPlayer.create(this, R.raw.chest_hindupushup);
        mediaPlayer2.start();

        /*Link for tutorial
        btnTutorial = findViewById(R.id.hindupushup_link);
        btnTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://www.youtube.com/watch?v=HE0ijmUc6Og");
            }
        });

         */

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

        timer = findViewById(R.id.timer_hindupushup);
        btnDone = findViewById(R.id.hindupushup_btndone);
        btnSkip = findViewById(R.id.skipRest);
        NextExercise = findViewById(R.id.nextexercise_medicineball);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                status = true;

                if(mediaPlayer2 != null) {
                    mediaPlayer2.stop();
                    mediaPlayer2.release();
                    mediaPlayer2 = null;
                }

                hindupushup_done = MediaPlayer.create(activity_hindu_pushUp.this, R.raw.chest_hindupushupdone);
                hindupushup_done.start();

                btnDone.setVisibility(View.GONE);
                NextExercise.setText("Next Exercise: Medicine Ball Push-Up");
                StyleableToast.makeText(activity_hindu_pushUp.this, "Workout Done.", R.style.styleToast).show();
                btnSkip.setVisibility(View.VISIBLE);
                btnSkip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(mediaPlayer2 != null) {
                            mediaPlayer2.stop();
                            mediaPlayer2.release();
                            mediaPlayer2 = null;
                        }
                        if(hindupushup_done != null) {
                            hindupushup_done.stop();
                            hindupushup_done.release();
                            hindupushup_done = null;
                        }
                        startActivity(new Intent(activity_hindu_pushUp.this, activity_medicineBall_pushUp.class));
                        countDownTimer.cancel();

                        //COMPUTES CALORIE BURNED//
                        /*
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
                                        double metValue = 5.8;
                                        // Calculate calorie burn per minute
                                        double calorieBurn = (metValue * weight * duration * 10) / 10;
                                        // Format calorie burn to 2 decimal places
                                        @SuppressLint("DefaultLocale") String formattedCalorieBurn = String.format("%.2f", calorieBurn);
                                        // Store the calorie burn in the database
                                        reference.child(Uid).child("Calorie Burn/Chest/Hindu Push Ups").setValue(formattedCalorieBurn);

                                        // Save the calorie burn value in the Chest_Record object
                                        Chest_Record chestRecord = new Chest_Record();
                                        chestRecord.setHinduCB(formattedCalorieBurn);
                                    } else {
                                        // BMI record is null
                                        Toast.makeText(activity_hindu_pushUp.this,"No record found!",Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    // No BMI records found for the user
                                    Toast.makeText(activity_hindu_pushUp.this,"No record found!",Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                // Handle database error
                                Toast.makeText(activity_hindu_pushUp.this,"Something went wrong!", Toast.LENGTH_LONG).show();
                            }
                        });
                        */
                    }
                });
                countDownTimer = new CountDownTimer(25000, 1000) {
                    @SuppressLint("SetTextI18n")
                    public void onTick(long millisUntilFinished) {
                        // Update the TextView with the remaining seconds
                        int secondsLeft = (int) millisUntilFinished / 1000;
                        timer.setText("Rest: " + secondsLeft + " seconds");
                    }

                    public void onFinish() {
                        mediaPlayer.start();
                        startActivity(new Intent(activity_hindu_pushUp.this, activity_medicineBall_pushUp.class));
                        btnDone.setVisibility(View.GONE);
                        timer.setText("Workout Done.");

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
                    startActivity(new Intent(activity_hindu_pushUp.this, MainActivity.class));
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    countDownTimer.cancel();
                    if(mediaPlayer2 != null) {
                        mediaPlayer2.stop();
                        mediaPlayer2.release();
                        mediaPlayer2 = null;
                    }
                    if(hindupushup_done != null) {
                        hindupushup_done.stop();
                        hindupushup_done.release();
                        hindupushup_done = null;
                    }
                }
            });
        } else {
            customAlertExercise = new CustomAlertExercise(this, new CustomAlertExercise.CustomAlertExerciseListener() {
                @Override
                public void onYesClicked() {
                    startActivity(new Intent(activity_hindu_pushUp.this, MainActivity.class));
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    if(mediaPlayer2 != null) {
                        mediaPlayer2.stop();
                        mediaPlayer2.release();
                        mediaPlayer2 = null;
                    }
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