package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {
    MovieAdapter movieAdp;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        movieAdp = new MovieAdapter();
        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(movieAdp);
        movieAdp.notifyDataSetChanged();
    }

    public void launchSetting(View v){
        // this is current activeity
        Intent i = new Intent(this, SettingActivity.class);
        startActivity(i);
    }
    public void handleText(View v){
        TextView t = findViewById(R.id.input);
        String url = t.getText().toString();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest strRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ((TextView)findViewById(R.id.source)).setText(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("tag","some error",error);
                    }
                }
        );
        queue.add(strRequest);

    }
}