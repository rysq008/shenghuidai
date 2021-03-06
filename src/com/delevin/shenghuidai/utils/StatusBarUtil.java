package com.delevin.shenghuidai.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.yourenkeji.shenghuidai.R;

@SuppressLint("NewApi")
public class StatusBarUtil {

	private static final int	FAKE_STATUS_BAR_VIEW_ID	= R.id.statusbarutil_fake_status_bar_view;
	private static final int	DEFAULT_ALPHA			= 0;

	/**
	 * 设置状态栏颜色
	 * 
	 * @param activity
	 *            需要设置的activity
	 * @param color
	 *            状态栏颜色值
	 * @param statusBarAlpha
	 *            状态栏透明度
	 */
	public static void setColor(Activity activity, int color, int statusBarAlpha, boolean needShowStatusBar) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

			// View decorView = getWindow().getDecorView();
			// int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
			// | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
			// decorView.setSystemUiVisibility(option);
			// getWindow().setStatusBarColor(Color.TRANSPARENT);

			activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			if (needShowStatusBar) {
				ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();// 同下句等效
				// ViewGroup decorView = (ViewGroup) activity
				// .findViewById(android.R.id.content);
				View fakeStatusBarView = decorView.findViewById(FAKE_STATUS_BAR_VIEW_ID);
				if (fakeStatusBarView != null) {
					if (fakeStatusBarView.getVisibility() == View.GONE) {
						fakeStatusBarView.setVisibility(View.GONE);
					}
					fakeStatusBarView.setBackgroundColor(calculateStatusColor(color, statusBarAlpha));
				} else {
					View view = createStatusBarView(activity, color, statusBarAlpha);
					LinearLayout linearlayout = (LinearLayout) decorView.getChildAt(0);
					linearlayout.addView(view, 0);
					// decorView.addView(view);
				}
				// setRootView(activity);
			} else {
				hideFakeStatusBarView(activity);
			}
		}
	}

	/**
	 * 动态隐藏状态栏
	 * 
	 * @param context
	 * @param enable
	 *            true：隐藏 ，false：显示
	 */
	public static void full(Activity context, boolean enable) {
		if (enable) {
			WindowManager.LayoutParams lp = context.getWindow().getAttributes();
			lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
			context.getWindow().setAttributes(lp);
			context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
		} else {
			WindowManager.LayoutParams attr = context.getWindow().getAttributes();
			attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
			context.getWindow().setAttributes(attr);
			context.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
		}
	}

	@SuppressLint("NewApi")
	public static void setShoworHideStatuBar(View rootView, boolean showStatusBar) {

		if (showStatusBar) {
			rootView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
		} else {
			rootView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
		}

	}

	/**
	 * 设置状态栏颜色
	 * 
	 * @param activity
	 *            需要设置的activity
	 * @param color
	 *            状态栏颜色值
	 * @param statusBarAlpha
	 *            状态栏透明度
	 */
	public static void setColor(Activity activity, int color) {
		setColor(activity, color, DEFAULT_ALPHA, true);
	}

	public static void setPrimaryColor(Activity activity) {
		setColor(activity,
				activity.getResources().getColor(R.color.boluo_Yellow),
				DEFAULT_ALPHA, true);
	}

	public static void setColorFullScreenNoTitle(Activity activity) {
		setColor(activity, Color.TRANSPARENT, DEFAULT_ALPHA, false);
	}

	/**
	 * 隐藏伪状态栏 View
	 * 
	 * @param activity
	 *            调用的 Activity
	 */
	public static void hideFakeStatusBarView(Activity activity) {
		ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
		View fakeStatusBarView = decorView.findViewById(FAKE_STATUS_BAR_VIEW_ID);
		if (fakeStatusBarView != null) {
			fakeStatusBarView.setVisibility(View.GONE);
		}
	}

	// /////////////////////////////////////////////////////////////////////////////////

	@TargetApi(Build.VERSION_CODES.KITKAT)
	public static void clearPreviousSetting(Activity activity) {
		ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
		View fakeStatusBarView = decorView.findViewById(FAKE_STATUS_BAR_VIEW_ID);
		if (fakeStatusBarView != null) {
			decorView.removeView(fakeStatusBarView);
			ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
			rootView.setPadding(0, 0, 0, 0);
		}
	}

	/**
	 * 生成一个和状态栏大小相同的彩色矩形条
	 * 
	 * @param activity
	 *            需要设置的 activity
	 * @param color
	 *            状态栏颜色值
	 * @return 状态栏矩形条
	 */
	public static View createStatusBarView(Activity activity, int color) {
		return createStatusBarView(activity, color, 0);
	}

	/**
	 * 生成一个和状态栏大小相同的半透明矩形条
	 * 
	 * @param activity
	 *            需要设置的activity
	 * @param color
	 *            状态栏颜色值
	 * @param alpha
	 *            透明值
	 * @return 状态栏矩形条
	 */
	public static View createStatusBarView(Activity activity, int color, int alpha) {
		// 绘制一个和状态栏一样高的矩形
		View statusBarView = new View(activity);
		int default_status_bar_height = getStatusBarHeight(activity);
		// default_status_bar_height = DisplayUtil.dip2px(activity, 20);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, default_status_bar_height);
		statusBarView.setLayoutParams(params);
		statusBarView.setBackgroundColor(calculateStatusColor(color, alpha));
		statusBarView.setId(FAKE_STATUS_BAR_VIEW_ID);
		return statusBarView;
	}

	/**
	 * 设置根布局参数
	 */
	@SuppressLint("NewApi")
	public static void setRootView(Activity activity) {
		Window window = activity.getWindow();
		ViewGroup mContentView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);

		// 首先使 ChildView 不预留空间
		View mChildView = mContentView.getChildAt(0);
		if (mChildView != null) {
			// ViewCompat.setFitsSystemWindows(mChildView, false);
			mChildView.setFitsSystemWindows(false);
		}

		int statusBarHeight = getStatusBarHeight(activity);
		// 需要设置这个 flag 才能设置状态栏
		window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		// 避免多次调用该方法时,多次移除了 View
		if (mChildView != null && mChildView.getLayoutParams() != null && mChildView.getLayoutParams().height == statusBarHeight) {
			// 移除假的 View.
			mContentView.removeView(mChildView);
			mChildView = mContentView.getChildAt(0);
		}
		if (mChildView != null) {
			FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mChildView.getLayoutParams();
			// 清除 ChildView 的 marginTop 属性
			if (lp != null && lp.topMargin >= statusBarHeight) {
				lp.topMargin -= statusBarHeight;
				mChildView.setLayoutParams(lp);
			}
		}
		ViewGroup parent = (ViewGroup) activity.findViewById(android.R.id.content);
		for (int i = 0, count = parent.getChildCount(); i < count; i++) {
			View childView = parent.getChildAt(i);
			if (childView instanceof ViewGroup) {
				childView.setFitsSystemWindows(true);
				((ViewGroup) childView).setClipToPadding(true);
			}
		}
	}

	/**
	 * 获取状态栏高度
	 * 
	 * @param context
	 *            context
	 * @return 状态栏高度
	 */
	public static int getStatusBarHeight(Context context) {
		// 获得状态栏高度
		int resourceId = context.getResources().getIdentifier(
				"status_bar_height", "dimen", "android");
		return context.getResources().getDimensionPixelSize(resourceId);
	}

	/**
	 * 计算状态栏颜色
	 * 
	 * @param color
	 *            color值
	 * @param alpha
	 *            alpha值
	 * @return 最终的状态栏颜色
	 */
	public static int calculateStatusColor(int color, int alpha) {
		if (alpha == 0) { return color; }
		float a = 1 - alpha / 255f;
		int red = color >> 16 & 0xff;
		int green = color >> 8 & 0xff;
		int blue = color & 0xff;
		red = (int) (red * a + 0.5);
		green = (int) (green * a + 0.5);
		blue = (int) (blue * a + 0.5);
		return 0xff << 24 | red << 16 | green << 8 | blue;
	}

	/**
	 * 设置与顶部状态栏的间距
	 */
	public static void setStatusBarPadding(Context context, int StatusBarHeight) {
		if (StatusBarHeight == 0) {
			int statusBarHeight = StatusBarUtil.getStatusBarHeight(context);
			StatusBarHeight = statusBarHeight;
		}
		ViewGroup contentView = ((ViewGroup) ((Activity) context)
				.findViewById(android.R.id.content));
		contentView.setPadding(0, StatusBarHeight, 0, 0);
	}

	public static void setStatusBarPadding(Context context) {
		setStatusBarPadding(context, 0);
	}

}
