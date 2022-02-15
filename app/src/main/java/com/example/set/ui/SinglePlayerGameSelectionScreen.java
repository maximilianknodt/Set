package com.example.set.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.set.R;
import com.example.set.controller.AppControllerHolder;

/**
 * The single player screen class
 * Adds functionality to the buttons on the screen.
 * <p>
 * The author is responsible for this class.
 *
 * @author Linus Kurze
 * @version 1.0
 */
public class SinglePlayerGameSelectionScreen extends AppCompatActivity {

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
        setContentView(R.layout.single_player_game_selection_screen);

        // Searching in Resources for the IDs
        Button btnNormalG = this.findViewById(R.id.button_Start_NG);
        Button btnShortG = this.findViewById(R.id.button_Start_SG);
        Button btnResumeG = this.findViewById(R.id.button_Resume);

        // -------- NORMAL GAME --------
        btnNormalG.setOnClickListener(v -> {
            Log.d("Debug", "On Click - Single Player Normal Game starts");

            Intent intentSP = new Intent();
            intentSP.setClass(this, SinglePlayerGameScreen.class);
            intentSP.putExtra("newGame", true);
            intentSP.putExtra("shortGame", false);
            startActivity(intentSP);
        });


        // -------- SHORT GAME --------
        btnShortG.setOnClickListener(v -> {
            Log.d("Debug", "On Click - Single Player Short Game starts");

            Intent intentSP = new Intent();
            intentSP.setClass(this, SinglePlayerGameScreen.class);
            intentSP.putExtra("newGame", true);
            intentSP.putExtra("shortGame", true);
            startActivity(intentSP);
        });

        // -------- RESUME GAME --------
        btnResumeG.setOnClickListener(v -> {
            Log.d("Debug", "On Click - Single Player Game Resumes");

            if(AppControllerHolder.getAppController().singlePlayerGameExists()) {
                Intent intentSP = new Intent();
                intentSP.setClass(this, SinglePlayerGameScreen.class);
                intentSP.putExtra("newGame", false);
                startActivity(intentSP);
            } else {
                Toast.makeText(this.getBaseContext(), R.string.no_game_to_resume, Toast.LENGTH_SHORT).show();
            }
        });
    }
}