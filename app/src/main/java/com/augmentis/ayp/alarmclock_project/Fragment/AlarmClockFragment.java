package com.augmentis.ayp.alarmclock_project.Fragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Ringtone;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.augmentis.ayp.alarmclock_project.AlarmClockReceiver;
import com.augmentis.ayp.alarmclock_project.Model.AlarmClock;
import com.augmentis.ayp.alarmclock_project.AlarmClockAdapter;
import com.augmentis.ayp.alarmclock_project.Model.AlarmClockLab;
import com.augmentis.ayp.alarmclock_project.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class AlarmClockFragment extends Fragment {

    private static final String TAG = "AlarmClockFragment";
    private static final String DIALOG_TIME = "Dialog Time";

    public RecyclerView mRecyclerView;
    private AlarmClockAdapter adapter;
    private static AlarmClockFragment inst;
    List<PendingIntent> pendingIntentList = new ArrayList<>();
    Intent mIntent;

    AlarmManager alarmManager;
    AlarmClock mAlarmClock;


    public static AlarmClockFragment instance() {

        return inst;
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }

    public static AlarmClockFragment newInstance() {
        Bundle args = new Bundle();
        AlarmClockFragment fragment = new AlarmClockFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alarm_clock, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_recycle_view_alarm_clock);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mIntent = new Intent(getActivity(), AlarmClockReceiver.class);
        alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        updateUI();

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private void updateUI() {
        AlarmClockLab alarmClockLab = AlarmClockLab.getInstance(getActivity());
        Log.d(TAG, "Update UI");
        if(!alarmClockLab.getAlarmClockList().isEmpty()) {
            Log.d(TAG,"Check Empty List ? ");

            List<AlarmClock> alarmClockList = alarmClockLab.getAlarmClockList();
            if(adapter == null){
                adapter = new AlarmClockAdapter(alarmClockList, getActivity());
                mRecyclerView.setAdapter(adapter);
            }
            else
            {
                adapter.setAlarmClockList(alarmClockLab.getAlarmClockList());
                adapter.notifyDataSetChanged();
            }
        }
        setShowTime();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
        Log.d(TAG, "Show MenuIcon");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnu_new_time:
                Log.d(TAG, "Add Timer");
                FragmentManager fm = getFragmentManager();
                TimePickerFragment timeDialogFragment = TimePickerFragment.newInstance();
                timeDialogFragment.show(fm, DIALOG_TIME);
                updateUI();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    public void showAlertDialog(Ringtone ringtone, int mId, final UUID uuid) {
        final Ringtone mRingtone;
        final int _mId = mId;
        mRingtone = ringtone;

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Wake Up Wake Up !!!");
        builder.setMessage("Are you sure close alert?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                AlarmClockLab alarmClockLab = AlarmClockLab.getInstance(getActivity());
                AlarmClock alarmClock = alarmClockLab.getAlarmClockById(uuid);
                alarmClock.setToggle(false);
                alarmClockLab.updateAlarmClock(alarmClock);
                //delete pendingIntent
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), _mId, mIntent, 0);
                alarmManager.cancel(pendingIntent);
                pendingIntent.cancel();
                mRingtone.stop();
                updateUI();
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // do nothing
            }
        });
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.show();

    }

    public void setShowTime() {
        if(pendingIntentList.size()>0)
        {
            for (int i = 0; i< pendingIntentList.size(); i++){
                Log.d("test", "Cancel alarm position" + i);
                alarmManager.cancel(pendingIntentList.get(i));
                pendingIntentList.get(i).cancel();
            }
            pendingIntentList.clear();
        }

        AlarmClockLab alarmClockLab = AlarmClockLab.getInstance(getActivity());
        List<AlarmClock> alarmClockList = alarmClockLab.getAlarmClockList();

        AlarmManager[] alarmManagers = new AlarmManager[alarmClockList.size()];
        for(int i = 0; i < alarmClockList.size(); i ++){
            AlarmClock alarmClock = alarmClockList.get(i);

            if(alarmClock.isToggle())
            {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, alarmClock.getHour());
                calendar.set(Calendar.MINUTE, alarmClock.getMinute());
                calendar.set(Calendar.SECOND, 00);
                Log.d("test", "On alarm : Hour = "+ alarmClock.getHour()+ "minute" + alarmClock.getMinute());
                mIntent.putExtra("MID",i);
                mIntent.putExtra("UUID",alarmClock.getId());
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), i, mIntent, PendingIntent.FLAG_ONE_SHOT);
                alarmManagers[i] = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                alarmManagers[i].set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
                pendingIntentList.add(pendingIntent);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(pendingIntentList.size()>0)
        {
            for (int i = 0; i< pendingIntentList.size(); i++){
                alarmManager.cancel(pendingIntentList.get(i));
                pendingIntentList.get(i).cancel();
            }
            pendingIntentList.clear();
        }
    }
}
