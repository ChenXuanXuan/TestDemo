package com.hjhz.testdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hjhz.testdemo.R;
import com.hjhz.testdemo.entity.NewsEntity;
import com.hjhz.testdemo.util.DateUtil;
import com.hjhz.testdemo.util.ImageLoaderUtil;
import com.hjhz.testdemo.view.CircleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 陈宣宣 on 2016/5/3.
 */
public class RecyclerAdapter1 extends RecyclerView.Adapter<RecyclerAdapter1.MyViewHolder> {

    private Context context;
    private ArrayList<NewsEntity> list;
    private ImageLoader imageLoader;

    public RecyclerAdapter1(Context context,List<NewsEntity> list){
        this.context = context;
        this.list = (ArrayList<NewsEntity>) list;
        imageLoader = ImageLoaderUtil.getImageLoader(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_part4_item,viewGroup,false);
        MyViewHolder vh = new MyViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvName.setText(list.get(position).getTitle());
        holder.tvContent.setText(list.get(position).getContent());
        holder.tvTime.setText(DateUtil.getRightTime(list.get(position).getCreateTime()));
        imageLoader.displayImage(list.get(position).getImgUrl(),
                holder.img, ImageLoaderUtil.options);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.img_circle)
        CircleImageView img;
        @InjectView(R.id.name)
        TextView tvName;
        @InjectView(R.id.time)
        TextView tvTime;
        @InjectView(R.id.content)
        TextView tvContent;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }

    }
}
