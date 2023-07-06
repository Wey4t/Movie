package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Movie {
    @ColumnInfo(name = "title")
    String title;
    @ColumnInfo(name = "year")

    String year;
    @ColumnInfo(name = "type")

    String type;
    @ColumnInfo(name = "poster")
    String poster;
    @NonNull
    @PrimaryKey
    String id;
    @ColumnInfo(name = "released")

    String released;
    @ColumnInfo(name = "rating")

    String rating;
    @ColumnInfo(name = "plot")

    String plot;
    @ColumnInfo(name = "saved")
    boolean saved;
    boolean expandable;
    boolean ch;
    public Movie(){
        return;
    }
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
        this.saved = false;
    }
    public void setExpandable (boolean value){
        this.expandable = value;
    }
    public boolean getExpandable(){
        return this.expandable;
    }
}
