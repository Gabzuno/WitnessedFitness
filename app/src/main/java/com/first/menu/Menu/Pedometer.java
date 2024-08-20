package com.first.menu.Menu;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.first.menu.BMI.BMI;
import com.first.menu.Credentials.LogIn;
import com.first.menu.Credentials.MainActivity;
import com.first.menu.Credentials.RecordBMI;
import com.first.menu.Credentials.Users;
import com.first.menu.CustomAlertDialog;
import com.first.menu.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.Locale;

import io.github.muddz.styleabletoast.StyleableToast;

public class Pedometer extends AppCompatActivity {
    private LatLng startPoint = null;
    private Polyline polyline = null;
    private boolean isStarted = false;
    private TextView durationDisplay;
    ImageView startButton;
    FirebaseUser user;
    DatabaseReference reference;
    String Uid;
    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient fused;
    Marker currentLocationMarker;
    Marker selectedLocationMarker;
    private boolean isDefaultMapType = true;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_pedometer);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.Map);
        fused = LocationServices.getFusedLocationProviderClient(this);

        Dexter.withContext(getApplicationContext()).withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        getCurrentLocation();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                    }
                }).check();

        /*
        Intent mapIntent = getPackageManager().getLaunchIntentForPackage("com.google.android.apps.maps");
        if (mapIntent != null) {
            startActivity(mapIntent);
        } else {
            Toast.makeText(this, "Google Maps is not installed on your device", Toast.LENGTH_SHORT).show();
        }

         */

        //MENU
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("users");
        Uid = user.getUid();

        NavigationView navigationView = findViewById(R.id.NavView);
        View header = navigationView.getHeaderView(0);

        TextView UserName = header.findViewById(R.id.AccName);
        TextView UserEmail = header.findViewById(R.id.AccEmail);


        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        toolbar.setNavigationOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                item.setChecked(true);
                drawerLayout.closeDrawer(GravityCompat.START);
                switch (id) {
                    case R.id.nav_home:
                        Home();
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
        });

        //USER
        reference.child(Uid).addListenerForSingleValueEvent(new ValueEventListener() {
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
                Toast.makeText(Pedometer.this, "Something went wrong!", Toast.LENGTH_LONG).show();
            }
        });

        //PEDOMETER
        //TextView stepsDisplay = findViewById(R.id.steps display);
        //stepCounter = new StepCounter((SensorManager) getSystemService(Context.SENSOR_SERVICE), stepsDisplay);

    }

    @Override
    public void onBackPressed() {
        CustomAlertDialog builder = new CustomAlertDialog(this);
        builder.show();
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Task<Location> task = fused.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(@NonNull GoogleMap googleMap) {

                        // Set up map click listener to add marker manually
                        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                            @Override
                            public void onMapClick(@NonNull LatLng latLng) {
                                // Remove the markers for current and selected location
                                if (currentLocationMarker != null) {
                                    currentLocationMarker.remove();
                                }
                                if (selectedLocationMarker != null) {
                                    selectedLocationMarker.remove();
                                }
                                // Add a marker for the selected location
                                MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("Selected Location");
                                selectedLocationMarker = googleMap.addMarker(markerOptions);
                            }
                        });

                        if (location != null) {
                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                            MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("Current Location");
                            googleMap.clear();
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
                            currentLocationMarker = googleMap.addMarker(markerOptions);

                            // MAP TYPE //
                            ImageView mapTypeButton = findViewById(R.id.mapType);
                            mapTypeButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (isDefaultMapType) {
                                        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                                        isDefaultMapType = false;
                                    } else {
                                        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                                        isDefaultMapType = true;
                                    }
                                }
                            });

                            // CURRENT LOCATION //
                            ImageView focusButton = findViewById(R.id.currentLoc);
                            focusButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // Remove the markers for current and selected location
                                    if (currentLocationMarker != null) {
                                        currentLocationMarker.remove();
                                    }
                                    if (selectedLocationMarker != null) {
                                        selectedLocationMarker.remove();
                                    }
                                    if (ActivityCompat.checkSelfPermission(Pedometer.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Pedometer.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                        return;
                                    }
                                    fused.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                                        @Override
                                        public void onSuccess(Location location) {
                                            if (location != null) {
                                                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                                                MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("Current Location");
                                                googleMap.clear();
                                                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
                                                currentLocationMarker = googleMap.addMarker(markerOptions);
                                            }
                                        }
                                    });
                                }
                            });

                            TextView calorieDisplay = findViewById(R.id.calories_display);
                            TextView distanceDisplay = findViewById(R.id.distance_display);

                            //TRACK//
                            durationDisplay = findViewById(R.id.duration_display);
                            Stopwatch stopwatch = new Stopwatch();

                            startButton = findViewById(R.id.start_button);
                            startButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (!isStarted) {
                                        startPoint = latLng;

                                        if (polyline != null) {
                                            polyline.remove();
                                        }

                                        startButton.setImageResource(R.drawable.stop);
                                        stopwatch.start();
                                    } else {
                                        polyline = googleMap.addPolyline(new PolylineOptions()
                                                .add(startPoint, latLng)
                                                .width(5)
                                                .color(Color.GREEN));
                                        startPoint = null;
                                        stopwatch.stop();
                                        startButton.setImageResource(R.drawable.play);

                                        //COMPUTES CALORIE BURNED//
                                        Query Pedometer = reference.child(Uid+"/BMI").orderByChild("recordKey").limitToLast(1);
                                        Pedometer.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @SuppressLint("DefaultLocale")
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                if (snapshot.exists()) {
                                                    // Get the latest BMI record
                                                    RecordBMI currentRecord = snapshot.getChildren().iterator().next().getValue(RecordBMI.class);
                                                    if (currentRecord != null) {
                                                        float[] results = new float[1];
                                                        if (startPoint != null) {
                                                            Location.distanceBetween(startPoint.latitude, startPoint.longitude, latLng.latitude, latLng.longitude, results);
                                                        }
                                                        double distanceInKm = results[0] / 1000.0;
                                                        distanceDisplay.setText(String.format("%.2f km", distanceInKm));

                                                        // Extract the weight from the BMI record
                                                        double weight = Double.parseDouble(currentRecord.weight);

                                                        // MET value for knee push-ups
                                                        double MET_VALUE = 7.5;

                                                        // Calculate calorie burn per minute
                                                        double caloriesBurned = MET_VALUE * weight * distanceInKm;
                                                        calorieDisplay.setText(String.format("%.2f cal", caloriesBurned));

                                                        // Get the start and end time for the stopwatch
                                                        long startTime = stopwatch.startTime;
                                                        long endTime = stopwatch.elapsedTime;

                                                        // Store the distance, calorie burn, start and end time in the database
                                                        DatabaseReference pedometerRef = reference.child(Uid).child("Pedometer");
                                                        pedometerRef.child("Distance Traveled").setValue(distanceInKm);
                                                        pedometerRef.child("Calories Burned per km").setValue(caloriesBurned);
                                                        pedometerRef.child("Start Time: ").setValue(startTime);
                                                        pedometerRef.child("End Time: ").setValue(endTime);
                                                    } else {
                                                        // BMI record is null
                                                        Toast.makeText(Pedometer.this,"No record found!",Toast.LENGTH_SHORT).show();
                                                    }
                                                } else {
                                                    // No BMI records found for the user
                                                    Toast.makeText(Pedometer.this,"No record found!",Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {
                                                // Handle database error
                                                Toast.makeText(Pedometer.this,"Something went wrong!", Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    }
                                    isStarted = !isStarted;
                                }
                            });

                        } else {
                            Toast.makeText(Pedometer.this,"Please turn on your location",Toast.LENGTH_SHORT).show();

                            ImageView mapTypeButton = findViewById(R.id.mapType);
                            mapTypeButton.setVisibility(View.GONE);

                            ImageView focusButton = findViewById(R.id.currentLoc);
                            focusButton.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
    }

    private class Stopwatch implements Runnable {
        private final Handler handler = new Handler();
        private long startTime;
        private long elapsedTime = 0;
        private boolean isRunning = false;

        @Override
        public void run() {
            elapsedTime = System.currentTimeMillis() - startTime;
            updateTimerText(elapsedTime);
            handler.postDelayed(this, 1000);
        }

        public void start() {
            if (!isRunning) {
                startTime = System.currentTimeMillis();
                handler.postDelayed(this, 0);
                isRunning = true;
            }
        }

        public void stop() {
            if (isRunning) {
                handler.removeCallbacks(this);
                isRunning = false;
                updateTimerText(elapsedTime);
            }
        }

        private void updateTimerText(long timeInMilliseconds) {
            int seconds = (int) (timeInMilliseconds / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            int hours = minutes / 60;
            minutes = minutes % 60;

            String timeString = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
            durationDisplay.setText(timeString);
        }
    }


    /*
    private class StepCounter implements SensorEventListener {

        private final SensorManager sensorManager;
        private final Sensor stepSensor;
        private int totalSteps = 0;
        private final TextView DisplaySteps;
        private boolean isSensorRegistered = false;

        public StepCounter(SensorManager sensorManager, TextView stepsDisplay) {
            this.sensorManager = sensorManager;
            this.stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            this.DisplaySteps = stepsDisplay;
        }

        public void start() {
            if(stepSensor != null){
                isSensorRegistered = true;
                Toast.makeText(Pedometer.this, "Step Sensor is available", Toast.LENGTH_SHORT).show();
                sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_NORMAL);
            } else {
                Toast.makeText(Pedometer.this, "Step Sensor not available", Toast.LENGTH_SHORT).show();
            }
        }

        public void stop() {
            if(isSensorRegistered){
                sensorManager.unregisterListener(this);
                isSensorRegistered = false;
            }
        }

        public int getTotalSteps() {
            return totalSteps;
        }

        public void reset(){
            totalSteps = 0;
            DisplaySteps.setText("0");
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];
                float acceleration = (float) Math.sqrt(x * x + y * y + z * z) - SensorManager.GRAVITY_EARTH;
                if (acceleration > 25) {
                    totalSteps++;
                    DisplaySteps.setText(String.valueOf(totalSteps));
                }
                //totalSteps = (int) event.values[0];
                //DisplaySteps.setText(String.valueOf(totalSteps));
            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // Do nothing
        }
    }

     */

    // MENU
    private void Home() {
        startActivity(new Intent(Pedometer.this, MainActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);    }
    private void history() {
        startActivity(new Intent(Pedometer.this, History.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);    }
    private void Bmi() {startActivity(new Intent(Pedometer.this, BMI.class));overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);}
    private void share() {
        /*startActivity(new Intent(MainActivity.this, Share.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
         */
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "Here's the link for our Witnessed Fitness App: \n\n https://drive.google.com/drive/folders/1pcoSaMJrnZYZKMlMgZCb_aUEhrWoCy7c?usp=share_link");
        startActivity(Intent.createChooser(intent, "Share via")); }
    private void about() {
        startActivity(new Intent(Pedometer.this, About.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);    }
    private void LogOut() {
        FirebaseAuth.getInstance().signOut();
        StyleableToast.makeText(Pedometer.this, "Logged out successfully!",Toast.LENGTH_SHORT, R.style.Succeeded).show();
        startActivity(new Intent(Pedometer.this, LogIn.class));
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        finishAffinity();
    }
}