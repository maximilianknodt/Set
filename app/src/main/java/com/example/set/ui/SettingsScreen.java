package com.example.set.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.ListPreference;

import com.example.set.R;
import com.example.set.SettingsFragment;
import com.example.set.controller.AppController;
import com.example.set.controller.AppControllerHolder;

import java.util.Locale;

/**
 * Class for the Settings Screen to load the SettingsFragment
 *
 * @author Maximilian Knodt
 */
public class SettingsScreen extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_screen);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView_Settings, new SettingsFragment())
                .commit();
    }
}
