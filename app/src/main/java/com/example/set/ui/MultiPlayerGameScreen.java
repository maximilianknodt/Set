package com.example.set.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.set.R;
import com.example.set.controller.AppController;
import com.example.set.controller.AppControllerHolder;
import com.example.set.controller.MultiPlayerGameController;

/**
 * The multi player game screen class
 * A class implementing the ui for the multi player game screen.
 * <p>
 * The author is responsible for this class.
 *
 * @author Linus Kurze
 * @version 1.0
 */
public class MultiPlayerGameScreen extends GameScreen {
    /**
     * layout with buttons on the screen
     */
    private LinearLayout buttonsLayout;

    /**
     * main layout with cards or buttons on the screen
     */
    private LinearLayout mainLayout;

    /**
     * layout with players in a scrollView on the screen
     */
    private LinearLayout playersLayout;

    /**
     * scrollView with players on the screen
     */
    private ScrollView playersScrollView;

    /**
     * layout parameters for buttons
     */
    private LinearLayout.LayoutParams buttonLayoutParams;

    /**
     * the set button
     */
    private Button set;

    /**
     * the add cards button
     */
    private Button addCards;

    /**
     * the cancel button for the player selection
     */
    private Button cancelPlayerSelection;

    /**
     * the cancel button for the set selection
     */
    private Button cancelSetSelection;

    /**
     * the text view for the names list for the points
     */
    private TextView pointsNameList;

    /**
     * the text view for points list
     */
    private TextView pointsList;

    /**
     * the text view for points title
     */
    private TextView pointsTitle;

    /**
     * onCreate method of the gameScreen
     * Is called when a game starts. Initializes variables and sets onClickListeners.
     *
     * @param savedInstanceState
     *
     * @author Linus Kurze
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] players = {getString(R.string.player) + " 1", getString(R.string.player) + " 2"};

        AppController appController = AppControllerHolder.getAppController();
        boolean newGame = true;
        boolean shortGame = false;
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            newGame = bundle.getBoolean("newGame");
            if(newGame) {
                shortGame = bundle.getBoolean("shortGame");
                players = bundle.getStringArray("namesList");
            }
        }

        pointsNameList = findViewById(R.id.points_list_text);
        pointsList = findViewById(R.id.points_list_content);
        pointsTitle = findViewById(R.id.points_text);

        buttonsLayout = findViewById(R.id.game_buttons_layout);
        mainLayout = findViewById(R.id.main_game_screen);

        buttonLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        set = new Button(this);
        set.setText(R.string.set);
        set.setOnClickListener(view -> {
            ((MultiPlayerGameController)gameController).setPressed();
        });

        addCards = new Button(this);
        addCards.setText(R.string.add_cards);
        addCards.setOnClickListener(view -> {
            if(((MultiPlayerGameController)gameController).addCards()) {
                Toast.makeText(this.getBaseContext(), R.string.message_add_cards_successful, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this.getBaseContext(), R.string.message_add_cards_not_possible, Toast.LENGTH_SHORT).show();
            }
        });

        cancelPlayerSelection = new Button(this);
        cancelPlayerSelection.setText(R.string.cancel);
        cancelPlayerSelection.setOnClickListener(view -> {
            ((MultiPlayerGameController)gameController).playerSelectionCanceled();
        });

        cancelSetSelection = new Button(this);
        cancelSetSelection.setText(R.string.cancel);
        cancelSetSelection.setOnClickListener(view -> {
            ((MultiPlayerGameController)gameController).cancelSetSelection();
        });

        playersLayout = new LinearLayout(this);
        playersLayout.setOrientation(LinearLayout.VERTICAL);
        playersLayout.setGravity(Gravity.CENTER);
        playersScrollView = new ScrollView(this);
        playersScrollView.setLayoutParams(new LinearLayout.LayoutParams(ScrollView.LayoutParams.FILL_PARENT, ScrollView.LayoutParams.FILL_PARENT));
        playersScrollView.addView(playersLayout);

        if(newGame) {
            appController.createNewMultiPlayerGame(this, players, shortGame);
        }
        gameController = appController.getMultiPlayerGameController();
        if(newGame) {
            gameController.startGame();
        } else {
            gameController.resume(this);
        }

        String[] names = ((MultiPlayerGameController)gameController).getPlayerNames();
        for(int i = 0; i < names.length; i++) {
            Button button = new Button(this);
            button.setText(names[i]);
            playersLayout.addView(button, buttonLayoutParams);
            final int finalI = i;
            button.setOnClickListener(view -> {
                if(((MultiPlayerGameController)gameController).selectPlayer(finalI)) {
                    Toast.makeText(this.getBaseContext(), R.string.message_player_is_selected, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this.getBaseContext(), R.string.message_player_is_suspended, Toast.LENGTH_SHORT).show();
                }
            });
        }

        writeDefaultView();
    }

    /**
     * Is called when a card is clicked. Selects a card if card selection is active.
     *
     * @param x x position of the card
     * @param y y position of the card
     * @param view view of the card
     *
     * @author Linus Kurze
     */
    @Override
    public void onCardClicked(int x, int y, View view) {
        if(((MultiPlayerGameController)gameController).isSetSelectionActive()) {
            cardSelected(x, y, view);
        }
    }

    /**
     * Writes the player selection screen.
     *
     * @author Linus Kurze
     */
    public void writePlayerSelection() {
        clearViews();
        mainLayout.addView(playersScrollView);
        buttonsLayout.addView(cancelPlayerSelection, buttonLayoutParams);
    }

    /**
     * Writes the default screen.
     *
     * @author Linus Kurze
     */
    public void writeDefaultView() {
        clearViews();
        mainLayout.addView(rvList);
        pointsTitle.setText(R.string.points);
        buttonsLayout.addView(addCards, buttonLayoutParams);
        buttonsLayout.addView(set, buttonLayoutParams);
        writePointsListNames();
        ((MultiPlayerGameController)gameController).writeScore();
    }

    /**
     * Writes the set selection screen.
     *
     * @author Linus Kurze
     */
    public void writeSetSelection(String playerName) {
        clearViews();
        mainLayout.addView(rvList);
        pointsTitle.setText(R.string.selection_time);
        buttonsLayout.addView(cancelSetSelection, buttonLayoutParams);
        buttonsLayout.addView(takeSet, buttonLayoutParams);
        pointsNameList.setText(R.string.selected_player);
        pointsList.setText(playerName);
    }

    /**
     * Writes the default screen. And informs that the set selection is over.
     *
     * @author Linus Kurze
     */
    public void writeSetSelectionOver() {
        final Context baseContext = this.getBaseContext();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(baseContext, R.string.message_set_selection_ended, Toast.LENGTH_SHORT).show();
                selectedCards.clear();
                ((MultiPlayerGameController)gameController).writeCards();
                writeDefaultView();
            }
        });
    }

    /**
     * Writes the set selection time.
     *
     * @author Linus Kurze
     */
    public void writeSetSelectionTime(long time){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(time < 0) {
                    points.setText("");
                } else {
                    points.setText("" + time);
                }
            }
        });

    }

    /**
     * Method called when the pause screen should be opened
     *
     * @param shortGame if the game is a short game
     * @param points the points the player received
     * @param cardsLeft the cards left on the stack
     * @param duration the time the game took
     * @param startTime the time the game started
     * @param deduction if the deduction rule was enabled
     *
     * @author Linus Kurze
     */
    public void openPause(boolean shortGame, int[] points, int cardsLeft, long duration, long startTime, boolean deduction, boolean suspension) {
        Intent intentPS = new Intent();
        intentPS.setClass(this, PauseScreen.class);
        intentPS.putExtra("gameMode", getResources().getString(R.string.multi_player));
        intentPS.putExtra("gameType", gameTypeToString(shortGame));
        intentPS.putExtra("pointsList", pointsToString(points));
        intentPS.putExtra("namesList", namesToString(((MultiPlayerGameController)gameController).getPlayerNames()));
        intentPS.putExtra("cardsLeft", cardsLeft);
        intentPS.putExtra("duration", timeToString(duration));
        intentPS.putExtra("startTime", timestampToString(startTime));
        intentPS.putExtra("rules", rulesToString(deduction, suspension));
        startActivity(intentPS);
    }

    /**
     * Method called when the game is over
     *
     * @param shortGame if the game is a short game
     * @param points the points of the players
     * @param duration the time the game took
     * @param startTime the time the game started
     * @param deduction if the deduction rule was enabled
     * @param suspension if the suspension rule was enabled
     * @param winners the names of the winners
     *
     * @author Linus Kurze
     */
    public void gameOver(boolean shortGame, int[] points, long duration, long startTime, boolean deduction, boolean suspension, String[] winners) {
        Intent intentES = new Intent();
        intentES.setClass(this, GameEndScreen.class);
        intentES.putExtra("gameMode", getResources().getString(R.string.multi_player));
        intentES.putExtra("gameType", gameTypeToString(shortGame));
        intentES.putExtra("pointsList", pointsToString(points));
        intentES.putExtra("namesList", namesToString(((MultiPlayerGameController)gameController).getPlayerNames()));
        intentES.putExtra("duration", timeToString(duration));
        intentES.putExtra("startTime", timestampToString(startTime));
        intentES.putExtra("rules", rulesToString(deduction, suspension));
        intentES.putExtra("winners", winnersToString(winners));
        intentES.putExtra("winnersPlural", winners.length > 1);
        startActivity(intentES);
        finish();
    }

    /**
     * Method called when the ui should write the points.
     *
     * @param points the points of the players
     *
     * @author Linus Kurze
     */
    public void writePoints(int[] points){
        pointsList.setText(pointsToString(points));
    }

    /**
     * Method called when the ui should write the players names.
     *
     * @author Linus Kurze
     */
    private void writePointsListNames() {
        String[] names = ((MultiPlayerGameController)gameController).getPlayerNames();
        pointsNameList.setText(namesToString(names));
    }

    /**
     * Converts names to a string with paragraphs.
     *
     * @param names the names to convert
     * @return the names as string with paragraphs
     *
     * @author Linus Kurze
     */
    private String namesToString(String[] names) {
        StringBuilder list = new StringBuilder();
        for(int i = 0; i < names.length; i++) {
            list.append(names[i]).append(": ");
            if (i < names.length - 1) {
                list.append("\n");
            }
        }
        return list.toString();
    }

    /**
     * Converts points to a string with paragraphs.
     *
     * @param points the points to convert
     * @return the points as string with paragraphs
     *
     * @author Linus Kurze
     */
    private String pointsToString(int[] points) {
        StringBuilder list = new StringBuilder();
        for(int i = 0;i < points.length; i++) {
            list.append(points[i]);
            if (i < points.length - 1) {
                list.append("\n");
            }
        }
        return list.toString();
    }

    /**
     * Converts list of winners to a string.
     *
     * @param winners list of winners
     * @return list of winners as a string
     *
     * @author Linus Kurze
     */
    private String winnersToString(String[] winners) {
        StringBuilder list = new StringBuilder();
        for(int i = 0;i < winners.length; i++) {
            list.append(winners[i]);
            if (i < winners.length - 2) {
                list.append(", ");
            } else if (i == winners.length - 2) {
                list.append(" " + getString(R.string.and) + " ");
            }
        }
        return list.toString();
    }

    /**
     * Clears the views in the button and main layout
     *
     * @author Linus Kurze
     */
    private void clearViews() {
        buttonsLayout.removeAllViews();
        mainLayout.removeAllViews();
    }

    /**
     * Converts rules in seconds as long to a string.
     *
     * @param deduction if the deduction is active
     * @return the rules as readable String
     *
     * @author Linus Kurze
     */
    private String rulesToString(boolean deduction, boolean exposure) {
        String rules = getResources().getString(R.string.deduction) + ": ";
        if (deduction) {
            rules += getResources().getString(R.string.switchOn);
        } else {
            rules += getResources().getString(R.string.switchOff);
        }
        rules += "\n" + getResources().getString(R.string.suspension) + ": ";
        if (exposure) {
            rules += getResources().getString(R.string.switchOn);
        } else {
            rules += getResources().getString(R.string.switchOff);
        }
        return rules;
    }
}
