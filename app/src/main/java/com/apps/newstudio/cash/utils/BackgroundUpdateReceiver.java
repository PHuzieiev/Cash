package com.apps.newstudio.cash.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.apps.newstudio.cash.R;
import com.apps.newstudio.cash.data.managers.DataManager;
import com.apps.newstudio.cash.data.managers.DatabaseManager;
import com.apps.newstudio.cash.data.managers.LanguageManager;
import com.apps.newstudio.cash.ui.activities.SplashActivity;

import java.util.Calendar;

public class BackgroundUpdateReceiver extends BroadcastReceiver {

    private String mMessage;

    /**
     * Shows notification if background update will be successful
     *
     * @param context Context object
     * @param intent  main Intent object of BroadcastReceiver object
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if (hour < 20 && hour > 8) {
            DataManager.getInstance().getDatabaseManager().updateDataBase(new DatabaseManager.FinalActionSuccess() {
                @Override
                public void finalFunctionSuccess() {
                    try {
                        new LanguageManager() {
                            @Override
                            public void engLanguage() {
                                mMessage = CashApplication.getContext().getString(R.string.toast_update_success_2_eng);
                            }

                            @Override
                            public void ukrLanguage() {
                                mMessage = CashApplication.getContext().getString(R.string.toast_update_success_2_ukr);
                            }

                            @Override
                            public void rusLanguage() {
                                mMessage = CashApplication.getContext().getString(R.string.toast_update_success_2_rus);
                            }
                        };
                        Intent intent_act = new Intent(CashApplication.getContext(), SplashActivity.class);
                        PendingIntent pIntent_act = PendingIntent.getActivity(CashApplication.getContext(), 1, intent_act, 0);

                        NotificationCompat.Builder mBuilder =
                                new NotificationCompat.Builder(CashApplication.getContext(), ConstantsManager.NOTIFICATION_CHANNEL_ID);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            mBuilder.setSmallIcon(R.drawable.ic_small);
                            mBuilder.setColor(CashApplication.getContext().getResources().getColor(R.color.color_primary));
                        } else {
                            mBuilder.setSmallIcon(R.drawable.ic_main);
                        }
                        mBuilder.setContentTitle(CashApplication.getContext().getString(R.string.app_name))
                                .setDefaults(Notification.DEFAULT_SOUND)
                                .setAutoCancel(true)
                                .setTicker(CashApplication.getContext().getString(R.string.app_name))
                                .setContentIntent(pIntent_act)
                                .setContentText(mMessage);

                        NotificationManager notificationManager = (NotificationManager) CashApplication.getContext()
                                .getSystemService(Context.NOTIFICATION_SERVICE);
                        notificationManager.notify(1, mBuilder.build());
                    } catch (Exception ignored) {
                    }
                }
            }, null);
        }
    }
}
