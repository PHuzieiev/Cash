package com.apps.newstudio.cash.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.apps.newstudio.cash.R;
import com.apps.newstudio.cash.data.storage.models.DaoMaster;
import com.apps.newstudio.cash.data.storage.models.DaoSession;

import org.greenrobot.greendao.database.Database;


public class CashApplication extends Application {

    private static Context sContext;
    private static SharedPreferences sSharedPreferences;
    private static DaoSession sDaoSession;

    /**
     *Defines Context, SharedPreferences and DaoSession objects
     */
    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        sSharedPreferences = getSharedPreferences(getString(R.string.app_name),MODE_PRIVATE);

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,"cash-db");
        Database db = helper.getWritableDb();
        sDaoSession = new DaoMaster(db).newSession();
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


    /**
     * Getter for DaoSession object
     * @return sDaoSession
     */
    public static DaoSession getDaoSession() {
        return sDaoSession;
    }
}
