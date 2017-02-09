package com.hjhz.testdemo.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.hjhz.testdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 陈宣宣 on 2016/7/15.
 */
public class AnimatorMenuActivity extends Activity implements View.OnClickListener{

    private int[] res = {R.id.iv_a,R.id.iv_b,R.id.iv_c,R.id.iv_d};
    private List<ImageView> imageViewList = new ArrayList<ImageView>();
    private boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animatormenu);

        for (int i=0;i<res.length;i++){
            ImageView imageView = (ImageView) findViewById(res[i]);
            imageView.setOnClickListener(this);
            imageViewList.add(imageView);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_a:
                if (flag){
                    startAnim();//展开
                }else {
                    closeAnim();//关闭
                }
                break;
            default:
                Toast.makeText(AnimatorMenuActivity.this, "click"+v.getId(), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void startAnim() {
        for (int i=1;i<res.length;i++){
            ObjectAnimator animator = ObjectAnimator.ofFloat(imageViewList.get(i),
                    "translationY",0F,i*400F);
            animator.setDuration(300);
            animator.setInterpolator(new BounceInterpolator());//设置插值器Interpolator  BounceInterpolator为自由落体插值器
            animator.setStartDelay(i * 200);//延时动画 一个一个跳出来
            animator.start();
            flag = false;
        }
    }
    private void closeAnim() {
        for (int i=1;i<res.length;i++){
            ObjectAnimator animator = ObjectAnimator.ofFloat(imageViewList.get(i),
                    "translationY",i*400F,0F);
            animator.setDuration(300);
            animator.setInterpolator(new AccelerateInterpolator());//设置插值器Interpolator
            animator.setStartDelay(i * 200);//延时动画 一个一个跳出来
            animator.start();
            flag = true;
        }
    }

    public void click(View view){
        final Button bt = (Button) view;
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0,100);
        valueAnimator.setDuration(3000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                bt.setText(""+value);
            }
        });
        valueAnimator.start();
    }
}
