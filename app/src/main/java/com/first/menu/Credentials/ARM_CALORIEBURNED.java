package com.first.menu.Credentials;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.first.menu.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ARM_CALORIEBURNED extends AppCompatActivity {

    private TextView totalMETTextView;
    private TextView userWeightTextView;
    private TextView totalCalorieTextView;
    private Button btnHome;

    private DatabaseReference userWeightReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arm_calorieburned);

        userWeightTextView = findViewById(R.id.UserWeightText);
        totalCalorieTextView = findViewById(R.id.totalCalorie);
        btnHome = findViewById(R.id.btnHOME);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ARM_CALORIEBURNED.this, MainActivity.class));
            }
        });

        // Get the current user's ID
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();

            // Reference to the user's weight in Firebase Database
            userWeightReference = FirebaseDatabase.getInstance().getReference("users")
                    .child(userId)
                    .child("weight");

            // Retrieve and display user's weight
            retrieveAndDisplayUserWeight();

            // Calculate total calorie burned and display it
            calculateAndDisplayTotalCalorieBurned();
        }
    }

    private double userWeight = 0.0; // Default value, you may want to set this to a meaningful default

    private void retrieveAndDisplayUserWeight() {
        userWeightReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Assuming the user's weight is stored as a String
                String userWeightString = snapshot.getValue(String.class);
                if (userWeightString != null) {
                    userWeight = Double.parseDouble(userWeightString);
                    userWeightTextView.setText("Weight: " + userWeight + " kg");

                    // Now that you have the user's weight, you can trigger the calculation
                    calculateAndDisplayTotalCalorieBurned();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled event if needed
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void calculateAndDisplayTotalCalorieBurned() {
        // Assuming you have MET value and duration from your calculation
        double totalMET = 29.0; // Replace with your actual MET value
        double durationInHours = 15.0 / 60.0; // Convert 15 minutes to hours

        // Calculate total calorie burned using the retrieved user weight
        double totalCalorieBurned = totalMET * userWeight * durationInHours;

        // Display total calorie burned
        totalCalorieTextView.setText("Total Calorie Burned: " + totalCalorieBurned + " kcal");

        // Save total calorie burned to Firebase under the path "Arm_CalorieBurned"
        saveTotalCalorieBurnedToFirebase(totalCalorieBurned);
    }

    private void saveTotalCalorieBurnedToFirebase(double totalCalorieBurned) {
        // Get the current user's ID
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();

            // Reference to the "Arm_CalorieBurned" path in Firebase Database
            DatabaseReference calorieBurnedReference = FirebaseDatabase.getInstance().getReference("users")
                    .child(userId)
                    .child("Arm_CalorieBurned");

            // Create a unique key for each entry (like a folder) under "Arm_CalorieBurned"
            String entryKey = calorieBurnedReference.push().getKey();

            // Create a new child node under "Arm_CalorieBurned" using the unique key
            DatabaseReference entryReference = calorieBurnedReference.child(entryKey);

            // Save the total calorie burned to the new child node
            entryReference.setValue(totalCalorieBurned)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Successfully saved the total calorie burned
                            // You can add any additional logic or feedback here
                        } else {
                            // Failed to save the total calorie burned
                            // Handle the error if needed
                        }
                    });
        }
    }

}
