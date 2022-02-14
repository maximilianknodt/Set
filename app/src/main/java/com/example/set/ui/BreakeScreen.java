package com.example.set.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.set.R;

/**
 * @author Maximilian Knodt
 */
public class BreakeScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.breake_screen);

        Button btnSettings = findViewById(R.id.button_Break_Screen_Settings);
        Button btnMenu = findViewById(R.id.button_Break_Screen_Menu);
        ImageButton ibtnPlay = findViewById(R.id.imageButton_Break_Screen_Play);

        btnSettings.setOnClickListener(v -> {
            Log.d("Debug", "On Click - From PauseScreen to Settingsscreen");

            Intent intentSettings = new Intent();
            intentSettings.setClass(this, SettingsScreen.class);
            startActivity(intentSettings);
        });

        btnMenu.setOnClickListener(v -> {
            Log.d("Debug", "On Click - From PauseScreen to Startscreen");

            Intent intentSettings = new Intent();
            intentSettings.setClass(this, StartScreen.class);
            startActivity(intentSettings);
        });

        ibtnPlay.setOnClickListener(v -> {
            Log.d("Debug", "On Click - From PauseScreen back to Gamescreen");

            Intent intentSettings = new Intent();
            intentSettings.setClass(this, GameScreen.class);
            startActivity(intentSettings);
        });
    }
}
