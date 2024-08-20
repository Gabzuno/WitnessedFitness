package com.first.menu.Credentials;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.first.menu.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class Register extends AppCompatActivity implements View.OnClickListener{

    private TextInputLayout TextEmail,TextPassword,TextUsername;
    private ProgressBar progressBar;
    private ImageView ShowPass;
    private FirebaseAuth auth;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();

        register = findViewById(R.id.registerUser);
        register.setOnClickListener(this);

        TextEmail = findViewById(R.id.email);
        TextUsername = findViewById(R.id.username);
        TextPassword = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressbar);
        /*
        ShowPass.setImageResource(R.drawable.unsee_white);
        ShowPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextPassword.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                    //if pass visible hide
                    TextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    //change Icon
                    ShowPass.setImageResource(R.drawable.unsee_white);
                } else{
                    TextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    ShowPass.setImageResource(R.drawable.see_white);
                }
            }
        });
         */

        TextEmail.getEditText().addTextChangedListener(new TextWatcher() {
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

        TextUsername.getEditText().addTextChangedListener(new TextWatcher() {
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
                validateUsername();
            }
        });

        TextPassword.getEditText().addTextChangedListener(new TextWatcher() {
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
                validatePassword();
            }
        });
    }

    @Override
    public void onClick(View v) {
        // Check which view was clicked
        if (v == register) {
            registerUser(v);
        }
    }

    private String validateEmail(){
        String eMail = TextEmail.getEditText().getText().toString().trim();

        if(eMail.isEmpty()){
            TextEmail.setHelperTextEnabled(true);
            TextEmail.setHelperText("Email is required!");
            TextEmail.setHelperTextColor(ColorStateList.valueOf(Color.parseColor("#A60000")));
            return null;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(eMail).matches() | !eMail.toLowerCase().endsWith("gmail.com")) {
            TextEmail.setError("Please provide a valid gmail account.");
            TextEmail.requestFocus();
            return null;
        }
        else{
            TextEmail.setError(null);
            TextEmail.setHelperTextEnabled(false);
            TextEmail.setBoxStrokeColor(Color.parseColor("#4CAF50"));
            return eMail;
        }
    }

    private String validateUsername(){
        String userName = TextUsername.getEditText().getText().toString().trim();

        if(userName.isEmpty()){
            TextUsername.setHelperTextEnabled(true);
            TextUsername.setHelperText("Username is required!");
            TextUsername.setHelperTextColor(ColorStateList.valueOf(Color.parseColor("#A60000")));
            return null;
        }
        if(userName.length()>15){
            TextUsername.setError("Username is too long!");
            TextUsername.requestFocus();
            return null;
        }
        else{
            TextUsername.setError(null);
            TextUsername.setHelperTextEnabled(false);
            TextUsername.setBoxStrokeColor(Color.parseColor("#4CAF50"));
            return userName;
        }
    }

    private String validatePassword(){
        String passWord = TextPassword.getEditText().getText().toString().trim();

        if(passWord.isEmpty()){
            TextPassword.setHelperTextEnabled(true);
            TextPassword.setHelperText("Password is required!");
            TextPassword.setHelperTextColor(ColorStateList.valueOf(Color.parseColor("#A60000")));
            return null;
        }
        if(passWord.length()<6) {
            TextPassword.setHelperTextEnabled(true);
            TextPassword.setHelperText("Minimum of 6 characters!");
            TextPassword.setHelperTextColor(ColorStateList.valueOf(Color.parseColor("#A60000")));
            return null;
        }
        else{
            TextPassword.setError(null);
            TextPassword.setHelperTextEnabled(false);
            TextPassword.setBoxStrokeColor(Color.parseColor("#4CAF50"));
            return passWord;
        }
    }

    public void registerUser(View v) {
        String username = validateUsername();
        String password = validatePassword();
        String email = validateEmail();

        if(email == null | username == null | password == null){
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        String hashedPassword = getMD5Hash(password);

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        String accountCreationDate = getCurrentDate();

                        Users user = new Users(email, hashedPassword, username, accountCreationDate);

                        FirebaseDatabase.getInstance().getReference("users")
                                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                                .setValue(user).addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        Toast toast = Toast.makeText(Register.this, "Registered successfully!\nWelcome to Witnessed Fitness, " + TextUsername.getEditText().getText().toString(), Toast.LENGTH_LONG);
                                        toast.show();
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                toast.cancel();
                                            }
                                        }, 4500);
                                        progressBar.setVisibility(View.GONE);
                                        startActivity(new Intent(Register.this, UserWeight.class));
                                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                                    } else {
                                        if (task1.getException() instanceof FirebaseNetworkException) {
                                            Toast.makeText(Register.this, "Error! Network connection lost!", Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(Register.this, "Email already taken", Toast.LENGTH_LONG).show();
                                        }
                                        progressBar.setVisibility(View.GONE);
                                    }
                                });
                    } else {
                        if (task.getException() instanceof FirebaseNetworkException) {
                            Toast.makeText(Register.this, "Error! Network connection lost!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(Register.this, "Email already taken", Toast.LENGTH_LONG).show();
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    private String getCurrentDate() {
        return new SimpleDateFormat("MMMM dd yyyy", Locale.getDefault()).format(new Date());
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        startActivity(new Intent(Register.this, LogIn.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private String getMD5Hash(String password) {
        try {
            // Get instance of MD5 hash algorithm
            MessageDigest md = MessageDigest.getInstance("MD5");

            // Convert password to bytes and compute hash
            md.update(password.getBytes());
            byte[] bytes = md.digest();

            // Convert hash bytes to hex string
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void SignIn(View view) {
        startActivity(new Intent(Register.this,LogIn.class));
    }
}
