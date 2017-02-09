package com.hjhz.testdemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.hjhz.testdemo.R;

/**
 * Created by 陈宣宣 on 2016/6/12.
 */
public class ShowActivity extends Activity {

    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        imageView = (ImageView)findViewById(R.id.iv_pho);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //退出动画
                overridePendingTransition(0, R.anim.main_set_out);

            }
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(intent!=null && bundle!=null){
            byte [] bis=intent.getByteArrayExtra("qrcode");   //接收byte[]    并转成bitmap
            Bitmap bitmap= BitmapFactory.decodeByteArray(bis, 0, bis.length);
            if(bitmap != null){
                imageView.setImageBitmap(bitmap);
            }
        }


    }
}
