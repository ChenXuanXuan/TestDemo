package com.hjhz.testdemo.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.hjhz.testdemo.R;
import com.hjhz.testdemo.util.SharePreferenceHelper;

public class WelcomeActivity extends AppCompatActivity {

    private TextView tvMy;
    SharePreferenceHelper sp;
    @SuppressLint("HandlerLeak")
    private Handler handler=new Handler(){
        public void handleMessage(android.os.Message msg) {
            int a=msg.what;
            if(a==1){
                if (!sp.getBooleanValue("Loading")) {
                    startActivity(new Intent(WelcomeActivity.this,GuideActivity.class));
                    sp.setBooleanValue("Loading", true);
                    finish();
                }else {
                    startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
                    overridePendingTransition(R.anim.slide_left_in,R.anim.slide_left_out);
                    finish();
                }
            }
        };
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏

        setContentView(R.layout.activity_welcome);

        sp=SharePreferenceHelper.getInstance(WelcomeActivity.this);
        handler.sendEmptyMessageDelayed(1, 1500);

        tvMy = (TextView) findViewById(R.id.tv_my);
        tvMy.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/test.ttf"));

        Intent i_getvalue = getIntent();
        String action = i_getvalue.getAction();
        if(Intent.ACTION_VIEW.equals(action)){
            Uri uri = i_getvalue.getData();
            if(uri != null){
                String age= uri.getQueryParameter("age");
                //Toast.makeText(WelcomeActivity.this, "age="+age, Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
