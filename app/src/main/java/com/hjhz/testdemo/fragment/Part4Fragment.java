package com.hjhz.testdemo.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.hjhz.testdemo.R;
import com.hjhz.testdemo.activity.AnimatorActivity;
import com.hjhz.testdemo.activity.AnimatorMenuActivity;
import com.hjhz.testdemo.activity.DianzanActivity;
import com.hjhz.testdemo.activity.GestureDetectorActivity;
import com.hjhz.testdemo.activity.GuaGuaKaActivity;
import com.hjhz.testdemo.activity.ImageHelperActivity;
import com.hjhz.testdemo.activity.PagerViewActivity;
import com.hjhz.testdemo.activity.PaintActivity;
import com.hjhz.testdemo.activity.PowerManagerActivity;
import com.hjhz.testdemo.activity.TablayoutActivity;
import com.hjhz.testdemo.activity.TopBarActivity;
import com.hjhz.testdemo.activity.ZooScrollviewActivity;
import com.hjhz.testdemo.adapter.Part4Adapter;
import com.hjhz.testdemo.entity.NewsEntity;
import com.hjhz.testdemo.util.ScreenUtil;
import com.hjhz.testdemo.util.ToastUtil;
import com.hjhz.testdemo.view.swipemenulistview.ListViewWithFooter;
import com.hjhz.testdemo.view.swipemenulistview.SwipeMenu;
import com.hjhz.testdemo.view.swipemenulistview.SwipeMenuCreator;
import com.hjhz.testdemo.view.swipemenulistview.SwipeMenuItem;
import com.hjhz.testdemo.view.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 陈宣宣 on 2016/4/25.
 */
public class Part4Fragment extends Fragment {

    private View view;
    private SwipeMenuListView listView;//可滑动 编辑、删除的listview
    private Part4Adapter adapter;
    private List<NewsEntity> list = new ArrayList<NewsEntity>();
    private NewsEntity entity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_part4, container, false);
        listView = (SwipeMenuListView) view.findViewById(R.id.swip_listview);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    //可缩放顶部图片的scrollview
                    startActivity(new Intent(getActivity(), ZooScrollviewActivity.class));
                }else if (position == 1){
                    //刮刮卡效果
                    startActivity(new Intent(getActivity(), GuaGuaKaActivity.class));
                }else if (position == 2){
                    //画板
                    startActivity(new Intent(getActivity(), PaintActivity.class));
                }else if (position == 3){
                    //TabLayout  选项卡
                    startActivity(new Intent(getActivity(), TablayoutActivity.class), ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                } else if (position == 4){
                    //GestureDetector  手势
                    startActivity(new Intent(getActivity(), GestureDetectorActivity.class));
                }  else if (position == 5){
                    //自定义标题栏  com.hjhz.testdemo.view.mytopbar.Topbar  及 CardView
                    startActivity(new Intent(getActivity(), TopBarActivity.class), ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                } else if (position == 6){
                    //图片效果处理 及  简单的属性动画
                    startActivity(new Intent(getActivity(), ImageHelperActivity.class), ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                } else if (position == 7){
                    //属性动画
                    startActivity(new Intent(getActivity(), AnimatorActivity.class), ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                } else if (position == 8){
                    //属性动画 之 折叠菜单   PowerManagerActivity
                startActivity(new Intent(getActivity(), AnimatorMenuActivity.class), ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                } else if (position == 9){
                    //控制屏幕常亮
                    startActivity(new Intent(getActivity(), PowerManagerActivity.class), ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                } else if (position == 10){
                    //点赞动画
                    startActivity(new Intent(getActivity(), DianzanActivity.class), ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                } else if (position == 11){
                    //viewPager
                    startActivity(new Intent(getActivity(), PagerViewActivity.class));
                }

            }
        });

        for(int i=0;i<10;i++){
            entity = new NewsEntity();
            entity.setId(i);
            entity.setTitle("大宣丸");
            entity.setContent("木叶三忍之一|技能：三重罗生门、秽土转生。");
            entity.setCreateTime(new Date().getTime());
            entity.setImgUrl("drawable://" + R.drawable.dashe);
            if (i==0){
                entity.setTitle("ZooScrollview");
            }else if (i==1){
                entity.setTitle("刮刮卡");
            }else if (i==2){
                entity.setTitle("画板");
            }else if (i==3){
                entity.setTitle("选项卡Tablayout");
            }else if (i==4){
                entity.setTitle("手势GestureDetector");
            }else if (i==5){
                entity.setTitle("自定义标题栏及CardView及简单动画");
            }else if (i==6){
                entity.setTitle("图片效果处理");
            }else if (i==7){
                entity.setTitle("属性动画");
            }else if (i==8){
                entity.setTitle("属性动画 之 折叠菜单");
            }else if (i==9){
                entity.setTitle("屏幕常亮");
            }
            list.add(entity);
        }
        if(adapter==null){
            adapter = new Part4Adapter(getActivity(),list);
            listView.setAdapter(adapter);

            SwipeMenuCreator creator = new SwipeMenuCreator() {
                @Override
                public void create(SwipeMenu menu) {

                    // create item  ONE
                    SwipeMenuItem openItem = new SwipeMenuItem(
                            getActivity());
                    // set item background
                    openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                            0xCE)));
                    // set item width
                    openItem.setWidth(ScreenUtil.dp2px(getActivity(),90));
                    // set item title
                    openItem.setTitle("关注");
                    // set item title fontsize
                    openItem.setTitleSize(18);
                    // set item title font color
                    openItem.setTitleColor(Color.WHITE);
                    // add to menu
                    menu.addMenuItem(openItem);

                    //TWO
                    SwipeMenuItem deleteItem = new SwipeMenuItem(
                            getActivity());
                    deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                            0x3F, 0x25)));
                    deleteItem.setWidth(ScreenUtil.dp2px(getActivity(),90));
                    deleteItem.setTitle("删除");
                    deleteItem.setTitleSize(18);
                    deleteItem.setTitleColor(getResources().getColor(R.color.white));
                    menu.addMenuItem(deleteItem);

                }
            };
            listView.setMenuCreator(creator);

            listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
                @Override
                public void onMenuItemClick(int position, SwipeMenu menu, int index) {
                    switch (index) {
                        case 0:
                            ToastUtil.showShort(getActivity(), "关注");
                            break;
                        case 1:
                            ToastUtil.showShort(getActivity(), "删除");
                            break;
                        default:
                            break;
                    }
                }
            });
            listView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {
                @Override
                public void onSwipeStart(int position) {

                }

                @Override
                public void onSwipeEnd(int position) {

                }
            });
        }else{
            adapter.notifyDataSetChanged();
        }

        listView.loadMore();

        listView.setListener(new ListViewWithFooter.LoadMore() {
            @Override
            public void loadmore() {
                for (int i = 0; i < 5; i++) {
                    entity = new NewsEntity();
                    entity.setId(i);
                    entity.setTitle("卡卡宣");
                    entity.setContent("六代目火影|技能：千鸟、雷切、万花筒写轮眼。");
                    entity.setCreateTime(new Date().getTime());
                    entity.setImgUrl("drawable://" + R.drawable.kkx);
                    if (i==0){
                        entity.setTitle("点赞动画");
                    }else if (i==1){
                        entity.setTitle("ViewPager");
                    }else if (i==2){
                        entity.setTitle("未实现");
                    }else if (i==3){
                        entity.setTitle("未实现");
                    }else if (i==4){
                        entity.setTitle("未实现");
                    }
                    list.add(entity);

                    listView.loadSuccess(10, 5, 2);
                }
            }
        });

        return view;
    }
}
