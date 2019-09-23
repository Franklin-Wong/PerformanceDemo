package com.integration.performancedemo.energy_saving;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.integration.performancedemo.R;
import com.integration.performancedemo.utils.DateUtil;

public class AlarmIdleActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "AlarmIdleActivity";
    public static final String ALARM_EVENT = "com.integration.performancedemo.idle";
    private Button btn_alarm;
    private static TextView tv_alarm;
    //意图对象
    private PendingIntent mPendingIntent;
    //闹钟管理器
    private AlarmManager mAlarmManager;
    //描述信息
    private String mDesc = "";
    //是否首次进入
    private boolean isRunning = false;
    //
    private int mDelay = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_idle);
        tv_alarm = findViewById(R.id.tv_alarm);
        btn_alarm = findViewById(R.id.btn_alarm);
        btn_alarm.setOnClickListener(this);
        Intent intent = new Intent(ALARM_EVENT);
        //PendingIntent 获取一个扮演广播的意图 相当于 sendBroadcast(), 这个意图在广播组件中使用
        mPendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        TextView tv_start = findViewById(R.id.tv_start);
        tv_start.setText("页面打开时间为：" + DateUtil.getNowTime());
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAlarmReceiver = new AlarmReceiver();
        registerReceiver(mAlarmReceiver, new IntentFilter(ALARM_EVENT));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mAlarmReceiver);

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_alarm) {
            //未运行
            if (!isRunning) {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                    mAlarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + mDelay,
                            mPendingIntent);
                } else {
                    mAlarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + mDelay, mPendingIntent);

                }

                mDesc = DateUtil.getNowTime() + "设置闹钟";
                tv_alarm.setText(mDesc);
                btn_alarm.setText("取消闹钟");
            } else {

                mDesc = DateUtil.getNowTime() + "取消闹钟";
                //取消闹钟意图
                mAlarmManager.cancel(mPendingIntent);
                btn_alarm.setText("设置闹钟");
            }
            isRunning = ! isRunning;
        }
    }

    private AlarmReceiver mAlarmReceiver;

    /**
     * 接收闹钟消息的广播
     */
    public class AlarmReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                mDesc = String.format("%s\n 闹钟时间到达%s", mDesc ,DateUtil.getNowTime() );
                tv_alarm.setText(mDesc);
                Log.i(TAG, "onReceive: "+ mDesc);
                repeatAlarm();
            }
        }
    }

    private void repeatAlarm() {

        //取消原来的闹钟
        mAlarmManager.cancel(mPendingIntent);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            mAlarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + mDelay,
                    mPendingIntent);
        } else {
            mAlarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + mDelay, mPendingIntent);

        }
    }
}

