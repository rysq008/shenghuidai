package com.delevin.shenghuidai.denglu;

import java.util.ArrayList;
import java.util.List;

import android.app.Instrumentation;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.RadioButton;

import com.delevin.shenghuidai.base.activity.BaseFragmentActivity;
import com.delevin.shenghuidai.view.TitleView;
import com.yourenkeji.shenghuidai.R;

/**
 * @author 李红涛 E-mail:
 * @version 创建时间：2017-3-13 上午10:10:09 类说明
 */
public class DengluActvity extends BaseFragmentActivity implements
		OnPageChangeListener {

	// 两个滑动页面
	private ViewPager mViewPager;
	private FragmentPagerAdapter mAdapter;
	private List<Fragment> mDatas;
	// 控件
	private RadioButton ll_seller_description = null;
	private RadioButton ll_purchase_process = null;

	// 滑动条颜色
	private int select_color;
	private int unselect_color;

	private int mScreen1_4;

	/** 当前视图宽度 **/
	private Integer viewPagerW = 0;

	private static int width = 0;

	@Override
	protected void findViews() {
		setContentView(R.layout.login_denglu);
		View statusBarview = findViewById(R.id.statusBarview);
		// 设置状态栏一体化
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			statusBarview.setVisibility(View.GONE);
		}
		TitleView titleView = (TitleView) findViewById(R.id.titleView_denglu);
		titleView.initViewsVisible(true, true, true, false);
		titleView.setAppTitle("登录");
		initView();
		initFragment();
	}

	@Override
	protected void getData() {
		// TODO Auto-generated method stub

	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		// 获取颜色
		ll_seller_description = (RadioButton) findViewById(R.id.linearLa_seller_description);
		ll_purchase_process = (RadioButton) findViewById(R.id.linearLa_purchase_process);
		ll_seller_description.setOnClickListener(new MyOnClickListenser(0));
		ll_purchase_process.setOnClickListener(new MyOnClickListenser(1));
		mViewPager = (ViewPager) findViewById(R.id.mViewpagerS);
		mDatas = new ArrayList<Fragment>();
	}

	/**
	 * 初始化fragment
	 */
	private void initFragment() {
		DengluFragnment mSDF = new DengluFragnment();
		YanZhengMaDengluFragment mCPF = new YanZhengMaDengluFragment();
		mDatas.add(mSDF);
		mDatas.add(mCPF);
		mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

			@Override
			public int getCount() {
				return mDatas == null ? 0 : mDatas.size();
			}

			@Override
			public Fragment getItem(int position) {
				return mDatas.get(position);
			}
		};
		mViewPager.setAdapter(mAdapter);
		mViewPager.setOnPageChangeListener(this);
		mViewPager.setCurrentItem(0);
	}

	@Override
	public void onPageScrollStateChanged(int position) {

	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {

	}

	@Override
	public void onPageSelected(int position) {
		switch (mViewPager.getCurrentItem()) {
		case 0:
			ll_seller_description.setChecked(true);
			break;
		case 1:
			ll_purchase_process.setChecked(true);
			break;
		}
	}

	/**
	 * 点击文字进行切换
	 * 
	 * @author wuxl
	 * 
	 */
	public class MyOnClickListenser implements OnClickListener {

		private int index = 0;

		public MyOnClickListenser(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.linearLa_seller_description:
			case R.id.linearLa_purchase_process:
				mViewPager.setCurrentItem(index);
				break;
			}
		}
	}

	/**
	 * 获取屏幕宽度
	 * 
	 * @param context
	 * @return
	 */
	public static int getScreenWidth(Context context) {
		if (width == 0) {
			WindowManager manager = (WindowManager) context
					.getSystemService(Context.WINDOW_SERVICE);
			Display display = manager.getDefaultDisplay();
			width = display.getWidth();
		}
		return width;
	}

	/** 返回上一个状态 */
	public void fanhui(View view) {
		new Thread() {
			public void run() {
				try {
					Instrumentation instrumentation = new Instrumentation();
					instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
		}.start();
	}

}
