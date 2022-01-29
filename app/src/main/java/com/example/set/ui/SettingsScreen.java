package com.example.set.ui;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.example.set.R;

/**
 * Class for the Settings Screen that handles the interaction with every element within itself
 * Because of the Spinner element the interface 'OnTimeSelectedListener' has to be implemented and with it two methods (below)
 *
 * TODO: Creating the Adapter for the Spinner Element, handle the EditText Element for the Timer
 *
 * @author Maximilian Knodt
 */
public class SettingsScreen extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
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


        // --------- SPINNER ---------
        // 'android.R.layout.simple_spinner_item' - is a default layout provided by Android Studio and defines the appearance of the one(!) selected Choice
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_language_en, android.R.layout.simple_spinner_item);
        // 'android.R.layout.simple_spinner_dropdown_item' - is another default layout and defines the appearance of all(!) items in the drop down menu
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLanguage.setAdapter(adapter);

        // --------- SWITCH CONTROL ---------
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

    /**
     * Callback method that is invoked when a new item of the view has been selected (only then!)
     * @param parent the AdapterView where the selection happened
     * @param view the view that was clicked
     * @param position position of the view that was clicked
     * @param id the row id of the selected item
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // TODO: Ueberpruefung welche Sprache ausgewaehlt wurde -> Aenderung der Sprachausgabe
    }

    /**
     * Callback method that is invoked when no item is selected
     * For example after a touch to open the drop down menu
     * @param parent the AdapterView where the selection happened
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent){
        // I need to be here because of the implemented interface
    }
}
