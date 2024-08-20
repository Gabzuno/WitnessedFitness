package com.first.menu.Credentials;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.first.menu.BMI.BMI;
import com.first.menu.CustomAlertDialog;
import com.first.menu.Menu.About;
import com.first.menu.Menu.History;
import com.first.menu.Menu.Profile;
import com.first.menu.R;
import com.first.menu.Workouts.arm;
import com.first.menu.Workouts.backk;
import com.first.menu.Workouts.chestt;
import com.first.menu.Workouts.coree;
import com.first.menu.Workouts.legg;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.github.muddz.styleabletoast.StyleableToast;

public class MainActivity extends AppCompatActivity {

  FirebaseUser user;
  DatabaseReference reference;
  String Uid;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    user = FirebaseAuth.getInstance().getCurrentUser();
    reference = FirebaseDatabase.getInstance().getReference("users");
    Uid = user.getUid();

    NavigationView navigationView = findViewById(R.id.NavView);
    navigationView.setCheckedItem(R.id.nav_home);
    View header = navigationView.getHeaderView(0);

    TextView UserName = header.findViewById(R.id.AccName);
    TextView UserEmail = header.findViewById(R.id.AccEmail);

    MaterialToolbar toolbar = findViewById(R.id.topAppBar);
    DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
    toolbar.setNavigationOnClickListener(v ->
      drawerLayout.openDrawer(GravityCompat.START)
    );

    navigationView.setNavigationItemSelectedListener(
      new NavigationView.OnNavigationItemSelectedListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
          int id = item.getItemId();
          item.setChecked(true);
          drawerLayout.closeDrawer(GravityCompat.START);
          switch (id) {
            case R.id.nav_profile:
              profile();
              break;
            //case R.id.nav_history:
            //history();break;
            case R.id.nav_bmi:
              Bmi();
              break;
            case R.id.nav_share:
              share();
              break;
            case R.id.nav_about:
              about();
              break;
            case R.id.nav_out:
              LogOut();
              break;
            default:
              return true;
          }
          return true;
        }
      }
    );

    //USER
    reference
      .child(Uid)
      .addListenerForSingleValueEvent(
        new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot snapshot) {
            Users user = snapshot.getValue(Users.class);

            if (user != null) {
              String username = user.user;
              String email = user.email;

              UserName.setText(username);
              UserEmail.setText(email);
            }
          }

          @Override
          public void onCancelled(@NonNull DatabaseError error) {
            Toast
              .makeText(
                MainActivity.this,
                "Something went wrong!",
                Toast.LENGTH_LONG
              )
              .show();
          }
        }
      );
  }

  @Override
  public void onBackPressed() {
    CustomAlertDialog builder = new CustomAlertDialog(this);
    builder.show();
  }

  // MENU
  private void profile() {
    startActivity(new Intent(MainActivity.this, Profile.class));
    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
  }

  private void history() {
    startActivity(new Intent(MainActivity.this, History.class));
    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
  }

  private void Bmi() {
    startActivity(new Intent(MainActivity.this, BMI.class));
    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
  }

  /*
  private void Pedometer() {
    startActivity(new Intent(MainActivity.this, Pedometer.class));
    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
  }

   */

  private void share() {
    /*startActivity(new Intent(MainActivity.this, Share.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

     */
    Intent intent = new Intent(Intent.ACTION_SEND);
    intent.setType("text/plain");
    intent.putExtra(
      Intent.EXTRA_TEXT,
      "Here's the link for our Witnessed Fitness App: \n\n https://drive.google.com/drive/folders/1pcoSaMJrnZYZKMlMgZCb_aUEhrWoCy7c?usp=share_link"
    );
    startActivity(Intent.createChooser(intent, "Share via"));
  }


  private void about() {
    startActivity(new Intent(MainActivity.this, About.class));
    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
  }

  private void LogOut() {
    FirebaseAuth.getInstance().signOut();
    StyleableToast
      .makeText(
        MainActivity.this,
        "Logged out successfully!",
        Toast.LENGTH_SHORT,
        R.style.Succeeded
      )
      .show();
    startActivity(new Intent(MainActivity.this, LogIn.class));
    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    finishAffinity();
  }

  //WORKOUT
  public void chest(View view) {
    startActivity(new Intent(MainActivity.this, chestt.class));
  }

  public void core(View view) {
    startActivity(new Intent(MainActivity.this, coree.class));
  }

  public void arm(View view) {
    startActivity(new Intent(MainActivity.this, arm.class));
  }

  public void back(View view) {
    startActivity(new Intent(MainActivity.this, backk.class));
  }

  public void leg(View view) {
    startActivity(new Intent(MainActivity.this, legg.class));
  }

  // MUSIC PLAYER //
  public void OpenMusic(View view) {
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle("Choose a music player");
    builder.setMessage("Which would you like to listen?");
    builder.setNegativeButton(
            "Local",
            new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialog, int which) {
                // Launch local music player
                Intent goToLocal = getPackageManager().getLaunchIntentForPackage("com.miui.player");
                if (goToLocal != null) {
                  Toast.makeText(MainActivity.this,"Opening Local Music Library",Toast.LENGTH_LONG).show();
                  goToLocal.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                  startActivity(goToLocal);
                } else {
                  // Handle the case where the local music player app is not installed
                  Toast.makeText(getApplicationContext(), "Local music player not installed", Toast.LENGTH_SHORT).show();
                }
              }
            });
    builder.setPositiveButton(
            "Spotify",
            new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialog, int which) {
                // Launch YouTube Music
                /*
                String youtubeMusicWebUrl = "https://music.youtube.com/";
                Intent GoToYM = new Intent(Intent.ACTION_VIEW, Uri.parse(youtubeMusicWebUrl));
                startActivity(GoToYM);

                 */

                PackageManager pm = getPackageManager();
                boolean isSpotifyInstalled;

                try {
                  pm.getPackageInfo("com.spotify.music", 0);
                  isSpotifyInstalled = true;
                } catch (PackageManager.NameNotFoundException e) {
                  isSpotifyInstalled = false;
                }

                if (isSpotifyInstalled) {
                  // Spotify is installed, open the Spotify app
                  Intent intent = new Intent(Intent.ACTION_VIEW);
                  intent.setData(Uri.parse("https://open.spotify.com/album/0sNOF9WDwhWunNAHPD3Baj"));
                  startActivity(intent);
                } else {
                  // Spotify is not installed, open the content in a browser using the branch link
                  Toast.makeText(MainActivity.this,"Opening Spotify",Toast.LENGTH_LONG).show();
                  final String branchLink = "https://spotify.link/content_linking?~campaign=" +
                          MainActivity.this.getPackageName() +
                          "&$deeplink_path=https://open.spotify.com/album/0sNOF9WDwhWunNAHPD3Baj" +
                          "&$fallback_url=https://open.spotify.com/album/0sNOF9WDwhWunNAHPD3Baj";

                  Intent intent = new Intent(Intent.ACTION_VIEW);
                  intent.setData(Uri.parse(branchLink));
                  startActivity(intent);
                }
              }
            });
    builder.show();
  }
}
