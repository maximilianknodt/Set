package com.example.set.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.set.R;
import com.example.set.ui.GameScreen;
import com.example.set.ui.SettingsScreen;

public class StartScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_screen);

        // Searching in Ressources for the IDs
        Button btnSP = this.findViewById(R.id.button_Start_SP);
        Button btnMP = this.findViewById(R.id.button_Start_MP);
        Button btnSettings = this.findViewById(R.id.button_Start_Settings);
        Button btnScore = this.findViewById(R.id.button_Start_Highscore);

        // -------- SINGLEPLAYER --------
        btnSP.setOnClickListener(v -> {
            Log.d("Debug", "On Click - Singleplayer Match startet");

            Intent intentSP = new Intent();
            intentSP.setClass(this, GameScreen.class);
            startActivity(intentSP);
        });


        // -------- MULTIPLAYER --------
        btnMP.setOnClickListener(v -> {
            Log.d("Debug", "On Click - Multiplayer Match startet");

            Intent intentMP = new Intent();
            intentMP.setClass(this, GameScreen.class);          // TODO: Klasse MPzwischenScreen + die entsprechende .xml Datei erzeugen (?)
            startActivity(intentMP);
        });

        // -------- SETTINGS --------
        btnSettings.setOnClickListener(v -> {
            Log.d("Debug", "On Click - From Startscreen to Settingsscreen");

            Intent intentSettings = new Intent();
            intentSettings.setClass(this, SettingsScreen.class);
            startActivity(intentSettings);
        });

        // -------- HIGHSCORE --------
        btnScore.setOnClickListener(v -> {
            Log.d("Debug", "On Click - Highscore");

            Intent intentScore = new Intent();
            intentScore.setClass(this, SettingsScreen.class);       //TODO: Klasse Highscore + die entsprechende .xml Datei erzeugen
            startActivity(intentScore);
        });
    }
}