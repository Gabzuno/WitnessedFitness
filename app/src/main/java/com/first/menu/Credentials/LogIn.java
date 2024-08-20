package com.first.menu.Credentials;

import android.annotation.SuppressLint;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.first.menu.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import io.github.muddz.styleabletoast.StyleableToast;

public class LogIn extends AppCompatActivity implements View.OnClickListener{

    private TextView register, resetPass;
    private TextInputLayout emailText,passwordText;
    private Button signIn;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private int counts = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        register = findViewById(R.id.register);
        register.setOnClickListener(this);

        signIn = findViewById(R.id.signIn);
        signIn.setOnClickListener(this);

        resetPass = findViewById(R.id.forgotPassword);
        resetPass.setOnClickListener(this);

        emailText = findViewById(R.id.email);
        passwordText = findViewById(R.id.password);

        progressBar = findViewById(R.id.progressbar);

        // terms = findViewById(R.id.terms);

        auth = FirebaseAuth.getInstance();

        emailText.getEditText().addTextChangedListener(new TextWatcher() {
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

        passwordText.getEditText().addTextChangedListener(new TextWatcher() {
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

        // HIDE OR SHOW PASSWORD
        /*
        ShowPass = findViewById(R.id.hidePass);
        ShowPass.setImageResource(R.drawable.unsee_white);
        ShowPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(passwordText.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                    //if pass visible hide
                    passwordText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    //change Icon
                    ShowPass.setImageResource(R.drawable.unsee_white);
                } else{
                    passwordText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    ShowPass.setImageResource(R.drawable.see_white);
                }
            }
        });


        private CheckBox terms;
        private static final String Terms_Accepted = "terms_accepted";

        signIn.setEnabled(false);
        MaterialAlertDialogBuilder dialogMaterial = new MaterialAlertDialogBuilder(this);
        // Check if terms were already accepted
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean termsAccepted = sharedPreferences.getBoolean(Terms_Accepted, false);
        if (termsAccepted) {
            // Hide the checkbox
            terms.setVisibility(View.GONE);
            signIn.setEnabled(true);
            signIn.setBackgroundColor(Color.parseColor("#FF4C29"));
        } else {
            signIn.setEnabled(false);
            signIn.setBackgroundColor(Color.parseColor("#973623"));
        }

        // TERMS AND CONDITIONS //
        terms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    dialogMaterial.setTitle("Terms and Conditions");
                    dialogMaterial.setMessage("Do you accept the terms and conditions?");
                    dialogMaterial.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                        @SuppressLint("ResourceAsColor")
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Store the information that terms were accepted
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean(Terms_Accepted, true);
                            editor.apply();

                            signIn.setEnabled(true);
                            signIn.setBackgroundColor(Color.parseColor("#FF4C29"));
                            dialog.dismiss();
                        }
                    });
                    dialogMaterial.setNegativeButton("Decline", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            terms.setChecked(false);
                            signIn.setBackgroundColor(Color.parseColor("#973623"));
                        }
                    });
                    dialogMaterial.show();
                } else {
                    // Handle the case when terms are not accepted
                    signIn.setEnabled(false);
                    signIn.setBackgroundColor(Color.parseColor("#973623"));
                    dialogMaterial.setTitle("Privacy Policy");
                    dialogMaterial.setMessage("By not accepting the terms and conditions, you cannot proceed. Please review our privacy policy for more information.");
                    dialogMaterial.setPositiveButton("View Privacy Policy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Open the privacy policy link in a web browser
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.freeprivacypolicy.com/live/a5e5bab7-df38-4875-ab2c-35c3f5d501b8"));
                            startActivity(intent);
                        }
                    });
                    dialogMaterial.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dialogMaterial.show();
                }
            }
        });

         */
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finishAffinity();
    }

    /*
    public void onAcceptedTermsClicked(View view){
        //Remembers Accepted
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(Terms_Accepted,true);
        editor.apply();

        Toast.makeText(this,"Terms Accepted",Toast.LENGTH_SHORT).show();
        terms.setVisibility(View.GONE);
    }

     */

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register:
                startActivity(new Intent(this, Register.class));
                break;
            case R.id.signIn:
                UserLogin();
                break;
            case R.id.forgotPassword:
                startActivity(new Intent(this, PassReset.class));
                break;
        }
    }

    private String validateEmail(){
        String eMail = emailText.getEditText().getText().toString().trim();

        if(eMail.isEmpty()){
            emailText.setHelperTextEnabled(true);
            emailText.setHelperText("Email is required!");
            emailText.setHelperTextColor(ColorStateList.valueOf(Color.parseColor("#A60000")));
            return null;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(eMail).matches() | !eMail.toLowerCase().endsWith(".com")) {
            emailText.setError("Please provide valid email!");
            emailText.requestFocus();
            return null;
        }
        else{
            emailText.setError(null);
            emailText.setHelperTextEnabled(false);
            emailText.setBoxStrokeColor(Color.parseColor("#4CAF50"));
            return eMail;
        }
    }

    private String validatePassword(){
        String passWord = passwordText.getEditText().getText().toString().trim();

        if(passWord.isEmpty()){
            passwordText.setHelperTextEnabled(true);
            passwordText.setHelperText("Password is required!");
            passwordText.setHelperTextColor(ColorStateList.valueOf(Color.parseColor("#A60000")));
            return null;
        }
        else{
            passwordText.setError(null);
            passwordText.setHelperTextEnabled(false);
            passwordText.setBoxStrokeColor(Color.parseColor("#4CAF50"));
            return passWord;
        }
    }

    private void UserLogin() {
        String eMail = validateEmail();
        String pass = validatePassword();

        if(eMail == null | pass == null){
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        /* Check if it's the user's first time opening the application
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        boolean isFirstTime = sharedPreferences.getBoolean("isFirstTime", true);
        */

        auth.signInWithEmailAndPassword(eMail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Handler handler = new Handler();
                int delay = 3000;
                counts++;
                if (task.isSuccessful()) {
                    StyleableToast.makeText(LogIn.this, "Logged in successfully!", R.style.Succeeded).show();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(LogIn.this, MainActivity.class));
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        }
                    }, 500);
                    emailText.getEditText().setText("");
                    emailText.setHelperTextEnabled(false);
                    passwordText.getEditText().setText("");
                    passwordText.setHelperTextEnabled(false);
                } else {
                    Exception e = task.getException();
                    if (e instanceof FirebaseAuthInvalidUserException) {
                        Toast.makeText(LogIn.this, "Account not found!", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    } else {
                        if (e instanceof FirebaseNetworkException) {
                            Toast.makeText(LogIn.this, "Error! Network connection lost!", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        } else {
                            Toast.makeText(LogIn.this, "Invalid Login! You have  "+ (3 - counts) +" attempts left!",Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                            if (counts >= 3) {
                                Toast.makeText(LogIn.this, "Limit number of attempts reached!\nSuggesting to reset your password", Toast.LENGTH_LONG).show();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        startActivity(new Intent(LogIn.this, PassReset.class));
                                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                    }
                                }, delay);
                            }
                        }
                    }
                }
            }
        });
    }
}
