package com.hjhz.testdemo.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hjhz.testdemo.application.AppContext;

/**
 * Created by 陈宣宣 on 2016/6/30.
 */
public class MyViewHolder {

    private SparseArray<View> mViews;
    private int mPosition;
    private View mConvertView;

    public MyViewHolder(Context context, ViewGroup parent, int layoutId, int position){

        this.mPosition = position;
        this.mViews = new SparseArray<View>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId,parent,false);
        mConvertView.setTag(this);
    }


    public static MyViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position){
        if (convertView == null){
            return new MyViewHolder(context, parent, layoutId, position);
        }else{
            MyViewHolder holder = (MyViewHolder) convertView.getTag();
            holder.mPosition = position;
            return holder;
        }
    }

    /*
    * 获取控件view
    * 使用了泛型，T表示View的子类
    * */
    public <T extends View> T getView(int viewId){
        View view = mViews.get(viewId);
        if(view == null){
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId,view);
        }
        return (T) view;
    }

    public View getmConvertView(){
        return mConvertView;
    }

    /*
    * 设置TextView的值
    * */
    public MyViewHolder setText(int viewId, String text){
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;//为了链式编程
    }

    /*
    * 设置ImageView的值
    * */
    public MyViewHolder setImageResource(int viewId, int resId){
        ImageView imageView = getView(viewId);
        imageView.setImageResource(resId);
        return this;
    }
    public MyViewHolder setImageBitmap(int viewId, Bitmap bitmap){
        ImageView imageView = getView(viewId);
        imageView.setImageBitmap(bitmap);
        return this;
    }
    public MyViewHolder setImageURL(int viewId, String url){
        ImageView imageView = getView(viewId);
        AppContext.imageLoader.displayImage(url, imageView, ImageLoaderUtil.options);
        return this;
    }
}
