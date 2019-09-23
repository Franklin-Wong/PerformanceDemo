package com.integration.performancedemo.layout_optimize;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.integration.performancedemo.R;

import java.util.List;

/**
 * Created by Wongerfeng on 2019/9/5.
 */
public class PlanetAdapter extends BaseAdapter {
    private static final String TAG = "PlanetAdapter";
    private Context mContext;
    private int mLayoutId;
    private List<Planet> mPlanetList;
    private int mBackgroundColor;

    public PlanetAdapter(Context context, int layoutId, List<Planet> planetList, int color) {
        mContext = context;
        this.mLayoutId = layoutId;
        mPlanetList = planetList;
        mBackgroundColor = color;
        Log.i(TAG, "PlanetAdapter: ");
    }

    @Override
    public int getCount() {
        return mPlanetList.size();
    }

    @Override
    public Planet getItem(int position) {
        return mPlanetList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(mLayoutId, null);
            viewHolder.iv_icon = convertView.findViewById(R.id.iv_icon);
            viewHolder.ll_item = convertView.findViewById(R.id.ll_item);
            viewHolder.tv_desc = convertView.findViewById(R.id.tv_desc);
            viewHolder.tv_name = convertView.findViewById(R.id.tv_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Planet planet = getItem(position);
        if (planet != null) {
            viewHolder.tv_name.setText(planet.name);
            viewHolder.tv_desc.setText(planet.desc);
//            viewHolder.iv_icon.setImageResource(planet.image);
            viewHolder.ll_item.setBackgroundColor(mBackgroundColor);
        }
        return convertView;
    }

    public class ViewHolder{
        LinearLayout ll_item;
        ImageView iv_icon;
        TextView tv_name;
        TextView tv_desc;
    }
}
