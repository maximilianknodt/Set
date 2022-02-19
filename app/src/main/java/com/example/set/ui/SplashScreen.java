package com.example.set.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.set.R;
import com.example.set.controller.AppControllerHolder;

/**
 * Splash screen class
 * Implements the loading from the database.
 *
 * Source: https://www.codexpedia.com/android/a-simple-android-loader-example/
 *
 * the author is responsible for this class
 *
 * @author Linus Kurze
 */
public class SplashScreen extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Void> {

    /**
     * the method called when the screen is created
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        LoaderManager.getInstance(this).initLoader(0, null, (LoaderManager.LoaderCallbacks<Void>)this).forceLoad();
    }

    /**
     * the method called when the loader is created
     *
     * @param id the id of the loader
     * @param args arguments
     * @return the AsyncTaskLoader
     */
    @NonNull
    @Override
    public Loader<Void> onCreateLoader(int id, @Nullable Bundle args) {
        return new FetchData(this);
    }

    /**
     * the method called when the load finished
     *
     * @param loader the loader finished
     * @param data the loaded data
     */
    @Override
    public void onLoadFinished(@NonNull Loader<Void> loader, Void data) {
        Intent intentHome = new Intent();
        intentHome.setClass(this, HomeScreen.class);
        startActivity(intentHome);
    }

    /**
     * called when a loader is reset
     *
     * @param loader the loader being reset
     */
    @Override
    public void onLoaderReset(@NonNull Loader<Void> loader) {

    }

    /**
     * Fetch data class
     * Fetches the data from the database.
     *
     * the author is responsible for this class
     *
     * @author Linus Kurze
     */
    private static class FetchData extends AsyncTaskLoader<Void> {
        /**
         * the context for the fetching
         */
        private final Context context;

        /**
         * Constructor
         * Creates an FetchData object and sets the context.
         *
         * @param context the context for the fetching
         */
        public FetchData(Context context) {
            super(context);
            this.context = context;
        }

        /**
         * Loads the data from the database. Returns Void because the data is held and set in global class AppController.
         *
         * @return Void
         */
        @Override
        public Void loadInBackground() {
            AppControllerHolder.getAppController().loadGamesFromDatabase(context);
            return null;
        }
    }
}