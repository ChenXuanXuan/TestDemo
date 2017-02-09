package com.hjhz.testdemo.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.hjhz.testdemo.R;
import com.hjhz.testdemo.util.ImageHelper;

/**
 * Created by 陈宣宣 on 2016/7/14.
 * 处理图片效果
 */
public class ImageHelperActivity extends Activity implements SeekBar.OnSeekBarChangeListener{
    //实现SeekBar的监听

    private ImageView ivImg;
    private SeekBar sbHue,sbSaturation,sbLum;//色相、饱和度、亮度进度条
    private static int MAX_VALUE = 255;//颜色最大值
    private static int MID_VALUE = 127;
    private float mHue,mSaturation,mLum;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagehelper);
        //初始化控件
        ivImg = (ImageView) findViewById(R.id.iv_img);
        sbHue = (SeekBar) findViewById(R.id.sb_hue);
        sbSaturation = (SeekBar) findViewById(R.id.sb_saturation);
        sbLum = (SeekBar) findViewById(R.id.sb_lum);
        bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.img1);
        ivImg.setImageBitmap(bitmap);
        //ivImg.setImageBitmap(ImageHelper.handleImagePixelsRelief(bitmap));

        //注册监听
        sbHue.setOnSeekBarChangeListener(this);
        sbSaturation.setOnSeekBarChangeListener(this);
        sbLum.setOnSeekBarChangeListener(this);

        //初始化最大值
        sbHue.setMax(MAX_VALUE);
        sbSaturation.setMax(MAX_VALUE);
        sbLum.setMax(MAX_VALUE);

        //设置初始值
        sbHue.setProgress(MID_VALUE);
        sbSaturation.setProgress(MID_VALUE);
        sbLum.setProgress(MID_VALUE);

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()){
            case R.id.sb_hue:
                mHue = (progress - MID_VALUE) * 1.0F / MID_VALUE *180; //并不固定，此为经验值
                break;
            case R.id.sb_saturation:
                mSaturation = progress * 1.0F / MID_VALUE ;
                break;
            case R.id.sb_lum:
                mLum = progress * 1.0F / MID_VALUE ;
                break;
        }
        ivImg.setImageBitmap(ImageHelper.handleImageEffect(bitmap, mHue, mSaturation, mLum));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
