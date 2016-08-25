package com.augmentis.ayp.alarmclock_project.Model;

import java.util.UUID;

/**
 * Created by Theerawuth on 8/24/2016.
 */
public class AlarmClock {
    private UUID id;
    private int hour;
    private int minute;
    private boolean toggle;

    public AlarmClock() {
        this(UUID.randomUUID());
    }

    public AlarmClock(UUID uuid){

        this.id = uuid;
        this.toggle = true;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public boolean isToggle() {
        return toggle;
    }

    public void setToggle(boolean toggle) {
        this.toggle = toggle;
    }
}
