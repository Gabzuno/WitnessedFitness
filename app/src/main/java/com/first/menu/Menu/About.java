package com.first.menu.Menu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.first.menu.BMI.BMI;
import com.first.menu.FAQs.FAQs;
import com.first.menu.Credentials.LogIn;
import com.first.menu.Credentials.MainActivity;
import com.first.menu.Credentials.Users;
import com.first.menu.CustomAlertDialog;
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

public class About extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_about);

        //firebase
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        assert user != null;
        String Uid = user.getUid();

        NavigationView navigationView = findViewById(R.id.NavView);
        navigationView.setCheckedItem(R.id.nav_about);

        //get user's name and email
        View header = navigationView.getHeaderView(0);
        TextView UserName = header.findViewById(R.id.AccName);
        TextView UserEmail = header.findViewById(R.id.AccEmail);
        TextView GreetingTxt = findViewById(R.id.greetingTxt);

        Button gotoFAQs = findViewById(R.id.goToFAQs);
        gotoFAQs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(About.this, FAQs.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });

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
                        Home();break;
                    case R.id.nav_profile:
                        profile();break;
                    //case R.id.nav_history:
                     //   history();break;
                    case R.id.nav_bmi:
                        bmi();break;
                    case R.id.nav_share:
                        share();break;
                    case R.id.nav_out:
                        LogOut();break;
                    default:
                        return true;
                }
                return true;
            }
        });

        reference.child(Uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users user = snapshot.getValue(Users.class);

                if(user != null){

                    String username = user.user;
                    String email = user.email;

                    UserName.setText(username);
                    UserEmail.setText(email);
                    GreetingTxt.setText("Welcome, "+ username +"! Come Get Fit with Us!");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(About.this,"Something went wrong!", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackPressed(){
        CustomAlertDialog builder = new CustomAlertDialog(this);
        builder.show();
    }

    private void Home() {
        startActivity(new Intent(About.this, MainActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);    }
    private void history(){
        startActivity(new Intent(About.this,History.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);    }
    private void profile() {
        startActivity(new Intent(About.this, Profile.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
    private void bmi(){startActivity(new Intent(About.this, BMI.class));overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);}
    private void Pedometer() {startActivity(new Intent(About.this, Pedometer.class));overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);}

    private void share(){
        /*startActivity(new Intent(MainActivity.this, Share.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
         */
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "Here's the link for our Witnessed Fitness App: \n\n https://drive.google.com/drive/folders/1pcoSaMJrnZYZKMlMgZCb_aUEhrWoCy7c?usp=share_link");
        startActivity(Intent.createChooser(intent, "Share via")); }
    private void LogOut() {
        FirebaseAuth.getInstance().signOut();
        StyleableToast.makeText(About.this, "Logged out successfully!",Toast.LENGTH_SHORT, R.style.Succeeded).show();
        startActivity(new Intent(About.this, LogIn.class));
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        finishAffinity();
    }
}