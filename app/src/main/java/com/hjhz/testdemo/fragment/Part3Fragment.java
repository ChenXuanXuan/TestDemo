package com.hjhz.testdemo.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hjhz.testdemo.R;
import com.hjhz.testdemo.activity.PhotoDetailActivity;
import com.hjhz.testdemo.adapter.Part3ImgAdapter;
import com.hjhz.testdemo.adapter.TextTagsAdapter;
import com.hjhz.testdemo.entity.NewsEntity;
import com.moxun.tagcloudlib.view.TagCloudView;
import com.yalantis.phoenix.PullToRefreshView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 陈宣宣 on 2016/4/18.
 */
public class Part3Fragment extends Fragment implements View.OnClickListener {

    private View v;
    private PullToRefreshView mPullToRefreshView;
    private ListView listView;
    private Part3ImgAdapter adapter;
    private List<NewsEntity> list = new ArrayList<NewsEntity>();
    private NewsEntity entity;

    private List<String> urlList = new ArrayList<String>();

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_part3, container, false);

        listView = (ListView) v.findViewById(R.id.list_view);
        mPullToRefreshView = (PullToRefreshView) v.findViewById(R.id.pull_to_refresh);
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshView.setRefreshing(false);
                    }
                }, 2000);
            }
        });

        for(int i=0;i<10;i++){
            entity = new NewsEntity();
            if(i%3==0){
                entity.setImgUrl("drawable://" + R.drawable.img1);
            }else if (i%3==1){
                entity.setImgUrl("drawable://" + R.drawable.img2);
            }else{
                entity.setImgUrl("drawable://" + R.drawable.img3);
            }
            if(i==3||i==7){
                entity.setImgUrl("drawable://" + R.drawable.img4);
            }

            entity.setId(i);
            list.add(entity);

        }

        if(adapter == null){
            adapter = new Part3ImgAdapter(getActivity(),list);
            listView.setAdapter(adapter);
        }else{
            adapter.notifyDataSetChanged();
        }

        urlList.add("drawable://" + R.drawable.img1);
        urlList.add("drawable://" + R.drawable.img2);
        urlList.add("drawable://" + R.drawable.img3);
        urlList.add("drawable://" + R.drawable.img4);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), PhotoDetailActivity.class);
                intent.putExtra("urlList", (Serializable) urlList);
                startActivity(intent);
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
}