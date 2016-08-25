package com.augmentis.ayp.alarmclock_project.Activity;

import android.support.v4.app.Fragment;

import com.augmentis.ayp.alarmclock_project.Fragment.AlarmClockFragment;

public class AlarmClockActivity extends SingleFragmentActivity {

    @Override
    protected Fragment onCreateFragment() {
        return AlarmClockFragment.newInstance();
    }
}
