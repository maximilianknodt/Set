package com.example.set.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.set.R;
import com.example.set.SettingsFragment;

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
