package com.delevin.shenghuidai.activity;


import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.View;
import android.view.WindowManager;

import com.delevin.shenghuidai.base.activity.BaseActivity;
import com.delevin.shenghuidai.view.TitleView;
import com.yourenkeji.shenghuidai.R;

public class NoShimingRenzhengActivity extends BaseActivity {
	private TitleView tvTitle;

	@Override
	protected void findViews() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_no_shiming_renzheng);
		// phone = BoluoUtils.getShareOneData(getApplicationContext(), "phone");
		View statusBarview = findViewById(R.id.no_shiming_renzheng_statusBarview);
		// 设置状态栏一体化
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			getWindow().setFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			statusBarview.setVisibility(View.GONE);
		}
		tvTitle = (TitleView) findViewById(R.id.no_shiming_renzheng_titleview);
		tvTitle.initViewsVisible(true, true, true, false);
		tvTitle.setAppTitle("实名认证");
	}

	@Override
	protected void getData() {
		// TODO Auto-generated method stub

	}

}
