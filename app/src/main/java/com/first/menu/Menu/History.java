package com.first.menu.Menu;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.first.menu.BMI.RecordAdapter;
import com.first.menu.Credentials.LogIn;
import com.first.menu.Credentials.MainActivity;
import com.first.menu.Credentials.RecordBMI;
import com.first.menu.Credentials.Users;
import com.first.menu.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

public class History extends AppCompatActivity {

    private RecyclerView recyclerView,chestViewer;
    private RecordAdapter adapter;
    private List<RecordBMI> recordList;
    //private Chest_RecordAdapter chestAdapter;
    //private List<Chest_Record> chestList;
    String Uid;
    String RecordKey;
    DatabaseReference reference, checkRecord;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_history);

        //Firebase
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("users");
        assert user != null;
        Uid = user.getUid();


        // Recycler(History)
        recordList = new ArrayList<>();
        //chestList = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        /*
        chestViewer = findViewById(R.id.chestViewer);
        chestViewer.setHasFixedSize(true);
        chestViewer.setLayoutManager(new LinearLayoutManager(this));
         */
        NavigationView navigationView = findViewById(R.id.NavView);
        navigationView.setCheckedItem(R.id.nav_bmi);

        //get user's name and email
        View header = navigationView.getHeaderView(0);
        TextView UserName = header.findViewById(R.id.AccName);
        TextView UserEmail = header.findViewById(R.id.AccEmail);

        //SIDEBAR
        reference.child(Uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users user = snapshot.getValue(Users.class);

                if(user != null){

                    String username = user.user;
                    String email = user.email;

                    UserName.setText(username);
                    UserEmail.setText(email);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(History.this,"Something went wrong!", Toast.LENGTH_LONG).show();
            }
        });

        //DELETE RECORD
        RecordAdapter.OnDeleteClickListener deleteClickListener = new RecordAdapter.OnDeleteClickListener() {
            @Override
            public void onDeleteClick(String recordKey) {

                AlertDialog.Builder builder = new AlertDialog.Builder(History.this);
                builder.setTitle("NOTICE!");
                builder.setMessage("Data will be archived.");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseReference database = reference.child(Uid+"/BMI").child(recordKey);

                        // Check if the database reference is not null
                        database.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(History.this, "Record deleted successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(History.this, "Failed to delete record", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(History.this, "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        };

        adapter = new RecordAdapter(this, recordList, deleteClickListener);
        recyclerView.setAdapter(adapter);

        checkRecord = reference.child(Uid+"/BMI");

        reference.keepSynced(true);
        reference.child(Uid+"/BMI").addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    recordList.clear();
                    for (DataSnapshot recordSnapshot : snapshot.getChildren()) {
                        RecordKey = recordSnapshot.getKey();
                        RecordBMI record = recordSnapshot.getValue(RecordBMI.class);
                        assert record != null;
                        record.setRecordKey(RecordKey);
                        recordList.add(record);
                    }
                    adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(History.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        /*
        chestAdapter = new Chest_RecordAdapter(this,chestList);
        chestViewer.setAdapter(chestAdapter);

        reference.keepSynced(true);
        reference.child("Calorie Burn/Chest").addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chestList.clear();
                for (DataSnapshot recordSnapshot : snapshot.getChildren()) {
                    RecordKey = recordSnapshot.getKey();
                    Chest_Record chest_record = recordSnapshot.getValue(Chest_Record.class);
                    assert chest_record != null;
                    chest_record.setRecordKey(RecordKey);
                    chestList.add(chest_record);
                }
                chestAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(History.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        */

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        //SIDEBAR
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                item.setChecked(true);
                drawerLayout.closeDrawer(GravityCompat.START);
                switch (id) {
                    case R.id.nav_home:
                        Home()
                        ;break;
                    case R.id.nav_profile:
                        profile()
                        ;break;
                    case R.id.nav_about:
                        about();
                        break;
                    case R.id.nav_share:
                        share();
                        break;
                    case R.id.nav_out:
                        LogOut();break;
                    default:
                        return true;
                }
                return true;
            }
        });
    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(History.this, BMI.class));
    }

    // MENU //
    private void Home() {
        startActivity(new Intent(History.this, MainActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);    }
    private void profile() {
        startActivity(new Intent(History.this, Profile.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);    }
    private void bmi(){startActivity(new Intent(History.this, BMI.class));overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);}
    private void Pedometer() {startActivity(new Intent(History.this, Pedometer.class));overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);}
    private void share() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "Here's the link for our Witnessed Fitness App: \n\n https://drive.google.com/drive/folders/1pcoSaMJrnZYZKMlMgZCb_aUEhrWoCy7c?usp=share_link");
        startActivity(Intent.createChooser(intent, "Share via"));    }
    private void about() {
        startActivity(new Intent(History.this, About.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);    }
    private void LogOut() {
        FirebaseAuth.getInstance().signOut();
        StyleableToast.makeText(History.this, "Logged out successfully!",Toast.LENGTH_SHORT, R.style.Succeeded).show();
        startActivity(new Intent(History.this, LogIn.class));
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        finishAffinity();
    }
}