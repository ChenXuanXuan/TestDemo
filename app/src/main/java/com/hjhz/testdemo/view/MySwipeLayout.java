package com.hjhz.testdemo.view;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by yt on 2015/9/14.
 */
public class MySwipeLayout extends SwipeRefreshLayout {
    public MySwipeLayout(Context context) {
        super(context);
    }

    public MySwipeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private float yy;
    private float xx;


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        ;
        final int action = MotionEventCompat.getActionMasked(ev);
        switch (action) {
            case MotionEvent.ACTION_DOWN:

                yy = ev.getY();
                xx = ev.getX();
                break;

            case MotionEvent.ACTION_MOVE:

                final float preY = yy;// 按下时的y坐标
                final float preX = xx;// 按下时的y坐标

                float nowY = ev.getY();// 时时y坐标
                float nowX = ev.getX();// 时时y坐标

                int deltaY = (int) (preY - nowY);// 滑动距
                int deltaX = (int) (preX - nowX);// 滑动距
              /*  System.out.println("deltaY 与 deltaX " + Math.abs(deltaY) + " "
                        + Math.abs(deltaX));*/

                if (Math.abs(deltaY) > Math.abs(deltaX)) {
                 //   System.out.println("deltaY > deltaX");
                    // 应该是让里面的move 消费。   而else的话 是直接 返回true。
                } else {
                    return false;
                }
                break;
        }

        return super.onInterceptTouchEvent(ev);
    }


}
