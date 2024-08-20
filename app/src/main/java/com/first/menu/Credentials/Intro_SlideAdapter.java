package com.first.menu.Credentials;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.first.menu.R;

public class Intro_SlideAdapter extends PagerAdapter {
    private final Context context;
    LayoutInflater inflater;

    public int[] slides = {
            R.layout.slide_main,
            R.layout.slide_first,
            R.layout.slide_second,
            R.layout.slide_third
    };

    public Intro_SlideAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return slides.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position){
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;
        TextView slideTitle;
        Button SignUp;

        switch (position){
            case 0:
                view = inflater.inflate(R.layout.slide_main,container,false);
                slideTitle = view.findViewById(R.id.slide_title);
                slideTitle.setText(slides[position]);
                break;
            case 1:
                view = inflater.inflate(R.layout.slide_first,container,false);
                slideTitle = view.findViewById(R.id.slide_title);
                slideTitle.setText(slides[position]);
                break;
            case 2:
                view = inflater.inflate(R.layout.slide_second,container,false);
                slideTitle = view.findViewById(R.id.slide_title);
                slideTitle.setText(slides[position]);
                break;
            case 3:
                view = inflater.inflate(R.layout.slide_third,container,false);
                slideTitle = view.findViewById(R.id.slide_title);
                slideTitle.setText(slides[position]);
                SignUp = view.findViewById(R.id.goToSignUp);
                SignUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context, Register.class));
                    }
                });
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + position);
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, @NonNull Object object){
        container.removeView((RelativeLayout)object);
    }
}