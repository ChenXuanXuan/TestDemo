package com.hjhz.testdemo.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by 陈宣宣 on 2016/4/26.
 */
public class BaseApplication extends Application {

    public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        this.context = getApplicationContext();
    }

    public static synchronized BaseApplication context() {
        return (BaseApplication) context;
    }
}
