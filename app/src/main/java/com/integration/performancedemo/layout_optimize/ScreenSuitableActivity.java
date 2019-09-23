package com.integration.performancedemo.layout_optimize;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewStub;
import android.widget.GridView;
import android.widget.ListView;

import com.integration.performancedemo.R;

public class ScreenSuitableActivity extends AppCompatActivity {
    private static final String TAG = "ScreenSuitableActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_suitable);
        //设置标题
        setTitle("自适应布局演示页面");
        //获取屏幕的设置
        Configuration configuration = getResources().getConfiguration();

        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            showGrid();
        } else {
            showList();
        }

    }

    private void showGrid() {
        Log.i(TAG, "showGrid: ");
        // 从布局文件中获取名叫vs_grid的占位视图
        ViewStub vs_grid = findViewById(R.id.vs_grid);
        vs_grid.inflate();
        // 下面通过网格视图展示行星信息
        GridView gv_hello = findViewById(R.id.gv_hello);
        gv_hello.setAdapter(new PlanetAdapter(this, R.layout.item_grid, Planet.getDefaultList(), Color.LTGRAY));

    }

    private void showList() {
        Log.i(TAG, "showList: ");
        // 从布局文件中获取名叫vs_list的占位视图
        ViewStub vs_list = findViewById(R.id.vs_list);
        vs_list.inflate();
        // 下面通过列表视图展示行星信息
        ListView lv_hello = findViewById(R.id.lv_hello);
        lv_hello.setAdapter(new PlanetAdapter(this, R.layout.item_list, Planet.getDefaultList(), Color.LTGRAY));
    }
}
