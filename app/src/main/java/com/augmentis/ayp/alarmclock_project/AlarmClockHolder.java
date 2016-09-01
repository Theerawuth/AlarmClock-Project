package com.augmentis.ayp.alarmclock_project;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.augmentis.ayp.alarmclock_project.Activity.AlarmClockActivity;
import com.augmentis.ayp.alarmclock_project.Fragment.AlarmClockFragment;
import com.augmentis.ayp.alarmclock_project.Model.AlarmClock;
import com.augmentis.ayp.alarmclock_project.Model.AlarmClockLab;

import java.sql.Time;
import java.util.Calendar;
import java.util.UUID;

/**
 * Created by Theerawuth on 8/24/2016.
 */
public class AlarmClockHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

    private static final String TAG = "AlarmClockHolder";
    private TextView hourTextView;
    private TextView minuteTextView;
    private Switch checkToggle;
    private TimePicker alarmPicker;

    AlarmClock mAlarmClock;
    int mPosition;
    UUID mAlarmClockId;
    Callbacks callbacks;
    Context _context;

    public interface Callbacks{
        void updateAlarm();
    }

    public AlarmClockHolder(View itemView, Context context) {
        super(itemView);
        _context = context;
        callbacks = (Callbacks) _context;
        alarmPicker = (TimePicker) itemView.findViewById(R.id.time_picker_fragment);
        hourTextView = (TextView) itemView.findViewById(R.id.txt_view_hour);
        minuteTextView = (TextView) itemView.findViewById(R.id.txt_view_minute);
        checkToggle = (Switch) itemView.findViewById(R.id.switch_on);

        itemView.setOnLongClickListener(this);
    }


    public void bindAlarmClock(final AlarmClock alarmClock, int position){
        mAlarmClock = alarmClock;
        mAlarmClockId = alarmClock.getId();
        mPosition = position;

        String hourStr = String.format("%02d", alarmClock.getHour());
        String minuteStr = String.format("%02d", alarmClock.getMinute());

        hourTextView.setText(hourStr);
        minuteTextView.setText(minuteStr);

        checkToggle.setChecked(alarmClock.isToggle());
        checkToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                AlarmClockLab alarmClockLab = AlarmClockLab.getInstance(_context);
                AlarmClock alarmClock = alarmClockLab.getAlarmClockById(mAlarmClock.getId());
                alarmClock.setToggle(isChecked);
                alarmClockLab.updateAlarmClock(alarmClock);
                callbacks.updateAlarm();
            }
        });
    }

    @Override
    public boolean onLongClick(View view) {
        new AlertDialog.Builder(_context)
                .setTitle("Delete Alarm")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d(TAG, "Delete Alarm Finish");
                        AlarmClockLab.getInstance(_context).deleteAlarmClock(mAlarmClock.getId());
                        Intent intent = new Intent(_context, AlarmClockActivity.class);
                        _context.startActivity(intent);
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
        return true;
    }
}

