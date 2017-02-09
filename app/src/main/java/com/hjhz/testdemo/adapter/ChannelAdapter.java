package com.hjhz.testdemo.adapter;

import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hjhz.testdemo.R;
import com.hjhz.testdemo.base.BaseViewPagerFragment;
import com.hjhz.testdemo.entity.ChannalBean;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author HuangWenwei
 * @date 2014年9月29日
 */
public class ChannelAdapter extends ListBaseAdapter<ChannalBean> {

    static class ViewHolder {
        @InjectView(R.id.button1)
        TextView tv_name;
        @InjectView(R.id.imageButton2)
        ImageButton ib_delete;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    @Override
    protected View getRealView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null || convertView.getTag() == null) {
            convertView = getLayoutInflater(parent.getContext()).inflate(
                    R.layout.channel, null);
            vh = new ViewHolder(convertView);

            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }


        ChannalBean channal = mDatas.get(position);

        if (channal.getColumnName().equals("新闻")) {
            vh.tv_name.setBackgroundDrawable(new BitmapDrawable());
        }
        vh.tv_name.setText(channal.getColumnName());

        if (BaseViewPagerFragment.editable)
            vh.ib_delete.setVisibility(View.VISIBLE);
        else {
            vh.ib_delete.setVisibility(View.GONE);
        }


        return convertView;
    }
}
