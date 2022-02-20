package com.example.set.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;

import java.util.Locale;

public class LocalManager {
    private static final String SELECTED_LANGUAGE = "LocaleManager_Selected_Language";

    /**
     * Method to update the language of the device at runtime
     * it distinguishs between Version higher or lower Nougat
     *
     * @param context
     * @param language the language to change the device to
     * @return
     */
    public static  Context setLocale(Context context, String language){
        persist(context, language);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            return updateResourcesNHigher(context, language);
        }
        return updateResourcesNLower(context, language);
    }

    private static void persist(Context context, String language){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SELECTED_LANGUAGE, language);
        editor.apply();
    }

    /**
     * Method to update the language for devices with OS above android nougat
     *
     * @param context
     * @param language the language to change the device to
     * @return
     */
    private static Context updateResourcesNHigher(Context context, String language){
        // Locale is the Object to format languages or numbers to a specific region of the world
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        //Configuration describes all device information -> need to be changed when language is changed
        Configuration config = context.getResources().getConfiguration();
        config.setLocale(locale);
        // Sets the Layout RTL or LTR depending on the locale
        config.setLayoutDirection(locale);

        return context.createConfigurationContext(config);
    }

    /**
     * Method to update the language for devices with OS lower android nougat
     * That is the reason of the use of deprecated hits and the SuppressionWarning
     *
     * @param context
     * @param language the language to change the device to
     * @return
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
