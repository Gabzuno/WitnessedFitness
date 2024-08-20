package com.first.menu.BMI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.first.menu.Credentials.LogIn;
import com.first.menu.Credentials.MainActivity;
import com.first.menu.Credentials.Users;
import com.first.menu.CustomAlertDialog;
import com.first.menu.Menu.About;
import com.first.menu.Menu.History;
import com.first.menu.Menu.Pedometer;
import com.first.menu.Menu.Profile;
import com.first.menu.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.github.muddz.styleabletoast.StyleableToast;

public class BMI extends AppCompatActivity {
    private final Handler handler = new Handler();
    private boolean isIncrementing = false;
    private boolean isDecrementing = false;
    android.widget.Button calculateBMI;
    TextView currentHeight;
    TextView currentWeight;
    TextView currentAge;
    ImageView incrementAge, decrementAge, viewHistory;
    SeekBar seekbarHeight, seekbarWeight;
    RelativeLayout male, female;
    int intAge = 0;
    int heightCurrentProgress;
    int weightCurrentProgress;
    String age2 = "0";
    String intHeightProgress = "0";
    String intWeightProgress = "0";
    String typeOfUser = "0";
    FirebaseUser user;
    DatabaseReference reference;
    String Uid;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bmi_activity);

        calculateBMI=findViewById(R.id.calculateBMI);
        currentAge=findViewById(R.id.agenum);
        currentWeight=findViewById(R.id.currentWeight);
        currentHeight=findViewById(R.id.currentHeight);
        incrementAge=findViewById(R.id.incrementage);
        decrementAge=findViewById(R.id.decrementage);
        seekbarHeight=findViewById(R.id.seekbarHeight);
        seekbarWeight=findViewById(R.id.seekbarWeight);
        male=findViewById(R.id.male);
        female=findViewById(R.id.female);
        viewHistory = findViewById(R.id.textHistory);

        //FIREBASE
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("users");
        Uid = user.getUid();

        NavigationView navigationView = findViewById(R.id.NavView);
        navigationView.setCheckedItem(R.id.nav_bmi);
        View header = navigationView.getHeaderView(0);

        TextView UserName = header.findViewById(R.id.AccName);
        TextView UserEmail = header.findViewById(R.id.AccEmail);


        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        toolbar.setNavigationOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                item.setChecked(true);
                drawerLayout.closeDrawer(GravityCompat.START);
                switch (id) {
                    case R.id.nav_home:
                        home();break;
                    case R.id.nav_profile:
                        profile();break;
                    //case R.id.nav_history:
                    //   history();break;
                    case R.id.nav_share:
                        share();break;
                    case R.id.nav_about:
                        about();break;
                    case R.id.nav_out:
                        LogOut();break;
                    default:
                        return true;
                }
                return true;
            }
        });

        //VIEW BMI HISTORY
        viewHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BMI.this, History.class));
            }
        });

        //USER
        reference.child(Uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users user = snapshot.getValue(Users.class);

                if(user != null){

                    String username = user.user;
                    String email = user.email;

                    UserName.setText(username);
                    UserEmail.setText(email);

                    reference = FirebaseDatabase.getInstance().getReference("users")
                            .child(Uid)
                            .child("weight");

                    retrieveAndDisplayUserWeight();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(BMI.this,"Something went wrong!", Toast.LENGTH_LONG).show();
            }
        });



        //GENDER

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                male.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.malefemalefocus));
                female.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.malefemalenotfocus));
                typeOfUser="Male";
            }
        });


        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                female.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.malefemalefocus));
                male.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.malefemalenotfocus));
                typeOfUser="Female" ;
            }
        });


        //HEIGHT
        seekbarHeight.setMax(250);
        seekbarHeight.setProgress(0);
        seekbarHeight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                heightCurrentProgress=i;
                intHeightProgress=String.valueOf(heightCurrentProgress);
                currentHeight.setText(intHeightProgress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        //WEIGHT
        seekbarWeight.setMax(250);
        seekbarWeight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {
                weightCurrentProgress=i;
                intWeightProgress=String.valueOf(weightCurrentProgress);
                currentWeight.setText(intWeightProgress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        //HOLD
        incrementAge.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        isIncrementing = true;
                        repeatIncrement();
                        return true;
                    case MotionEvent.ACTION_UP:
                        isIncrementing = false;
                        decrementAge.setEnabled(true);
                        handler.removeCallbacksAndMessages(null); // Stop the continuous incrementation
                        return true;
                }
                return false;
            }
        });

        decrementAge.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        isDecrementing = true;
                        repeatDecrement();
                        return true;
                    case MotionEvent.ACTION_UP:
                        isDecrementing = false;
                        handler.removeCallbacksAndMessages(null); // Stop the continuous incrementation
                        return true;
                }
                return false;
            }
        });


        calculateBMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (typeOfUser.equals("0"))
                {
                    Toast.makeText(BMI.this,"Select Your Gender First", Toast.LENGTH_SHORT).show();
                }
                else if (intHeightProgress.equals("0"))
                {
                    Toast.makeText(BMI.this, "Select Your Height First", Toast.LENGTH_SHORT).show();
                }
                else if (intWeightProgress.equals("0"))
                {
                    Toast.makeText(BMI.this, "Select Your Weight First", Toast.LENGTH_SHORT).show();
                }
                else if (intAge == 0)
                {
                    Toast.makeText(BMI.this,"Age is incorrect",Toast.LENGTH_SHORT).show();
                }
                else if (intAge < 16)
                {
                    Toast.makeText(BMI.this,"Sorry, your age is too young!",Toast.LENGTH_SHORT).show();
                }
                else if (intAge == 50)
                {
                    Toast.makeText(BMI.this,"Sorry, you're too old.",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent intent = new Intent(BMI.this, BMI_Result.class);
                    intent.putExtra("gender", typeOfUser);
                    intent.putExtra("height", intHeightProgress);
                    intent.putExtra("weight", intWeightProgress);
                    intent.putExtra("age", age2);
                    startActivity(intent);
                }
            }
        });
    }

    private void repeatIncrement() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (intAge < 50 && isIncrementing) {
                    intAge++;
                    age2 = String.valueOf(intAge);
                    currentAge.setText(age2);
                    repeatIncrement();
                } else {
                    incrementAge.setEnabled(false);
                    Toast.makeText(BMI.this, "Sorry, you're too old.", Toast.LENGTH_SHORT).show();

                }
            }
        }, 200);
    }

    private  void repeatDecrement(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(intAge > 0 && isDecrementing)
                {
                    intAge--;
                    age2 = String.valueOf(intAge);
                    currentAge.setText(age2);
                    incrementAge.setEnabled(true);
                    repeatDecrement();
                } else {
                    currentAge.setText("0");
                }
            }
        },200);
    }

    private void retrieveAndDisplayUserWeight() {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String userWeightString = snapshot.getValue(String.class);
                if (userWeightString != null) {
                    currentWeight.setText(userWeightString);
                    int userWeight = Integer.parseInt(userWeightString);
                    seekbarWeight.setProgress(userWeight);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled event if needed
            }
        });
    }

    @Override
    public void onBackPressed(){
        //moveTaskToBack(true);
        CustomAlertDialog builder = new CustomAlertDialog(this);
        builder.show();
    }

    // MENU
    private void profile(){
        startActivity(new Intent(BMI.this, Profile.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);    }
    private void home(){
        startActivity(new Intent(BMI.this, MainActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);    }
    private void share() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "Here's the link for our Witnessed Fitness App: \n\n https://drive.google.com/drive/folders/1pcoSaMJrnZYZKMlMgZCb_aUEhrWoCy7c?usp=share_link");
        startActivity(Intent.createChooser(intent, "Share via"));   }
    private void history() {
        startActivity(new Intent(BMI.this, History.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);    }
    private void Pedometer() {startActivity(new Intent(BMI.this, Pedometer.class));overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);}
    private void about() {
        startActivity(new Intent(BMI.this, About.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);    }
    private void LogOut() {
        FirebaseAuth.getInstance().signOut();
        StyleableToast.makeText(BMI.this, "Logged out successfully!",Toast.LENGTH_SHORT, R.style.Succeeded).show();
        startActivity(new Intent(BMI.this, LogIn.class));
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        finishAffinity();
    }

}
