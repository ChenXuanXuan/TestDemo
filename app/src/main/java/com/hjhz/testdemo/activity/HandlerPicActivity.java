package com.hjhz.testdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.hjhz.testdemo.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 陈宣宣 on 2016/7/1.
 * 通过handler轮播图片
 */
public class HandlerPicActivity extends Activity {

    @InjectView(R.id.iv_pic)
    ImageView ivPic;
    @InjectView(R.id.bt)
    Button bt;

    private boolean flag;
    private Handler handler = new Handler();
    private int[] images = {R.drawable.img1,R.drawable.img2,R.drawable.img3,R.drawable.img4};
    private int index;
    private MyRunnable runnable = new MyRunnable();

    class MyRunnable implements Runnable{

        @Override
        public void run() {
            index++;
            index = index%4;
            ivPic.setImageResource(images[index]);
            handler.postDelayed(runnable, 500);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handlerpic);
        // 通过注解绑定控件
        ButterKnife.inject(this);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag){
                    handler.removeCallbacks(runnable);
                }else{
                    //开启轮播图片
                    handler.postDelayed(runnable, 500);
                }
                flag = !flag;
            }
        });
    }
}
