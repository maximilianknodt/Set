package com.example.set;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Class for the Settings Screen
 * It will handle the Interaction with every Element within itself
 *
 * TODO: Creating the Adapter for the Spinner Element, handle the EditText Element for the Timer
 *
 * @author Maximilian Knodt
 */
public class SettingsScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_screen);

        Switch switchSPD = this.findViewById(R.id.switch_Settings_SP_Deduction);
        Switch switchMPD = this.findViewById(R.id.switch_Settings_MP_Deduction);
        Switch switchSuspenden = this.findViewById(R.id.switch_Settings_Suspended);
        Switch switchDarkmode = this.findViewById(R.id.switch_Settings_Darkmode);

        Spinner spinnerLanguage = this.findViewById(R.id.spinner_Settings_Language);

        EditText editTextTimer = this.findViewById(R.id.editText_Settings_Timer);

        // -------- SWITCH CONTROL --------
        switchSPD.setOnCheckedChangeListener((cb, b) -> {
            if(b){
                switchSPD.setText(R.string.switchOn);
                switchSPD.setTypeface(switchSPD.getTypeface(), Typeface.BOLD);
            }
            else {
                switchSPD.setText(R.string.switchOff);
                // Typeface ist null so the active Typeface will not be kept -> Style: bold can be removed
                switchSPD.setTypeface(null, Typeface.NORMAL);
            }
        });

        switchMPD.setOnCheckedChangeListener((cb, b) -> {
            if(b){
                switchMPD.setText(R.string.switchOn);
                switchMPD.setTypeface(switchMPD.getTypeface(), Typeface.BOLD);
            }
            else{
                switchMPD.setText(R.string.switchOff);
                switchMPD.setTypeface(null, Typeface.NORMAL);
            }
        });

        switchSuspenden.setOnCheckedChangeListener((cb, b) -> {
            if(b){
                switchSuspenden.setText(R.string.switchOn);
                switchSuspenden.setTypeface(switchSuspenden.getTypeface(), Typeface.BOLD);
            }
            else{
                switchSuspenden.setText(R.string.switchOff);
                switchSuspenden.setTypeface(null, Typeface.NORMAL);
            }
        });

        switchDarkmode.setOnCheckedChangeListener((cb, b) -> {
            if(b){
                switchDarkmode.setText(R.string.switchOn);
                switchDarkmode.setTypeface(switchDarkmode.getTypeface(), Typeface.BOLD);
            }
            else {
                switchDarkmode.setText(R.string.switchOff);
                switchDarkmode.setTypeface(null, Typeface.NORMAL);
            }
        });
    }
}
