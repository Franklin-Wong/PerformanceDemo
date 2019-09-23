package com.integration.performancedemo.memory;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.integration.performancedemo.R;
import com.integration.performancedemo.utils.DateUtil;

public class ReferStrongActivity extends AppCompatActivity implements View.OnClickListener {
    private final static String TAG = "ReferStrongActivity";
    private static TextView tv_strong;
    private Button btn_strong;
    private static String mDesc = "";
    private boolean isRunning = false;
    public StrongHandler mStrongHandler = new StrongHandler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer_strong);
        tv_strong = findViewById(R.id.tv_strong);
        btn_strong = findViewById(R.id.btn_strong);
        btn_strong.setOnClickListener(this);
        TextView tv_start = findViewById(R.id.tv_start);
        tv_start.setText("页面打开时间为"+ DateUtil.getNowTime());

    }


    @Override
    public void onClick(View v) {
        if (!isRunning) {
            mStrongHandler.postDelayed(mTask, 1000);
            //修改按钮提示
            btn_strong.setText("取消定时任务");
        } else {
            mStrongHandler.removeCallbacks(mTask);
            btn_strong.setText("开始定时任务");
        }
        isRunning = !isRunning;
    }
    private Runnable mTask = new Runnable() {
        @Override
        public void run() {
            //发出空消息
            mStrongHandler.sendEmptyMessage(0);
            //延时后，再次发出
            mStrongHandler.postDelayed(this, 2000);
        }
    };
    private static class StrongHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mDesc = String.format("%s打印一次测试日志%s\n", mDesc, DateUtil.getNowTime());
            tv_strong.setText(mDesc);
            Log.i(TAG, "handleMessage: "+mDesc);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mStrongHandler.removeCallbacksAndMessages(null);
    }
}
