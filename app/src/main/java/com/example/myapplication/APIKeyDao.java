package com.example.myapplication;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface APIKeyDao {
    @Insert
    void InsertKey(APIkey k);
    @Update
    void UpdateKey(APIkey k);
    @Query("DELETE FROM APIKey")
    void Deletekey();
    @Query("SELECT * FROM APIKey")
    List<APIkey> getKey();

}
