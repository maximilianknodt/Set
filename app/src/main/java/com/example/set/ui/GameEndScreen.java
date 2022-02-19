package com.example.set.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.set.R;

/**
 * @author Maximilian Knodt
 */
public class GameEndScreen extends AppCompatActivity {
    /**
     * onCreate method of the gameEndScreen, called when the game is finished
     *
     * @param savedInstanceState
     * @author Maximilian Knodt
     * @author Linus Kurze
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_end_screen);

        Button btnNewGame = findViewById(R.id.button_Game_End_Screen_New_Game);
        Button btnMenu = findViewById(R.id.button_Game_End_Screen_Menu);
        TextView tvType = findViewById(R.id.textView_Game_End_Screen_Mode_Body);
        TextView tvMode = findViewById(R.id.textView_Game_End_Screen_Type_Body);
        TextView tvPoints = findViewById(R.id.textView_Game_End_Screen_Points_Body);
        TextView tvPointsList = findViewById(R.id.textView_Game_End_Screen_Points_List_Body);
        TextView tvPlayersList = findViewById(R.id.textView_Game_End_Screen_Points_List_Header);
        TextView tvTime = findViewById(R.id.textView_Game_End_Screen_Time_Body);
        TextView tvStart = findViewById(R.id.textView_Game_End_Screen_Start_Body);
        TextView tvRules = findViewById(R.id.textView_Game_End_Screen_Rules_Body);
        TextView tvCongrats = findViewById(R.id.textView_Game_End_Screen_Headline2);

        Bundle bundle = getIntent().getExtras();
        boolean shortGame = false;
        boolean multiPlayer = false;

        if (bundle != null) {
            String gameModeText = bundle.getString("gameMode");
            String gameTypeText = bundle.getString("gameType");
            if (gameModeText.equals(getString(R.string.multi_player))) {
                multiPlayer = true;
            }
            if (gameTypeText.equals(getString(R.string.short_game))) {
                shortGame = true;
            }
            tvMode.setText(gameModeText);
            tvType.setText(bundle.getString("gameType"));
            tvTime.setText(bundle.getString("duration"));
            tvStart.setText(bundle.getString("startTime"));
            tvRules.setText(bundle.getString("rules"));
            if (gameModeText.equals(getString(R.string.multi_player))) {
                tvPointsList.setText(bundle.getString("pointsList"));
                tvPlayersList.setText(bundle.getString("namesList"));
                String congrats;
                if (bundle.getBoolean("winnersPlural")) {
                    congrats = getString(R.string.congrats_multiplayer_plural);
                } else {
                    congrats = getString(R.string.congrats_multiplayer_singular);
                }
                tvCongrats.setText(congrats + " " + bundle.getString("winners"));
            } else {
                tvPoints.setText("" + bundle.getInt("points"));
            }
        }

        if (shortGame) {
            btnNewGame.setText(R.string.new_short_game);
        } else {
            btnNewGame.setText(R.string.new_normal_game);
        }

        final boolean finalMultiPlayer = multiPlayer;
        final boolean finalShortGame = shortGame;
        btnNewGame.setOnClickListener(v -> {
            Intent intentSP = new Intent();
            if (finalMultiPlayer) {
                intentSP.setClass(this, MultiPlayerGameScreen.class);
            } else {
                intentSP.setClass(this, SinglePlayerGameScreen.class);
            }
            intentSP.putExtra("newGame", true);
            intentSP.putExtra("shortGame", finalShortGame);
            startActivity(intentSP);
        });

        btnMenu.setOnClickListener(v -> {
            Intent intentStart = new Intent();
            intentStart.setClass(this, HomeScreen.class);
            intentStart.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intentStart);
        });
    }
}
