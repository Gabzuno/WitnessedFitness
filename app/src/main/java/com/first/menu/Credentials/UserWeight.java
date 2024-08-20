package com.first.menu.Credentials;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.first.menu.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserWeight extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userweight);

        TextInputLayout userWeightInput = findViewById(R.id.userWeight);
        Button btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String weight = userWeightInput.getEditText().getText().toString().trim();
                if (weight.isEmpty()) {
                    userWeightInput.setError("Please enter your weight");
                } else if (Float.parseFloat(weight) > 250) {
                    userWeightInput.setError("Apologies, you've surpassed the application's weight limit.");
                } else {
                    saveWeightToFirebase(weight);
                }
            }
        });
    }

    private void saveWeightToFirebase(String weight) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference userWeightRef = FirebaseDatabase.getInstance().getReference("users")
                .child(userId)
                .child("weight");

        userWeightRef.setValue(weight);

        startActivity(new Intent(UserWeight.this, MainActivity.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
