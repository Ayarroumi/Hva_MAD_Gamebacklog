package com.example.anass.gamebacklog;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.anass.gamebacklog.R;

public class EditGameActivity extends AppCompatActivity {

    EditText title;
    EditText platform;
    EditText notes;
    Spinner  status;
    FloatingActionButton editFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_game);

        final Game game = getIntent().getParcelableExtra("game");

        title       = (EditText) findViewById(R.id.TitleField);
        platform    = (EditText) findViewById(R.id.PlatformField);
        notes       = (EditText)   findViewById(R.id.NotesField);
        status      = (Spinner)   findViewById(R.id.StatusField);
        editFab     = (FloatingActionButton) findViewById(R.id.floatingActionButtonEditAdd);

        title.setText(game.getTitle());
        platform.setText(game.getPlatform());
        notes.setText(game.getNotes());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, Game.getStatussen());

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        status.setAdapter(adapter);
        status.setSelection(getSelectionSpinner(game.getStatus(),Game.getStatussen()));

        editFab.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                game.setTitle(title.getText().toString());
                game.setPlatform(platform.getText().toString());
                game.setNotes(notes.getText().toString());
                game.setStatus(status.getSelectedItem().toString());

                Intent data = new Intent(EditGameActivity.this, MainActivity.class);
                data.putExtra("game", game);

                setResult(Activity.RESULT_OK, data);
                finish();
            }
        });
    }

    public int getSelectionSpinner(String value, String[] items){
        for (int i = 0; i < items.length; i++){
            if(value.equals(items[i])){
                return i;
            }
        }
        return 0;
    }
}
