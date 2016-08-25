package com.augmentis.ayp.alarmclock_project.Database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.augmentis.ayp.alarmclock_project.Model.AlarmClock;
import com.augmentis.ayp.alarmclock_project.Database.AlarmClockSchema.AlarmClockTable;

import java.util.UUID;

/**
 * Created by Theerawuth on 8/24/2016.
 */
public class AlarmClockCursorWrapper extends CursorWrapper {

    public AlarmClockCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public AlarmClock getAlarmClock() {
        String uuidStr = getString(getColumnIndex(AlarmClockTable.Cols.UUID));
        int hour = getInt(getColumnIndex(AlarmClockTable.Cols.HOUR));
        int minute = getInt(getColumnIndex(AlarmClockTable.Cols.MINUTE));
        int isToggle = getInt(getColumnIndex(AlarmClockTable.Cols.TOGGLE));

        AlarmClock alarmClock = new AlarmClock(UUID.fromString(uuidStr));
        alarmClock.setHour(hour);
        alarmClock.setMinute(minute);
        alarmClock.setToggle( isToggle != 0);

        return alarmClock;
    }
}
