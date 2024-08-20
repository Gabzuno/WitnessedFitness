package com.first.menu.Menu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.first.menu.BMI.BMI;
import com.first.menu.Credentials.LogIn;
import com.first.menu.Credentials.MainActivity;
import com.first.menu.Credentials.Users;
import com.first.menu.CustomAlertDialog;
import com.first.menu.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import io.github.muddz.styleabletoast.StyleableToast;

public class Profile extends AppCompatActivity {

    TextView UserName,UserEmail,usersName,usersEmail,dateCreated;
    TextInputLayout name,passWord, passwordNew;
    Button updateBtn;
    String Uid;
    DatabaseReference reference;
    FirebaseUser user;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_profile);

        //Firebase
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("users");
        assert user != null;
        Uid = user.getUid();

        usersName = findViewById(R.id.txtUsername);
        usersEmail = findViewById(R.id.userEmail);

        progressBar = findViewById(R.id.progressbar);

        NavigationView navigationView = findViewById(R.id.NavView);
        navigationView.setCheckedItem(R.id.nav_profile);
        View header = navigationView.getHeaderView(0);
        UserName = header.findViewById(R.id.AccName);
        UserEmail = header.findViewById(R.id.AccEmail);
        dateCreated = findViewById(R.id.DateCreated);
        name = findViewById(R.id.username);
        passWord = findViewById(R.id.password);
        passwordNew = findViewById(R.id.newPassword);

        updateBtn = findViewById(R.id.updateUser);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUsernamePassword();
            }
        });

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
                    usersName.setText(username);
                    usersEmail.setText(email);

                    displayAccountCreationDate();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Profile.this,"Something went wrong!", Toast.LENGTH_LONG).show();
            }
        });

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
                        Home();break;
                    //case R.id.nav_history:
                     //   history();break;
                    case R.id.nav_bmi:
                        bmi();break;
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

    private void updateUsernamePassword() {
        progressBar.setVisibility(View.VISIBLE);

        // Get user inputs
        String newUsername = name.getEditText().getText().toString().trim();
        String currentPassword = passWord.getEditText().getText().toString().trim();
        String newPassword = passwordNew.getEditText().getText().toString().trim();

        // Validate and update USERNAME and PASSWORD
        if (TextUtils.isEmpty(newUsername) && TextUtils.isEmpty(currentPassword) && TextUtils.isEmpty(newPassword)) {
            Toast.makeText(this, "Empty inputs!", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        } else if(!TextUtils.isEmpty(newUsername) && newUsername.length() <= 15 && newUsername.length() > 3 && TextUtils.isEmpty(currentPassword) && TextUtils.isEmpty(newPassword)){
            updateUsernameInDatabase(newUsername);
            progressBar.setVisibility(View.GONE);
        } else if (TextUtils.isEmpty(newUsername) && !TextUtils.isEmpty(currentPassword) && !TextUtils.isEmpty(newPassword)) {
            reAuthenticateAndUpdatePassword(currentPassword, newPassword);
            progressBar.setVisibility(View.GONE);
        } else{
            updateUsernameInDatabase(newUsername);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    reAuthenticateAndUpdatePassword(currentPassword, newPassword);
                }
            },500);
            progressBar.setVisibility(View.GONE);
        }
    }

    private void updateUsernameInDatabase(String newUsername) {
        name.setError(null);
        // Update username in the database
        reference.child(Uid).child("user").setValue(newUsername);
        name.getEditText().setText("");
        Toast.makeText(Profile.this, "Username updated successfully", Toast.LENGTH_SHORT).show();
    }

    private void reAuthenticateAndUpdatePassword(String currentPassword, String newPassword) {
        AuthCredential credential = EmailAuthProvider.getCredential(Objects.requireNonNull(user.getEmail()), currentPassword);
        user.reauthenticate(credential).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                updatePasswordSuccessfully(newPassword);
            } else {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(Profile.this, "Re-authentication failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updatePasswordSuccessfully(String newPassword) {
        user.updatePassword(newPassword).addOnCompleteListener(passwordUpdateTask -> {
            if (passwordUpdateTask.isSuccessful()) {
                handlePasswordUpdateSuccess(newPassword);
            } else {
                handlePasswordUpdateFailure();
            }
        });
    }

    private void handlePasswordUpdateSuccess(String newPassword) {
        progressBar.setVisibility(View.GONE);
        passWord.getEditText().setText("");
        passwordNew.getEditText().setText("");
        Toast.makeText(Profile.this, "Password updated successfully", Toast.LENGTH_SHORT).show();
        updateHashedPasswordInDatabase(newPassword);
    }

    private void handlePasswordUpdateFailure() {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(Profile.this, "Failed to update password", Toast.LENGTH_SHORT).show();
    }

    private void updateHashedPasswordInDatabase(String newPassword) {
        String hashedPassword = getMD5Hash(newPassword);
        reference.child(Uid).child("pass").setValue(hashedPassword);
    }

    private String getMD5Hash(String newPassword) {
        try {
            // Get instance of MD5 hash algorithm
            MessageDigest md = MessageDigest.getInstance("MD5");

            // Convert password to bytes and compute hash
            md.update(newPassword.getBytes());
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

    private void displayAccountCreationDate() {
        if (user != null) {
            reference.child(user.getUid()).child("accountDate")
                    .get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (task.isSuccessful()) {
                                String accountCreationDate = task.getResult().getValue(String.class);
                                if (accountCreationDate != null) {
                                    dateCreated.setText("Joined since: " + accountCreationDate);
                                }
                            }
                        }
                    });
        }
    }

    @Override
    public void onBackPressed(){
        CustomAlertDialog builder = new CustomAlertDialog(this);
        builder.show();
    }

    // MENU //
    private void Home() {
        startActivity(new Intent(Profile.this, MainActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);    }
    private void history() {
        startActivity(new Intent(Profile.this, History.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);    }
    private void bmi(){startActivity(new Intent(Profile.this, BMI.class));overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);}
    private void Pedometer() {startActivity(new Intent(Profile.this, Pedometer.class));overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);}
    private void share() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "Here's the link for our Witnessed Fitness App: \n\n https://drive.google.com/drive/folders/1pcoSaMJrnZYZKMlMgZCb_aUEhrWoCy7c?usp=share_link");
        startActivity(Intent.createChooser(intent, "Share via"));    }
    private void about() {
        startActivity(new Intent(Profile.this, About.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);    }
    private void LogOut() {
        FirebaseAuth.getInstance().signOut();
        StyleableToast.makeText(Profile.this, "Logged out successfully!",Toast.LENGTH_SHORT, R.style.Succeeded).show();
        startActivity(new Intent(Profile.this, LogIn.class));
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        finishAffinity();
    }
}