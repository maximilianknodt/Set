package edu.hsos.set.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.example.set.R;

import edu.hsos.set.controller.SettingsFragment;

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

        SettingsFragment.setSharedPreferences(PreferenceManager.getDefaultSharedPreferences(this));
        SettingsScreen screen = new SettingsScreen();
        screen.changeMode(SettingsFragment.getDarkMode());

        // Searching in Resources for the IDs
        Button btnSP = this.findViewById(R.id.button_Start_Screen_SPG);
        Button btnMP = this.findViewById(R.id.button_Start_Screen_MPG);
        Button btnSettings = this.findViewById(R.id.button_Start_Screen_Settings);

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
    }

    /**
     * Method called when back button is pressed.
     *
     * the author is responsible for this method.
     *
     * @author Linus Kurze
     */
    @Override
    public void onBackPressed() {
        if (lastPressedBackTime + 2000 > System.currentTimeMillis()) {
            finishAffinity();
        } else {
            Toast.makeText(getBaseContext(), R.string.press_back_again, Toast.LENGTH_SHORT).show();
        }
        lastPressedBackTime = System.currentTimeMillis();
    }

    /**
     * Wraps the new generate context into the original
     * With a new start of the application the language will be changed
     *
     * @author Maximilian Knodt
     *
     * @param newBase original Context
     */
    @Override
    protected void attachBaseContext(Context newBase){
        SettingsFragment.setSharedPreferences(PreferenceManager.getDefaultSharedPreferences(newBase));
        super.attachBaseContext(LocalManager.setLocale(newBase, SettingsFragment.getLanguage()));
    }
}