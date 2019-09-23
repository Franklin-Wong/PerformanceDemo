package com.integration.performancedemo.memory;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.integration.performancedemo.R;
import com.integration.performancedemo.utils.DateUtil;


public class LogoutServiceActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "LogoutServiceActivity";
    private CheckBox ck_logout;
    private Button btn_alarm;
    private static TextView tv_alarm;
    private AlarmManager mAlarmManager;
    private PendingIntent mPendingIntent;
    private boolean isRunning = false;
    private int mDelay = 3000;
    private String desc = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout_service);
        ck_logout = findViewById(R.id.ck_logout);
        tv_alarm = findViewById(R.id.tv_alarm);
        btn_alarm = findViewById(R.id.btn_alarm);
        btn_alarm.setOnClickListener(this);
        TextView tv_start = findViewById(R.id.tv_start);
        tv_start.setText("页面打开时间为：" + DateUtil.getNowTime());

        mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        //创建一个意图
        Intent intent = new Intent(ALARM_RECEIVER);
        //获取延时意图
        mPendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

    }

    @Override
    public void onClick(View v) {
        if (!isRunning) {
            //先注销闹钟
//            mAlarmManager.cancel(mPendingIntent);
            //设置重复的闹钟
            mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), mDelay, mPendingIntent);
//            mAlarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+ mDelay, mPendingIntent);
            desc = DateUtil.getNowTime() +"设置闹钟\n";
            tv_alarm.setText(desc);
            //修改按钮按钮提示
            btn_alarm.setText("取消定时任务");
        } else {
            mAlarmManager.cancel(mPendingIntent);
            btn_alarm.setText("开始定时任务");
        }
        isRunning = !isRunning;
    }

    public static final String ALARM_RECEIVER = "com.integration.performancedemo.memory.alarm";
    private AlarmReceiver mAlarmReceiver;
    //创建定时广播接收器
    public class AlarmReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && intent.getAction().equals(ALARM_RECEIVER)) {
                desc = String.format("%s%s 闹钟时间到达\n", desc, DateUtil.getNowTime());
                tv_alarm.setText(desc);
                Log.i(TAG, "onReceive: "+ desc);

                repeatAlarm();
            }

        }
    }

    private void repeatAlarm() {
        //取消设置的定时器
        mAlarmManager.cancel(mPendingIntent);
        //设置延时的定时器，重复闹钟
        mAlarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + mDelay,
                mPendingIntent);


    }

    @Override
    protected void onStart() {
        super.onStart();
        mAlarmReceiver = new AlarmReceiver();
        registerReceiver(mAlarmReceiver, new IntentFilter(ALARM_RECEIVER));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mAlarmReceiver);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (ck_logout.isChecked()) {
            unregisterReceiver(mAlarmReceiver);
            mAlarmManager.cancel(mPendingIntent);
        }

    }
}
