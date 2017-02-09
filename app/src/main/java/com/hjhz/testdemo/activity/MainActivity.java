package com.hjhz.testdemo.activity;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.hjhz.testdemo.R;
import com.hjhz.testdemo.fragment.SettingFragment;
import com.hjhz.testdemo.interf.OnTabReselectListener;
import com.hjhz.testdemo.util.StringUtils;
import com.hjhz.testdemo.view.MFragmentTabHost;
import com.hjhz.testdemo.view.MainTab;
import com.hjhz.testdemo.view.TitleView;
import com.hjhz.testdemo.view.sensor.ShakeSensor;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.Types.BoomType;
import com.nightonke.boommenu.Types.ButtonType;
import com.nightonke.boommenu.Types.PlaceType;
import com.nightonke.boommenu.Util;
import com.xys.libzxing.zxing.activity.CaptureActivity;
import com.xys.libzxing.zxing.encoding.EncodingUtils;

import java.io.ByteArrayOutputStream;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements
        TabHost.OnTabChangeListener, View.OnTouchListener, ShakeSensor.OnShakeListener{

    private boolean init = false;
    private BoomMenuButton boomMenuButton;//圆形
    private BoomMenuButton boomMenuButtonTitle;//状态栏 条形
    private ImageView ivLeft;
    private SettingFragment setfragment;
    public static SlidingMenu menu;
    private FrameLayout titleBar;

    public MFragmentTabHost mTabHost;

    public static MainActivity context;

    //摇一摇功能
    private ShakeSensor mShakeSensor;
    //播放声音
    private MediaPlayer mediaPlayer;
    private AudioManager mAudioManager;
    int current = 0; //当前音量
    int max = 0; //最大音量

    //通知栏
    private NotificationManager manager;
    Bitmap icon ;
    Random rand = new Random();
    Notification notification;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager windowManager = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);
        StringUtils.screenDensity = dm.density;
        StringUtils.screenWidth = dm.widthPixels; //轮播广告高度时用到

        setContentView(R.layout.activity_main);
        context = this;
        //导航栏
        mTabHost = (MFragmentTabHost) findViewById(R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        if (android.os.Build.VERSION.SDK_INT > 10) {
            mTabHost.getTabWidget().setShowDividers(0);
        }
        initTabs();
        mTabHost.setCurrentTab(0);
        mTabHost.setOnTabChangedListener(this);

        titleBar = (FrameLayout) findViewById(R.id.include);
        /*if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.KITKAT){
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

            int statusBarHeight = ScreenUtil.getStatusBarHeight(this.getBaseContext());
            titleBar.setPadding(0, statusBarHeight, 0, 0);

        }*/

        initView();

        //初始化自定义传感器
        mShakeSensor = new ShakeSensor(this);
            //注册回调事件
        mShakeSensor.setOnShakeListener(this);
        mShakeSensor.init();

        //启动动画   标题栏左侧图片旋转
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.main_img_rotate);
        ivLeft.startAnimation(animation);

        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        icon = BitmapFactory.decodeResource(getResources(),
                R.drawable.icon);
        //媒体音量
//        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//        current = mAudioManager.getStreamVolume( AudioManager.STREAM_MUSIC );
//        max = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);//系统最大音量
    }

    @Override
    public void onShake() {
        //实现摇一摇操作

        //播放声音
//        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, max, AudioManager.FLAG_PLAY_SOUND);//设置为系统音量
        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.ding);
        //mediaPlayer.setLooping(true);//循环播放
        mediaPlayer.start();


        //弹通知栏
        Intent resultIntent = new Intent(context, MainActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 1000, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification = new NotificationCompat.Builder(context)
                .setLargeIcon(icon)
                .setSmallIcon(R.drawable.icon)
                //.setContentIntent(resultPendingIntent)
                .setTicker("").setContentInfo("")
                .setContentTitle("新消息").setContentText("发现一大波美女")
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setAutoCancel(true).setDefaults(Notification.DEFAULT_ALL)
                .build();
        manager.notify(1000, notification);

        //动画加载美女activity
        Intent intent = new Intent(this,ShowActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.main_set_in, 0);

    }

    public void initView(){
        boomMenuButton = (BoomMenuButton)findViewById(R.id.boom);
        //titleBar = (LinearLayout) findViewById(R.id.include);
        //标题栏设置
        TitleView titleView = new TitleView(this, titleBar, "那个男人", R.drawable.icon, true);
        boomMenuButtonTitle = titleView.getBoomButton();
        ivLeft = titleView.getLeftButton();

        //侧滑设置
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        //menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);//全屏侧滑
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);//贴边侧滑
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow_slidemenu);
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);//滑动剩余
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.menu_frame);
        setfragment = new SettingFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.menu_frame, setfragment).commit();

        ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.showMenu();
            }
        });
    }

    private void initTabs() {
        mTabHost.clearAllTabs();
        MainTab[] tabs = MainTab.values();
        final int size = tabs.length;
        for (int i = 0; i < size; i++) {
            MainTab mainTab = tabs[i];
            TabHost.TabSpec tab = mTabHost.newTabSpec(getString(mainTab.getResName()));
            View indicator = LayoutInflater.from(getApplicationContext())
                    .inflate(R.layout.tab_indicator, null);
            TextView title = (TextView) indicator.findViewById(R.id.tab_title);
            Drawable drawable = this.getResources().getDrawable(
                    mainTab.getResIcon());
            title.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null,
                    null);
            title.setText(getString(mainTab.getResName()));
            tab.setIndicator(indicator);
            tab.setContent(new TabHost.TabContentFactory() {
                @Override
                public View createTabContent(String tag) {
                    return new View(MainActivity.this);
                }
            });
            mTabHost.addTab(tab, mainTab.getClz(), null);
            /*
            //新动态红点提醒
            if (mainTab.equals(MainTab.ME)) {
                View cn = indicator.findViewById(R.id.tab_mes);
                mBvNotice = new BadgeView(MainActivity.this, cn);
                mBvNotice.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
                mBvNotice.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                mBvNotice.setBackgroundResource(R.drawable.notification_bg);
                mBvNotice.setGravity(Gravity.CENTER);
            }*/
            mTabHost.getTabWidget().getChildAt(i).setOnTouchListener(this);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        super.onTouchEvent(event);
        boolean consumed = false;
        // use getTabHost().getCurrentTabView to decide if the current tab is
        // touched again
        if (event.getAction() == MotionEvent.ACTION_DOWN
                && v.equals(mTabHost.getCurrentTabView())) {
            // use getTabHost().getCurrentView() to get a handle to the view
            // which is displayed in the tab - and to get this views context
            Fragment currentFragment = getCurrentFragment();
            if (currentFragment != null
                    && currentFragment instanceof OnTabReselectListener) {
                OnTabReselectListener listener = (OnTabReselectListener) currentFragment;
                listener.onTabReselect();
                consumed = true;
            }
        }
        return consumed;
    }

    private Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentByTag(
                mTabHost.getCurrentTabTag());
    }

    @Override
    public void onTabChanged(String tabId) {

        final int size = mTabHost.getTabWidget().getTabCount();
        for (int i = 0; i < size; i++) {
            View v = mTabHost.getTabWidget().getChildAt(i);
            if (i == mTabHost.getCurrentTab()) {
                v.setSelected(true);
            } else {
                v.setSelected(false);
            }
        }
        /*if (tabId.equals(getString(MainTab.ME.getResName()))) {
            mBvNotice.setText("");
            mBvNotice.hide();
        }*/
        supportInvalidateOptionsMenu();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        // Use a param to record whether the boom button has been initialized
        // Because we don't need to init it again when onResume()
        if (init) return;
        init = true;

        initRightBoom();//初始化标题栏右上boom
        initMainBoom();//初始化右下boom

    }

    private void initRightBoom() {

        Drawable[] drawables = new Drawable[3];
        int[] drawablesResource = new int[]{
                R.drawable.java,
                R.drawable.github,
                R.drawable.java
        };
        for (int i = 0; i < 3; i++)
            drawables[i] = ContextCompat.getDrawable(this, drawablesResource[i]);

        int[][] colors = new int[3][2];
        for (int i = 0; i < 3; i++) {
            colors[i][1] = ContextCompat.getColor(this, R.color.material_white);
            colors[i][0] = Util.getInstance().getPressedColor(colors[i][1]);
        }

        boomMenuButtonTitle.init(
                drawables,
                new String[]{"来盘五子棋", "图表", "开发中"},
                colors,
                ButtonType.HAM,
                BoomType.PARABOLA,
                PlaceType.HAM_3_1,
                null,
                null,
                null,
                null,
                null,
                null,
                null);

        boomMenuButtonTitle.setSubButtonShadowOffset(Util.getInstance().dp2px(2), Util.getInstance().dp2px(2));
        boomMenuButtonTitle.setTextViewColor(ContextCompat.getColor(this, R.color.black));
        boomMenuButtonTitle.setBoomType(BoomType.PARABOLA_2);

        boomMenuButtonTitle.setOnSubButtonClickListener(new BoomMenuButton.OnSubButtonClickListener() {
            @Override
            public void onClick(int buttonIndex) {
                switch (buttonIndex){
                    case 0:
                        //五子棋
                        startActivity(new Intent(MainActivity.this,WuziqiActivity.class));
                        break;
                    case 1:
                        //图表
                        startActivity(new Intent(MainActivity.this,ChartActivity.class));
                        break;
                    case 2:
                        //中午吃啥
                        startActivity(new Intent(MainActivity.this,HandlerPicActivity.class));
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void initMainBoom() {
        int number = 4;

        Drawable[] drawables = new Drawable[number];
        int[] drawablesResource = new int[]{
                R.drawable.mark,
                R.drawable.refresh,
                R.drawable.copy,
                R.drawable.heart,
                R.drawable.info,
                R.drawable.like,
                R.drawable.record,
                R.drawable.search,
                R.drawable.settings
        };
        for (int i = 0; i < number; i++)
            drawables[i] = ContextCompat.getDrawable(this, drawablesResource[i]);

        String[] STRINGS = new String[]{
                "Mark",
                "Refresh",
                "Copy",
                "Heart",
                "Info",
                "Like",
                "Record",
                "Search",
                "Settings"
        };
        String[] strings = new String[number];
        for (int i = 0; i < number; i++)
            strings[i] = STRINGS[i];

        int[][] colors = new int[number][2];
        for (int i = 0; i < number; i++) {
            colors[i][1] = GetRandomColor();
            colors[i][0] = Util.getInstance().getPressedColor(colors[i][1]);
        }

        ButtonType buttonType = ButtonType.CIRCLE;

        boomMenuButton.init(
                drawables,
                strings,
                colors,
                buttonType,
                BoomType.PARABOLA,
                PlaceType.CIRCLE_4_1,
                null,
                null,
                null,
                null,
                null,
                null,
                null);

        boomMenuButton.setSubButtonShadowOffset(Util.getInstance().dp2px(2), Util.getInstance().dp2px(2));
        boomMenuButton.setDuration(500);//整体动画延时
        boomMenuButton.setDelay(100);//单个动画间隔延时
        boomMenuButton.setRotateDegree(360);//单个动画图标旋转幅度

        boomMenuButton.setOnSubButtonClickListener(new BoomMenuButton.OnSubButtonClickListener() {
            @Override
            public void onClick(int buttonIndex) {
                Intent intent = null;
                switch(buttonIndex){
                    case 0:
                        intent = new Intent(MainActivity.this,RecyclerActivity1.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(MainActivity.this,RecyclerActivity2.class);
                        startActivity(intent);
                        break;
                    case 2:
                        //扫二维码
                        startActivityForResult(new Intent(MainActivity.this, CaptureActivity.class), 0);  //调用libzing
                        break;
                    case 3:
                        //生成二维码，并在中间加logo
                        Bitmap bitmap = EncodingUtils.createQRCode("奇变偶不变，符号看象限——陈宣宣",
                                500, 500,
                                BitmapFactory.decodeResource(getResources(), R.drawable.icon));
                        //动画加载二维码
                        intent = new Intent(MainActivity.this,ShowActivity.class);
                        ByteArrayOutputStream baos=new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                        byte [] bitmapByte =baos.toByteArray();
                        intent.putExtra("qrcode",bitmapByte);  //intent  传递bitmap   先转成byte[]
                        startActivity(intent);
                        overridePendingTransition(R.anim.main_set_in,0);

                        break;
                    default:
                        break;
                }
            }
        });
    }

    //自定义随机颜色
    private String[] Colors = {
            "#F44336",
            "#E91E63",
            "#9C27B0",
            "#2196F3",
            "#03A9F4",
            "#00BCD4",
            "#009688",
            "#4CAF50",
            "#8BC34A",
            "#CDDC39",
            "#FFEB3B",
            "#FFC107",
            "#FF9800",
            "#FF5722",
            "#795548",
            "#9E9E9E",
            "#607D8B"};
    public int GetRandomColor() {
        Random random = new Random();
        int p = random.nextInt(Colors.length);
        return Color.parseColor(Colors[p]);
    }


    @Override
    public void onBackPressed() {
        //返回键处理
        if (menu.isMenuShowing()) {
            menu.showContent();
        }else{
            if (boomMenuButton.isClosed()
                    && boomMenuButtonTitle.isClosed()) {
                super.onBackPressed();
            } else {
                boomMenuButton.dismiss();
                boomMenuButtonTitle.dismiss();
            }
        }

        if(mediaPlayer != null){
            mediaPlayer.stop();//停止播放
            mediaPlayer.release();//释放资源
            //mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, current, AudioManager.FLAG_PLAY_SOUND);//设置为系统当前音量
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String result = bundle.getString("result");
            Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
        }
    }
}
