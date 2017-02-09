package com.hjhz.testdemo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hjhz.testdemo.R;

/**
 * Created by 陈宣宣 on 2016/6/27.
 */
public class TablayoutFragment2 extends Fragment {

    ImageView ivBg;

    private View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_tablayout1, container, false);
        ivBg = (ImageView) v.findViewById(R.id.iv_bg);
        ivBg.setImageResource(R.drawable.white);
        return v;
    }


}
