package com.hjhz.testdemo.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;

import com.hjhz.testdemo.R;
import com.hjhz.testdemo.view.SketchView;

public class PowerManagerActivity extends AppCompatActivity {

    PowerManager powerManager;
    PowerManager.WakeLock wakeLock;

    SketchView sketchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_powermanager);

        powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK,"MYLOCK");

        sketchView = (SketchView) findViewById(R.id.sv);
        sketchView.startAnimation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        wakeLock.acquire();
    }

    @Override
    protected void onPause() {
        super.onPause();
        wakeLock.release();
    }
}
