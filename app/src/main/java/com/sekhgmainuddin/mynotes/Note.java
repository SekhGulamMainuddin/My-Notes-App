package com.sekhgmainuddin.mynotes;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_table")
public class Note {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private String description;
    private int priority;

    public Note(String title, int priority, String description) {
        this.title = title;
        this.priority = priority;
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public int getPriority() {
        return priority;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }
}
