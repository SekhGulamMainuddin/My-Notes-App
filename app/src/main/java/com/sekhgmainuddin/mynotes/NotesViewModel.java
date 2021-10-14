package com.sekhgmainuddin.mynotes;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NotesViewModel extends AndroidViewModel {
    private NotesRepository notesRepository;
    public LiveData<List<Note>> allNotes;

    public NotesViewModel(@NonNull Application application) {
        super(application);
        notesRepository=new NotesRepository(application);
        allNotes=notesRepository.getAllNotes();
    }

    public void insert(Note note){
        notesRepository.insert(note);
    }

    public void delete(Note note){
        notesRepository.delete(note);
    }

    public void update(Note note){
        notesRepository.update(note);
    }

    public void deleteAll(){
        notesRepository.deleteAll();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }
}
