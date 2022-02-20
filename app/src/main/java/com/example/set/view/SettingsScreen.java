package com.example.set.view;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

import com.example.set.R;
import com.example.set.SettingsFragment;

/**
 * Class for the Settings Screen to load the SettingsFragment
 *
 * @author Maximilian Knodt
 */
public class SettingsScreen extends AppCompatActivity{
    /**
     * the lister as static field to keep the SharedPreference and avoid the garbage collection
     */
    private static SharedPreferences.OnSharedPreferenceChangeListener preferenceListener;

    /**
     * the method called when the screen is called
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_screen);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView_Settings, new SettingsFragment())
                .commit();


        preferenceListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                changeMode();
            }
        };

        SettingsFragment.getPreference().registerOnSharedPreferenceChangeListener(preferenceListener);
    }

    /**
     * Method to change the Mode of the App
     * It is called when the Dark Mode option in the settingsscreen is changed
     */
    private void changeMode(){
        String mode = SettingsFragment.getDarkMode();

        switch(mode){
            case "default":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
            case "yes":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case "no":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            default:
                break;
        }
    }
}
