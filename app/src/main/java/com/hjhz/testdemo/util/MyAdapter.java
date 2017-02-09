package com.hjhz.testdemo.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by 陈宣宣 on 2016/6/30.
 */
public abstract class MyAdapter<T> extends BaseAdapter {

    protected Context mContext;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    protected int layoutId;

    public MyAdapter(Context context,List<T> datas,int layoutId){
        this.mContext = context;
        this.mDatas = datas;
        this.layoutId = layoutId;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        MyViewHolder holder = MyViewHolder.get(mContext, convertView,parent, layoutId, position);
        convert(holder,mDatas.get(position));
        return holder.getmConvertView();
    }

    public abstract void convert(MyViewHolder holder, T t);
}
