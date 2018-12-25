package com.apps.newstudio.cash.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import static android.content.Context.ALARM_SERVICE;

public class BackgroundUpdateTask {

    /**
     * Sets task for background update in AlarmManager object
     */
    public BackgroundUpdateTask() {
        try {
            Context context = CashApplication.getContext();
            Intent alarm_intent = new Intent(context, BackgroundUpdateReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarm_intent, 0);
            AlarmManager alarmManager_not = (AlarmManager) context.getSystemService(ALARM_SERVICE);
            alarmManager_not.cancel(pendingIntent);
            alarmManager_not.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 7200000, pendingIntent);
        } catch (Exception ignored) {
        }
    }
}
