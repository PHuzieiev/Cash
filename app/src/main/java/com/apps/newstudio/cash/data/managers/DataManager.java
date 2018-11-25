package com.apps.newstudio.cash.data.managers;

import android.content.SharedPreferences;

public class DataManager {

    private static DataManager INSTANCE = null;
    private PreferenceManager mPreferenceManager;

    /**
     * Constructor for DataManager
     * Defines PreferenceManager project
     */
    private DataManager() {
        mPreferenceManager = new PreferenceManager();
    }

    /**
     * Checks DataManager object, creates DataManager object INSTANCE if Instance is null
     *
     * @return DataManager object INSTANCE
     */
    public static DataManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DataManager();
        }
        return INSTANCE;
    }

    /**
     * Getter for PreferenceManager object
     *
     * @return mPreferenceManager
     */
    public PreferenceManager getPreferenceManager() {
        return mPreferenceManager;
    }

}
