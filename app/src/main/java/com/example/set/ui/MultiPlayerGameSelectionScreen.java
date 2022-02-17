package com.example.set.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
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
public class MultiPlayerGameSelectionScreen extends AppCompatActivity {

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

        // Change Text for Multiplayer (reusing the single_player_game_selection_screen)
        TextView headline = this.findViewById(R.id.textview_Singleplayer_Headline);
        headline.setText(R.string.multi_player);

        // Searching in Resources for the IDs
        Button btnNormalG = this.findViewById(R.id.button_Start_Screen_SPG);
        Button btnShortG = this.findViewById(R.id.button_Start_Screen_MPG);
        Button btnResumeG = this.findViewById(R.id.button_Start_Screen_Settings);

        // -------- NORMAL GAME --------
        btnNormalG.setOnClickListener(v -> {
            Log.d("Debug", "On Click - Multi Player Normal Game starts");

            Intent intentMP = new Intent();
            intentMP.setClass(this, SinglePlayerGameScreen.class);
            intentMP.putExtra("newGame", true);
            intentMP.putExtra("shortGame", false);
            startActivity(intentMP);
        });


        // -------- SHORT GAME --------
        btnShortG.setOnClickListener(v -> {
            Log.d("Debug", "On Click - Multi Player Short Game starts");

            Intent intentMP = new Intent();
            intentMP.setClass(this, SinglePlayerGameScreen.class);
            intentMP.putExtra("newGame", true);
            intentMP.putExtra("shortGame", true);
            startActivity(intentMP);
        });

        // -------- RESUME GAME --------
        btnResumeG.setOnClickListener(v -> {
            Log.d("Debug", "On Click - Multi Player Game Resumes");

            if(AppControllerHolder.getAppController().multiPlayerGameExists()) {
                Intent intentMP = new Intent();
                intentMP.setClass(this, SinglePlayerGameScreen.class);
                intentMP.putExtra("newGame", false);
                startActivity(intentMP);
            } else {
                Toast.makeText(this.getBaseContext(), R.string.no_game_to_resume, Toast.LENGTH_SHORT).show();
            }
        });
    }
}