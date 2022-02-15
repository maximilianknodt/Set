package com.example.set.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.set.R;
import com.example.set.controller.AppController;

import java.io.Serializable;

/**
 * Home screen class
 * Implements the main menu.
 *
 * @author Maximilian Knodt
 * @author Linus Kurze
 */
public class HomeScreen extends AppCompatActivity {
    /**
     * the last time back was pressed
     */
    private long lastPressedBackTime;

    /**
     * the method called when the screen is created
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_screen);

        // Searching in Ressources for the IDs
        Button btnSP = this.findViewById(R.id.button_Start_NG);
        Button btnMP = this.findViewById(R.id.button_Start_SG);
        Button btnSettings = this.findViewById(R.id.button_Resume);
        Button btnScore = this.findViewById(R.id.button_Start_Highscore);

        // -------- SINGLEPLAYER --------
        btnSP.setOnClickListener(v -> {
            Log.d("Debug", "On Click - From HomeScreen to SinglePlayerScreen");

            Intent intentSP = new Intent();
            intentSP.setClass(this, SinglePlayerScreen.class);
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

    /**
     * Method called when back button is pressed.
     *
     * @author Linus Kurze
     */
    @Override
    public void onBackPressed() {
        if (lastPressedBackTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
        } else {
            Toast.makeText(getBaseContext(), R.string.press_back_again, Toast.LENGTH_SHORT).show();
        }
        lastPressedBackTime = System.currentTimeMillis();
    }
}