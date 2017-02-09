package com.hjhz.testdemo.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.FrameLayout;

import com.hjhz.testdemo.R;
import com.hjhz.testdemo.adapter.Part4Adapter;
import com.hjhz.testdemo.entity.NewsEntity;
import com.hjhz.testdemo.view.TitleView;
import com.hjhz.testdemo.view.slideactivity.SlidingActivity;
import com.hjhz.testdemo.view.xlistview.XListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 陈宣宣 on 2016/4/28.
 */
public class FansActivity extends SlidingActivity {

    @InjectView(R.id.include)
    FrameLayout titleBar;
    @InjectView(R.id.id_xlistview)
    XListView xListView;

    private Part4Adapter adapter;
    private List<NewsEntity> list = new ArrayList<NewsEntity>();
    private NewsEntity entity;

    private int pagecount = 0;

    @SuppressLint("HandlerLeak")
    private Handler handler=new Handler(){
        public void handleMessage(android.os.Message msg) {
            int a=msg.what;
            if(a==1){
                xListView.stopRefresh();
            }
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fans,true);
        // 通过注解绑定控件
        ButterKnife.inject(this);
        initView();
        initTitle();
        initData();
    }

    private void initView() {
        xListView.setPullLoadEnable(true);
        xListView.setPullRefreshEnable(true);
        xListView.setFootViewText("--大玉螺宣丸--");

        xListView.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                if (xListView != null) {
                    xListView.setRefreshTime(sdf.format(new Date()));
                }
                handler.sendEmptyMessageDelayed(1, 3000);

            }

            @Override
            public void onLoadMore() {
                if(list.size()>22){
                    xListView.stopLoadMore();
                    xListView.setFootViewText("--没有更多了--");
                    return;
                }
                if(pagecount<1){
                    for(int i=0;i<10;i++){
                        entity = new NewsEntity();
                        entity.setId(i);
                        entity.setTitle("卡卡宣");
                        entity.setContent("六代目火影|技能：千鸟、雷切、万花筒写轮眼。");
                        entity.setCreateTime(new Date().getTime());
                        entity.setImgUrl("drawable://" + R.drawable.kkx);
                        list.add(entity);
                    }
                    pagecount++;
                }else{
                    for(int i=0;i<3;i++){
                        entity = new NewsEntity();
                        entity.setId(i);
                        entity.setTitle("大宣丸");
                        entity.setContent("木叶三忍之一|技能：三重罗生门、秽土转生。");
                        entity.setCreateTime(new Date().getTime());
                        entity.setImgUrl("drawable://" + R.drawable.dashe);
                        list.add(entity);
                    }
                    xListView.setFootViewText("--没有更多了--");
                    //xListView.setPullLoadEnable(false);
                }

            }
        });
    }

    private void initTitle() {
        TitleView titleView = new TitleView(this, titleBar, "我的粉丝", R.drawable.ic_back, false);
        titleView.getBoomButton().setVisibility(View.GONE);
        titleView.getLeftButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
            }
        });
    }

    private void initData() {

        for(int i=0;i<10;i++){
            entity = new NewsEntity();
            entity.setId(i);
            entity.setTitle("大宣丸");
            entity.setContent("木叶三忍之一|技能：三重罗生门、秽土转生。");
            entity.setCreateTime(new Date().getTime());
            entity.setImgUrl("drawable://" + R.drawable.dashe);
            list.add(entity);
        }

        if(adapter==null){
            adapter = new Part4Adapter(FansActivity.this,list);
            xListView.setAdapter(adapter);
            xListView.stopRefresh();
            xListView.stopLoadMore();
        }else{
            adapter.notifyDataSetChanged();
        }
    }

}
