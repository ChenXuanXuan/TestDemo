package com.hjhz.testdemo.application;

import com.hjhz.testdemo.base.BaseApplication;
import com.hjhz.testdemo.util.ImageLoaderUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by 陈宣宣 on 2016/6/30.
 */
public class AppContext extends BaseApplication {
    public static ImageLoader imageLoader;
    @Override
    public void onCreate() {
        super.onCreate();
        imageLoader = ImageLoaderUtil.getImageLoader(context);
    }

    //用于换壁纸第几张
    public static int current = 0;
    //定义一个存放图片路径的集合
    public static ArrayList<String> picPaths = new ArrayList<String>();

}
