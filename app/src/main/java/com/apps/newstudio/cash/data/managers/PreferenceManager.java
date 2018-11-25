package com.apps.newstudio.cash.data.managers;

import android.content.SharedPreferences;

import com.apps.newstudio.cash.utils.CashApplication;

public class PreferenceManager {
    private SharedPreferences mSharedPreferences;


    /**
     * Gets SharedPreferences object from CashApplication object
     * @return SharedPreferences mSharedPreferences
     */
    public SharedPreferences getSharedPreferences() {
        return CashApplication.getSharedPreferences();
    }

    /**
     * Saves String value using mSharedPreferences object
     * @param key is String identificator which is used to save value
     * @param value is String object which will be saved
     */
    public void saveString(String key, String value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * Loads String value from mSharedPreferences
     * @param key is String identificator which was used to save value
     * @param defaultValue is String value which will be returned if function do not find result with this key
     * @return String object
     */
    public String loadString(String key, String defaultValue) {
        return mSharedPreferences.getString(key, defaultValue);
    }
}
