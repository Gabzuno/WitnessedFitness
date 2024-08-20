package com.first.menu.FAQs;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.first.menu.BMI.BMI;
import com.first.menu.Credentials.LogIn;
import com.first.menu.Credentials.MainActivity;
import com.first.menu.Credentials.Users;
import com.first.menu.Menu.About;
import com.first.menu.Menu.History;
import com.first.menu.Menu.Pedometer;
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

import java.util.ArrayList;
import java.util.List;

import io.github.muddz.styleabletoast.StyleableToast;

public class FAQs extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<DataModel> mList;
    private ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqs);

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
                        Home();
                        break;
                    //case R.id.nav_history:
                    //    history();break;
                    case R.id.nav_bmi:
                        bmi();
                        break;
                    case R.id.nav_share:
                        share();
                        break;
                    case R.id.nav_out:
                        LogOut();
                        break;
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

                if (user != null) {

                    String username = user.user;
                    String email = user.email;

                    UserName.setText(username);
                    UserEmail.setText(email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(FAQs.this, "Something went wrong!", Toast.LENGTH_LONG).show();
            }
        });

        recyclerView = findViewById(R.id.faq_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mList = new ArrayList<>();

        List<String> nestedList1 = new ArrayList<>();
        nestedList1.add("   Witnessed Fitness is a virtual gym workout trainer that will help the user to perform body exercises by a step-by-step " +
                "procedure provided through images.");

        List<String> nestedList2 = new ArrayList<>();
        nestedList2.add("   They need an internet connection to access the application and also to watch the tutorial links on YouTube.");

        List<String> nestedList3 = new ArrayList<>();
        nestedList3.add("   No, Witnessed Fitness is developed for Android users. The operating systems of Android and iOS differ from one another in terms of their software structures and coding languages, so that's why it is not possible to install Android applications on iOS.");

        List<String> nestedList4 = new ArrayList<>();
        nestedList4.add("• Registration and Login - wherein users have to set up an account in order to access the app. \n\n" +
                "• BMI - Users can enter their measurement to calculate their BMI. \n\n" +
                "• Music - The user can play music from the device's library or Spotify while performing the exercise.\n\n" +
                "• AI Voice Over - The application utilizes text-to-speech features for each exercise description to enhance execution.\n\n" +
                "• Example of exercises in the form of GIF (Graphic Interchange Format). \n\n" +
                "• Sample video of exercises linked to YouTube - if you are having trouble visualizing the correct form for that exercise.");

        List<String> nestedList5 = new ArrayList<>();
        nestedList5.add("   Yes, there are only 10 exercises in each of the five categories of workouts we've created, so users can pick the ones that best suit their goals for improving their bodies.");

        List<String> nestedList6 = new ArrayList<>();
        nestedList6.add("   Yes, we offer guidance on proper form and techniques for that exercise by swiping the GIF exercise to the right to watch the exercise tutorial video from YouTube. Also we provided GIF exercise to visualize and we add some description about the tutorial of that exercise.");

        List<String> nestedList7 = new ArrayList<>();
        nestedList7.add("    Yes, you can track your weight by going to the history, which can be found in the sidebar of our application, and comparing their last weight with the most recently saved weight record in which calories were burned.");

        List<String> nestedList8 = new ArrayList<>();
        nestedList8.add("   You can monitor your BMI by going to the BMI Records, which can be found within the BMI Tracker on the sidebar of the application." +
                ".");

        List<String> nestedList9 = new ArrayList<>();
        nestedList9.add("   Yes, although there are some exercises that require equipment to execute. However, you can find it inside your house.");

        List<String> nestedList10 = new ArrayList<>();
        nestedList10.add("   Witnessed Fitness' device screen size or dimension is more appropriate to Android handsets, with dimensions of 165.3 x 76.8 x 9.4 millimeters. For instance, the Samsung Galaxy A52, the Xiaomi Redmi Note 10 Pro, and the OnePlus 8T.");

        List<String> nestedList11 = new ArrayList<>();
        nestedList11.add("  For any further inquiries or to obtain additional information about our innovative application, please feel free to reach out to us at:\n\n witnessedfitness@gmail.com.\n\n Our dedicated team is here to address your questions and provide you with the assistance you need. We value your interest and look forward to hearing from you soon!"
);

        mList.add(new DataModel(nestedList1 , "What is Witnessed Fitness?"));
        mList.add(new DataModel(nestedList2 , "Can I use the app offline?"));
        mList.add(new DataModel(nestedList3 , "Is the fitness app available for both iOS and Android?"));
        mList.add(new DataModel(nestedList4 , "What are the main features of Witnessed Fitness?"));
        mList.add(new DataModel(nestedList5 , "Is the app suitable for beginners?"));
        mList.add(new DataModel(nestedList6 , "Does Witnessed Fitness offer guidance on proper form and technique for exercises?"));
        mList.add(new DataModel(nestedList7 , "Can I track my weight within the app?"));
        mList.add(new DataModel(nestedList8 , "How does Witnessed Fitness monitor BMI (Body Mass Index)?"));
        mList.add(new DataModel(nestedList9 , "Can I use Witnessed Fitness without any gym equipment?"));
        mList.add(new DataModel(nestedList10 , "Is the device dimension of Witnessed Fitness appropriate for Android devices?"));
        mList.add(new DataModel(nestedList11 , "How can I provide feedback or suggest new features for the fitness app?"));

        adapter = new ItemAdapter(mList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(FAQs.this, About.class));
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    private void Home() {
        startActivity(new Intent(FAQs.this, MainActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);    }
    private void history(){
        startActivity(new Intent(FAQs.this, History.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);    }
    private void bmi(){startActivity(new Intent(FAQs.this, BMI.class));overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);}
    private void Pedometer() {startActivity(new Intent(FAQs.this, Pedometer.class));overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);}
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
        StyleableToast.makeText(FAQs.this, "Logged out successfully!",Toast.LENGTH_SHORT, R.style.Succeeded).show();
        startActivity(new Intent(FAQs.this, LogIn.class));
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        finishAffinity();
    }
}
