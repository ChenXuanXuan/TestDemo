package com.hjhz.testdemo.view.swipemenulistview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hjhz.testdemo.R;

public class ListViewWithFooter extends ListView {
    private LinearLayout moreView;
    private LoadMore loadMore;
    private TextView textView;
    private ProgressBar bar;
    private ClearHis clearHis;
    private goNewActivity go;

    public ListViewWithFooter(Context context, AttributeSet attrs,
                              int defStyle) {
        super(context, attrs, defStyle);
        initWithContext(context);

    }

    public ListViewWithFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        initWithContext(context);

    }

    public ListViewWithFooter(Context context) {
        super(context);
        initWithContext(context);
    }


    private void initWithContext(final Context context) {
        // TODO Auto-generated method stub
        moreView = (LinearLayout) LayoutInflater.from(context).inflate(
                R.layout.footer_loadmore, null);


        bar = (ProgressBar) moreView
                .findViewById(R.id.xlistview_footer_progressbar);

        textView = (TextView) moreView
                .findViewById(R.id.xlistview_footer_hint_textview);

        addFooterView(moreView);
        moreView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (loadMore != null) {
                    bar.setVisibility(View.VISIBLE);
                    textView.setText("加载中...");
                    moreView.setClickable(false);
                    loadMore.loadmore();
                } else if (clearHis != null) {
                    clearHis.clear();
                } else if (go != null) {
                    go.go();
                }
            }
        });
    }

    public void loadError() {
        textView.setText("点击重试");
        moreView.setClickable(true);
        bar.setVisibility(View.GONE);
    }
    public void loadMore() {
        textView.setText("点击查看更多");
        moreView.setClickable(true);
        bar.setVisibility(View.GONE);
    }

    public void loadSuccess(int pagesize, int newsize, int page) {
        // TODO Auto-generated method stub
        if (newsize == 0 && page == 1) {
            textView.setText("暂无数据");
            moreView.setClickable(false);
            bar.setVisibility(View.GONE);
        } else if (newsize < pagesize) {
            textView.setText("已加载完毕");
            moreView.setClickable(false);
        } else {
            textView.setText("点击查看更多");
            moreView.setClickable(true);
        }
        bar.setVisibility(View.GONE);
    }

    public void showNodata() {
        textView.setText("");
        moreView.setClickable(false);
        moreView.setBackgroundColor(getResources().getColor(R.color.white));
        bar.setVisibility(View.GONE);
        // TODO Auto-generated method stub
    }


    public LinearLayout getFooterView() {
        // TODO Auto-generated method stub
        return moreView;
    }


    public void setFooterViewText(String string) {
        // TODO Auto-generated method stub
        textView.setText(string);
    }


    public interface LoadMore {
        public void loadmore();
    }

    public interface ClearHis {
        public void clear();
    }

    public interface goNewActivity {
        public void go();
    }


    public void setListener(LoadMore loadMore) {
        // TODO Auto-generated method stub
        this.loadMore = loadMore;
    }

    public void setClearHisListener(ClearHis clearHis) {
        // TODO Auto-generated method stub
        this.clearHis = clearHis;
    }

    public void setgoListener(goNewActivity go) {
        // TODO Auto-generated method stub
        this.go = go;
    }


}
