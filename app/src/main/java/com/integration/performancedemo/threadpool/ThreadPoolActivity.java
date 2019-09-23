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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private final static String TAG = "ThreadPoolActivity";
    private TextView tv_desc;
    private String mDesc = "";
    //是否第一次进入
    private boolean isFirst = true;

    private String[] poolArray = new String[] {"单线程线程池", "多线程线程池", "无限制线程池", "自定义线程池"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_pool);

        tv_desc = findViewById(R.id.tv_desc);
        initPoolSpinner();
    }

    private void initPoolSpinner() {
        Spinner spinner = findViewById(R.id.sp_thread_pool);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.item_select, poolArray);
        spinner.setAdapter(adapter);
        spinner.setPrompt("请选择普通线程池类型");
        spinner.setEnabled(true);
        spinner.setOnItemSelectedListener(this);
        spinner.setSelection(0);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //
        if (isFirst) {
            isFirst = false;
            return;
        }
        if (position == 0) {
            //single线程
            ExecutorService executor = Executors.newSingleThreadExecutor();
            startPool(executor);
        } else if (position == 1) {
            //多线程线程池
            ExecutorService pool = Executors.newFixedThreadPool(4);
            startPool(pool);

        } else if (position == 2) {
            //无限制线程池
            ExecutorService executor = Executors.newCachedThreadPool();
            startPool(executor);

        } else if (position == 3) {
            //自定义线程池
            ThreadPoolExecutor executor =
                    new ThreadPoolExecutor(2,5,60,
                    TimeUnit.SECONDS,new LinkedBlockingDeque<Runnable>(19));
            startPool(executor);
        }

    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    /**
     * 创建多个子线程测试
     * @param executor
     */
    private void startPool(ExecutorService executor) {
        //清空文字
        mDesc = "";
        for (int i = 0; i < 20; i++) {
            //创建子线程
            MessageRunnable runnable = new MessageRunnable(i);
            //线程池运行任务
            executor.execute(runnable);
        }
    }

    private MessageHandler mMessageHandler = new MessageHandler(this);

    public static class MessageHandler extends Handler {
        //弱引用缓存对象
        public static WeakReference<ThreadPoolActivity> sReference;

        private MessageHandler(ThreadPoolActivity activity) {
            sReference = new WeakReference<>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ThreadPoolActivity activity = sReference.get();
            activity.mDesc = String.format("%s%s    子线程的序号是：%s\n", activity.mDesc, DateUtil.getNowTime(), msg.arg1);
            activity.tv_desc.setText(activity.mDesc);
            Log.i(TAG, "handleMessage: "+ activity.mDesc);
        }
    }

    /**
     * 多线程执行 的任务
     */
    private class MessageRunnable implements Runnable {
        private int index;
        private MessageRunnable(int index) {
            this.index = index;
        }

        @Override
        public void run() {
            //获取handler中的activity的弱引用对象
            ThreadPoolActivity activity = MessageHandler.sReference.get();
            //使用弱引用对象
            Message message = activity.mMessageHandler.obtainMessage();
            message.arg1 = index;
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
