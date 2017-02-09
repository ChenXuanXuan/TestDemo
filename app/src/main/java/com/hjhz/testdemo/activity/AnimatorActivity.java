package com.hjhz.testdemo.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.hjhz.testdemo.R;

/**
 * Created by 陈宣宣 on 2016/7/15.
 */
public class AnimatorActivity extends Activity {
    private ImageView ivImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator);
        ivImg = (ImageView) findViewById(R.id.iv_img);
    }

    public void start(View view){
        /*
        //View Animation   补间动画  平移
        TranslateAnimation translateAnimation = new TranslateAnimation(0,300,0,300);
        translateAnimation.setDuration(1000);
        translateAnimation.setFillAfter(true);
        ivImg.startAnimation(translateAnimation);*/

        /*
        //ObjectAnimator 属性动画   父类ValueAnimator    ObjectAnimator.start异步执行，动画效果同时显示
        ObjectAnimator.ofFloat(ivImg,"translationX",0F,600F).setDuration(2000).start(); //translationX X轴位移  setDuration设置动画时长
        ObjectAnimator.ofFloat(ivImg,"translationY",0F,800F).setDuration(2000).start(); //translationY Y轴位移
        ObjectAnimator.ofFloat(ivImg,"rotation",0F,720F).setDuration(3000).start(); //rotation 水平旋转
        ObjectAnimator.ofFloat(ivImg,"scaleX",0.5F,1.5F).setDuration(1000).start(); //scaleX 缩放   1.5F是倍数
        ObjectAnimator.ofFloat(ivImg,"scaleY",0.5F,1.5F).setDuration(1000).start(); //scaleY 缩放*/

        /*
        //PropertyValuesHolder 更高效
        PropertyValuesHolder p1 = PropertyValuesHolder.ofFloat("translationX",0F,600F);
        PropertyValuesHolder p2 = PropertyValuesHolder.ofFloat("translationY",0F,800F);
        PropertyValuesHolder p3 = PropertyValuesHolder.ofFloat("rotation",0F,360F);
        ObjectAnimator.ofPropertyValuesHolder(ivImg,p1,p2,p3).setDuration(1000).start();*/

        //AnimatorSet  功能更丰富
        ObjectAnimator o1 = ObjectAnimator.ofFloat(ivImg,"translationX",0F,600F);
        ObjectAnimator o2 = ObjectAnimator.ofFloat(ivImg,"translationY",0F,800F);
        ObjectAnimator o3 = ObjectAnimator.ofFloat(ivImg,"rotationY",0F,360F); //立体旋转
        ObjectAnimator o4 = ObjectAnimator.ofFloat(ivImg,"rotationX",0F,360F); //立体旋转
        AnimatorSet animatorSet = new AnimatorSet();
        //animatorSet.playTogether(o1,o2,o3);//playTogether  同时执行
        //animatorSet.playSequentially(o1,o2,o3);//playSequentially 顺序执行
        animatorSet.play(o1).with(o2);  //with after 等 控制顺序
        animatorSet.play(o3).with(o4).after(o1);
        animatorSet.setDuration(1000);
        animatorSet.start();

        //按钮点击后 透明度变化
        ObjectAnimator animator = ObjectAnimator.ofFloat(view,"alpha",0F,1F);//alpha  透明度
        animator.setDuration(1000);
        //动画监听
        /*
        //方法1
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Toast.makeText(AnimatorActivity.this, "anim end", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });*/
        //方法2
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                Toast.makeText(AnimatorActivity.this, "anim end", Toast.LENGTH_SHORT).show();
            }
        });
        animator.start();
    }
}
