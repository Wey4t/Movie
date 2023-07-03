package com.example.myapplication;

import java.util.Date;

public class Movie {
    String title;
    String year;
    String type;
    String poster;
    String id;
    public Movie(String title, String year,  String id, String poster_url,String type){
        this.title = title;
        this.year = year;
        this.type = type;
        this.id = id;
        this.poster = poster_url;

    }
}
