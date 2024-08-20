package com.first.menu.BMI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.first.menu.Credentials.MainActivity;
import com.first.menu.Credentials.RecordBMI;
import com.first.menu.Menu.History;
import com.first.menu.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.text.DateFormat;
import java.util.Date;

public class BMI_Result extends AppCompatActivity {

  FirebaseUser user;
  DatabaseReference reference;
  android.widget.Button recalculateBMI;
  TextView bmiDisplay, bmiCategory, bmiGender;
  String categoryString, Uid, height, weight, age, gender, formatted;
  ImageView imageview;
  RelativeLayout background;
  int intAge;
  double intHeight, intWeight, bmi;
  boolean isRecordSaved = false;

  @SuppressLint({ "DefaultLocale", "SetTextI18n" })
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.bmi_result_activity);
    Intent intent = getIntent();

    bmiDisplay = findViewById(R.id.bmidisplay);
    bmiCategory = findViewById(R.id.bmicategory);
    bmiGender = findViewById(R.id.genderdisplay);

    background = findViewById(R.id.contentlayout);
    imageview = findViewById(R.id.imageview);

    recalculateBMI = findViewById(R.id.recalculateBMI);

    height = (intent.getStringExtra("height"));
    weight = (intent.getStringExtra("weight"));
    age = (intent.getStringExtra("age"));
    gender = (intent.getStringExtra("gender"));
    bmiGender.setText(intent.getStringExtra("gender"));

    intHeight = Double.parseDouble((height));
    intWeight = Double.parseDouble((weight));

    intHeight = intHeight / 100;

    bmi = intWeight / (intHeight * intHeight);
    formatted = String.format("%.2f", bmi);
    bmiDisplay.setText(formatted);

    Button save = findViewById(R.id.saveBMI);
    Button view = findViewById(R.id.viewRecord);

    save.setOnClickListener(
      new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if (!isRecordSaved) {
            BMI_Result.this.savedRecord();
            isRecordSaved = true;
            save.setEnabled(false);
          }
        }
      }
    );

    /*(if(bmi<16.5)
        {
            bmiCategory.setText("Severely Underweight");
            categoryString = ("Severely Underweight");
            background.setBackgroundColor(Color.RED);
            imageview.setImageResource(R.drawable.cross);

        }
        else if(bmi<18.5)
        {
            bmiCategory.setText("Underweight");
            categoryString = ("Underweight");
            background.setBackgroundColor(Color.YELLOW);
            imageview.setImageResource(R.drawable.warning);

        }
        else if(bmi<25)
        {
            bmiCategory.setText("Normal");
            categoryString = ("Normal");
            imageview.setImageResource(R.drawable.ok);

        }
        else if(bmi<30)
        {
            bmiCategory.setText("Overweight");
            categoryString = ("Overweight");
            background.setBackgroundColor(Color.RED);
            imageview.setImageResource(R.drawable.warning);

        }
        else if(bmi>29 && bmi<40)
        {
            bmiCategory.setText("Obese");
            categoryString = ("Obese");
            background.setBackgroundColor(Color.RED);
            imageview.setImageResource(R.drawable.warning);

        }
        else
        {
            bmiCategory.setText("Obese Class 1");
            categoryString = ("Obese Class 1");
            background.setBackgroundColor(Color.RED);
            imageview.setImageResource(R.drawable.warning);
        }

         */

    if (intAge < 18) {
      if (gender.equalsIgnoreCase("male")) {
        if (bmi < 15.3) {
          bmiCategory.setText("Underweight");
          categoryString = ("Underweight");
          background.setBackgroundColor(Color.YELLOW);
          imageview.setImageResource(R.drawable.warning);
        } else if (bmi >= 15.3 && bmi <= 23.1) {
          bmiCategory.setText("Normal");
          categoryString = ("Normal");
          imageview.setImageResource(R.drawable.ok);
        } else if (bmi > 23.1 && bmi <= 28.8) {
          bmiCategory.setText("Overweight");
          categoryString = ("Overweight");
          background.setBackgroundColor(Color.RED);
          imageview.setImageResource(R.drawable.warning);
        } else {
          bmiCategory.setText("Obese");
          categoryString = ("Obese");
          background.setBackgroundColor(Color.RED);
          imageview.setImageResource(R.drawable.warning);
        }
      } else {
        if (bmi < 14.7) {
          bmiCategory.setText("Underweight");
          categoryString = ("Underweight");
          background.setBackgroundColor(Color.YELLOW);
          imageview.setImageResource(R.drawable.warning);
        } else if (bmi >= 14.7 && bmi <= 22.5) {
          bmiCategory.setText("Normal");
          categoryString = ("Normal");
          imageview.setImageResource(R.drawable.ok);
        } else if (bmi > 22.5 && bmi <= 27.3) {
          bmiCategory.setText("Overweight");
          categoryString = ("Overweight");
          background.setBackgroundColor(Color.RED);
          imageview.setImageResource(R.drawable.warning);
        } else {
          bmiCategory.setText("Obese");
          categoryString = ("Obese");
          background.setBackgroundColor(Color.RED);
          imageview.setImageResource(R.drawable.warning);
        }
      }
    } else {
      if (bmi < 18.5) {
        bmiCategory.setText("Underweight");
        categoryString = ("Underweight");
        background.setBackgroundColor(Color.YELLOW);
        imageview.setImageResource(R.drawable.warning);
      } else if (bmi >= 18.5 && bmi <= 24.9) {
        bmiCategory.setText("Normal");
        categoryString = ("Normal");
        imageview.setImageResource(R.drawable.ok);
      } else if (bmi > 24.9 && bmi <= 29.9) {
        bmiCategory.setText("Overweight");
        categoryString = ("Overweight");
        background.setBackgroundColor(Color.RED);
        imageview.setImageResource(R.drawable.warning);
      } else {
        bmiCategory.setText("Obese");
        categoryString = ("Obese");
        background.setBackgroundColor(Color.RED);
        imageview.setImageResource(R.drawable.warning);
      }
    }

    recalculateBMI.setOnClickListener(
      new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          startActivity(new Intent(BMI_Result.this, BMI.class));
          overridePendingTransition(
            R.anim.slide_in_left,
            R.anim.slide_out_right
          );
          finish();
        }
      }
    );

    view.setOnClickListener(
      new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          startActivity(new Intent(BMI_Result.this, History.class));
        }
      }
    );
  }

  public void onBackPressed() {
    startActivity(new Intent(BMI_Result.this, MainActivity.class));
  }

  private void savedRecord() {
    String Height = height;
    String Weight = weight;
    String Gender = gender;

    //Get current date and time
    Date currentDate = new Date();
    String dateString = DateFormat.getDateInstance().format(currentDate);
    String timeString = DateFormat.getTimeInstance().format(currentDate);

    //GET USER AND SAVE RECORD
    user = FirebaseAuth.getInstance().getCurrentUser();
    reference = FirebaseDatabase.getInstance().getReference("users");
    assert user != null;
    Uid = user.getUid();
    RecordBMI record = new RecordBMI(
      Gender,
      Height,
      Weight,
      dateString,
      timeString,
      categoryString,
      formatted,
      age
    );

    String recordKey = reference.child(Uid + "/BMI").push().getKey();
    record.setRecordKey(recordKey);
    assert recordKey != null;

    DatabaseReference BMI = reference.child(Uid + "/BMI").child(recordKey);
    BMI.setValue(
      record,
      new DatabaseReference.CompletionListener() {
        @Override
        public void onComplete(
          @Nullable DatabaseError error,
          @NonNull DatabaseReference ref
        ) {
          if (error != null) {
            Toast
              .makeText(
                BMI_Result.this,
                "Error! Something happened!",
                Toast.LENGTH_SHORT
              )
              .show();
          } else {
            Toast
              .makeText(BMI_Result.this, "Record saved", Toast.LENGTH_SHORT)
              .show();
          }
        }
      }
    );
  }
}
