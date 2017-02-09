package com.hjhz.testdemo.view;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.hjhz.testdemo.R;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.Types.BoomType;
import com.nightonke.boommenu.Types.ButtonType;
import com.nightonke.boommenu.Types.PlaceType;
import com.nightonke.boommenu.Util;


public class TitleView extends LinearLayout {
    private Activity context;
    private FrameLayout linearLayout;
    private ImageView imageButton1;
    private ImageView imageButton2;
    private ImageView imageCenterButton;
    private TextView lefttext;
    private TextView righttext;
    private TextView title;
    private BoomMenuButton boomMenuButton;

    private String titleName;
    private int rsId;
    private int rsId2;
    private int rsCenterId;
    private String text;
    private String text2;
    private int type;
    private boolean isBoom = false;
    private boolean init = false;

    public TitleView(Activity context, FrameLayout titleLayout,
                     String titleName, int rsId) {
        super(context);
        this.linearLayout = titleLayout;
        this.context = context;
        this.titleName = titleName;
        this.rsId = rsId;
        initTitle();
    }

    public TitleView(Activity context, FrameLayout titleLayout,
                     String titleName, int rsId, boolean isBoom) {   //是否使用爆炸菜单
        super(context);
        this.linearLayout = titleLayout;
        this.context = context;
        this.titleName = titleName;
        this.rsId = rsId;
        this.isBoom = isBoom;
        initTitle();
    }

    public TitleView(Activity context, FrameLayout titleLayout,
                     String titleName) {      //两边都没有事件。
        super(context);
        this.linearLayout = titleLayout;
        this.context = context;
        this.titleName = titleName;
        type = 1;
        initTitle();
    }

    public TitleView(Activity context, FrameLayout titleLayout,
                     int rsId1, int center, int rsId2) {
        super(context);
        rsCenterId = center;
        this.linearLayout = titleLayout;
        this.context = context;
        this.rsId = rsId1;
        this.rsId2 = rsId2;
        initTitle();
    }


    public TitleView(Activity context, FrameLayout titleLayout,
                     String titleName, String text, int rsId2) {   //左边文字 右边图片
        super(context);
        this.linearLayout = titleLayout;
        this.context = context;
        this.titleName = titleName;
        this.text = text;
        this.rsId2 = rsId2;
        initTitle();
    }

    public TitleView(Activity context, FrameLayout titleLayout,
                     String titleName, int rsId, String text2) { //左边图片右边文字
        super(context);
        this.linearLayout = titleLayout;
        this.context = context;
        this.titleName = titleName;

        this.rsId = rsId;
        this.text2 = text2;
        initTitle();
    }

    public TitleView(Activity context,FrameLayout titleLayout,
                     String titleName, int rsId, int rsId2) { //都是图片
        super(context);
        this.linearLayout = titleLayout;
        this.context = context;
        this.titleName = titleName;

        this.rsId = rsId;
        this.rsId2 = rsId2;
        initTitle(); // 左边 0文字 1 图片 右边 0 文字 1图片。
    }


    public TitleView(Activity context, FrameLayout titleLayout,
                     String titleName, String text, String text2) { //都是文字
        super(context);
        this.linearLayout = titleLayout;
        this.context = context;
        this.titleName = titleName;

        this.text = text;
        this.text2 = text2;

        initTitle(); // 左边 0文字 1 图片 右边 0 文字 1图片。
    }


    public static class Options {
        public int leftTextColor;
        public int centerTextColor;
        public int rightTextColor;

        public int getLeftTextColor() {
            return leftTextColor;
        }

        public void setLeftTextColor(int leftTextColor) {
            this.leftTextColor = leftTextColor;
        }

        public int getCenterTextColor() {
            return centerTextColor;
        }

        public void setCenterTextColor(int centerTextColor) {
            this.centerTextColor = centerTextColor;
        }

        public int getRightTextColor() {
            return rightTextColor;
        }

        public void setRightTextColor(int rightTextColor) {
            this.rightTextColor = rightTextColor;
        }

    }

    public static Options options1;

    static {
        TitleView.options1 = new Options();
    }

    private void initTitle() {
        // TODO Auto-generated method stub
        title = (TextView) linearLayout.findViewById(R.id.center);
        title.setGravity(Gravity.CENTER);
        title.setText(titleName);
        title.setTextSize(22);
        title.setTextColor(getResources().getColor(R.color.white));
        title.setVisibility(View.VISIBLE);
        if (type == 1) {
            return;
        }


        imageButton1 = (ImageView) linearLayout.findViewById(R.id.leftimg);
        imageButton2 = (ImageView) linearLayout.findViewById(R.id.rightimg);
        imageCenterButton = (ImageView) linearLayout.findViewById(R.id.centeriamge);

        if (rsCenterId != 0) {
            title.setVisibility(View.GONE);
            imageCenterButton.setVisibility(View.VISIBLE);
            imageCenterButton.setImageResource(rsCenterId);
        }

        lefttext = (TextView) linearLayout.findViewById(R.id.lefttext);
        righttext = (TextView) linearLayout.findViewById(R.id.righttext);

        if (rsId != 0 && text2 == null && rsId2 == 0 && text == null) {
            imageButton1.setVisibility(View.VISIBLE);
            imageButton1.setImageResource(R.drawable.ic_back);
            imageButton2.setVisibility(View.VISIBLE);
            imageButton2.setImageResource(rsId);

        }

        if (options1.getRightTextColor() != 0) {
            righttext.setTextColor(context.getResources().getColor(
                    options1.getRightTextColor()));
        }

        if (rsId != 0) {
            imageButton1.setVisibility(View.VISIBLE);
            imageButton1.setImageResource(rsId);
            lefttext.setVisibility(View.GONE);

        } else {
            imageButton1.setVisibility(View.GONE);
        }

        if (rsId2 != 0) {
            imageButton2.setVisibility(View.VISIBLE);
            imageButton2.setImageResource(rsId2);

            righttext.setVisibility(View.GONE);

        } else {
            imageButton2.setVisibility(View.GONE);
        }

        if (text != null) {
            lefttext.setVisibility(View.VISIBLE);
            lefttext.setText(text);
            imageButton1.setVisibility(View.GONE);

        } else {
            lefttext.setVisibility(View.GONE);
        }

        if (text2 != null) {
            righttext.setVisibility(View.VISIBLE);
            righttext.setText(text2);
            imageButton2.setVisibility(View.GONE);

        } else {
            righttext.setVisibility(View.GONE);
        }


        imageButton1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {

                ((TitleClickCallback) context).leftBtclick();
            }
        });
        imageButton2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {

                ((TitleClickCallback) context).rightBtclick();
            }
        });

        lefttext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                ((TitleClickCallback) context).leftBtclick();
            }
        });

        righttext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                ((TitleClickCallback) context).rightBtclick();
            }
        });

        boomMenuButton = (BoomMenuButton) linearLayout.findViewById(R.id.rightBoom);
    }

    public TextView getCenterText() {
        return title;
    }

    public interface TitleClickCallback {
        public void rightBtclick();

        public void leftBtclick();
    }


    public void setRightButtonImg(int icoShare) {
        // TODO Auto-generated method stub
        imageButton2.setImageResource(icoShare);

    }

    public ImageView getLeftButton() {
        return imageButton1;
    }

    public ImageView getRightButton() {
        return imageButton2;
    }

    public BoomMenuButton getBoomButton() {
        return boomMenuButton;
    }
}
