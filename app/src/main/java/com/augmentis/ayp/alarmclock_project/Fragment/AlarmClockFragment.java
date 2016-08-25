package com.augmentis.ayp.alarmclock_project.Fragment;

import android.content.Context;
import android.content.DialogInterface;
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

import com.augmentis.ayp.alarmclock_project.Model.AlarmClock;
import com.augmentis.ayp.alarmclock_project.AlarmClockAdapter;
import com.augmentis.ayp.alarmclock_project.Model.AlarmClockLab;
import com.augmentis.ayp.alarmclock_project.R;

import java.util.List;

public class AlarmClockFragment extends Fragment {

    private static final String TAG = "AlarmClockFragment";
    private static final String DIALOG_TIME = "Dialog Time";
    public RecyclerView mRecyclerView;
    private AlarmClockAdapter adapter;
    private static AlarmClockFragment inst;


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
                mRecyclerView.setAdapter(adapter);
            }
        }
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

    public void showAlertDialog(Ringtone ringtone) {

        final Ringtone mRingtone;
        mRingtone = ringtone;

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Wake Up Wake Up !!!");
        builder.setMessage("Are you sure close alert?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                mRingtone.stop();
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

}
