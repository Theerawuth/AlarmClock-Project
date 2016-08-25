package com.augmentis.ayp.alarmclock_project.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.augmentis.ayp.alarmclock_project.Database.AlarmClockSchema.AlarmClockTable;

/**
 * Created by Theerawuth on 8/24/2016.
 */
public class AlarmClockBaseHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "alarmClockBase.db";
    private static final int VERSION = 1;
    private static final String TAG = "AlarmClockBaseHelper";

    public AlarmClockBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "Create Database");
        db.execSQL("CREATE table " + AlarmClockTable.NAME
                + "("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + AlarmClockTable.Cols.UUID + ","
                + AlarmClockTable.Cols.HOUR + ","
                + AlarmClockTable.Cols.MINUTE + ","
                + AlarmClockTable.Cols.TOGGLE + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
