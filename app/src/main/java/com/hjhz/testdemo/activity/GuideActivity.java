package com.hjhz.testdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hjhz.testdemo.R;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity {

    private ViewPager mPager;
    private LinearLayout mNumLayout;
    private int curIndex = 0;
    private int oldIndex = 0;
    private List<View> mFragments;
    private int[] images={R.drawable.loading1, R.drawable.loading2,R.drawable.loading3, R.drawable.loading4,R.drawable.loading5};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏

        setContentView(R.layout.activity_guide);

        initView();
    }

    private void initView(){
        mPager = (ViewPager) findViewById(R.id.loading_vp_main);
        mNumLayout = (LinearLayout) findViewById(R.id.loading_lin_point);

        mPager.setOffscreenPageLimit(3);
        mFragments = new ArrayList<View>();

        for (int i = 0; i < 5; i++) {
            ImageView image=new ImageView(this);
            image.setImageResource(images[i]);
            image.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            image.setScaleType(ImageView.ScaleType.CENTER_CROP);
            mFragments.add(image);
            if(i==4){
                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(GuideActivity.this,MainActivity.class));
                        finish();
                    }
                });
            }
        }
        mPager.setAdapter(new MyPagerAdapter(mFragments));
        mPager.setCurrentItem(0);

        //圆点
        int Ovalheight = (int) (mNumLayout.getLayoutParams().height * 0.7);
        int Ovalmargin = (int) (mNumLayout.getLayoutParams().height * 0.3);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                Ovalheight, Ovalheight);
        layoutParams.setMargins(Ovalmargin, 0, Ovalmargin, 0);
        for (int i = 0; i < images.length; i++) {
            View v = new View(this);
            v.setLayoutParams(layoutParams);
            v.setBackgroundResource(R.drawable.icon_dot_normal);
            mNumLayout.addView(v);
        }
        mNumLayout.getChildAt(0).setBackgroundResource(R.drawable.icon_dot_select);

        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                curIndex = position % images.length;
                if (mNumLayout != null && images.length > 1) {
                    mNumLayout.getChildAt(oldIndex).setBackgroundResource(R.drawable.icon_dot_normal);
                    mNumLayout.getChildAt(curIndex).setBackgroundResource(R.drawable.icon_dot_select);
                    oldIndex = curIndex;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(GuideActivity.this,WelcomeActivity.class));
        finish();
    }
}
