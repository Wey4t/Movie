package com.example.myapplication;

import java.util.Date;

public class Movie {
    String title;
    String year;
    String type;
    String poster;
    String id;
    String released;
    String rating;
    String plot;
    boolean expandable;
    boolean ch;
    public Movie(String title, String year,  String id, String poster_url,String type){
        this.title = title;
        this.year = year;
        this.type = type;
        this.id = id;
        this.poster = poster_url;
        this.expandable = false;
        this.ch = false;
        this.released = "";
        this.plot = "";
        this.rating = "";
    }
    public void setExpandable (boolean value){
        this.expandable = value;
    }
    public boolean getExpandable(){
        return this.expandable;
    }
}
