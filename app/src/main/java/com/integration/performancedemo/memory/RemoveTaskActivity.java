package com.integration.performancedemo.memory;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.integration.performancedemo.R;
import com.integration.performancedemo.utils.DateUtil;

public class RemoveTaskActivity extends AppCompatActivity implements View.OnClickListener {
    private final static String TAG = "RemoveTaskActivity";

    public static final String TASK_EVENT = "com.integration.performancedemo.task";
    private CheckBox ck_remove;
    private TextView tv_remove;
    private Button btn_remove;
    private String mDesc = "";

    private boolean isRunning = false;
    private Handler mHandler = new Handler();
    private TaskReceiver mReceiver;

    /**
     *
     */
    public class  TaskReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && intent.getAction().equals(TASK_EVENT)) {
                mDesc = String.format("%s%s打印一次测试日志\n",mDesc, DateUtil.getNowTime());
                tv_remove.setText(mDesc);
                Log.i(TAG, "onReceive: "+mDesc);
            }

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_task);
        ck_remove = findViewById(R.id.ck_remove);
        tv_remove = findViewById(R.id.tv_remove);
        btn_remove = findViewById(R.id.btn_remove);
        btn_remove.setOnClickListener(this);
        TextView tv_start = findViewById(R.id.tv_start);
        tv_start.setText("页面的打卡时间为：" + DateUtil.getNowTime());

    }

    /**
     *
     */
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(TASK_EVENT);
            ///通过本地广播管理器发出广播
            LocalBroadcastManager.getInstance(RemoveTaskActivity.this).sendBroadcast(intent);
            //
            mHandler.postDelayed(mRunnable, 2000);
        }
    };
    @Override
    public void onClick(View v) {

        if (!isRunning) {
            mHandler.postDelayed(mRunnable, 500);
            //修改按钮文字
            btn_remove.setText("取消异常任务");
        } else {
            mHandler.removeCallbacks(mRunnable);
            //
            btn_remove.setText("开始加入任务");
        }
        //修改任务运行的状态
        isRunning = !isRunning;

    }
    @Override
    protected void onStart() {
        super.onStart();
        //创建接收器
        mReceiver = new TaskReceiver();
        //注册广播接收器
        IntentFilter filter = new IntentFilter();
        filter.addAction(TASK_EVENT);
        //注册广播
        LocalBroadcastManager.getInstance(RemoveTaskActivity.this).registerReceiver(mReceiver, filter);


    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(RemoveTaskActivity.this).unregisterReceiver(mReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (ck_remove.isChecked()) {
            mHandler.removeCallbacks(mRunnable);
        }

    }


}
