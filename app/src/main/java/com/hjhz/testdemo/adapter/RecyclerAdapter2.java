package com.hjhz.testdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
 * Created by 陈宣宣 on 2016/5/5.
 */
public class RecyclerAdapter2 extends RecyclerView.Adapter<RecyclerAdapter2.MyViewHolder> {

    private Context context;
    private ArrayList<NewsEntity> list;
    private ImageLoader imageLoader;

    public RecyclerAdapter2(Context context,List<NewsEntity> list){
        this.context = context;
        this.list = (ArrayList<NewsEntity>) list;
        imageLoader = ImageLoaderUtil.getImageLoader(context);
    }


    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
        void onItemLongClick(View view , int position);
    }
    private OnItemClickLitener mOnItemClickLitener;
    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerpubu_item,viewGroup,false);
        MyViewHolder vh = new MyViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        imageLoader.displayImage(list.get(position).getImgUrl(),
                holder.img, ImageLoaderUtil.options);
        holder.text.setText(list.get(position).getContent());

        // 如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null)
        {
            holder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener()
            {
                @Override
                public boolean onLongClick(View v)
                {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.img)
        ImageView img;
        @InjectView(R.id.text)
        TextView text;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }

    }
}
