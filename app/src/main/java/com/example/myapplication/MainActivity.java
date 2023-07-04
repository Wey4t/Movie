package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    MovieAdapter movieAdp;
    RecyclerView recyclerView;
    private SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchView = (SearchView)findViewById(R.id.input);
        searchView.clearFocus();
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
        movieAdp = new MovieAdapter();
        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(movieAdp);
        movieAdp.setMoives(new ArrayList<>());
//        movieAdp.notifyDataSetChanged();
    }

    public void launchSetting(View v){
        // this is current activeity
        Intent i = new Intent(this, SettingActivity.class);
        startActivity(i);
    }
    public void handleText(String query){
        SearchView t = findViewById(R.id.input);

        String url = "https://www.omdbapi.com/?apikey=3dfcd5a7&s="+query;
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest  = new JsonObjectRequest(
                url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        ArrayList<Movie> m = new ArrayList<>();
                        try {
                            Boolean isError = response.getBoolean("Response");
                            Log.d("isError",Boolean.toString(isError));

                            if(isError){
                                JSONArray jsonArray = response.getJSONArray("Search");
                                Log.d("length",Integer.toString(jsonArray.length()));

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
                                    Log.d("Title",movie_json.getString("Title"));
                                }
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        Log.d("sss",Integer.toString(response.length()));
                        Log.d("ss",response.toString());
                        movieAdp.setMoives(m);
                        movieAdp.notifyDataSetChanged();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("tag","some error",error);
                    }
                }
        );
        queue.add(jsonObjectRequest );

    }
}