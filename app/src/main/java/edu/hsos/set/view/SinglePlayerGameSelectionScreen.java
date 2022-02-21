package edu.hsos.set.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.set.R;
import edu.hsos.set.controller.AppController;
import edu.hsos.set.controller.SettingsFragment;

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
     * @author Linus Kurze
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_player_game_selection_screen);

        // Searching in Resources for the IDs
        Button btnNormalG = this.findViewById(R.id.button_Singleplayer_Screen_NG);
        Button btnShortG = this.findViewById(R.id.button_Singleplayer_Screen_SG);
        Button btnResumeG = this.findViewById(R.id.button_Singleplayer_Screen_Resume);

        // -------- NORMAL GAME --------
        btnNormalG.setOnClickListener(v -> {
            Intent intentSP = new Intent();
            intentSP.setClass(this, SinglePlayerGameScreen.class);
            intentSP.putExtra("newGame", true);
            intentSP.putExtra("shortGame", false);
            startActivity(intentSP);
        });


        // -------- SHORT GAME --------
        btnShortG.setOnClickListener(v -> {
            Intent intentSP = new Intent();
            intentSP.setClass(this, SinglePlayerGameScreen.class);
            intentSP.putExtra("newGame", true);
            intentSP.putExtra("shortGame", true);
            startActivity(intentSP);
        });

        // -------- RESUME GAME --------
        btnResumeG.setOnClickListener(v -> {
            if (AppController.getAppController().singlePlayerGameExists()) {
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