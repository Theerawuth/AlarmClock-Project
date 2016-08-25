package com.augmentis.ayp.alarmclock_project;

import android.app.Dialog;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.augmentis.ayp.alarmclock_project.Activity.AlarmClockActivity;

/**
 * Created by Theerawuth on 8/24/2016.
 */

public class AlarmClockService extends IntentService {

    private NotificationManager alarmNotificationManager;

    private static final String TAG = "AlarmClockService";


    public AlarmClockService() {

        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        sendNotification("Wake up Wake up!!");

    }

    private void sendNotification(String msg) {
        Log.d(TAG, "Preparing to send notification...: ");
        alarmNotificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, AlarmClockActivity.class), 0);

        NotificationCompat.Builder alarmNotificationBuilder =
                new NotificationCompat.Builder(this)
                        .setContentTitle("Alarm")
                        .setSmallIcon(R.drawable.ic_action_name)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                        .setContentText(msg);

        alarmNotificationBuilder.setContentIntent(contentIntent);
        alarmNotificationManager.notify(1, alarmNotificationBuilder.build());
        Log.d("AlarmService", "Notification sent.");
    }
}

