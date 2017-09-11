package com.delevin.shenghuidai.view;

import com.calendar.Utils;
import com.delevin.shenghuidai.utils.StatusBarUtil;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView {
	private static String TAG = MyScrollView.class.getName();

	public void setScrollListener(ScrollListener scrollListener) {
		this.mScrollListener = scrollListener;
	}

	private ScrollListener mScrollListener;

	public MyScrollView(Context context) {
		super(context);
	}

	public MyScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {

		switch (ev.getAction()) {
		case MotionEvent.ACTION_MOVE:

			if (mScrollListener != null) {
				int contentHeight = getChildAt(0).getHeight();
				int scrollHeight = getHeight();
				Log.d(TAG, "scrollY:" + getScrollY() + "contentHeight:"
						+ contentHeight + " scrollHeight" + scrollHeight
						+ "object:" + this);

				int scrollY = getScrollY();
				mScrollListener.onScroll(scrollY);

				if (scrollY + scrollHeight >= contentHeight
						|| contentHeight <= scrollHeight) {
					mScrollListener.onScrollToBottom();
				} else {
					mScrollListener.notBottom();
				}

				if (scrollY == 0) {
					mScrollListener.onScrollToTop();
				}

			}

			break;
		}
		boolean result = super.onTouchEvent(ev);
		requestDisallowInterceptTouchEvent(false);

		return result;
	}

	public interface ScrollListener {
		void onScrollToBottom();

		void onScrollToTop();

		void onScroll(int scrollY);

		void notBottom();
	}
}
