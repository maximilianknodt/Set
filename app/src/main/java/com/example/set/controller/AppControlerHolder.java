package com.example.set.controller;

/**
 * Singleton class to hold the app controller
 * <p>
 * The author is responsible for this class.
 *
 * @author Linus Kurze
 * @version 1.0
 */
public class AppControlerHolder {
    /**
     * the controller for the app
     */
    private static AppController appController = new AppController();

    /**
     * Getter
     * Returns the app controller.
     *
     * @return the app controller
     *
     * @author Linus Kurze
     */
    public static AppController getAppController() {
        return appController;
    }
}
