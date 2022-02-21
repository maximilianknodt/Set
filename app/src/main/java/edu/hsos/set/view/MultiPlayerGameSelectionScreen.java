package edu.hsos.set.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
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
public class MultiPlayerGameSelectionScreen extends AppCompatActivity {

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

        TextView headline = this.findViewById(R.id.textview_Singleplayer_Headline);
        headline.setText(R.string.multi_player);

        Button btnNormalG = this.findViewById(R.id.button_Singleplayer_Screen_NG);
        Button btnShortG = this.findViewById(R.id.button_Singleplayer_Screen_SG);
        Button btnResumeG = this.findViewById(R.id.button_Singleplayer_Screen_Resume);

        btnNormalG.setOnClickListener(v -> {
            Intent intentMP = new Intent();
            intentMP.setClass(this, MultiPlayerPlayersScreen.class);
            intentMP.putExtra("shortGame", false);
            startActivity(intentMP);
        });


        btnShortG.setOnClickListener(v -> {
            Intent intentMP = new Intent();
            intentMP.setClass(this, MultiPlayerPlayersScreen.class);
            intentMP.putExtra("shortGame", true);
            startActivity(intentMP);
        });

        btnResumeG.setOnClickListener(v -> {
            if (AppController.getAppController().multiPlayerGameExists()) {
                Intent intentMP = new Intent();
                intentMP.setClass(this, MultiPlayerGameScreen.class);
                intentMP.putExtra("newGame", false);
                startActivity(intentMP);
            } else {
                Toast.makeText(this.getBaseContext(), R.string.no_game_to_resume, Toast.LENGTH_SHORT).show();
            }
        });
    }
}