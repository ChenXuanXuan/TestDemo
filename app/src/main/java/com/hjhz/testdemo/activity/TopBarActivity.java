package com.hjhz.testdemo.activity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Outline;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.Toast;

import com.hjhz.testdemo.R;
import com.hjhz.testdemo.view.mytopbar.Topbar;

public class TopBarActivity extends Activity {

    private View v ;
    private CardView cardView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topbar);

        Topbar topbar = (Topbar) findViewById(R.id.topbar);
        topbar.setOnTopbarClickListener(new Topbar.topbarClickListener() {
            @Override
            public void leftClick() {
                finish();
            }

            @Override
            public void rightClick() {
                Toast.makeText(TopBarActivity.this, "right", Toast.LENGTH_SHORT).show();
            }
        });
        topbar.setRightIsvisable(false);

        //视图轮廓提供者   自己画圆  5.0及以上才能使用
        ViewOutlineProvider viewOutlineProvider = new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                //获取按钮尺寸
                int size = getResources().getDimensionPixelSize(R.dimen.fab_size);
                //设置轮廓的尺寸
                outline.setOval(-4, -4, size+2, size+2);
            }
        };
        v = findViewById(R.id.v_zidingyi);
        v.setOutlineProvider(viewOutlineProvider);
        v.setClipToOutline(true);
        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getActionMasked();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        v.setTranslationZ(100);
                        break;
                    case MotionEvent.ACTION_UP:
                        v.setTranslationZ(0);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        ObjectAnimator.ofFloat(v,"translationY",0,-300F).setDuration(1000).start();//垂直 Y轴移动
        ObjectAnimator.ofFloat(v,"translationX",0,-300F).setDuration(1000).start();//水平 X轴移动
        ObjectAnimator.ofFloat(v,"rotation",0,360F).setDuration(1000).start();//旋转
        ObjectAnimator.ofFloat(v,"translationZ",0,100F).setDuration(1000).start();//悬浮 Z轴移动

        cardView = (CardView) findViewById(R.id.cv_cardview);
        cardView.setRadius(30);
        cardView.setElevation(50);
        ObjectAnimator.ofFloat(cardView,"rotation",0,360F).setDuration(1000).start();
        cardView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getActionMasked();
                switch (action){
                    case MotionEvent.ACTION_DOWN:
                        v.setTranslationZ(100);
                        break;
                    case MotionEvent.ACTION_UP:
                        v.setTranslationZ(0);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

}
