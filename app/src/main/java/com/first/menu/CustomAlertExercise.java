package com.first.menu;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class CustomAlertExercise extends Dialog{

    private CustomAlertExerciseListener listener;
    private Activity activity;

    private Button yes;
    private Button no;

    public CustomAlertExercise(Activity activity, CustomAlertExerciseListener listener) {
        super(activity);
        this.activity = activity;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_alert_exercises);

        yes = findViewById(R.id.btn_yes);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onYesClicked();
            }
        });

        no = findViewById(R.id.btn_no);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        activity.moveTaskToBack(true);
    }

    public interface CustomAlertExerciseListener {
        void onYesClicked();
    }
}