package com.example.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

public class SavedMoviesActivity extends AppCompatActivity {
    private APIKeyDatabase apiKeyDatabase;
    MovieDao movieDao;
    MovieAdapter mvAdp;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movies);
        apiKeyDatabase = Room.databaseBuilder(this, APIKeyDatabase.class,"apiKeyDatabase").allowMainThreadQueries().build();
        movieDao = apiKeyDatabase.getMovieDao();
        mvAdp = new MovieAdapter();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mvAdp);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        List<Movie> l = movieDao.getAll();
        l.forEach(x -> {x.setExpandable(false); x.saved = true; });
        mvAdp.setMovieDao(movieDao);
        mvAdp.setMoives(new ArrayList<Movie>(l));
        mvAdp.notifyDataSetChanged();

    }
}