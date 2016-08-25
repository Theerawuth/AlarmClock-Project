package com.augmentis.ayp.alarmclock_project.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.augmentis.ayp.alarmclock_project.Database.AlarmClockBaseHelper;
import com.augmentis.ayp.alarmclock_project.Database.AlarmClockCursorWrapper;
import com.augmentis.ayp.alarmclock_project.Database.AlarmClockSchema.AlarmClockTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Theerawuth on 8/24/2016.
 */
public class AlarmClockLab {

    private static final String TAG = "AlarmClockLab";
    private Context context;
    private List<AlarmClock> alarmClockList;
    private static AlarmClockLab instance;
    private SQLiteDatabase database;

    public static AlarmClockLab getInstance(Context context) {
        if (instance == null){
            instance = new AlarmClockLab(context);
        }
        return instance;
    }

    private static ContentValues getContentValues(AlarmClock alarmClock){
        ContentValues contentValues = new ContentValues();
        contentValues.put(AlarmClockTable.Cols.UUID, alarmClock.getId().toString());
        contentValues.put(AlarmClockTable.Cols.HOUR, alarmClock.getHour());
        contentValues.put(AlarmClockTable.Cols.MINUTE, alarmClock.getMinute());
        contentValues.put(AlarmClockTable.Cols.TOGGLE, alarmClock.isToggle());

        return contentValues;
    }

    private AlarmClockLab(Context context){
        this.context = context.getApplicationContext();

        AlarmClockBaseHelper alarmClockBaseHelper = new AlarmClockBaseHelper(context);
        database = alarmClockBaseHelper.getWritableDatabase();
    }

    public AlarmClockCursorWrapper queryAlarmClock(String whereCause, String[] whereArgs){
        Cursor cursor = database.query(AlarmClockTable.NAME,
                null,
                whereCause,
                whereArgs,
                null,
                null,
                null);

        return new AlarmClockCursorWrapper(cursor);
    }

    public List<AlarmClock> getAlarmClockList() {
        List<AlarmClock> alarmClockList = new ArrayList<>();

        AlarmClockCursorWrapper cursorWrapper = queryAlarmClock(null, null);

        try {
            cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()){
                alarmClockList.add(cursorWrapper.getAlarmClock());

                cursorWrapper.moveToNext();
            }
        }finally {
            cursorWrapper.close();
        }

        return alarmClockList;
    }

    public AlarmClock getAlarmClockById(UUID uuid){
        AlarmClockCursorWrapper cursorWrapper = queryAlarmClock( AlarmClockTable.Cols.UUID + " = ? ",
                new String[] { uuid.toString() } );
        try {
            if(cursorWrapper.getCount() == 0){
                return null;
            }

            cursorWrapper.moveToFirst();
            return cursorWrapper.getAlarmClock();
        }
        finally
        {
            cursorWrapper.close();
        }
    }

    public void addAlarmClock(AlarmClock alarmClock){
        ContentValues contentValues = getContentValues(alarmClock);
        database.insert(AlarmClockTable.NAME, null, contentValues);
    }

    public void deleteAlarmClock(UUID uuid){
        database.delete(AlarmClockTable.NAME,
                AlarmClockTable.Cols.UUID + " = ? ",
                new String[] { uuid.toString() });

    }

    public void updateAlarmClock(AlarmClock alarmClock){
        Log.d(TAG, "Update AlarmClock.Db");
        String uuidStr = alarmClock.getId().toString();
        ContentValues contentValues = getContentValues(alarmClock);

        database.update(AlarmClockTable.NAME, contentValues,
                AlarmClockTable.Cols.UUID + " = ? ", new String[] {uuidStr} );

    }


}
