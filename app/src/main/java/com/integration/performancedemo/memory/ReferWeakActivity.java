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

import java.lang.ref.WeakReference;

public class ReferWeakActivity extends AppCompatActivity implements View.OnClickListener {
    private final static String TAG = "ReferWeakActivity";
    private TextView tv_weak;
    private Button btn_weak;
    private String mDesc = "";
    private boolean isRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer_weak);
        tv_weak = findViewById(R.id.tv_weak);
        btn_weak = findViewById(R.id.btn_weak);
        btn_weak.setOnClickListener(this);
        TextView tv_start = findViewById(R.id.tv_start);
        tv_start.setText("页面打开时间为：" + DateUtil.getNowTime());

    }

    //创建子线程定时的任务
    private Runnable mTask = new Runnable() {
        @Override
        public void run() {
            //发送空消息
            mWeakHandler.sendEmptyMessage(0);
            //定时任务延时2000
            mWeakHandler.postDelayed(this, 2000);
        }
    };

    @Override
    public void onClick(View v) {
        if (!isRunning) {
            mWeakHandler.post(mTask);
            btn_weak.setText("取消定时任务");
        } else {
            mWeakHandler.removeCallbacks(mTask);
            btn_weak.setText("开始定时任务");
        }
        //
        isRunning = !isRunning;

    }
    private WeakHandler mWeakHandler = new WeakHandler(this);

    public static class WeakHandler extends Handler {
        private static WeakReference<ReferWeakActivity> mReference;

        public WeakHandler(ReferWeakActivity weakActivity) {
            mReference = new WeakReference<ReferWeakActivity>(weakActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ReferWeakActivity activity = mReference.get();
            activity.mDesc = String.format("%s%s打印一次测试日志\n", activity.mDesc, DateUtil.getNowTime());
            activity.tv_weak.setText(activity.mDesc);
            Log.i(TAG, "handleMessage: "+ activity.mDesc);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWeakHandler != null) {
            mWeakHandler.removeCallbacksAndMessages(null);
        }

    }
}
