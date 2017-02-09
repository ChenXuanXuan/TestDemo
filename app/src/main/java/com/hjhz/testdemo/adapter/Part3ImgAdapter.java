package com.hjhz.testdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.gjiazhe.scrollparallaximageview.ScrollParallaxImageView;
import com.gjiazhe.scrollparallaximageview.parallaxstyle.VerticalMovingStyle;
import com.hjhz.testdemo.R;
import com.hjhz.testdemo.entity.NewsEntity;
import com.hjhz.testdemo.util.ImageLoaderUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 陈宣宣 on 2016/4/21.
 */
public class Part3ImgAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<NewsEntity> list;
    private ImageLoader imageLoader;
    LayoutInflater inflater = null;

    public Part3ImgAdapter(Context context, List<NewsEntity> list) {
        this.context = context;
        this.list = (ArrayList<NewsEntity>) list;
        inflater = LayoutInflater.from(context);
        imageLoader = ImageLoaderUtil.getImageLoader(context);

    }
    @Override
    public int getCount() {
        return list.size();
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
            convertView = inflater.inflate(R.layout.fragment_part3_item, null);
            hoder = new ViewHolder(convertView);
            convertView.setTag(hoder);
        }else{
            hoder = (ViewHolder) convertView.getTag();
        }

        imageLoader.displayImage(list.get(position).getImgUrl(),
                hoder.img, ImageLoaderUtil.options);


        return convertView;
    }


    static class ViewHolder {
        @InjectView(R.id.img)
        ScrollParallaxImageView img;


        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
            img.setParallaxStyles(new VerticalMovingStyle());
        }

    }
}