package com.example.set;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import com.example.set.controller.AppController;

/**
 * Fragmentclass for the settingFragment of the SettingScreen
 *
 * @author Maximilian Knodt
 */
public class SettingsFragment extends PreferenceFragmentCompat {
    private SharedPreferences sharedPreferences;
    /**
     * Method to set the preferred XML-FragmentPreference
     *
     * @author Maximilian Knodt
     *
     * @param savedInstanceState
     * @param rootKey
     */
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings_preferences, rootKey);

        // SharedPreference that keeps all the data of the instances of this class
        this.sharedPreferences = getPreferenceManager().getSharedPreferences();
    }

    public String getLanguage(){
        return this.sharedPreferences.getString("Settings_Preferences_Language", "");
    }

    public boolean getSPDeduction(){
        return this.sharedPreferences.getBoolean("Settings_Preferences_SP_Deduction", false);
    }

    public boolean getMPDeduction(){
        return this.sharedPreferences.getBoolean("Settings_Preferences_MP_Deduction", false);
    }

    public boolean getSuspended(){
        return this.sharedPreferences.getBoolean("Settings_Preferences_Suspended", false);
    }

    public int getTimer(){
        String timeString = sharedPreferences.getString("Settings_Preferences_Timer", "");
        return Integer.valueOf(timeString);
    }

    public boolean getDarkMode(){
        return this.sharedPreferences.getBoolean("Settings_Preferences_Dark_Mode", false);
    }
}