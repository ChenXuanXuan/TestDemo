package com.hjhz.testdemo.activity;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hjhz.testdemo.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class TopBarActivityFragment extends Fragment {

    public TopBarActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_top_bar, container, false);
    }
}
