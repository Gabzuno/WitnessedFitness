package com.first.menu.Credentials;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.first.menu.R;

import me.relex.circleindicator.CircleIndicator;

public class Activity_IntroSlide extends AppCompatActivity {

    ViewPager viewPager;
    Intro_SlideAdapter adapter;
    CircleIndicator indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slide_view);

        viewPager = findViewById(R.id.viewPager_id);
        indicator = findViewById(R.id.indicator_id);
        adapter = new Intro_SlideAdapter(this);
        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager);
    }
}
