package com.augmentis.ayp.alarmclock_project.Database;

/**
 * Created by Theerawuth on 8/24/2016.
 */
public class AlarmClockSchema {
    public static final class AlarmClockTable {
        public static final String NAME = "alarmclock";

        public static final class Cols{
            public static final String UUID = "uuid";
            public static final String HOUR = "hour";
            public static final String MINUTE = "minute";
            public static final String TOGGLE = "toggle";
        }
    }
}
