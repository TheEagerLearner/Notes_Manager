package com.example.taskmanager;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.Date;

@Entity
public class NoteEntity implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String Heading;

    private String Description;

    private String created;

  public void setCreated(String created) {
        this.created = created;
    }

    public String getCreated() {
        return created;
    }

    public int getId() {
        return id;
    }

    public String getHeading() {
        return Heading;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setHeading(String heading) {
        Heading = heading;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDescription() {
        return Description;
    }
}
