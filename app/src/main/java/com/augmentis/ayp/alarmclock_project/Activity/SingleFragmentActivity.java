package com.augmentis.ayp.alarmclock_project.Activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.augmentis.ayp.alarmclock_project.R;

public abstract class SingleFragmentActivity extends AppCompatActivity {

    private static final String TAG = "SingleFragmentActivity" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);

        Log.d(TAG, "On Create Activity");

        FragmentManager fm = getSupportFragmentManager();
        Fragment f = fm.findFragmentById(R.id.fragment_container);

        if(f == null) {
            f = onCreateFragment();

            fm.beginTransaction()
                    .add(R.id.fragment_container, f)
                    .commit();
            Log.d(TAG, "Fragment is created");
        }
        else
        {
            Log.d(TAG, "Fragment have already been created");
        }
    }

    protected abstract Fragment onCreateFragment();
}
