package com.first.menu.Credentials;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.first.menu.R;

import java.util.Calendar;

public class BirthdateActivityDialog extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agecheckerdialog);

        DatePicker datePicker = findViewById(R.id.datePicker);
        Button btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the selected birthdate from the DatePicker
                int year = datePicker.getYear();
                int month = datePicker.getMonth();
                int day = datePicker.getDayOfMonth();

                // Calculate age based on the selected birthdate
                Calendar dob = Calendar.getInstance();
                dob.set(year, month, day);

                Calendar today = Calendar.getInstance();
                int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

                if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
                    age--;
                }

                // Check if age is less than 15
                if (age < 16) {
                    Toast.makeText(BirthdateActivityDialog.this, "Sorry, this app is only for 16 and above.", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(BirthdateActivityDialog.this, Activity_IntroSlide.class));
                    finish();
                }
            }
        });
    }
}

