package com.hjhz.testdemo.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hjhz.testdemo.R;
import com.hjhz.testdemo.base.BaseActivity;
import com.hjhz.testdemo.util.ImageLoaderUtil;
import com.hjhz.testdemo.view.PhotoViewPager;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;
import butterknife.InjectView;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by 陈宣宣 on 2016/4/22.
 */
public class PhotoDetailActivity extends BaseActivity {
    @InjectView(R.id.view_pager)
    PhotoViewPager mViewPager;
    private PhotoViewAdapter mAdapter;
    private PhotoView image;
    private TextView tvPage;
    private PhotoViewAttacher mAttacher;
    private List<String> urlList;
    public ImageLoader imageLoader;

    @Override
    public int getLayoutId() {
        return R.layout.photo_viewpager;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initData() {
        urlList = (List<String>)getIntent().getSerializableExtra("urlList");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imageLoader = ImageLoaderUtil.getImageLoader(PhotoDetailActivity.this);
        if(urlList!=null){
            setupData();//加载数据
        }
    }

    private void setupData(){
        mAdapter = new PhotoViewAdapter();
        mViewPager.setAdapter(mAdapter);
        //设置当前需要显示的图片
        mViewPager.setCurrentItem(0);
    }

    class PhotoViewAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = container.inflate(PhotoDetailActivity.this,
                    R.layout.photo_detail,null);
            image = (PhotoView) view.findViewById(R.id.image);
            tvPage = (TextView) view.findViewById(R.id.page);
            tvPage.setText(position+1+"/"+urlList.size());
            //使用ImageLoader加载图片
            imageLoader.displayImage(urlList.get(position), image, ImageLoaderUtil.options);
            image.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
                @Override
                public void onViewTap(View view, float x, float y) {
                    finish();
                }
            });
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            mAttacher = null;
            container.removeView((View)object);
        }

        @Override
        public int getCount() {
            return urlList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

}
