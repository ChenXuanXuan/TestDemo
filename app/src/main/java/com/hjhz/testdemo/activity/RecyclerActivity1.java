package com.hjhz.testdemo.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import com.hjhz.testdemo.R;
import com.hjhz.testdemo.adapter.RecyclerAdapter1;
import com.hjhz.testdemo.base.BaseActivity;
import com.hjhz.testdemo.entity.NewsEntity;
import com.hjhz.testdemo.view.DividerLine;
import com.hjhz.testdemo.view.TitleView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by 陈宣宣 on 2016/5/3.
 */
public class RecyclerActivity1 extends BaseActivity implements TitleView.TitleClickCallback{

    @InjectView(R.id.include)
    FrameLayout titleBar;
    @InjectView(R.id.id_recyclerview)
    RecyclerView recyclerView;

    private RecyclerAdapter1 adapter;
    private List<NewsEntity> list = new ArrayList<NewsEntity>();
    private NewsEntity entity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.recyclerview1;
    }

    @Override
    protected void initView() {

        //new LinearLayoutManager(this).findFirstCompletelyVisibleItemPosition();//得到第一个可视都的item位置
        recyclerView.setLayoutManager(new LinearLayoutManager(this));//listview
//        recyclerView.setLayoutManager(new GridLayoutManager(this,4));//gridview
        recyclerView.setAdapter(adapter = new RecyclerAdapter1(this, list));
        //添加分割线
        //方法一
        /*recyclerView.addItemDecoration(new DividerItemDecoration(
                this, DividerItemDecoration.VERTICAL_LIST));*/
        //方法二
        DividerLine dividerLine = new DividerLine(DividerLine.VERTICAL);
        dividerLine.setSize(1);
        dividerLine.setColor(0xff00ddff);
        recyclerView.addItemDecoration(dividerLine);

        // 设置item动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    @Override
    protected void initTitle() {
        TitleView titleView = new TitleView(this, titleBar, "RecylerView", R.drawable.ic_back, "删除");
        titleView.getBoomButton().setVisibility(View.GONE);

    }

    @Override
    protected void initData() {
        for(int i=0;i<10;i++){
            entity = new NewsEntity();
            entity.setId(i);
            entity.setTitle("大宣丸");
            entity.setContent("木叶三忍之一|技能：三重罗生门、秽土转生。");
            entity.setCreateTime(new Date().getTime());
            entity.setImgUrl("drawable://" + R.drawable.dashe);
            list.add(entity);
        }
    }

    @Override
    public void rightBtclick() {
        list.remove(2);
        adapter.notifyItemRemoved(2);//删除item并更新的方法
    }

    @Override
    public void leftBtclick() {
        finish();
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
    }
}
