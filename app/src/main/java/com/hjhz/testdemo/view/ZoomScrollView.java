package com.hjhz.testdemo.view;

/**
 * Created by 陈宣宣 on 2016/6/15.
 */

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.hjhz.testdemo.R;

/**
 * 重写让ScrollView有滚动监听(23以前是没有滚动监听的)
 * 拦截touch事件，让其支持下拉放大图片
 */
public class ZoomScrollView extends ScrollView {

    private View zoomView;
    /** 记录上次事件的Y轴*/
    private float mLastMotionY;
    /** 记录一个滚动了多少距离，通过这个来设置缩放*/
    private int allScroll = -1;
    /** 控件原本的高度*/
    private int height = 0;
    /** 被放大的控件id*/
    private int zoomId;
    /** 最大放大多少像素*/
    private int maxZoom;
    /** 滚动监听*/
    private ScrollViewListener scrollViewListener = null;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            /*这里是当ACTION_UP事件发生时，如果图片还在放大状态，就模拟动画效果，吧图片缩放回去*/
            allScroll -= 25;
            if(allScroll < 0){
                allScroll = 0;
            }
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) zoomView.getLayoutParams();
            lp.height = (int) (height + allScroll/2);
            zoomView.setLayoutParams(lp);
            if(allScroll != 0){
                handler.sendEmptyMessageDelayed(1,10);
            }else{
                allScroll = -1;
            }
        }
    };
    public ZoomScrollView(Context context) {
        super(context);
    }

    public ZoomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ZoomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ZoomScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    /*
    * 当控件从xml中初始化完成的生命周期方法，在这里我们找到被放大的图片控件
    * */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        zoomView = findViewById(zoomId);
    }

    /*
    * 获取2个自定义属性，一个是被放大的图片控件id，一个是最大的放大像素
    * */
    private void init(AttributeSet attrs){
        TypedArray t = getContext().obtainStyledAttributes(attrs, R.styleable.ObservableScrollView);
        zoomId = t.getResourceId(R.styleable.ObservableScrollView_zoomId,-1);
        maxZoom = t.getDimensionPixelOffset(R.styleable.ObservableScrollView_maxZoom,0);
    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }


    /*
    * 事件拦截
    * */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //当控件为空和最大放大像素为0 的时候，不进行事件拦截
        if(zoomView == null || maxZoom == 0){
            return super.dispatchTouchEvent(event);
        }

        final int action = event.getAction();
        //当事件取消和手指松开时，判断当前偏移量（allScroll ）是否回到了最初状态-1，如果没有说明图片没有缩放，要缩放回去
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            if(allScroll != -1){
                handler.sendEmptyMessageDelayed(1,10);
            }
            return super.dispatchTouchEvent(event);
        }

        /*
        * 拦截移动事件，每次记录下Y轴坐标，当滚动为0的时候，就计算与上次坐标的偏移量，
        * 大于0就开始放大，每次放大总偏移值的二分之一，
        * 因为每次放大总偏移值的效果不大好看，同时判断总偏移值是否大于最大偏移值，大于就设置总偏移值为最大值，相当于停止放大。
        * 如果小于0，就把总偏移值设置为0，
        * 并且重置偏移值的为-1，-1的时候，就不会拦截事件
        * */
        switch (action) {
            case MotionEvent.ACTION_MOVE: {
                final float y = event.getY();
                final float diff, absDiff;
                diff = y - mLastMotionY;
                mLastMotionY = y;
                absDiff = Math.abs(diff);
                if(allScroll >= 0 && absDiff > 1){
                    allScroll += diff;

                    if(allScroll < 0){
                        allScroll = 0;
                    }else if(allScroll > maxZoom){
                        allScroll = maxZoom;
                    }
                    LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) zoomView.getLayoutParams();
                    lp.height = (int) (height + allScroll/2);
                    zoomView.setLayoutParams(lp);
                    if(allScroll == 0){
                        allScroll = -1;
                    }
                    return false;
                }
                if (isReadyForPullStart()) {
                    if (absDiff > 0 ) {
                        if (diff >= 1f && isReadyForPullStart()) {
                            mLastMotionY = y;
                            allScroll = 0;
                            height = zoomView.getHeight();
                            return true;
                        }
                    }
                }
                break;
            }


        }

        return super.dispatchTouchEvent(event);
    }

    /*
    * 重写onTouchEvent，当偏移值不是-1的时候，说明图片在进行放大或缩放，这时候不能让ScrollView滚动，所以需要把onTouchEvent拦截掉
    * */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(allScroll != -1){
            Log.i("ScrollView", "onTouchEvent");
            return false;
        }
        return super.onTouchEvent(ev);
    }



    /**
     * 获取当前ScrollView的滚动位置，是0的时候才可以开始放大图片
     * 返回是否可以开始放大
     * @return
     */
    protected boolean isReadyForPullStart() {
        return getScrollY() == 0;
    }


    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
        }
    }
    public interface ScrollViewListener {

        void onScrollChanged(ZoomScrollView scrollView, int x, int y, int oldx, int oldy);

    }
}
