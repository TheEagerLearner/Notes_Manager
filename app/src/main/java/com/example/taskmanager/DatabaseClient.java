package com.example.taskmanager;

import android.content.Context;

import androidx.room.Room;

public class DatabaseClient {

    private Context context;
    private static DatabaseClient instance;
    private NoteDatabase dab;

    private DatabaseClient(Context context)
    {
        this.context=context;

        dab= Room.databaseBuilder(context,NoteDatabase.class,"Notes").fallbackToDestructiveMigration().build();
    }

    public static DatabaseClient getInstance(Context ctx)
    {
        if(instance==null)
        {
            instance=new DatabaseClient(ctx);
        }

        return instance;
    }

    public  NoteDatabase getDab()
    {
        return dab;
    }



}
