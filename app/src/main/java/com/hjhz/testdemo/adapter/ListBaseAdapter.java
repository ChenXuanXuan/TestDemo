package com.hjhz.testdemo.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hjhz.testdemo.R;
import com.hjhz.testdemo.activity.MainActivity;
import com.hjhz.testdemo.entity.Entity;
import com.hjhz.testdemo.util.StringUtils;
import com.hjhz.testdemo.view.MyLinkMovementMethod;
import com.hjhz.testdemo.view.TweetTextView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class ListBaseAdapter<T extends Entity> extends BaseAdapter {
    public static final int STATE_EMPTY_ITEM = 0;  // 暂无内容
    public static final int STATE_LOAD_MORE = 1; // 加载中
    public static final int STATE_NO_MORE = 2; // 没有更多
    public static final int STATE_NO_DATA = 3;
    public static final int STATE_LESS_ONE_PAGE = 4;  //没有底部
    public static final int STATE_NETWORK_ERROR = 5;  //网络错误
    public static final int STATE_OTHER = 6;
    protected int state = STATE_LESS_ONE_PAGE;

    protected int _loadmoreText;
    protected int _loadFinishText;
    protected int _noDateText;
    protected int mScreenWidth;

    private LayoutInflater mInflater;
    protected Context context;
    protected LayoutInflater getLayoutInflater(Context context) {
        if (mInflater == null) {
            mInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        return mInflater;
    }

    public void setScreenWidth(int width) {
        mScreenWidth = width;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return this.state;
    }

    protected ArrayList<T> mDatas = new ArrayList<T>();

    public ListBaseAdapter() {
        _loadmoreText = R.string.loading;
        _loadFinishText = R.string.loading_no_more;
        _noDateText = R.string.error_view_no_data;
    }

    @Override
    public int getCount() {

        switch (getState()) {
            case STATE_EMPTY_ITEM:
                return getDataSize() + 1;
            case STATE_NETWORK_ERROR:
            case STATE_LOAD_MORE:
                return getDataSize() + 1;
            case STATE_NO_DATA:
                return 1;
            case STATE_NO_MORE:
                return getDataSize() + 1;
            case STATE_LESS_ONE_PAGE:    //这种情况下 是不存在的。 当然也没有必要 重写getCount  只有两个值 一个是2 一个是 data大小
                return getDataSize();
            default:
                break;
        }
        return getDataSize();   // 这个方法必须重写。如果有底部。那么必须count+1 而把底部 通过getview 绘制出来。 这个 底部 不是通过 addfooterview 出来的。
    }

    public int getDataSize() {
        if (mDatas == null)
            mDatas = new ArrayList<T>();

        return mDatas.size();
    }

    @Override
    public T getItem(int arg0) {
        if (mDatas.size() > arg0) {
            return mDatas.get(arg0);
        }
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    public void setData(ArrayList<T> data) {
        mDatas = data;
        notifyDataSetChanged();
    }

    public ArrayList<T> getData() {
        return mDatas == null ? (mDatas = new ArrayList<T>()) : mDatas;
    }
    public void addData(List<T> data) {
        if (mDatas != null && data != null && !data.isEmpty()) {
            mDatas.addAll(data);
        }
        notifyDataSetChanged();
    }

    public void addItem(T obj) {
        if (mDatas != null) {
            mDatas.add(obj);
        }
        notifyDataSetChanged();
    }

    public void addItem(int pos, T obj) {
        if (mDatas != null) {
            mDatas.add(pos, obj);
        }
        notifyDataSetChanged();
    }

    public void removeItem(Object obj) {
        mDatas.remove(obj);
        //  notifyDataSetChanged();
    }

    public void clear() {
        mDatas.clear();
         notifyDataSetChanged();
    }

    public void setLoadmoreText(int loadmoreText) {
        _loadmoreText = loadmoreText;
    }

    public void setLoadFinishText(int loadFinishText) {
        _loadFinishText = loadFinishText;
    }

    public void setNoDataText(int noDataText) {
        _noDateText = noDataText;
    }

    protected boolean loadMoreHasBg() {
        return true;
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       context= parent.getContext();
       // System.out.println("position" + position + "getCount " + getCount());  /// getView 这里的persion 是 0-9  底部是10  是不包含headerview 和footerview 。这个的和footerview 完全作为了一项绘制 而不是真正的footerview 。
        if (position == getCount() - 1) {//        处理最后一条

            if (getState() == STATE_LOAD_MORE || getState() == STATE_NO_MORE
                    || state == STATE_EMPTY_ITEM
                    || getState() == STATE_NETWORK_ERROR) {
                this.mFooterView = (LinearLayout) LayoutInflater.from(
                        parent.getContext()).inflate(R.layout.list_cell_footer,
                        null);
                if (!loadMoreHasBg()) {
                    mFooterView.setBackgroundDrawable(null);
                }
                ProgressBar progress = (ProgressBar) mFooterView
                        .findViewById(R.id.progressbar);
                TextView text = (TextView) mFooterView.findViewById(R.id.text);
                switch (getState()) {
                    case STATE_LOAD_MORE:
                        setFooterViewLoading();
                        break;
                    case STATE_NO_MORE:
                        mFooterView.setVisibility(View.VISIBLE);
                        progress.setVisibility(View.GONE);
                        text.setVisibility(View.VISIBLE);
                        text.setText(_loadFinishText);
                        break;
                    case STATE_EMPTY_ITEM:
                        progress.setVisibility(View.GONE);
                        mFooterView.setVisibility(View.VISIBLE);
                        text.setText(_noDateText);
                        break;
                    case STATE_NETWORK_ERROR:
                        mFooterView.setVisibility(View.VISIBLE);
                        progress.setVisibility(View.GONE);
                        text.setVisibility(View.VISIBLE);
                        text.setText("加载出错了");

                        break;
                    default:            //上面所有的情形中 都是有底部状态的。 空数据。 没有有 更多。 或者 有更多  都会以一种 底部状态 来显示。
                        progress.setVisibility(View.GONE);
                        mFooterView.setVisibility(View.GONE);
                        text.setVisibility(View.GONE);
                        break;
                }
                return mFooterView;
            }
        }
        if (position < 0) {
            position = 0; // 若列表没有数据，是没有footview/headview的
        }
        return getRealView(position, convertView, parent);
    }

    protected View getRealView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    private LinearLayout mFooterView;

    public View getFooterView() {
        if (mFooterView == null)
            return (LinearLayout) LayoutInflater.from(
                    MainActivity.context).inflate(R.layout.list_cell_footer,
                    null);
        else
            return this.mFooterView;
    }

    public void setFooterViewLoading(String loadMsg) {
        ProgressBar progress = (ProgressBar) mFooterView
                .findViewById(R.id.progressbar);
        TextView text = (TextView) mFooterView.findViewById(R.id.text);
        mFooterView.setVisibility(View.VISIBLE);
        progress.setVisibility(View.VISIBLE);
        text.setVisibility(View.VISIBLE);
        if (StringUtils.isEmpty(loadMsg)) {
            text.setText(_loadmoreText);
        } else {
            text.setText(loadMsg);
        }
    }

    public void setFooterViewLoading() {
        setFooterViewLoading("");
    }

    public void setFooterViewText(String msg) {
        ProgressBar progress = (ProgressBar) mFooterView
                .findViewById(R.id.progressbar);
        TextView text = (TextView) mFooterView.findViewById(R.id.text);
        mFooterView.setVisibility(View.VISIBLE);
        progress.setVisibility(View.GONE);
        text.setVisibility(View.VISIBLE);
        text.setText(msg);
    }

    /*protected void setContent(TweetTextView contentView, String content) {
        contentView.setMovementMethod(MyLinkMovementMethod.a());
        contentView.setFocusable(false);
        contentView.setDispatchToParent(true);
        contentView.setLongClickable(false);
        Spanned span = Html.fromHtml(TweetTextView.modifyPath(content));
        span = InputHelper.displayEmoji(contentView.getResources(),
                span.toString());
        contentView.setText(span);
        MyURLSpan.parseLinkText(contentView, span);
    }*/

    protected void setText(TextView textView, String text, boolean needGone) {
        if (text == null || TextUtils.isEmpty(text)) {
            if (needGone) {
                textView.setVisibility(View.GONE);
            }
        } else {
            textView.setText(text);
        }
    }

    protected void setText(TextView textView, String text) {
        setText(textView, text, false);
    }
}
