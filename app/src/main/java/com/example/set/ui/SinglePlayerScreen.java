package com.example.set.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.set.R;

/**
 * The single player screen class
 * Adds functionality to the buttons on the screen.
 * <p>
 * The author is responsible for this class.
 *
 * @author Linus Kurze
 * @version 1.0
 */
public class SinglePlayerScreen extends AppCompatActivity {

    /**
     * Method called when screen is created. Handles clicks on the different buttons.
     *
     * @param savedInstanceState Bundle with saved data
     *
     * @author Linus Kurze
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singleplayer_screen);

        // Searching in Ressources for the IDs
        Button btnNormalG = this.findViewById(R.id.button_Start_NG);
        Button btnShortG = this.findViewById(R.id.button_Start_SG);
        Button btnResumeG = this.findViewById(R.id.button_Resume);

        // -------- NORMAL GAME --------
        btnNormalG.setOnClickListener(v -> {
            Log.d("Debug", "On Click - Single Player Normal Game starts");

            Intent intentSP = new Intent();
            intentSP.setClass(this, GameScreen.class);
            intentSP.putExtra("shortGame", false);
            startActivity(intentSP);
        });


        // -------- SHORT GAME --------
        btnShortG.setOnClickListener(v -> {
            Log.d("Debug", "On Click - Single Player Short Game starts");

            Intent intentSP = new Intent();
            intentSP.setClass(this, GameScreen.class);
            intentSP.putExtra("shortGame", true);
            startActivity(intentSP);
        });

        // -------- RESUME GAME --------
        btnResumeG.setOnClickListener(v -> {
            Log.d("Debug", "On Click - Single Player Game Resumes");
            //TODO: Funktionalität einbauen
        });
    }
}