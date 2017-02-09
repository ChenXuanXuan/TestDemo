package com.hjhz.testdemo.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hjhz.testdemo.R;
import com.hjhz.testdemo.adapter.TextTagsAdapter;
import com.moxun.tagcloudlib.view.TagCloudView;

/**
 * Created by 陈宣宣 on 2016/4/18.
 */
public class Part2Fragment extends Fragment implements View.OnClickListener {

    private View v;
    private ImageView ivTopBlured;//高斯模糊后的图
    private ImageView ivTopOrigin;//原始图

    private float downY;//手指按下的纵坐标
    private int mAlpha = 0;//透明度

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_part2, container, false);
        ivTopBlured = (ImageView) v.findViewById(R.id.iv_top_blured);
        ivTopOrigin = (ImageView) v.findViewById(R.id.iv_top_origin);
        TagCloudView tagCloudView = (TagCloudView) v.findViewById(R.id.tag_cloud);
        tagCloudView.setBackgroundColor(Color.WHITE);

        TextTagsAdapter tagsAdapter = new TextTagsAdapter(new String[]{"梅丽珊卓","布蕾妮","提利昂","席恩·葛雷乔伊","瑟曦·兰尼斯特","乔拉·莫尔蒙","丹妮莉丝·坦格利安","珊莎·史塔克","艾丽娅","拉姆斯·波顿","布兰","大麻雀"});
        tagCloudView.setAdapter(tagsAdapter);


        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.got1);
        Bitmap topBg = blurBitmap(bitmap);
        ivTopBlured.setImageBitmap(topBg);

        ivTopOrigin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        downY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float moveY = event.getY();
                        //手指滑动距离
                        float offsetY = moveY - downY;
                        //指定的view高度
                        int screenY = v.getHeight();
                        //手指滑动距离占view的百分比
                        float movePercent = offsetY / screenY;
                        mAlpha = (int) (movePercent*100);
                        Log.d("mAlpha", "onTouch: "+mAlpha);
                        if (mAlpha > 100) {
                            mAlpha = 100;
                        }
                        if (mAlpha < -100) {
                            mAlpha = -100;
                        }
                        //设置模糊度
                        if(mAlpha>0){
                            ivTopOrigin.setAlpha((int) (255 - mAlpha * 2.55));
                        }else if (mAlpha<0){
                            ivTopOrigin.setAlpha((int) (-mAlpha * 2.55));
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }


                return true;
            }
        });

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 返回键
//            case R.id.iv_back_setting:
//                HomeActivity.menu.showContent();
//                break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    public Bitmap blurBitmap(Bitmap bitmap){
        //Let's create an empty bitmap with the same size of the bitmap we want to blur
        Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        //Instantiate a new Renderscript
        RenderScript rs = RenderScript.create(getActivity());
        //Create an Intrinsic Blur Script using the Renderscript
        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        //Create the Allocations (in/out) with the Renderscript and the in/out bitmaps
        Allocation allIn = Allocation.createFromBitmap(rs, bitmap);
        Allocation allOut = Allocation.createFromBitmap(rs, outBitmap);
        //Set the radius of the blur
        blurScript.setRadius(25.f);
        //Perform the Renderscript
        blurScript.setInput(allIn);
        blurScript.forEach(allOut);
        //Copy the final bitmap created by the out Allocation to the outBitmap
        allOut.copyTo(outBitmap);
        //recycle the original bitmap
        bitmap.recycle();
        //After finishing everything, we destroy the Renderscript.
        rs.destroy();
        return outBitmap;
    }

}