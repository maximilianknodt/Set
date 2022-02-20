package com.example.set.controller;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.example.set.R;

/**
 * Fragmentclass for the settingFragment of the SettingScreen
 *
 * @author Maximilian Knodt
 */
public class SettingsFragment extends PreferenceFragmentCompat {
    private static SharedPreferences sharedPreferences;
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

    public static SharedPreferences getPreference(){
        return sharedPreferences;
    }

    public static String getLanguage(){
        return sharedPreferences.getString("Settings_Preferences_Language", "");
    }

    public static boolean getSPDeduction(){
        return sharedPreferences.getBoolean("Settings_Preferences_SP_Deduction", false);
    }

    public static boolean getMPDeduction(){
        return sharedPreferences.getBoolean("Settings_Preferences_MP_Deduction", false);
    }

    public static boolean getSuspended(){
        return sharedPreferences.getBoolean("Settings_Preferences_Suspended", false);
    }

    public static int getTimer(){
        String timeString = sharedPreferences.getString("Settings_Preferences_Timer", "20");
        return Integer.valueOf(timeString);
    }

    public static String getDarkMode(){
        return sharedPreferences.getString("Settings_Preferences_Dark_Mode", "default");
    }
}