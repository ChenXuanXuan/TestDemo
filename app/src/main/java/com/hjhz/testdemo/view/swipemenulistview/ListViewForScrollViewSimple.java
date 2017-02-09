package com.hjhz.testdemo.view.swipemenulistview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;


/**
 * @author: xiyuan
 * @类 说 明:
 * @version 1.0
 * @创建时间：2014-11-8 下午1:51:06
 * 
 */
public class ListViewForScrollViewSimple extends ListView {
 

	public ListViewForScrollViewSimple(Context context, AttributeSet attrs,
									   int defStyle) {
		super(context, attrs, defStyle);
	}

	public ListViewForScrollViewSimple(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ListViewForScrollViewSimple(Context context) {
		super(context);
	}
	// AT_MOST 这个也就是父组件，能够给出的最大的空间，当前组件的长或宽最大只能为这么大，当然也可以比这个小。
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

 

}
