package com.apps.newstudio.cash.data.managers;

import android.content.SharedPreferences;

import com.apps.newstudio.cash.utils.CashApplication;
import com.apps.newstudio.cash.utils.ConstantsManager;

public class PreferenceManager {
    private SharedPreferences mSharedPreferences;


    /**
     * Gets SharedPreferences object from CashApplication object
     *
     * @return SharedPreferences mSharedPreferences
     */
    public PreferenceManager() {
        mSharedPreferences = CashApplication.getSharedPreferences();
    }

    /**
     * Saves String value using mSharedPreferences object
     *
     * @param key   is String identificator which is used to save value
     * @param value is String object which will be saved
     */
    public void saveString(String key, String value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * Loads String value from mSharedPreferences
     *
     * @param key          is String identificator which was used to save value
     * @param defaultValue is String value which will be returned if function do not find result with this key
     * @return String object
     */
    public String loadString(String key, String defaultValue) {
        return mSharedPreferences.getString(key, defaultValue);
    }

    public String getLanguage() {
        return loadString(ConstantsManager.LANGUAGE_KEY, ConstantsManager.LANGUAGE_ENG);
    }

    public String getOrganizationsFilterParameter() {
        return loadString(ConstantsManager.ORGANIZATIONS_FILTER_PARAMETER, ConstantsManager.EMPTY_STRING_VALUE);
    }

    public void setOrganizationsFilterParameter(String parameter) {
        saveString(ConstantsManager.ORGANIZATIONS_FILTER_PARAMETER, parameter);
    }

    public String getOrganizationsSearchParameter() {
        return loadString(ConstantsManager.ORGANIZATIONS_SEARCH_PARAMETER, ConstantsManager.EMPTY_STRING_VALUE);
    }

    public void setOrganizationsSearchParameter(String parameter) {
        saveString(ConstantsManager.ORGANIZATIONS_SEARCH_PARAMETER, parameter);
    }
}
