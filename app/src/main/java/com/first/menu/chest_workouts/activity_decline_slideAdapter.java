package com.first.menu.chest_workouts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.first.menu.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import pl.droidsonroids.gif.GifImageView;

public class activity_decline_slideAdapter extends PagerAdapter {
    private final Context context;
    LayoutInflater inflater;

    public int[] slides = {
            R.drawable.kneepushup,
            R.id.youtube_Id
    };

    public activity_decline_slideAdapter(Context context){
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
        GifImageView gifImageView;
        YouTubePlayerView youTubePlayerView;
        TextView slideTitle;

        switch (position){
            case 0:
                view = inflater.inflate(R.layout.activity_decline_demo,container,false);
                slideTitle = view.findViewById(R.id.slide_title);
                slideTitle.setText(slides[position]);
                break;
            case 1:
                view = inflater.inflate(R.layout.activity_decline_video,container,false);
                slideTitle = view.findViewById(R.id.slide_title);
                slideTitle.setText(slides[position]);
                youTubePlayerView = view.findViewById(R.id.youtube_Id);
                youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady(@NonNull YouTubePlayer youTubePlayer){
                        String videoId = "aq2xZxfrQlM";
                        youTubePlayer.loadVideo(videoId,0);
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
        container.removeView((LinearLayout)object);
    }
}

