package edu.hsos.set.controller;

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
    /**
     * SharedPreference that keeps all the data of the instances of this class
     */
    private static SharedPreferences sharedPreferences;
    /**
     * Method to set the preferred XML-FragmentPreference
     *
     * @author Maximilian Knodt
     *
     * @param savedInstanceState Bundle with saved data
     * @param rootKey Key of the preference, can be null
     */
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings_preferences, rootKey);

        this.sharedPreferences = getPreferenceManager().getSharedPreferences();
    }

    /**
     * Method to set the SharedPreference
     *
     * @author Maximilian Knodt
     *
     * @param sp SharedPreference
     */
    public static void setSharedPreferences(SharedPreferences sp){
        sharedPreferences = sp;
    }

    /**
     * Method to get the SharedPreference
     *
     * @author Maximilian Knodt
     *
     * @return SharedPreference
     */
    public static SharedPreferences getPreference(){
        return sharedPreferences;
    }

    /**
     * Method to get the string value of the language list of the SharedPreference
     *
     * @author Maximilian Knodt
     *
     * @return String - language code
     */
    public static String getLanguage(){
        return sharedPreferences.getString("Settings_Preferences_Language", "en");
    }

    /**
     * Method to get the boolean value the single player deduction state of the SharedPreference
     *
     * @author Maximilian Knodt
     *
     * @return boolean - true if deduction is active
     */
    public static boolean getSPDeduction(){
        return sharedPreferences.getBoolean("Settings_Preferences_SP_Deduction", false);
    }

    /**
     * Method to get the boolean value the multiplayer deduction state of the SharedPreference
     *
     * @author Maximilian Knodt
     *
     * @return boolean - true if deduction is active
     */
    public static boolean getMPDeduction(){
        return sharedPreferences.getBoolean("Settings_Preferences_MP_Deduction", false);
    }

    /**
     * Method to get the boolean value the suspended state of the SharedPreference
     *
     * @author Maximilian Knodt
     *
     * @return boolean - true if suspended
     */
    public static boolean getSuspended(){
        return sharedPreferences.getBoolean("Settings_Preferences_Suspended", false);
    }

    /**
     * Method to get the String value of the timer state of the SharedPreference
     * It converts the String to int
     *
     * @author Maximilian Knodt
     *
     * @return int - array value
     */
    public static int getTimer(){
        String timeString = sharedPreferences.getString("Settings_Preferences_Timer", "20");
        return Integer.valueOf(timeString);
    }

    /**
     * Method to get the array value of the dark mode state of the SharedPreference
     *
     * @author Maximilian Knodt
     *
     * @return String - array value
     */
    public static String getDarkMode(){
        return sharedPreferences.getString("Settings_Preferences_Dark_Mode", "default");
    }
}