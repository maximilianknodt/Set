package com.example.set.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.set.R;

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
     *
     * @author Maximilian Knodt
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_screen);

        // Searching in Ressources for the IDs
        Button btnSP = this.findViewById(R.id.button_Start_Screen_SPG);
        Button btnMP = this.findViewById(R.id.button_Start_Screen_MPG);
        Button btnSettings = this.findViewById(R.id.button_Start_Screen_Settings);
        Button btnScore = this.findViewById(R.id.button_Start_Screen_Highscore);

        // -------- SINGLEPLAYER --------
        btnSP.setOnClickListener(v -> {
            Intent intentSP = new Intent();
            intentSP.setClass(this, SinglePlayerGameSelectionScreen.class);
            startActivity(intentSP);
        });


        // -------- MULTIPLAYER --------
        btnMP.setOnClickListener(v -> {
            Intent intentMP = new Intent();
            intentMP.setClass(this, MultiPlayerGameSelectionScreen.class);
            startActivity(intentMP);
        });

        // -------- SETTINGS --------
        btnSettings.setOnClickListener(v -> {
            Intent intentSettings = new Intent();
            intentSettings.setClass(this, SettingsScreen.class);
            startActivity(intentSettings);
        });

        // -------- HIGHSCORE --------
        btnScore.setOnClickListener(v -> {
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