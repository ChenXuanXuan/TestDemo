package com.hjhz.testdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.hjhz.testdemo.R;

/**
 * Created by 陈宣宣 on 2016/7/6.
 */
public class GestureDetectorActivity extends Activity {
    private ImageView ivImg;

    private GestureDetector mGestureDetector;

    class MySimpleGestureListener extends GestureDetector.SimpleOnGestureListener{
        @Override //滑动事件
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            if(e1.getX()-e2.getX()>50){
                Toast.makeText(GestureDetectorActivity.this, "从右向左滑", Toast.LENGTH_SHORT).show();
            }else if(e2.getX()-e1.getX()>50){
                Toast.makeText(GestureDetectorActivity.this, "从左向右滑", Toast.LENGTH_SHORT).show();
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override //双击事件
        public boolean onDoubleTap(MotionEvent e) {
            Toast.makeText(GestureDetectorActivity.this, "双击", Toast.LENGTH_SHORT).show();
            return super.onDoubleTap(e);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesturedetector);
        mGestureDetector = new GestureDetector(new MySimpleGestureListener());
        ivImg = (ImageView)findViewById(R.id.img);
        ivImg.setOnTouchListener(new View.OnTouchListener() {
            @Override//捕获触摸屏幕事件
            public boolean onTouch(View v, MotionEvent event) {
                //转发MotionEvent
                mGestureDetector.onTouchEvent(event);
                return true;
            }
        });
    }
}
