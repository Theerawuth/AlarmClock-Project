package com.augmentis.ayp.alarmclock_project;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.augmentis.ayp.alarmclock_project.Fragment.AlarmClockFragment;

import java.util.UUID;

/**
 * Created by Theerawuth on 8/24/2016.
 */
public class AlarmClockReceiver extends WakefulBroadcastReceiver {

    private static final String TAG = "AlarmClockReceiver";

    private int id = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "This is shown");

        int mId = (int) intent.getExtras().get("MID");
        UUID uuid = (UUID) intent.getExtras().get("UUID");

        //this will sound the alarm tone
        //this will sound the alarm once, if you wish to
        //raise alarm in loop continuously then use MediaPlayer and setLooping(true)
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null){
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        Ringtone ringtone = RingtoneManager.getRingtone(context, alarmUri);
        ringtone.play();

        //this will update the UI with Dialog
        AlarmClockFragment inst = AlarmClockFragment.instance();
        inst.showAlertDialog(ringtone,mId,uuid);

        //this will send a notification dialog
        ComponentName comp = new ComponentName(context.getPackageName(),
                AlarmClockService.class.getName());
        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);


        //multiple alert
        Intent intent1 = new Intent(context, AlarmClockHolder.class);
        intent1.setData(Uri.parse("alarm :" + (id++)));



    }
}
