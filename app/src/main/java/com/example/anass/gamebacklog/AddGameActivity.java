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

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddGameActivity extends AppCompatActivity {

    private String date;
    EditText title;
    EditText platform;
    EditText notes;
    Spinner  status;
    FloatingActionButton addFab;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_game);

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        date = formatter.format(new Date()).toString();

        title       = (EditText) findViewById(R.id.TitleField);
        platform    = (EditText) findViewById(R.id.PlatformField);
        notes       = (EditText)   findViewById(R.id.NotesField);
        status      = (Spinner)   findViewById(R.id.StatusField);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, Game.getStatussen());

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        status.setAdapter(adapter);

        addFab = (FloatingActionButton) findViewById(R.id.floatingActionButtonEditAdd);
        addFab.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent data = new Intent(AddGameActivity.this, MainActivity.class);
                data.putExtra("game", new Game(title.getText().toString(), platform.getText().toString(), notes.getText().toString(), status.getSelectedItem().toString(),date));
                setResult(Activity.RESULT_OK, data);
                finish();
            }
        });

    }
}
