package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void launchSetting(View v){
        // this is current activeity
        Intent i = new Intent(this, SettingActivity.class);
        startActivity(i);
    }
    public void handleText(View v){
        TextView t = findViewById(R.id.input);
        String input = t.getText().toString();
        new APIQuery(((TextView)findViewById(R.id.source))).execute(input);
//        ((TextView)findViewById(R.id.source)).setText(input);
        Log.d("info",input);
    }
}