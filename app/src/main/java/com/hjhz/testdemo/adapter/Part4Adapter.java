package com.hjhz.testdemo.adapter;

import android.content.Context;

import com.hjhz.testdemo.R;
import com.hjhz.testdemo.entity.NewsEntity;
import com.hjhz.testdemo.util.DateUtil;
import com.hjhz.testdemo.util.MyAdapter;
import com.hjhz.testdemo.util.MyViewHolder;

import java.util.List;

/**
 * Created by 陈宣宣 on 2016/4/25.
 */
public class Part4Adapter extends MyAdapter<NewsEntity> {

    //private ImageLoader imageLoader;

    public Part4Adapter(Context context,List<NewsEntity> list){
        super(context, list, R.layout.fragment_part4_item);//调用父构造方法
        //imageLoader = ImageLoaderUtil.getImageLoader(context);
    }

    @Override
    public void convert(MyViewHolder holder, NewsEntity newsEntity) {
        //链式编程
        holder.setText(R.id.name,newsEntity.getTitle())
                .setText(R.id.content,newsEntity.getContent())
                .setText(R.id.time,DateUtil.getRightTime(newsEntity.getCreateTime()));
        holder.setImageURL(R.id.img_circle,newsEntity.getImgUrl());

        //getView方法可以设置其它控件的值  参考如下
//        imageLoader.displayImage(newsEntity.getImgUrl(),
//                (CircleImageView)holder.getView(R.id.img_circle), ImageLoaderUtil.options);
    }
}
