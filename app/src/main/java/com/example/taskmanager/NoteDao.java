package com.example.taskmanager;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    void Insert(NoteEntity note);

    @Query("SELECT * FROM noteentity")
    List<NoteEntity> Display();

    @Query("DELETE FROM noteentity")
    void DeleteAll();

    @Delete
    void Delete(NoteEntity noteEntity);

    @Update
    void Update(NoteEntity noteEntity);
}
