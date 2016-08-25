package com.augmentis.ayp.alarmclock_project.Fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import com.augmentis.ayp.alarmclock_project.Activity.AlarmClockActivity;
import com.augmentis.ayp.alarmclock_project.Model.AlarmClock;
import com.augmentis.ayp.alarmclock_project.Model.AlarmClockLab;
import com.augmentis.ayp.alarmclock_project.R;

public class TimePickerFragment extends DialogFragment {

    private static final String TAG = "TimePickerFragment";

    //Step 1: build TimePicker
    public static TimePickerFragment newInstance() {

        Bundle args = new Bundle();

        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    TimePicker mTimePicker;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d(TAG, "Dialog Add");
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_time_picker, null);
        mTimePicker = (TimePicker) v.findViewById(R.id.time_picker_fragment);


        AlertDialog.Builder builder =  new AlertDialog.Builder(getActivity());   // object ที่ใช้สร้าง Dialog แล้วกำหนดค่าต่างๆ ต่อ
        builder.setView(v);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d(TAG, "Click Save");

                int hour = 0;
                int minute = 0;

                // TimePicker ---> Model
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    hour = mTimePicker.getHour();
                    minute =  mTimePicker.getMinute();
                }

                AlarmClock alarmClock = new AlarmClock();
                alarmClock.setHour(hour);
                alarmClock.setMinute(minute);
                AlarmClockLab.getInstance(getActivity()).addAlarmClock(alarmClock);
                Intent intent = new Intent(getActivity(), AlarmClockActivity.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        return  builder.create();
    }
}
