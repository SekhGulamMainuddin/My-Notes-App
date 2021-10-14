package com.sekhgmainuddin.mynotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class Add_Note extends AppCompatActivity {

    public static final String EXTRA_ID = "com.sekhgmainuddin.mynotes.EXTRA_ID";
    public static final String EXTRA_TITLE = "com.sekhgmainuddin.mynotes.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "com.sekhgmainuddin.mynotes.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY = "com.sekhgmainuddin.mynotes.EXTRA_PRIORITY";

    private EditText Title;
    private EditText Description;
    private NumberPicker numberPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        Title = findViewById(R.id.Title);
        Description = findViewById(R.id.description);
        numberPicker = findViewById(R.id.number_picker);

        numberPicker.setMaxValue(10);
        numberPicker.setMinValue(1);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.cancel);
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Note");
            Title.setText(intent.getStringExtra(EXTRA_TITLE));
            Description.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            numberPicker.setValue(intent.getIntExtra(EXTRA_PRIORITY,1));
        } else {
            setTitle("Add Note");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void saveNote() {
        String title = Title.getText().toString();
        String description = Description.getText().toString();
        int priority = numberPicker.getValue();

        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Please add a title and description", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, description);
        data.putExtra(EXTRA_PRIORITY, priority);

        int id=getIntent().getIntExtra(EXTRA_ID,-1);
        if(id!=-1){
            data.putExtra(EXTRA_ID,id);
        }

        setResult(RESULT_OK, data);
        finish();
    }
}