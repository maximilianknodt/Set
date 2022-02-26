package edu.hsos.set.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.set.R;
import edu.hsos.set.controller.SettingsFragment;

//Quelle: https://developer.android.com/guide/topics/ui/settings
//Quelle: https://stackoverflow.com/questions/2542938/sharedpreferences-onsharedpreferencechangelistener-not-being-called-consistently

/**
 * Class for the Settings Screen to load the SettingsFragment
 *
 * @author Maximilian Knodt - responsible for this class.
 */
public class SettingsScreen extends AppCompatActivity{
    /**
     * the listener as static field to keep the SharedPreference and avoid the garbage collection
     */
    private static SharedPreferences.OnSharedPreferenceChangeListener preferenceListener;

    /**
     * the method called when the screen is called
     *
     * @param savedInstanceState Bundle with saved data
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_screen);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView_Settings, new SettingsFragment())
                .commit();

        changeMode(SettingsFragment.getDarkMode());

        preferenceListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                if(key.equals("Settings_Preferences_Dark_Mode")){
                    changeMode(SettingsFragment.getDarkMode());
                }
                if(key.equals("Settings_Preferences_Language")){
                    Toast.makeText(SettingsScreen.this, R.string.language_changed, Toast.LENGTH_SHORT).show();
                }
            }
        };

        SettingsFragment.getPreference().registerOnSharedPreferenceChangeListener(preferenceListener);
    }

    /**
     * Method to change the Mode of the App
     * It is called when the Dark Mode option in the SettingsScreen is changed
     */
    protected void changeMode(String mode){
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
