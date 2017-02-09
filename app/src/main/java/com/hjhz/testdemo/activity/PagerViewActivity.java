package com.hjhz.testdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.hjhz.testdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 陈宣宣 on 2016/12/15.
 */
public class PagerViewActivity extends Activity {
    ViewPager mViewPager;
    private List<View> mFragments;
    private int[] images={R.drawable.loading1, R.drawable.loading2,R.drawable.loading3, R.drawable.loading4,R.drawable.loading5};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagerview);

        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setPageMargin(50);
        mFragments = new ArrayList<View>();

        for (int i = 0; i < 5; i++) {
            ImageView image=new ImageView(this);
            image.setImageResource(images[i]);
            //image.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
            //image.setScaleType(ImageView.ScaleType.CENTER_CROP);
            mFragments.add(image);

        }
        mViewPager.setAdapter(new MyPagerAdapter(mFragments));
        mViewPager.setCurrentItem(0);
    }


    public class MyPagerAdapter extends PagerAdapter {
        public List<View> mListViews;

        public MyPagerAdapter(List<View> mListViews) {
            this.mListViews = mListViews;
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView(mListViews.get(arg1));
        }

        @Override
        public void finishUpdate(View arg0) {
        }

        @Override
        public int getCount() {
            return mListViews.size();
        }

        @Override
        public Object instantiateItem(View arg0, int arg1) {
            ((ViewPager) arg0).addView(mListViews.get(arg1), 0);
            return mListViews.get(arg1);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == (arg1);
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {
        }
    }
}
