package com.example.myapplication;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;
@Dao
public interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void Insert(Movie m);
    @Update
    void Update(Movie m);
    @Delete
    void delete(Movie m);
    @Query("DELETE FROM Movie WHERE id = :movieID")
    void deleteId(String movieID);
    @Query("SELECT EXISTS(SELECT * FROM movie WHERE id = :movieID)")
    boolean isMoiveExist(String movieID);
    @Query("SELECT * FROM Movie")
    List<Movie> getAll();
}
