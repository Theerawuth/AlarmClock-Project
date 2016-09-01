package com.augmentis.ayp.alarmclock_project;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.PowerManager;
import android.util.Log;

/**
 * Created by Theerawuth on 8/26/2016.
 */
public class ScreenShow {

    private static final String SCREEN_CLASS_TAG = "ScreenShow";
    private static final String TAG = "Screen On";

    public ScreenShow() {

    }

    public void showOn(Context context){
        PowerManager powerManager =
                (PowerManager) context.getSystemService(Context.POWER_SERVICE);

        Log.d(TAG, "Screen on ...");

        if(!powerManager.isScreenOn()){
            PowerManager.WakeLock wl = powerManager.newWakeLock(PowerManager.ON_AFTER_RELEASE, SCREEN_CLASS_TAG);
            wl.acquire();
        }
    }
}
