package com.hjhz.testdemo.base;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.hjhz.testdemo.R;
import com.hjhz.testdemo.activity.MainActivity;
import com.hjhz.testdemo.adapter.ChannelAdapter;
import com.hjhz.testdemo.adapter.ViewPageFragmentAdapter;
import com.hjhz.testdemo.entity.ChannalBean;
import com.hjhz.testdemo.fragment.Part1Fragment;
import com.hjhz.testdemo.view.DraggableGridViewPager;
import com.hjhz.testdemo.view.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

/**
 * 带有导航条的基类
 *
 * @author FireAnt（http://my.oschina.net/LittleDY）
 * @created 2014年11月6日 下午4:59:50
 */
public abstract class BaseViewPagerFragment extends BaseFragment {
    private static final String TAG = "DraggableGridViewPagerTestActivity";
    protected PagerSlidingTabStrip mTabStrip;
    protected ViewPager mViewPager;
    protected ViewPageFragmentAdapter mTabsAdapter;
    private ImageView button;
    private TextView view_pager_line2;
    private ChannelAdapter adapter;
    private ChannelAdapter adapter2;
    private Button complete;
    private ImageView imageButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.base_viewpage_fragment, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mTabStrip = (PagerSlidingTabStrip) view
                .findViewById(R.id.pager_tabstrip);
        button = (ImageView) view
                .findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPop();
                showpop();
            }
        });

        mViewPager = (ViewPager) view.findViewById(R.id.pager);

        view_pager_line2 = (TextView) view.findViewById(R.id.view_pager_line2);

        mTabsAdapter = new ViewPageFragmentAdapter(getChildFragmentManager(),
                mTabStrip, mViewPager);

        setScreenPageLimit();
        onSetupTabAdapter(mTabsAdapter);
        if (savedInstanceState != null) {
            int pos = savedInstanceState.getInt("position");
            mViewPager.setCurrentItem(pos, true);

        }

    }

    private PopupWindow popupWindow;
    private View popview;
    private GridView select;
    private GridView unselect;


    public static boolean editable = false;

    protected void initPop() {

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        popview = inflater.inflate(R.layout.popwindow, null, false);

        complete = (Button) popview.findViewById(R.id.complete);
        complete.setText("完成");
        imageButton = (ImageView) popview.findViewById(R.id.imageButton);

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
            }
        });

        popupWindow = new PopupWindow(popview, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, true);
        //popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        adapter = new ChannelAdapter();
        adapter2 = new ChannelAdapter();

        adapter.setData(Part1Fragment.channelList); //已选
    /*    adapter2.setData(DaoInterface.getChannel(2));//未选*/

        mDraggableGridViewPager = (DraggableGridViewPager) popview.findViewById(R.id.draggable_grid_view_pager);
        //  mDraggableGridViewPager2 = (DraggableGridViewPager) popview.findViewById(R.id.draggable_grid_view_pager2);

        mDraggableGridViewPager.setOnPageChangeListener(new DraggableGridViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        mDraggableGridViewPager.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
        mDraggableGridViewPager.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
     /*           complete.setText("完成");
                editable = true;
                adapter.notifyDataSetChanged();
                // showToast(" long clicked!!!");*/
                return true;
            }
        });
        mDraggableGridViewPager.setOnRearrangeListener(new DraggableGridViewPager.OnRearrangeListener() {
            @Override
            public void onRearrange(int oldIndex, int newIndex) {
                if (oldIndex == 0)
                    return;
                if (newIndex == 0)
                    return;


                ChannalBean item = adapter.getItem(oldIndex);
                adapter.removeItem(item);
                adapter.addItem(newIndex, item);
                adapter.notifyDataSetChanged();

                //需要用到数据库    以后再完善
                mTabsAdapter.removeAll();
                setScreenPageLimit();
                onSetupTabAdapter(mTabsAdapter);
            }
        });

   /*     mDraggableGridViewPager2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                // 添加新的标签。
                showToast("   mDraggableGridViewPager2 OnItemClickListener!!!");
            }
        });
        mDraggableGridViewPager2.setAdapter(adapter2);*/

        mDraggableGridViewPager.setAdapter(adapter);
    }

    /*private void showToast(String msg) {
        Toast.makeText(MainActivity.context, msg, Toast.LENGTH_SHORT).show();
    }*/

    private DraggableGridViewPager mDraggableGridViewPager;
    private DraggableGridViewPager mDraggableGridViewPager2;

    private void showpop() {
        // TODO Auto-generated method stub
        popview.startAnimation(AnimationUtils.loadAnimation(
                getActivity().getApplicationContext(), R.anim.fade_in));
        // popupWindow.showAtLocation(mPopuMark, Gravity.BOTTOM, 0, 0);

        popupWindow.showAsDropDown(view_pager_line2);
        popupWindow.update();
    }

    protected void setScreenPageLimit() {
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //No call for super(). Bug on API Level > 11.
        if (outState != null && mViewPager != null) {
            outState.putInt("position", mViewPager.getCurrentItem());
        }
        super.onSaveInstanceState(outState);
    }

    protected abstract void onSetupTabAdapter(ViewPageFragmentAdapter adapter);
}