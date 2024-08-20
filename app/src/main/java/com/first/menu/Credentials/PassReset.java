package com.first.menu.Credentials;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.first.menu.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class PassReset extends AppCompatActivity {

    private TextInputLayout userEmail;
    private ProgressBar progressBar;

    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_reset);

        userEmail = findViewById(R.id.email);
        Button resetPass = findViewById(R.id.resetPassword);
        progressBar = findViewById(R.id.progressbar);

        TextView login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(v == login){
                        startActivity(new Intent(PassReset.this, LogIn.class));
                    }
            }
        });

        auth = FirebaseAuth.getInstance();
        resetPass.setOnClickListener(v -> passReset());

        userEmail.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
                // This method is intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // This method is intentionally left blank
            }
            @Override
            public void afterTextChanged(Editable editable) {
                validateEmail();
            }
        });

    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        startActivity(new Intent(PassReset.this, LogIn.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private String validateEmail(){
        String eMail = userEmail.getEditText().getText().toString().trim();

        if(eMail.isEmpty()){
            userEmail.setHelperTextEnabled(true);
            userEmail.setHelperText("Email is required!");
            userEmail.setHelperTextColor(ColorStateList.valueOf(Color.parseColor("#A60000")));
            return null;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(eMail).matches() | !eMail.toLowerCase().endsWith("gmail.com")) {
            userEmail.setError("Please provide a valid gmail account.");
            userEmail.requestFocus();
            return null;
        }
        else{
            userEmail.setError(null);
            userEmail.setHelperTextEnabled(false);
            userEmail.setBoxStrokeColor(Color.parseColor("#4CAF50"));
            return eMail;
        }
    }

    private void passReset() {
        String email = validateEmail();

        if(email == null){
            return;
        }

        auth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Toast.makeText(PassReset.this,"Please check your email for password reset!",Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
                startActivity(new Intent(PassReset.this, LogIn.class));
            }
            else{
                Exception e = task.getException();
                if (e instanceof FirebaseAuthInvalidUserException) {
                    Toast.makeText(PassReset.this, "Account not found!", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                } else {
                    if (e instanceof FirebaseNetworkException) {
                        Toast.makeText(PassReset.this, "Error! Network connection lost!", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }
                    else {
                        Toast.makeText(PassReset.this, "Error! Something went wrong!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}