package edu.hsos.set.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;

import java.util.Locale;

//Quelle: https://www.geeksforgeeks.org/how-to-change-the-whole-app-language-in-android-programmatically/

/**
 * Class with primary function to change the language of the application
 *
 * @author Maximilian Knodt
 */

public class LocaleManager {
    /**
     * Variable to hold the language
     */
    private static final String SELECTED_LANGUAGE = "LocaleManager_Selected_Language";

    /**
     * Constructor - private
     */
    private LocaleManager(){}

    /**
     * Method to update the language of the device
     * it distinguishes between Version higher or lower Nougat
     *
     * @param context Context to set the new language to
     * @param language the language to change the device to
     * @return Context
     */
    public static  Context setLocale(Context context, String language){
        savePersist(context, language);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            return updateResourcesNHigher(context, language);
        }
        return updateResourcesNLower(context, language);
    }

    /**
     * Saves the language, writes it to the SharedPreferences
     *
     * @param context Context to set the new language to
     * @param language the language to change the device to
     */
    private static void savePersist(Context context, String language){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SELECTED_LANGUAGE, language);
        editor.apply();
    }

    /**
     * Method to update the language for devices with OS above android nougat
     *
     * @param context Context to set the new language to
     * @param language the language to change the device to
     * @return Context
     */
    private static Context updateResourcesNHigher(Context context, String language){
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Configuration config = context.getResources().getConfiguration();
        config.setLocale(locale);

        return context.createConfigurationContext(config);
    }

    /**
     * Method to update the language for devices with OS lower android nougat
     * That is the reason of the use of deprecated hits and the SuppressionWarning
     *
     * @param context Context to set the new language to
     * @param language the language to change the device to
     * @return Context
     */
    @SuppressWarnings("deprecation")
    private static Context updateResourcesNLower(Context context, String language){
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources resources = context.getResources();

        Configuration config = resources.getConfiguration();
        config.locale = locale;

        resources.updateConfiguration(config, resources.getDisplayMetrics());

        return context;
    }
}
