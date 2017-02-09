package com.hjhz.testdemo.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hjhz.testdemo.R;
import com.hjhz.testdemo.activity.WebviewActivity;
import com.hjhz.testdemo.adapter.ListBaseAdapter;
import com.hjhz.testdemo.adapter.NewsAdapter;
import com.hjhz.testdemo.base.BaseFragment;
import com.hjhz.testdemo.entity.Entity;
import com.hjhz.testdemo.entity.NewsEntity;
import com.hjhz.testdemo.entity.gallBean;
import com.hjhz.testdemo.util.ToastUtil;
import com.hjhz.testdemo.view.MyAdGallery;
import com.hjhz.testdemo.view.slideactivity.IntentUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 陈宣宣 on 2016/4/12.
 */
public class NewsFragment<T extends Entity> extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener,
        AbsListView.OnScrollListener {

    private View v;
    @InjectView(R.id.swiperefreshlayout)
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    @InjectView(R.id.listview)
    protected ListView mListView;

    private MyAdGallery gallery;
    private LinearLayout ovalLayout;
    private TextView textView00;
    private View gall;

    private int mCurrentPage = 1;
    protected NewsAdapter mAdapter;
    private List<gallBean> galllist = new ArrayList<gallBean>();
    private List<NewsEntity> newsList;

    private Handler handler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_pull_refresh_listview, container, false);
        ButterKnife.inject(this, v);
        initHandler();
        if (mInflater == null) {
            mInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(
                R.color.swiperefresh_color1, R.color.swiperefresh_color2,
                R.color.swiperefresh_color3, R.color.swiperefresh_color4);

        mListView.setOnItemClickListener(this);
        mListView.setOnScrollListener(this);

//------------------------------------------------------------------------------------------------------


        new Thread(){
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        }.start();


        return v;
    }

    @SuppressLint("HandlerLeak")//去警告
    private void initHandler() {
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        //初始化轮播控件
                        gall = mInflater.inflate(R.layout.gallery, null);
                        gallery = (MyAdGallery) gall.findViewById(R.id.adgallery); // 获取Gallery组件
                        ovalLayout = (LinearLayout) gall.findViewById(R.id.ovalLayout);// 获取圆点容器
                        textView00 = (TextView) gall.findViewById(R.id.textView00);
                        mListView.addHeaderView(gall);
                        gallery.setMyOnItemClickListener(new MyAdGallery.MyOnItemClickListener() {
                            @Override
                            public void onItemClick(int curIndex) {
                                if (galllist.get(curIndex) == null) {
                                    return;
                                }
                                Intent intent = new Intent(getActivity(), WebviewActivity.class);
                                IntentUtils.getInstance().startActivity(getActivity(), intent);
                            }
                        });
//------------------------------------------------------------------------------------------------------
                        //模拟列表数据
                        newsList = new ArrayList<NewsEntity>();
                        NewsEntity news = null;
                        for(int i=0;i<10;i++){
                            news = new NewsEntity();
                            news.setId(i);
                            news.setTitle("不吃肉");
                            news.setCommentNum(i);
                            news.setContent("减肥就是管住嘴迈开腿，你可以的！");
                            news.setImgUrl("drawable://" + R.drawable.bulan);
                            newsList.add(news);
                        }
                        if (mAdapter == null) {
                            mAdapter = new NewsAdapter(getActivity(),newsList);
                            mListView.setAdapter(mAdapter);

                        } else {
                            mAdapter.notifyDataSetChanged();
                        }

                        handler.sendEmptyMessage(1);

                        break;
                    case 1:
                        //模拟轮播数据
                        gallBean gallentity= null;
                        for(int i=0;i<5;i++){
                            gallentity= new gallBean();
                            gallentity.setId(i);
                            gallentity.setLabel("呵呵");
                            gallentity.setPicurl("drawable://" + R.drawable.bulan);
                            gallentity.setStyle(0);
                            if(i%2==0){
                                gallentity.setTitle("嘻嘻");
                            }else{
                                gallentity.setTitle("嘿嘿");
                            }
                            galllist.add(gallentity);
                        }
                        if (galllist != null && galllist.size() > 0){
                            //轮播开始
                            String galls[] = new String[galllist.size()];
                            String[] text = new String[galllist.size()];
                            for (int i = 0; i < galllist.size(); i++) {
                                galls[i] = galllist.get(i).getPicurl();
                                text[i] = galllist.get(i).getTitle();
                                System.out.println(" gall " + galls[i]);
                            }
                            gallery.start(getActivity(), galls, new int[3], 5000,
                                    ovalLayout, R.drawable.gallary_dot_focused,
                                    R.drawable.gallary_dot_normal, textView00, text);
                        }
                        break;
                    case 100:
                        Toast.makeText(getActivity(), "加载数据失败，请稍候重试", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        };
    }

    @Override
    protected View inflateView(int resId) {
        return super.inflateView(resId);
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                ToastUtil.showShort(getActivity(),"刷新完成");
                mSwipeRefreshLayout.setRefreshing(false);
            }
         }, 4000);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(getActivity(), WebviewActivity.class);
        IntentUtils.getInstance().startActivity(getActivity(), intent);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }
}
