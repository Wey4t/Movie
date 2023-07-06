package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
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