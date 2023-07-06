package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class SettingActivity extends AppCompatActivity {
    APIKeyDatabase apiKeyDatabase;
    APIKeyDao apiKeyDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("create","create a setting act");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        apiKeyDatabase = Room.databaseBuilder(this,APIKeyDatabase.class,"apiKeyDatabase").allowMainThreadQueries().build();
        apiKeyDao = apiKeyDatabase.getAPIKeyDao();
        List<APIkey> l= apiKeyDao.getKey();
        if( l.size() != 0){
            EditText t = findViewById(R.id.editTextText);
            t.setText(l.get(0).get_key());
        }
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
        Log.d("msg","resume  ！");
        List<APIkey> l= apiKeyDao.getKey();
        if( l.size() != 0){
            EditText t = findViewById(R.id.editTextText);
            t.setText(l.get(0).get_key());
        }
    }

    public void save_key(View v){
        EditText t = findViewById(R.id.editTextText);
        String s = t.getText().toString();
        apiKeyDao.Deletekey();
        apiKeyDao.InsertKey(new APIkey(s));
        Toast toast = Toast.makeText(this,"Saved",Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }
}