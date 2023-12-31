package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;

import com.android.volley.NoConnectionError;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MovieAdapter movieAdp;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private String key;
    private RequestQueue queue;
    private APIKeyDatabase apiKeyDatabase;
    APIKeyDao apiKeyDao;
    MovieDao movieDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("create","main");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiKeyDatabase = Room.databaseBuilder(this, APIKeyDatabase.class,"apiKeyDatabase").allowMainThreadQueries().build();
        apiKeyDao = apiKeyDatabase.getAPIKeyDao();
        movieDao = apiKeyDatabase.getMovieDao();
        movieAdp = new MovieAdapter();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(movieAdp);
        movieAdp.setMoives(new ArrayList<>());
        movieAdp.setMovieDao(movieDao);
        queue = Volley.newRequestQueue(this);
        movieAdp.SetQueue(queue);
        movieAdp.SetApiKey(key);
        List<APIkey> keyL = apiKeyDao.getKey();
        if(keyL.size() != 0){
//            key = "3dfcd5a7";
            key = keyL.get(0).get_key();
        }
        searchView = (SearchView)findViewById(R.id.input);
        searchView.clearFocus();
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setIconified(false);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                handleText(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


//        movieAdp.notifyDataSetChanged();
    }
    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.d("msg","resume in main ！");
        List<APIkey> l= apiKeyDao.getKey();
        if( l.size() != 0){
            String new_k = l.get(0).get_key();
            if( !key.equals(new_k)){
                movieAdp.setMoives(new ArrayList<>());
                movieAdp.notifyDataSetChanged();
            }
            key = l.get(0).get_key();
            movieAdp.SetApiKey(key);
        }
        movieAdp.notifyDataSetChanged();
        Log.d("msg","api:"+key);

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
            launchSetting(findViewById(R.id.APIkey));
        } else if (id == R.id.SavedMovies){
            launchSavedMovies(findViewById(R.id.SavedMovies));
        } else{
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }
        finish();

        return true;

    }

    public void launchSetting(View v){
        // this is current activeity
        Intent i = new Intent(this, SettingActivity.class);
        startActivity(i);
    }
    public void launchSavedMovies(View v){
        Intent i = new Intent(this, SavedMoviesActivity.class);
        startActivity(i);
    }
    public void handleText(String query){
        SearchView t = findViewById(R.id.input);

        String url = "https://www.omdbapi.com/?apikey="+key+"&s="+query;
        JsonObjectRequest jsonObjectRequest  = new JsonObjectRequest(
                url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        ArrayList<Movie> m = new ArrayList<>();
                        try {
                            Boolean isError = response.getBoolean("Response");

                            if(isError){
                                JSONArray jsonArray = response.getJSONArray("Search");

                                for (int i = 0 ; i < jsonArray.length(); ++i){
                                    JSONObject movie_json = jsonArray.getJSONObject(i);
                                    Movie newMoive = new Movie(
                                            movie_json.getString("Title"),
                                            movie_json.getString("Year"),
                                            movie_json.getString("imdbID"),
                                            movie_json.getString("Poster"),
                                            movie_json.getString("Type")
                                    );
                                    m.add(newMoive);
                                }
                            }else{
                                Toast toast = Toast.makeText(MainActivity.this,response.getString("Error"),Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER,0,0);
                                toast.show();
                            }
                        } catch (JSONException e) {

                            throw new RuntimeException(e);

                        }
                        movieAdp.setMoives(m);
                        movieAdp.notifyDataSetChanged();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast toast;

                        if(error instanceof TimeoutError ||
                                error instanceof NoConnectionError){
                            toast = Toast.makeText(MainActivity.this, "Check your internet connection", Toast.LENGTH_SHORT);
                        }else if(error.networkResponse != null && error.networkResponse.statusCode==401){
                            toast = Toast.makeText(MainActivity.this, "invaild API key", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER,0,0);
                        }else {
                            toast = Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT);
                        }
                        toast.show();
                    }
                }
        );
        int Timeout = 1000;
        RetryPolicy policy = new DefaultRetryPolicy(Timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjectRequest.setRetryPolicy(policy);
        queue.add(jsonObjectRequest);

    }
}