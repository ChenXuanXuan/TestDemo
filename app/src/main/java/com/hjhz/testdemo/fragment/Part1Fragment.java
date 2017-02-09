package com.hjhz.testdemo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.hjhz.testdemo.R;
import com.hjhz.testdemo.adapter.ViewPageFragmentAdapter;
import com.hjhz.testdemo.base.BaseViewPagerFragment;
import com.hjhz.testdemo.entity.ChannalBean;
import com.hjhz.testdemo.interf.OnTabReselectListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 陈宣宣 on 2016/4/11.
 */
public class Part1Fragment extends BaseViewPagerFragment implements
        OnTabReselectListener {

    public static ArrayList<ChannalBean> channelList;
    @Override
    protected void onSetupTabAdapter(final ViewPageFragmentAdapter adapter) {


        channelList = new ArrayList<ChannalBean>();// = DaoInterface.getChannel();
        ChannalBean bean = null;
        for(int i=0;i<10;i++){
            bean = new ChannalBean();
            if(i%3==1){
                bean.setColumnName("减肥");
            }else if(i%3==2){
                bean.setColumnName("吃饭");
            }else{
                bean.setColumnName("睡觉");
            }

            bean.setId(i);
            bean.setType(0);
            channelList.add(bean);
        }
        for (int i = 0; i < channelList.size(); i++) {
            if (channelList.get(i).getType() == 0) {
                adapter.addTab(channelList.get(i).getColumnName(), "news", NewsFragment.class);//设置频道数据
            } else {
               // adapter.addTab(list.get(i).getColumnName(), "baoliao", BaoliaoFragment.class,null);
            }
        }



    }

    private Bundle getBundle(int newType) {
        Bundle bundle = new Bundle();
        bundle.putInt("tab", newType);
        return bundle;
    }

    @Override
    protected void setScreenPageLimit() {
        mViewPager.setOffscreenPageLimit(10);
    }


    @Override
    public void onClick(View v) {

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
    public void onTabReselect() {
        try {
            int currentIndex = mViewPager.getCurrentItem();
            Fragment currentFragment = getChildFragmentManager().getFragments()
                    .get(currentIndex);
            if (currentFragment != null
                    && currentFragment instanceof OnTabReselectListener) {
                OnTabReselectListener listener = (OnTabReselectListener) currentFragment;
                listener.onTabReselect();
            }
        } catch (NullPointerException e) {
        }
    }
}
