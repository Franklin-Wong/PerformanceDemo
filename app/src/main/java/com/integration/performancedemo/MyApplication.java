package com.integration.performancedemo;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.integration.performancedemo.utils.DateUtil;

/**
 * Created by Wongerfeng on 2019/9/20.
 */
public class MyApplication extends Application {
    private static final String TAG = "MyApplication";
    private static MyApplication mApplication;
    private String mChange = "";

    /**
     * 用单例模式，获取唯一静态实例
     * @return
     */
    public static MyApplication getApplication() {
        return mApplication;
    }

    public String getChange() {
        return mApplication.mChange;
    }

    public void setChange(String change) {
        mApplication.mChange = mApplication.mChange +  change;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //z在打开应用时，为静态实例赋值，初始化本类的实例
        mApplication = this;
        //创建广播，添加过滤器，并注册广播
        LockScreenReceiver receiver = new LockScreenReceiver();
        IntentFilter filter = new IntentFilter();
        //添加亮屏过滤事件
        filter.addAction(Intent.ACTION_SCREEN_ON);
        //添加息屏过滤事件
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        //添加用户解锁过滤事件
        filter.addAction(Intent.ACTION_USER_PRESENT);
        registerReceiver(receiver, filter);
    }


    private class LockScreenReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                //创建新消息字符串，避免重复添加
                String mChange = "";
                //判断接收到的消息
                mChange = String.format("%s\n%s 收到广播 ： %s", mChange, DateUtil.getNowTime(), intent.getAction());
                //亮屏
                if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
                    mChange = String.format("%s\n%s 这是亮屏事件，可以执行耗电操作", mChange, DateUtil.getNowTime());

                    //息屏状态
                } else if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                    mChange = String.format("%s\n%s 息屏事件，停止耗电操作", mChange, DateUtil.getNowTime());
                    //用户解锁
                } else if (intent.getAction().equals(Intent.ACTION_USER_PRESENT)) {
                    mChange = String.format("%s\n%s 用户解锁", mChange, DateUtil.getNowTime());
                }
                Log.i(TAG, "onReceive: "+ mChange);
                MyApplication.getApplication().setChange(mChange);
            }

        }
    }
}
