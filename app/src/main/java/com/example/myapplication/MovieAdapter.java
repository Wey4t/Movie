package com.example.myapplication;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {
    ArrayList<Movie> movies;
    public void setMoives(ArrayList<Movie> m){
        movies = m;
    }
    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater lyif = LayoutInflater.from(parent.getContext());
        View v = lyif.inflate(R.layout.movie_card,parent,false);
        return new MovieHolder(v);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, int position) {
        Movie m = movies.get(position);
        holder.year.setText(m.year);
        Glide.with(holder._v)
                .load(m.poster)
                        .placeholder(R.drawable.ic_launcher_background)
                                .listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        return false;
                                    }
                                }).into(holder.movie_poster);
        holder.movie_title.setText(m.title);
    }

    static class MovieHolder extends RecyclerView.ViewHolder {
            TextView movie_title, year;
            ImageView movie_poster;
            View _v;
            public MovieHolder(@NonNull View v){
                super(v);
                _v = v;
                movie_title = v.findViewById(R.id.movie_title);
                year = v.findViewById(R.id.year);
                movie_poster = v.findViewById(R.id.movie_poster);
            }
        }
}
