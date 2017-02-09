package com.hjhz.testdemo.view;

import com.hjhz.testdemo.R;
import com.hjhz.testdemo.fragment.Part1Fragment;
import com.hjhz.testdemo.fragment.Part2Fragment;
import com.hjhz.testdemo.fragment.Part3Fragment;
import com.hjhz.testdemo.fragment.Part4Fragment;

public enum MainTab {

    NEWS(0, R.string.main_tab_name_part1, R.drawable.tab_icon_new,
            Part1Fragment.class),

    TWEET(1, R.string.main_tab_name_part2, R.drawable.tab_icon_tweet,
            Part2Fragment.class),

    EXPLORE(2, R.string.main_tab_name_part3, R.drawable.tab_icon_explore,
            Part3Fragment.class),

    ME(3, R.string.main_tab_name_part4, R.drawable.tab_icon_me,
            Part4Fragment.class);

    private int idx;
    private int resName;
    private int resIcon;
    private Class<?> clz;

    private MainTab(int idx, int resName, int resIcon, Class<?> clz) {
        this.idx = idx;
        this.resName = resName;
        this.resIcon = resIcon;
        this.clz = clz;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public int getResName() {
        return resName;
    }

    public void setResName(int resName) {
        this.resName = resName;
    }

    public int getResIcon() {
        return resIcon;
    }

    public void setResIcon(int resIcon) {
        this.resIcon = resIcon;
    }

    public Class<?> getClz() {
        return clz;
    }

    public void setClz(Class<?> clz) {
        this.clz = clz;
    }
}
