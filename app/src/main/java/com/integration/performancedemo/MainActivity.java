package com.integration.performancedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.integration.performancedemo.energy_saving.AlarmIdleActivity;
import com.integration.performancedemo.energy_saving.BatteryInfoActivity;
import com.integration.performancedemo.energy_saving.PowerSavingActivity;
import com.integration.performancedemo.imagecache.ImageCacheActivity;
import com.integration.performancedemo.imagecache.LruCacheActivity;
import com.integration.performancedemo.layout_optimize.ScreenSuitableActivity;
import com.integration.performancedemo.layout_optimize.WindowStyleActivity;
import com.integration.performancedemo.memory.LogoutServiceActivity;
import com.integration.performancedemo.memory.ReferStrongActivity;
import com.integration.performancedemo.memory.ReferWeakActivity;
import com.integration.performancedemo.memory.RemoveTaskActivity;
import com.integration.performancedemo.threadpool.SchedulePoolActivity;
import com.integration.performancedemo.threadpool.ThreadPoolActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_include_one).setOnClickListener(this);
        findViewById(R.id.btn_include_two).setOnClickListener(this);
        findViewById(R.id.btn_screen_suitable).setOnClickListener(this);
        findViewById(R.id.btn_window_style).setOnClickListener(this);
        findViewById(R.id.btn_remove_task).setOnClickListener(this);
        findViewById(R.id.btn_logout_service).setOnClickListener(this);
        findViewById(R.id.btn_refer_strong).setOnClickListener(this);
        findViewById(R.id.btn_refer_weak).setOnClickListener(this);
        findViewById(R.id.btn_thread_pool).setOnClickListener(this);
        findViewById(R.id.btn_schedule_pool).setOnClickListener(this);
        findViewById(R.id.btn_battery_info).setOnClickListener(this);
        findViewById(R.id.btn_power_saving).setOnClickListener(this);
        findViewById(R.id.btn_alarm_idle).setOnClickListener(this);
        findViewById(R.id.btn_lru_cache).setOnClickListener(this);
        findViewById(R.id.btn_image_cache).setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_screen_suitable) {
            Intent intent = new Intent(this, ScreenSuitableActivity.class);
            startActivity(intent);
        }else if (v.getId() == R.id.btn_window_style) {
            Intent intent = new Intent(this, WindowStyleActivity.class);
            startActivity(intent);
        }else if (v.getId() == R.id.btn_remove_task) {
            Intent intent = new Intent(this, RemoveTaskActivity.class);
            startActivity(intent);
        }else if (v.getId() == R.id.btn_logout_service) {
            Intent intent = new Intent(this, LogoutServiceActivity.class);
            startActivity(intent);
        }else if (v.getId() == R.id.btn_refer_strong) {
            Intent intent = new Intent(this, ReferStrongActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_refer_weak) {
            Intent intent = new Intent(this, ReferWeakActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_thread_pool) {
            Intent intent = new Intent(this, ThreadPoolActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_schedule_pool) {
            Intent intent = new Intent(this, SchedulePoolActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_battery_info) {
            Intent intent = new Intent(this, BatteryInfoActivity.class);
            startActivity(intent);
        }else if (v.getId() == R.id.btn_power_saving) {
            Intent intent = new Intent(this, PowerSavingActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_alarm_idle) {
            Intent intent = new Intent(this, AlarmIdleActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_lru_cache) {
            Intent intent = new Intent(this, LruCacheActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_image_cache) {
            Intent intent = new Intent(this, ImageCacheActivity.class);
            startActivity(intent);
        }




    }
}
