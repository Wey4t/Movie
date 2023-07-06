package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInf = getMenuInflater();
        menuInf.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.APIkey){
            Intent i = new Intent(this, SettingActivity.class);
            startActivity(i);
        } else if (id == R.id.SavedMovies){
            Intent i = new Intent(this, SavedMoviesActivity.class);
            startActivity(i);
        } else{
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }
        finish();
        return true;
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