package com.utsoft.simple.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.utsoft.simple.R;

import java.util.List;

/**
 * Created by hasee on 2016/12/7.
 */

public class ImgBrowserAdapter extends BaseAdapter {
    private List<String> list;
    private Context mContext;

    public ImgBrowserAdapter(List<String> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return list == null?0:list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null){
            convertView = View.inflate(mContext, R.layout.item_mainactivity_gridview,null);
            imageView = (ImageView) convertView.findViewById(R.id.img_gridView);
            convertView.setTag(imageView);
        }else {
            imageView = (ImageView) convertView.getTag();
        }
        Glide.with(mContext).load(list.get(position)).into(imageView);
        return convertView;
    }
}
