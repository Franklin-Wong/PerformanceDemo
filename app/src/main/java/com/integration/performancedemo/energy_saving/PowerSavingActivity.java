package com.integration.performancedemo.energy_saving;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.integration.performancedemo.MyApplication;
import com.integration.performancedemo.R;
@SuppressLint("StaticFieldLeak")
public class PowerSavingActivity extends AppCompatActivity  {
    private static TextView tv_screen;
    private static final String TAG = "PowerSavingActivity";
    //静态变量，跟随类的生命周期

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power_saving);
        tv_screen = findViewById(R.id.tv_screen);
    }

    @Override
    protected void onStart() {
        super.onStart();
        tv_screen.setText(MyApplication.getApplication().getChange());
        Log.i(TAG, "onStart: "+ MyApplication.getApplication().getChange());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tv_screen.setText("");
    }
}
