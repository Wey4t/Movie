package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater lyif = LayoutInflater.from(parent.getContext());
        View v = lyif.inflate(R.layout.movie_card,parent,false);
        return new MovieHolder(v);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, int position) {
        holder.movie_poster.setImageBitmap(null);
        holder.year.setText("2012");
        holder.movie_title.setText("batman");
    }

    static class MovieHolder extends RecyclerView.ViewHolder {
            TextView movie_title, year;
            ImageView movie_poster;
            public MovieHolder(@NonNull View v){
                super(v);
                movie_title = v.findViewById(R.id.movie_title);
                year = v.findViewById(R.id.year);
                movie_poster = v.findViewById(R.id.movie_poster);
            }
        }
}
