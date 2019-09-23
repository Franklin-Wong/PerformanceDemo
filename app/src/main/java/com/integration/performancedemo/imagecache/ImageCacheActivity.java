package com.integration.performancedemo.imagecache;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.integration.performancedemo.R;

public class ImageCacheActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "ImageCacheActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_cache);


    }


    @Override
    public void onClick(View v) {

    }
}
