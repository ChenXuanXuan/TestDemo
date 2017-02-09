package com.hjhz.testdemo.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hjhz.testdemo.R;
import com.hjhz.testdemo.entity.NewsEntity;
import com.hjhz.testdemo.util.ImageLoaderUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class NewsAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<NewsEntity> list;
    private ImageLoader imageLoader;
    LayoutInflater inflater = null;

    public NewsAdapter(Context context, List<NewsEntity> list) {
        if(context!=null){
            this.context = context;
            this.list = (ArrayList<NewsEntity>) list;
            inflater = LayoutInflater.from(context);
            imageLoader = ImageLoaderUtil.getImageLoader(context);
        }
    }
    @Override
    public int getCount() {
        if(list!=null){
            return list.size();
        }else{
            return 0;
        }

    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder hoder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_news, null);
            hoder = new ViewHolder(convertView);
            convertView.setTag(hoder);
        }else{
            hoder = (ViewHolder) convertView.getTag();
        }


        hoder.content.setText(list.get(position).getContent());

        hoder.title.setText(list.get(position).getTitle());

        hoder.textView26.setText(list.get(position).getCommentNum() + "评论");

        imageLoader.displayImage(list.get(position).getImgUrl(),
                hoder.img, ImageLoaderUtil.options);


        return convertView;
    }


    static class ViewHolder {
        @InjectView(R.id.textView23)
        TextView title;
        @InjectView(R.id.texcontent)
        TextView content;
        @InjectView(R.id.imageView9)
        ImageView img;
        @InjectView(R.id.textView25)
        TextView tag;
        @InjectView(R.id.pinglun)
        RelativeLayout pinglun;

        @InjectView(R.id.textView26)
        TextView textView26;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }

    }
}