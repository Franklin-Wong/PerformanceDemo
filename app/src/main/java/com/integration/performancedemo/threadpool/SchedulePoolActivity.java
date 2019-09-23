package com.integration.performancedemo.threadpool;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.integration.performancedemo.R;
import com.integration.performancedemo.utils.DateUtil;

import java.lang.ref.WeakReference;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SchedulePoolActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private final static String TAG = "SchedulePoolActivity";
    private TextView tv_desc;
    private String mDesc = "";
    private boolean isFirst = true;
    //声明线程池
    private ScheduledExecutorService mSinglePool;
    private ScheduledExecutorService mMulitiPool;
    //声明线程池的周期类型
    private int ONCE = 0;
    private int RATE = 1;
    private int DELAY = 2;


    @Override
    protected void onStart() {
        super.onStart();
        mSinglePool = Executors.newSingleThreadScheduledExecutor();
        mMulitiPool = Executors.newScheduledThreadPool(3);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!mMulitiPool.isTerminated()) {
            mMulitiPool.shutdown();
        }
        if (mSinglePool != null) {
            mSinglePool.shutdown();
        }
    }

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_pool);
        tv_desc = findViewById(R.id.tv_desc);
        initPoolSpinner();
    }

    // 初始化线程池下拉框
    private void initPoolSpinner() {
        ArrayAdapter<String> poolAdapter = new ArrayAdapter<String>(this,
                R.layout.item_select, poolArray);
        Spinner sp_schedule_pool = findViewById(R.id.sp_schedule_pool);
        sp_schedule_pool.setPrompt("请选择定时器线程池类型");
        sp_schedule_pool.setAdapter(poolAdapter);
        sp_schedule_pool.setOnItemSelectedListener(this);
        sp_schedule_pool.setSelection(0);
    }

    private String[] poolArray = {
            "单线程定时器延迟一次", "单线程定时器固定速率", "单线程定时器固定延迟",
            "多线程定时器延迟一次", "多线程定时器固定速率", "多线程定时器固定延迟"
    };

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (isFirst) {
            isFirst = false;
            return;
        }
        //区分单线程与多线程的条件
        ScheduledExecutorService executorService = position < 3 ? mSinglePool : mMulitiPool;

        startPool(executorService, position % 3);

    }

    private void startPool(ScheduledExecutorService pool, int type) {
        mDesc = "";
        //创建多个任务
        for (int i = 0; i < 3; i++) {
            MessageRunnable runnable = new MessageRunnable(i);
            //
            if (type == ONCE) {
                pool.schedule(runnable, 1, TimeUnit.SECONDS);

            } else if (type == RATE) {
                pool.scheduleAtFixedRate(runnable, 2,1,  TimeUnit.SECONDS);

            } else if (type == DELAY) {
                pool.scheduleWithFixedDelay(runnable, 2, 1, TimeUnit.SECONDS);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private MessageHandler mMessageHandler = new MessageHandler(this);
    /**
     *
     */
    public static class MessageHandler extends Handler {
        public static WeakReference<SchedulePoolActivity> sReference;

        public MessageHandler(SchedulePoolActivity activity) {
            sReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            SchedulePoolActivity activity = sReference.get();
            activity.mDesc = String.format("%s%s   子线程的序号是：%s\n", activity.mDesc, DateUtil.getNowTime(), msg.arg1);
            activity.tv_desc.setText(activity.mDesc);
            Log.i(TAG, "handleMessage: "+ activity.mDesc);
        }
    }

    /**
     *
     */
    public class MessageRunnable implements Runnable {
        int mIndex = 0;

        public MessageRunnable(int i) {
            mIndex = i;
        }

        @Override
        public void run() {
            SchedulePoolActivity activity = MessageHandler.sReference.get();
            Message message = activity.mMessageHandler.obtainMessage();
            message.arg1 = mIndex;
            activity.mMessageHandler.sendMessage(message);

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mMessageHandler != null) {
            mMessageHandler.removeCallbacksAndMessages(null);
        }

    }
}
