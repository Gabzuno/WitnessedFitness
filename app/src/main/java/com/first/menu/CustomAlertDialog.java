package com.first.menu;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class CustomAlertDialog extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;
    public Button yes, no;

    public CustomAlertDialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_alert);
        yes = findViewById(R.id.btn_yes);
        yes.setOnClickListener(this);

        no = findViewById(R.id.btn_no);
        no.setOnClickListener(this);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:

                c.finishAffinity();
                break;
            case R.id.btn_no:
                dismiss();
                break;
            default:
                break;
        }
    }
}
