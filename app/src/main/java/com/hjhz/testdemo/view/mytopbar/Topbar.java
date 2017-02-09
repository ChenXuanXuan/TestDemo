package com.hjhz.testdemo.view.mytopbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hjhz.testdemo.R;

/**
 * Created by 陈宣宣 on 2016/7/12.
 */
public class Topbar extends RelativeLayout {

    /*
    * 继承view
    * 重写构造方法
    *
    * */

    private Button leftButton,rightButton;
    private TextView tvTitle;

    private int leftTextColor;
    private Drawable leftBackground;
    private String leftText;
    private int rightTextColor;
    private Drawable rightBackground;
    private String rightText;

    private float titleTextSize;
    private int titleTextColor;
    private String title;

    private LayoutParams leftParams,rightParams,titleParams;//创建布局，用于添加控件到viewgroup，最终addView

    private topbarClickListener listener;
    public interface topbarClickListener{
        public void leftClick();
        public void rightClick();
    }
    public void setOnTopbarClickListener(topbarClickListener listener){
        this.listener = listener;
    }

    public Topbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Topbar); //把自定义的属性映射出去

        //拿到属性值
        leftTextColor = ta.getColor(R.styleable.Topbar_leftTextCilor, 0); //用下滑线找到子属性
        leftBackground =  ta.getDrawable(R.styleable.Topbar_leftBackground);
        leftText = ta.getString(R.styleable.Topbar_leftText);

        rightTextColor = ta.getColor(R.styleable.Topbar_rightTextCilor, 0);
        rightBackground =  ta.getDrawable(R.styleable.Topbar_rightBackground);
        rightText = ta.getString(R.styleable.Topbar_rightText);

        titleTextSize = ta.getDimension(R.styleable.Topbar_titleTextSize, 0);
        titleTextColor = ta.getColor(R.styleable.Topbar_titleTextCilor, 0);
        title = ta.getString(R.styleable.Topbar_titleText);

        ta.recycle();//回收 并且避免缓存

        //控件实例化
        leftButton = new Button(context);
        rightButton = new Button(context);
        tvTitle = new TextView(context);

        //控件赋值
        leftButton.setTextColor(leftTextColor);
        leftButton.setBackground(leftBackground);
        leftButton.setText(leftText);
        rightButton.setTextColor(rightTextColor);
        rightButton.setBackground(rightBackground);
        rightButton.setText(rightText);
        tvTitle.setTextColor(titleTextColor);
        tvTitle.setTextSize(titleTextSize);
        tvTitle.setText(title);
        tvTitle.setGravity(Gravity.CENTER);


        leftParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        leftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);//居左对齐
        addView(leftButton, leftParams);//添加控件

        rightParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        rightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);//居右对齐
        addView(rightButton, rightParams);

        titleParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.MATCH_PARENT);
        titleParams.addRule(RelativeLayout.CENTER_IN_PARENT,TRUE);//居左对齐
        addView(tvTitle, titleParams);//添加控件

        leftButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.leftClick();
            }
        });
        rightButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.rightClick();
            }
        });
    }

    public void setLeftIsvisable(boolean flag){
        if(flag){
            leftButton.setVisibility(View.VISIBLE);
        }else {
            leftButton.setVisibility(View.GONE);
        }
    }
    public void setRightIsvisable(boolean flag){
        if(flag){
            rightButton.setVisibility(View.VISIBLE);
        }else {
            rightButton.setVisibility(View.GONE);
        }
    }
}
