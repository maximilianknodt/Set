package com.example.set.view;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.set.R;
import com.google.android.material.textfield.TextInputEditText;

/**
 * Players screen class
 * Implements the players screen where the user can adjust the players for the multiplayer
 *
 * @author Linus Kurze
 */
public class MultiPlayerPlayersScreen extends AppCompatActivity {

    private static final int MIN_PLAYER_COUNT = 2;
    private static final int MAX_PLAYER_COUNT = 8;

    private Button selected;

    private LinearLayout playersLayout;

    private LinearLayout.LayoutParams buttonLayoutParams;

    private TextInputEditText playerNameInput;

    private ScrollView scrollView;

    private int lastAddedNumber = 0;

    /**
     * on Create method
     * Called when the screen is created. Writes the information to the screen. Implements button functions.
     *
     * @author Linus Kurze
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multi_player_players_screen);

        Button removePlayer = findViewById(R.id.button_Players_Screen_remove_player);
        Button addPlayer = findViewById(R.id.button_Players_Screen_add_player);
        Button startGame = findViewById(R.id.button_Players_Screen_start_game);

        scrollView = findViewById(R.id.players_scroll_view);

        playersLayout = findViewById(R.id.players_linear_layout);
        buttonLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        playerNameInput = findViewById(R.id.text_input_player_name);

        boolean shortGame = false;
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            shortGame = bundle.getBoolean("shortGame");
        }

        for (int i = 0; i < MIN_PLAYER_COUNT; i++) {
            addButton();
        }

        setSelectedButton(playersLayout.getChildAt(0));


        playerNameInput.addTextChangedListener(new TextWatcher() {
                                                   @Override
                                                   public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                                   }

                                                   @Override
                                                   public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                                       selected.setText(charSequence);
                                                   }

                                                   @Override
                                                   public void afterTextChanged(Editable editable) {
                                                   }
                                               }
        );

        removePlayer.setOnClickListener(view -> {
            if (playersLayout.getChildCount() > MIN_PLAYER_COUNT) {
                removeButton();
                selectLast();
                Toast.makeText(this.getBaseContext(), R.string.message_deleted_player, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this.getBaseContext(), R.string.message_can_not_delete_player, Toast.LENGTH_SHORT).show();
            }
        });

        addPlayer.setOnClickListener(view -> {
            if (playersLayout.getChildCount() < MAX_PLAYER_COUNT) {
                addButton();
                selectLast();
                Toast.makeText(this.getBaseContext(), R.string.message_added_player, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this.getBaseContext(), R.string.message_can_not_add_player, Toast.LENGTH_SHORT).show();
            }
        });

        boolean finalShortGame = shortGame;
        startGame.setOnClickListener(view -> {
            String[] names = new String[playersLayout.getChildCount()];
            for (int i = 0; i < playersLayout.getChildCount(); i++) {
                names[i] = String.valueOf(((Button) (playersLayout.getChildAt(i))).getText());
            }
            Intent intentMP = new Intent();
            intentMP.setClass(this, MultiPlayerGameScreen.class);
            intentMP.putExtra("newGame", true);
            intentMP.putExtra("shortGame", finalShortGame);
            intentMP.putExtra("namesList", names);
            startActivity(intentMP);
            finish();
        });
    }

    /**
     * Sets currently selected button
     *
     * @param view the button to set as selected
     */
    private void setSelectedButton(View view) {
        if (selected != null) {
            selected.getBackground().clearColorFilter();
        }
        selected = (Button) view;
        selected.getBackground().setColorFilter(getColor(R.color.button_selected), PorterDuff.Mode.MULTIPLY);
        updateTextInput();
    }

    /**
     * updates the text input field
     */
    private void updateTextInput() {
        playerNameInput.setText(selected.getText());
    }

    /**
     * selects the last button and scrolls to it
     */
    private void selectLast() {
        setSelectedButton(playersLayout.getChildAt(playersLayout.getChildCount() - 1));
        updateTextInput();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            scrollView.scrollToDescendant(selected);
        }
    }

    /**
     * removes a button
     */
    private void removeButton() {
        playersLayout.removeView(selected);
        lastAddedNumber--;
    }

    /**
     * adds a button
     */
    private void addButton() {
        Button button = new Button(this);
        String text = getString(R.string.player) + " " + ++lastAddedNumber;
        button.setAllCaps(false);
        button.setText(text);
        button.setOnClickListener(this::setSelectedButton);
        playersLayout.addView(button, buttonLayoutParams);
    }
}
