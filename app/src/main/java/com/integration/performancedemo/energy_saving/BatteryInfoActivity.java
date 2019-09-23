package com.integration.performancedemo.energy_saving;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.integration.performancedemo.R;
import com.integration.performancedemo.utils.DateUtil;

public class BatteryInfoActivity extends AppCompatActivity {
    private static final String TAG = "BatteryInfoActivity";
    private TextView tv_battery_change;
    private static String[] mStatus = {"不存在", "未知", "正在充电", "正在断电", "不在充电", "充满"};
    private static String[] mHealthy = {"不存在", "未知", "良好", "过热", "坏了", "短路", "未知错误", "冷却"};
    private static String[] mPlugged = {"电池", "充电器", "USB", "不存在", "无线"};

    private BatteryInfoReceiver mBatteryInfoReceiver;
    /**
     * 通过广播接收电池的电量变化
     */
    @Override
    protected void onStart() {
        super.onStart();
        //注册电池广播
        mBatteryInfoReceiver  = new BatteryInfoReceiver();
        registerReceiver(mBatteryInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }


    @Override
    protected void onStop() {
        super.onStop();
        //注销电池广播
        unregisterReceiver(mBatteryInfoReceiver);
    }

    public class BatteryInfoReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //获取广播中的电池信息
            if (intent != null) {
                //通过管理器BatteryManager获取电池的相关信息
                String techs = intent.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY);
                int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 0);
                int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, 0);
                int healthy = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0);
                int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
                int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 3);
                int voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);
                int temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0);
                boolean present = intent.getBooleanExtra(BatteryManager.EXTRA_PRESENT, false);

                String desc = String.format("%s : 当前收到广播 : %S", DateUtil.getNowTime(), intent.getAction());
                desc = String.format(" %s\n当前刻度是：%s", desc, scale);
                desc = String.format(" %s\n当前状态是：%s", desc, mStatus[status]);
                desc = String.format(" %s\n当前健康是：%s", desc, mHealthy[healthy]);
                desc = String.format(" %s\n当前电量是：%s", desc, level);
                desc = String.format(" %s\n当前充电是：%s", desc, mPlugged[plugged]);
                desc = String.format(" %s\n当前电压是：%s", desc, voltage);
                desc = String.format(" %s\n当前技术是：%s", desc, techs);
                desc = String.format(" %s\n当前温度是：%s", desc, temperature / 10);
                desc = String.format(" %s\n是否提供电池：%s", desc, present ? "是" : "否");
                tv_battery_change.setText(desc);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery_info);
        tv_battery_change = findViewById(R.id.tv_battery_change);

    }
}
