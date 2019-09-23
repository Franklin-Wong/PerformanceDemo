package com.integration.performancedemo.imagecache;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.integration.performancedemo.R;
import com.integration.performancedemo.utils.DateUtil;

import java.util.Map;

public class LruCacheActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "LruCacheActivity";

    private TextView tv_lru_cache;
    //声明一个LRU缓存对象
    private LruCache<String, String> mLanguageLru;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lru_cache);
        tv_lru_cache = findViewById(R.id.tv_lru_cache);
        findViewById(R.id.btn_android).setOnClickListener(this);
        findViewById(R.id.btn_ios).setOnClickListener(this);
        findViewById(R.id.btn_java).setOnClickListener(this);
        findViewById(R.id.btn_cpp).setOnClickListener(this);
        findViewById(R.id.btn_python).setOnClickListener(this);
        findViewById(R.id.btn_net).setOnClickListener(this);
        findViewById(R.id.btn_php).setOnClickListener(this);
        findViewById(R.id.btn_perl).setOnClickListener(this);
        //创建LRU缓存对象,设置大小是 5
        mLanguageLru = new LruCache<String, String>(5);

    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        //获取按钮中的文案
        String string = ((Button) v).getText().toString();
        //添加到缓存
        mLanguageLru.put(string, DateUtil.getNowTime());

        printLru();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void printLru() {
        //声明描述文字
        String desc = "";
        //获取缓存队列
        Map<String, String> snapshot = mLanguageLru.snapshot();
        int size = snapshot.size();
        Log.i(TAG, "printLru: size = "+ size);
        //更新缓存队列大小，
        if (size >= 5) {
            mLanguageLru.resize(10);
        }
        for (Map.Entry<String, String> item:
             snapshot.entrySet()) {

            desc = String.format("%s%s  最后一次更新时间是 %s\n ", desc, item.getKey(), item.getValue());
            tv_lru_cache.setText(desc);
        }
        Log.i(TAG, "printLru: "+ desc);
    }

}
