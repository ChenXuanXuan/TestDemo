package com.hjhz.testdemo.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hjhz.testdemo.R;
import com.hjhz.testdemo.activity.FansActivity;
import com.hjhz.testdemo.view.slideactivity.IntentUtils;

import cn.pedant.SweetAlert.SweetAlertDialog;
import tyrantgit.explosionfield.ExplosionField;


public class SettingFragment extends Fragment implements OnClickListener{

    private RelativeLayout xihuan;
    private RelativeLayout fans;
    private RelativeLayout guanzhu;
    private RelativeLayout pinglun;
    private RelativeLayout shoucang;
    private RelativeLayout sixin;
    private ImageView set;
    private ImageView head;
    private TextView tvName;
    private View v;
    ImageView iv1;
    ImageView iv2;
    ImageView iv3;
    ImageView iv4;
    ImageView iv100;
    private ExplosionField explosionField;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_user_center, container, false);
        iv100 = (ImageView) v.findViewById(R.id.imageView100);
        xihuan = (RelativeLayout) v.findViewById(R.id.favorate);
        fans = (RelativeLayout) v.findViewById(R.id.fans);
        guanzhu = (RelativeLayout) v.findViewById(R.id.guanzhu);
        pinglun = (RelativeLayout) v.findViewById(R.id.pinglun);
        shoucang = (RelativeLayout) v.findViewById(R.id.shoucang);
        sixin = (RelativeLayout) v.findViewById(R.id.sixin);
        set = (ImageView) v.findViewById(R.id.set);
        head = (ImageView) v.findViewById(R.id.head2);
        tvName = (TextView) v.findViewById(R.id.name);
        tvName.setTextColor(0xffffffff);//用16进制设置字体颜色
        xihuan.setOnClickListener(this);
        fans.setOnClickListener(this);
        guanzhu.setOnClickListener(this);
        pinglun.setOnClickListener(this);
        shoucang.setOnClickListener(this);
        sixin.setOnClickListener(this);
        set.setOnClickListener(this);
        iv100.setOnClickListener(this);

        iv1 = (ImageView) v.findViewById(R.id.imageView35);
        iv2 = (ImageView) v.findViewById(R.id.imageView36);
        iv3 = (ImageView) v.findViewById(R.id.imageView37);
        iv4 = (ImageView) v.findViewById(R.id.imageView38);
        iv1.setOnClickListener(this);
        iv2.setOnClickListener(this);
        iv3.setOnClickListener(this);
        iv4.setOnClickListener(this);
        explosionField = ExplosionField.attach2Window(getActivity());


        //启动动画   顶部左侧图片旋转
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.main_img_rotate);
        head.startAnimation(animation);
        return v;
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.favorate:
                SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("开发中...");
                pDialog.setCancelable(true);
                pDialog.setCanceledOnTouchOutside(true);
                pDialog.show();
                break;
            case R.id.fans:
                /*new SweetAlertDialog(getActivity())
                        .setTitleText("目前没有粉丝")
                        .show();*/
                Intent intent = new Intent(getActivity(), FansActivity.class);
                IntentUtils.getInstance().startActivity(getActivity(),intent);//滑动开始和finish
                //getActivity().overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
                break;
            case R.id.guanzhu:
                new SweetAlertDialog(getActivity())
                        .setTitleText("提示")
                        .setContentText("正在开发中")
                        .show();
                break;
            case R.id.pinglun:
                new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("提示")
                        .setContentText("正在施工，请绕道")
                        .setConfirmText("好吧")
                        .show();
                break;
            case R.id.shoucang:
                new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("提示")
                        .setContentText("正在施工，请绕道")
                        .setCancelText("就不绕")
                        .setConfirmText("好吧")
                        .showCancelButton(true)
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.setTitleText("必须绕")
                                        .setContentText("你已经无路可走！")
                                        .setConfirmText("艹")
                                        .showCancelButton(false)
                                        .setConfirmClickListener(null)
                                        .changeAlertType(SweetAlertDialog.ERROR_TYPE);

                            }
                        })
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();
                break;
            case R.id.sixin:
                new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("呵呵")
                        .setContentText("没有私信")
                        .show();
                break;
            case R.id.set:
                new SweetAlertDialog(getActivity(), SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                        .setTitleText("设置")
                        .setContentText("不需要设置")
                        .setConfirmText("转身离开")
                        .setCustomImage(R.drawable.icon)
                        .show();
                break;
            case R.id.imageView35:
                explosionField.explode(iv1);
                break;
            case R.id.imageView36:
                explosionField.explode(iv2);
                break;
            case R.id.imageView37:
                explosionField.explode(iv3);
                break;
            case R.id.imageView38:
                explosionField.explode(iv4);
                break;
            case R.id.imageView100:
                explosionField.explode(iv100);
                break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}