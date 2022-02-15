package com.example.set.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.set.R;

/**
 * Pause screen class
 * Implements the pause screen.
 *
 * @author Maximilian Knodt
 * @author Linus Kurze
 */
public class PauseScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pause_screen);

        Button btnSettings = findViewById(R.id.button_Break_Screen_Settings);
        Button btnMenu = findViewById(R.id.button_Break_Screen_Menu);
        ImageButton ibtnPlay = findViewById(R.id.imageButton_Break_Screen_Play);

        TextView gameType = findViewById(R.id.textView_Break_Screen_Game_Type_Body);
        TextView time = findViewById(R.id.textView_Break_Screen_Time_Body);
        TextView startTime = findViewById(R.id.textView_Break_Screen_Start_Body);
        TextView points = findViewById(R.id.textView_Break_Screen_Points_Body);
        TextView cardsLeft = findViewById(R.id.textView_Break_Screen_Cards_Left_Body);
        TextView rules = findViewById(R.id.textView_Break_Screen_Rules_Body);


        btnSettings.setOnClickListener(v -> {
            Log.d("Debug", "On Click - From PauseScreen to SettingsScreen");

            Intent intentSettings = new Intent();
            intentSettings.setClass(this, SettingsScreen.class);
            startActivity(intentSettings);
        });

        btnMenu.setOnClickListener(v -> {
            Log.d("Debug", "On Click - From PauseScreen to StartScreen");

            Intent intentSettings = new Intent();
            intentSettings.setClass(this, HomeScreen.class);
            startActivity(intentSettings);
            finish();
        });

        ibtnPlay.setOnClickListener(v -> {
            Log.d("Debug", "On Click - From PauseScreen back to SinglePlayerGameScreen");

            onBackPressed();
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            gameType.setText(bundle.getString("gameType"));
            time.setText(bundle.getString("duration"));
            startTime.setText(bundle.getString("startTime"));
            points.setText(""+bundle.getInt("points"));
            cardsLeft.setText(""+bundle.getInt("cardsLeft"));
            rules.setText(bundle.getString("rules"));
        }
    }
}
