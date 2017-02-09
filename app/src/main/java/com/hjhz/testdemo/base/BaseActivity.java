package com.hjhz.testdemo.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.hjhz.testdemo.util.ImageLoaderUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.ButterKnife;

/**
 * Created by 陈宣 on 2016/4/22.
 */
public abstract class BaseActivity extends FragmentActivity {


    public abstract int getLayoutId();

    protected abstract void initView();

    protected void initView(Bundle savedInstanceState){};

    protected abstract void initTitle();

    protected abstract void initData();

    private ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
        }
        // 通过注解绑定控件
        ButterKnife.inject(this);
        if (savedInstanceState!=null){
            initView(savedInstanceState);
        }else {
            initView();
        }
        initTitle();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}
