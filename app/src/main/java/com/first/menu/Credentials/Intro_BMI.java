package com.first.menu.Credentials;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.first.menu.BMI.BMI;
import com.first.menu.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Intro_BMI extends AppCompatActivity {
    private TextView userName;
    private Button bmiCalculate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_bmi);

        userName = findViewById(R.id.txtUsername);
        bmiCalculate = findViewById(R.id.goToBMI);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        assert user != null;
        String Uid = user.getUid();

        bmiCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intro_BMI.this, BMI.class));
            }
        });

        reference.child(Uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users user = snapshot.getValue(Users.class);

                if (user != null) {
                    String username = user.user;
                    userName.setText("Hello, " + username + "\nBefore we begin, we'd like to have your measurements in order to calculate your Body Mass Index.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Intro_BMI.this, "Something went wrong!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
