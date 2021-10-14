package com.sekhgmainuddin.mynotes;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Note.class},version = 1)
public abstract class NotesDatabase extends RoomDatabase {

    private static NotesDatabase instance;
    public abstract NoteDao noteDao();

    public static synchronized NotesDatabase getInstance(Context context){
        if(instance==null){
            instance= Room.databaseBuilder(context.getApplicationContext(),
                    NotesDatabase.class,"note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallBack =new RoomDatabase.Callback(){

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDBAsyncTask(instance).execute();
        }
    };



    public static class PopulateDBAsyncTask extends AsyncTask<Void,Void,Void>{
        private NoteDao noteDao;

        public PopulateDBAsyncTask(NotesDatabase db) {
            this.noteDao= db.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Note("Title 1",1,"Description 1"));
            noteDao.insert(new Note("Title 2",2,"Description 2"));
            noteDao.insert(new Note("Title 3",3,"Description 3"));
            noteDao.insert(new Note("Title 4",4,"Description 4"));
            return null;
        }
    }
}
