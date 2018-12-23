package com.apps.newstudio.cash.data.managers;

public class DataManager {

    private static DataManager INSTANCE = null;
    private PreferenceManager mPreferenceManager;
    private DatabaseManager mDatabaseManager;

    /**
     * Constructor for DataManager
     * Defines PreferenceManager and DatabaseManager
     */
    private DataManager() {
        mPreferenceManager = new PreferenceManager();
        mDatabaseManager = new DatabaseManager();
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

    /**
     * Getter for DatabaseManager object
     *
     * @return mDatabaseManager
     */
    public DatabaseManager getDatabaseManager() {
        return mDatabaseManager;
    }
}
