package com.example.myapplication;

import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.RoomDatabase;

@Database(entities = {APIkey.class}, version = 1, exportSchema = false)
public abstract class APIKeyDatabase extends RoomDatabase {
    public abstract APIKeyDao getAPIKeyDao();
}
