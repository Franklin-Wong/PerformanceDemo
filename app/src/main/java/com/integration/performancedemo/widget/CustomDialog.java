package com.integration.performancedemo.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.integration.performancedemo.R;

/**
 * Created by Wongerfeng on 2019/9/6.
 */
public class CustomDialog implements View.OnClickListener {
    private Context mContext;
    private int mStyle;
    private View mView;
    private Dialog mDialog;
    private TextView tv_title;
    private TextView tv_message;


    public CustomDialog(Context context, int style) {
        mContext = context;
        this.mStyle = style;
        mView = LayoutInflater.from(mContext).inflate(R.layout.dialog_background, null);
        tv_title = mView.findViewById(R.id.tv_title);
        tv_message = mView.findViewById(R.id.tv_message);
        mDialog = new Dialog(mContext, mStyle);
        mView.findViewById(R.id.btn_ok).setOnClickListener(this);
    }


    public void show() {
        //获取对话框的视图
        mDialog.getWindow().setContentView(mView);
        //设置对话框的布局参数
        mDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mDialog.show();
    }

    @Override
    public void onClick(View v) {
        if (mDialog != null) {
            mDialog.dismiss();
        }

    }
}
