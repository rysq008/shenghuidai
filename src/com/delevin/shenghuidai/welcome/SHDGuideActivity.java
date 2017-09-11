package com.delevin.shenghuidai.welcome;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.delevin.shenghuidai.base.activity.BaseFragmentActivity;
import com.delevin.shenghuidai.main.MainActivity;
import com.delevin.shenghuidai.utils.BoluoUtils;
import com.yourenkeji.shenghuidai.R;

public class SHDGuideActivity extends BaseFragmentActivity implements
		OnPageChangeListener {

	private ViewPager vp;
	private PagerAdapter vpAdapter;
	private List<View> views = new ArrayList<View>();

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		for (View v : views) {
			BitmapDrawable bd = (BitmapDrawable) v.getBackground();
			if (null != bd) {
				bd.setCallback(null);
				bd.getBitmap().recycle();
			}
			v.setBackgroundResource(0);
		}
	}

	private void initViews() {
		// 初始化引导图片列表
		int[] images = { R.drawable.boluo_guide1, R.drawable.boluo_guide2,
				R.drawable.boluo_guide3, R.drawable.boluo_guide4 };
		for (int i = 0, j = images.length; i < j; i++) {
			ImageView iv = new ImageView(SHDGuideActivity.this);
			iv.setScaleType(ScaleType.FIT_XY);
			iv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT));
			Bitmap bmp = BitmapFactory
					.decodeResource(getResources(), images[i]);
			BitmapDrawable bd = new BitmapDrawable(getResources(), bmp);
			iv.setBackgroundDrawable(bd);
			// iv.setBackground(bd);
			if (i == j - 1) {
				iv.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {

						startActivity(new Intent(SHDGuideActivity.this,
								MainActivity.class));

						SHDGuideActivity.this.finish();
					}
				});

			}
			views.add(iv);
		}

		// 初始化Adapter
		vpAdapter = new PagerAdapter() {

			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
				// TODO Auto-generated method stub
				container.removeView((View) object);
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				// TODO Auto-generated method stub
				View view = views.get(position);
				container.addView(view);
				return view;
			}

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				// TODO Auto-generated method stub
				return arg0 == arg1;
			}

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return views.size();
			}

			@Override
			public int getItemPosition(Object object) {
				// TODO Auto-generated method stub
				return POSITION_NONE;
			}
		};

		vp = (ViewPager) findViewById(R.id.vpGuide);
		vp.setAdapter(vpAdapter);
		vp.setOnPageChangeListener(this);
		vp.setCurrentItem(0);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int arg0) {
		// 设置底部小点选中状态
	}

	@Override
	protected void findViews() {
		// TODO Auto-generated method stub
		setContentView(R.layout.boluos_main_guide);
		View statusBarview = findViewById(R.id.statusBarview);
		// 设置状态栏一体化
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			statusBarview.setVisibility(View.GONE);
		}

		// 初始化页面
		initViews();

	}

	@Override
	protected void getData() {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		// map.put("visonCode", APPName.getVersionCode(this)+"");
		map.put("gesNum", "5");
		BoluoUtils.getShareCommit(this, map);
	}
}
