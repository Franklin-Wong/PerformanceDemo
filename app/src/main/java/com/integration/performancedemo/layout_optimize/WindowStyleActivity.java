package com.integration.performancedemo.layout_optimize;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.integration.performancedemo.R;
import com.integration.performancedemo.widget.CustomDialog;

public class WindowStyleActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "WindowStyleActivity";
    private int mStyle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window_style);
        initSpinner();


    }

    private void initSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.item_select, backgroundArray);
        Spinner spinner = findViewById(R.id.sp_background);
        spinner.setEnabled(true);
        spinner.setPrompt("");
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    private String[] backgroundArray = {
            "不显示对话框", "android:windowBackground风格",
            "android:background风格", "android:windowFrame风格"
    };
    private int[] styleArray = {0, R.style.CustomWindowBackground,
            R.style.CustomBackground, R.style.CustomFrame};

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            //创建自定义对对话框
            CustomDialog dialog = new CustomDialog(WindowStyleActivity.this, mStyle);
            dialog.show();
        }
    };
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mStyle = styleArray[position];
        new Handler().postDelayed(mRunnable, 500);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
