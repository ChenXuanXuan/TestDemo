package com.hjhz.testdemo.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.hjhz.testdemo.R;

public class DianzanActivity extends AppCompatActivity {

    private ImageView ivDianzan;
    private ImageView ivDianzan1;
    private RelativeLayout rlDianzan;
    private RelativeLayout rlBai;

    private GestureDetector mGestureDetector;

    float x = 0;
    float y = 0;
    ImageView iv;

    class MySimpleGestureDetector extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            x = e.getX();
            y = e.getY();
            rlBai.removeAllViews();
            iv.setX(x);
            iv.setY(y);
            rlBai.addView(iv);
            Log.e("touch", "onDoubleTap: x=" + x + ",y=" + y);

            //View Animation   补间动画  平移
            TranslateAnimation translateAnimation = new TranslateAnimation(0,200f,0,-400f);
            translateAnimation.setDuration(1000);
            translateAnimation.setFillAfter(true);

            //透明度变化
            AlphaAnimation alphaAnimation = new AlphaAnimation(1,0);
            alphaAnimation.setDuration(1000);
            alphaAnimation.setFillAfter(true);

            //旋转
            RotateAnimation rotateAnimation = new RotateAnimation(0,70f,Animation.RELATIVE_TO_SELF,1f,Animation.RELATIVE_TO_SELF,1f);//自转
            rotateAnimation.setDuration(1000);
            rotateAnimation.setFillAfter(true);

            //缩放
            ScaleAnimation scaleAnimation =new ScaleAnimation(1f, 1.4f, 1f, 1.4f,
                    Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 1f);
            scaleAnimation.setDuration(1000);//设置动画持续时间


            AnimationSet animationSet = new AnimationSet(true);
            animationSet.setFillEnabled(true);
            animationSet.setFillAfter(true);
            animationSet.addAnimation(translateAnimation);
            animationSet.addAnimation(alphaAnimation);
            animationSet.addAnimation(rotateAnimation);
            animationSet.addAnimation(rotateAnimation);
            animationSet.addAnimation(scaleAnimation);

            iv.startAnimation(animationSet);


            /*ObjectAnimator o1 = ObjectAnimator.ofFloat(iv,"translationX",0,250F);
            ObjectAnimator o2 = ObjectAnimator.ofFloat(iv,"translationY",0,-500F);
            ObjectAnimator o3 = ObjectAnimator.ofFloat(iv,"rotation",0,70F);
            ObjectAnimator o4 = ObjectAnimator.ofFloat(iv, "scaleX", 0, 1.5F);
            ObjectAnimator o5 = ObjectAnimator.ofFloat(iv, "scaleY", 0, 1.5F);
            ObjectAnimator o6 = ObjectAnimator.ofFloat(iv, "alpha", 1F, 0);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(o1, o2, o3, o4, o5);
            animatorSet.setDuration(1500);
            animatorSet.start();*/


            return super.onDoubleTap(e);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dianzan);
        rlDianzan = (RelativeLayout) findViewById(R.id.rl_dianzan);
        rlBai = (RelativeLayout) findViewById(R.id.rl_bai);
        ivDianzan = (ImageView) findViewById(R.id.iv_dianzan);
        ivDianzan1 = (ImageView) findViewById(R.id.iv_dianzan1);
        ivDianzan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator o1 = ObjectAnimator.ofFloat(ivDianzan1, "translationX", 0, -250F);
                ObjectAnimator o2 = ObjectAnimator.ofFloat(ivDianzan1, "translationY", 0, -500F);
                ObjectAnimator o3 = ObjectAnimator.ofFloat(ivDianzan1, "rotation", 0, -70F);
                ObjectAnimator o4 = ObjectAnimator.ofFloat(ivDianzan1, "scaleX", 0, 1.5F);
                ObjectAnimator o5 = ObjectAnimator.ofFloat(ivDianzan1, "scaleY", 0, 1.5F);
                ObjectAnimator o6 = ObjectAnimator.ofFloat(ivDianzan1, "alpha", 1F, 0);
                AnimatorSet animatorSet = new AnimatorSet();
                //animatorSet.play(o1).with(o2);
                animatorSet.playTogether(o1, o2, o3, o4, o5, o6);
                animatorSet.setDuration(1500);
                animatorSet.start();

            }
        });


        mGestureDetector = new GestureDetector(new MySimpleGestureDetector());
        iv = new ImageView(this);
        iv.setImageResource(R.drawable.icon);


        rlBai.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                mGestureDetector.onTouchEvent(event);
                /*
                //此段代码实现的功能：动态的根据手点击屏幕的坐标生成imageview，并随着手势活动imageview跟着滑动
                float x = event.getX();
                float y = event.getY();
                rlBai.removeAllViews();
                iv.setX(x);
                iv.setY(y);
                rlBai.addView(iv);*/

                return true;
            }
        });
    }
}
