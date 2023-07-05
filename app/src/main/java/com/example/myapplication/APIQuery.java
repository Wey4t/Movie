package com.example.myapplication;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class APIQuery extends AsyncTask<String, Void, String> {
    private TextView ouputText = null;
    private Integer MAX_LEN = 0;
    public APIQuery(TextView text){
        this.ouputText = text;
        this.MAX_LEN = 10000;
    }
    @Override
    protected String doInBackground(String... strings) {
        try{
            URL url = new URL(strings[0]);
            HttpsURLConnection myConnection = (HttpsURLConnection) url.openConnection();
            myConnection.setRequestMethod("GET");
            myConnection.setDoInput(true);
            myConnection.connect();
            Integer code = myConnection.getResponseCode();
            Log.d("code",code.toString());
            if (code == 200) {
                InputStream responseBody = myConnection.getInputStream();
                InputStreamReader responseBodyReader =
                        new InputStreamReader(responseBody, "UTF-8");
                char []buffer = new char[this.MAX_LEN];
                responseBodyReader.read(buffer,0,this.MAX_LEN);
                return new String(buffer);
            } else {
                return "not 200";
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return "";
        }

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(ouputText != null){
            ouputText.setText(s);
        }
        return;
    }
}
