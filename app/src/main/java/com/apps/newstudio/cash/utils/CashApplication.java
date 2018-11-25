package com.apps.newstudio.cash.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class CashApplication extends Application {

    private static Context sContext;
    private static SharedPreferences sSharedPreferences;

    /**
     *Defines Context and SharedPreferences objects
     */
    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        sSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    /**
     * Getter for Context object
     * @return sContext
     */
    public static Context getContext() {
        return sContext;
    }

    /**
     * Getter for SharedPreferences object
     * @return sSharedPreferences
     */
    public static SharedPreferences getSharedPreferences() {
        return sSharedPreferences;
    }
}
