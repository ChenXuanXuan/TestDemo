package com.hjhz.testdemo.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hjhz.testdemo.util.ImageLoaderUtil;
import com.hjhz.testdemo.util.StringUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class MyAdGallery extends Gallery implements
        AdapterView.OnItemClickListener,
        AdapterView.OnItemSelectedListener, OnTouchListener {

    private Context mContext;

    private MyOnItemClickListener mMyOnItemClickListener;

    private int mSwitchTime;
    private Timer mTimer;

    private LinearLayout mOvalLayout;

    private int curIndex = 0;

    private int oldIndex = 0;

    private int mFocusedId;

    private int mNormalId;

    private int[] mAdsId;

    private String[] mUris;

    List<ImageView> listImgs;

    protected ImageLoader imageLoader = ImageLoader.getInstance();
    private TextView textView;
    private String[] imgstr;

    public MyAdGallery(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    public MyAdGallery(Context context) {
        super(context);
    }

    public MyAdGallery(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void start(Context context, String[] mris, int[] adsId,
                      int switchTime, LinearLayout ovalLayout, int focusedId, int normalId, TextView textView, String[] imgstr) {
        if(context!=null){
            this.mContext = context;
            this.mUris = mris;
            this.mAdsId = adsId;
            this.mSwitchTime = switchTime;
            this.mOvalLayout = ovalLayout;
            this.mFocusedId = focusedId;
            this.mNormalId = normalId;

            this.textView = textView;
            this.imgstr = imgstr;
            ininImages();
            setAdapter(new AdAdapter());
            this.setOnItemClickListener(this);
            this.setOnTouchListener(this);
            this.setOnItemSelectedListener(this);
            this.setSoundEffectsEnabled(false);
            this.setAnimationDuration(700);
            this.setUnselectedAlpha(1);
            setSpacing(0);
            setSelection((getCount() / 2 / listImgs.size()) * listImgs.size());
            setFocusableInTouchMode(true);
            initOvalLayout();

            // if (mSwitchTime != 0)
            //startTimer();
        }
    }


    private void ininImages() {
        listImgs = new ArrayList<ImageView>();
        int len = mUris != null ? mUris.length : mAdsId.length;
        for (int i = 0; i < len; i++) {
            ImageView imageview = new ImageView(mContext);
            imageview.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageview.setLayoutParams(new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    StringUtils.screenWidth / 16 * 8));
            if (mUris == null) {
                imageview.setImageResource(mAdsId[i]);
            } else {
                imageLoader.displayImage(mUris[i], imageview, ImageLoaderUtil.options);
            }
            listImgs.add(imageview);
        }

    }


    private void initOvalLayout() {
        mOvalLayout.removeAllViews();
        if (mOvalLayout != null && listImgs.size() < 2) {
            mOvalLayout.getLayoutParams().height = 0;
        } else if (mOvalLayout != null) {
            int Ovalheight = (int) (mOvalLayout.getLayoutParams().height * 0.7);
            int Ovalmargin = (int) (mOvalLayout.getLayoutParams().height * 0.2);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    Ovalheight, Ovalheight);
            layoutParams.setMargins(Ovalmargin, 0, Ovalmargin, 0);
            for (int i = 0; i < listImgs.size(); i++) {
                View v = new View(mContext);
                v.setLayoutParams(layoutParams);
                v.setBackgroundResource(mNormalId);
                mOvalLayout.addView(v);
            }
            mOvalLayout.getChildAt(0).setBackgroundResource(mFocusedId);
        }
    }


    class AdAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            if (listImgs.size() < 2)
                return listImgs.size();
            return Integer.MAX_VALUE;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return listImgs.get(position % listImgs.size());
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
    }

    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {
        int kEvent;
        if (isScrollingLeft(e1, e2)) {
            kEvent = KeyEvent.KEYCODE_DPAD_LEFT;
        } else {
            kEvent = KeyEvent.KEYCODE_DPAD_RIGHT;
        }
        onKeyDown(kEvent, null);
        return true;

    }


    private boolean isScrollingLeft(MotionEvent e1, MotionEvent e2) {
        return e2.getX() > (e1.getX() + 50);
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
        return super.onScroll(e1, e2, distanceX, distanceY);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (MotionEvent.ACTION_UP == event.getAction()
                || MotionEvent.ACTION_CANCEL == event.getAction()) {
            if (mSwitchTime != 0)
                startTimer();
        } else {
            if (mSwitchTime != 0)
                stopTimer();
        }
        return false;
    }

    /**
     * ͼƬ�л��¼�
     */
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
                               long arg3) {


        curIndex = position % listImgs.size();
        if (mOvalLayout != null && listImgs.size() > 1) {
            mOvalLayout.getChildAt(oldIndex).setBackgroundResource(mNormalId);
            mOvalLayout.getChildAt(curIndex).setBackgroundResource(mFocusedId);
            oldIndex = curIndex;
        }

        if (textView != null && imgstr != null)
            textView.setText(imgstr[curIndex]);

    }


    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }


    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                            long arg3) {
        if (mMyOnItemClickListener != null) {
            mMyOnItemClickListener.onItemClick(curIndex);
        }
    }


    public void setMyOnItemClickListener(MyOnItemClickListener listener) {
        mMyOnItemClickListener = listener;
    }

    public interface MyOnItemClickListener {

        void onItemClick(int curIndex);
    }

    public void stopTimer() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }


    public void startTimer() {
        if (mTimer == null && listImgs.size() > 1 && mSwitchTime > 0) {
            mTimer = new Timer();
            mTimer.schedule(new TimerTask() {
                public void run() {
                    handler.sendMessage(handler.obtainMessage(1));
                }
            }, mSwitchTime, mSwitchTime);
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            onScroll(null, null, 1, 0);
            onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
        }
    };
}
