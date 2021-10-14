package com.sekhgmainuddin.mynotes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.behavior.SwipeDismissBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE=1;
    public static final int EDITREQUEST_CODE=2;

    final Note[] undonote = new Note[1];

    private NotesViewModel notesViewModel;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton floatingActionButton=findViewById(R.id.floating_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Add_Note.class);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });
        recyclerView=findViewById(R.id.Recycler_View);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        final NoteCustomAdapter noteCustomAdapter=new NoteCustomAdapter();
        recyclerView.setAdapter(noteCustomAdapter);
        notesViewModel= new ViewModelProvider(this).get(NotesViewModel.class);
        notesViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                noteCustomAdapter.submitList(notes);
            }
        });
        noteCustomAdapter.SetOnItemClickedListener(new NoteCustomAdapter.OnItemClickedListener() {
            @Override
            public void OnItemClicked(Note note) {
                Intent intent=new Intent(MainActivity.this,Add_Note.class);
                intent.putExtra(Add_Note.EXTRA_ID,note.getId());
                intent.putExtra(Add_Note.EXTRA_TITLE,note.getTitle());
                intent.putExtra(Add_Note.EXTRA_DESCRIPTION,note.getDescription());
                intent.putExtra(Add_Note.EXTRA_PRIORITY,note.getPriority());
                startActivityForResult(intent,EDITREQUEST_CODE);
            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                undonote[0] =noteCustomAdapter.getNoteAt(viewHolder.getAdapterPosition());
                notesViewModel.delete(noteCustomAdapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Note Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE && resultCode==RESULT_OK){
            String title=data.getStringExtra(Add_Note.EXTRA_TITLE);
            String description=data.getStringExtra(Add_Note.EXTRA_DESCRIPTION);
            int priority=data.getIntExtra(Add_Note.EXTRA_PRIORITY,1);

            Note note=new Note(title,priority,description);
            notesViewModel.insert(note);

            Toast.makeText(this, "Note added", Toast.LENGTH_SHORT).show();
        }
        else if(requestCode==EDITREQUEST_CODE && resultCode==RESULT_OK){
            int id=data.getIntExtra(Add_Note.EXTRA_ID,-1);
            if(id==-1){
                Toast.makeText(MainActivity.this, "Note can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String title=data.getStringExtra(Add_Note.EXTRA_TITLE);
            String description=data.getStringExtra(Add_Note.EXTRA_DESCRIPTION);
            int priority=data.getIntExtra(Add_Note.EXTRA_PRIORITY,1);

            Note note=new Note(title,priority,description);
            note.setId(id);
            notesViewModel.update(note);
            Toast.makeText(MainActivity.this, "Note updated", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Note not added", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.deleteallnotes:
                notesViewModel.deleteAll();
                Toast.makeText(MainActivity.this, "All Notes Deleted", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.undo:
                notesViewModel.insert(undonote[0]);
                Toast.makeText(MainActivity.this, "Undone", Toast.LENGTH_SHORT).show();
                return true;
            default:return super.onOptionsItemSelected(item);
        }

    }
}