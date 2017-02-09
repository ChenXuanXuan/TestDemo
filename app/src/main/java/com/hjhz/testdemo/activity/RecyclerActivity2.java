package com.hjhz.testdemo.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.FrameLayout;

import com.hjhz.testdemo.R;
import com.hjhz.testdemo.adapter.RecyclerAdapter1;
import com.hjhz.testdemo.adapter.RecyclerAdapter2;
import com.hjhz.testdemo.base.BaseActivity;
import com.hjhz.testdemo.entity.NewsEntity;
import com.hjhz.testdemo.util.ToastUtil;
import com.hjhz.testdemo.view.DividerLine;
import com.hjhz.testdemo.view.TitleView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by 陈宣宣 on 2016/5/3.
 */
public class RecyclerActivity2 extends BaseActivity implements TitleView.TitleClickCallback{

    @InjectView(R.id.include)
    FrameLayout titleBar;
    @InjectView(R.id.id_recyclerview)
    RecyclerView recyclerView;

    private RecyclerAdapter2 adapter;
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

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter = new RecyclerAdapter2(this, list));

        // 设置item动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter.setOnItemClickLitener(new RecyclerAdapter2.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                ToastUtil.showShort(RecyclerActivity2.this,list.get(position).getTitle()+""+position);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                list.remove(position);
                adapter.notifyItemRemoved(position);//删除item并更新的方法
            }
        });

    }

    @Override
    protected void initTitle() {
        TitleView titleView = new TitleView(this, titleBar, "Recyler瀑布", R.drawable.ic_back, "删除");
        titleView.getBoomButton().setVisibility(View.GONE);

    }

    @Override
    protected void initData() {
        for(int i=0;i<10;i++){
            entity = new NewsEntity();
            entity.setId(i);
            entity.setTitle("大宣丸");
            entity.setCreateTime(new Date().getTime());
            if(i%2==0){
                entity.setImgUrl("drawable://" + R.drawable.dashe);
                entity.setContent("木叶三忍之一|技能：三重罗生门、秽土转生、通灵术。");
            }else{
                entity.setImgUrl("drawable://" + R.drawable.kkx);
                entity.setContent("六代目火影|技能：雷遁·影分身之术、千鸟、雷切、万花筒写轮眼、血继限界、通灵 。");
            }

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
