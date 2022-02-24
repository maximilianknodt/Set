package edu.hsos.set.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.set.R;

/**
 * @author Maximilian Knodt
 * @author Linus Kurze
 */
public class GameEndScreen extends AppCompatActivity {
    /**
     * the game mode text
     */
    private String gameModeText;

    /**
     * the game type text
     */
    private String gameTypeText;

    /**
     * the game duration text
     */
    private String durationText;

    /**
     * the game start time text
     */
    private String startTimeText;

    /**
     * the rules text
     */
    private String rulesText;

    /**
     * the multi player game points list text
     */
    private String pointsListText;

    /**
     * the multi player game names list text
     */
    private String namesListText;

    /**
     * the multi player game winenr text
     */
    private String multiPlayerWinnerText;

    /**
     * the single player game points text
     */
    private String singlePlayerPoints;

    /**
     * if the game was a short game
     */
    private boolean shortGame;

    /**
     * if the game was a multi player game
     */
    private boolean multiPlayer;

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
        ImageButton btnShare = findViewById(R.id.imageButton_End_Screen_Share);
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

        if (bundle != null) {
            gameModeText = bundle.getString("gameMode");
            gameTypeText = bundle.getString("gameType");
            durationText = bundle.getString("duration");
            startTimeText = bundle.getString("startTime");
            rulesText = bundle.getString("rules");

            if (gameModeText.equals(getString(R.string.multi_player))) {
                multiPlayer = true;
            }
            if (gameTypeText.equals(getString(R.string.short_game))) {
                shortGame = true;
            }

            if (multiPlayer) {
                pointsListText = bundle.getString("pointsList");
                namesListText = bundle.getString("namesList");
                if (bundle.getBoolean("winnersPlural")) {
                    multiPlayerWinnerText = getString(R.string.congrats_multiplayer_plural);
                } else {
                    multiPlayerWinnerText = getString(R.string.congrats_multiplayer_singular);
                }
                multiPlayerWinnerText += " " + bundle.getString("winners");
            } else {
                singlePlayerPoints = "" + bundle.getInt("points");
            }
        }


        tvMode.setText(gameModeText);
        tvType.setText(gameTypeText);
        tvTime.setText(durationText);
        tvStart.setText(startTimeText);
        tvRules.setText(rulesText);
        if(multiPlayer) {
            tvPointsList.setText(pointsListText);
            tvPlayersList.setText(namesListText);
            tvCongrats.setText(multiPlayerWinnerText);
        } else {
            tvPoints.setText(singlePlayerPoints);
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
            finish();
        });

        btnMenu.setOnClickListener(v -> {
            Intent intentStart = new Intent();
            intentStart.setClass(this, HomeScreen.class);
            intentStart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intentStart);
            finish();
        });

        btnShare.setOnClickListener(v -> {
            // Source: https://developer.android.com/training/sharing/send
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, getShareText());
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
        });
    }

    /**
     * Generates the text for sharing
     * @return the text for sharing
     * @author Linus Kurze
     */
    private String getShareText() {
        String shareText = getString(R.string.shared_start_sentence_begin) + " " + gameModeText + " " + gameTypeText + " " + getString(R.string.shared_start_sentence_end) + "\n";
        shareText += getString(R.string.duration) + " " + durationText + "\n";
        if(multiPlayer) {
            shareText += multiPlayerWinnerText +"\n";
            shareText += getString(R.string.points) + "\n" + mergeStringLists(namesListText, pointsListText);
        } else {
            shareText += getString(R.string.points) + " " + singlePlayerPoints + "\n";
        }
        shareText += getString(R.string.game_start) + " " + startTimeText + "\n";
        shareText += getString(R.string.rules) + "\n" + rulesText;
        return shareText;
    }

    /**
     * Combines 2 lists as string to one string
     * @param list1 list 1 as string
     * @param list2 list 2 as string
     * @return lists combined
     * @author Linus Kurze
     */
    private String mergeStringLists(String list1, String list2) {
        StringBuilder result = new StringBuilder();
        String[] list1Array = list1.split("\\n");
        String[] list2Array = list2.split("\\n");
        if(list1Array.length != list2Array.length) {
            return result.toString();
        }

        for(int i = 0; i < list1Array.length; i++) {
            result.append(list1Array[i]).append(" ").append(list2Array[i]).append("\n");
        }

        return result.toString();
    }
}
