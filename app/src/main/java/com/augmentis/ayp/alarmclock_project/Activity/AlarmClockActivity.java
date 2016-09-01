package com.augmentis.ayp.alarmclock_project.Activity;

import android.support.v4.app.Fragment;
import android.util.Log;

import com.augmentis.ayp.alarmclock_project.AlarmClockHolder;
import com.augmentis.ayp.alarmclock_project.Fragment.AlarmClockFragment;
import com.augmentis.ayp.alarmclock_project.R;

public class AlarmClockActivity extends SingleFragmentActivity implements AlarmClockHolder.Callbacks {

    @Override
    protected Fragment onCreateFragment() {

        return AlarmClockFragment.newInstance();
    }

    @Override
    public void updateAlarm() {
        AlarmClockFragment alarmClockFragment = (AlarmClockFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if(alarmClockFragment.isAdded())
        {
            Log.d("CallBack","Call back ");
            alarmClockFragment.setShowTime();
        }
    }
}
