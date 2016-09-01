package com.augmentis.ayp.alarmclock_project;

import android.app.AlarmManager;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.augmentis.ayp.alarmclock_project.Model.AlarmClock;

import java.util.List;

/**
 * Created by Theerawuth on 8/24/2016.
 */
public class AlarmClockAdapter extends RecyclerView.Adapter<AlarmClockHolder> {

    private static final String TAG = "AlarmClockAdapter";
    private FragmentActivity mActivity;
    private List<AlarmClock> mAlarmClockList;


    public AlarmClockAdapter(List<AlarmClock> alarmClockList, FragmentActivity activity) {
        mAlarmClockList = alarmClockList;
        mActivity = activity;
    }

    public void setAlarmClockList(List<AlarmClock> alarmClockList) {
        mAlarmClockList = alarmClockList;
    }

    @Override
    public AlarmClockHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "Create ViewHolder for AlarmClockList");

        LayoutInflater layoutInflater = LayoutInflater.from(mActivity);
        View view = layoutInflater.inflate(R.layout.activity_alarm_clock_holder, parent, false);
        return new AlarmClockHolder(view, parent.getContext());
    }

    @Override
    public void onBindViewHolder(AlarmClockHolder holder, int position) {
        Log.d(TAG, "Bind ViewHolder For AlarmClockList");

        AlarmClock alarmClock = mAlarmClockList.get(position);
        holder.bindAlarmClock(alarmClock, position);
    }

    @Override
    public int getItemCount() {
        return mAlarmClockList.size();
    }


}
