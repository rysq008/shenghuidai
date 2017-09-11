package com.delevin.shenghuidai.activity;


import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.delevin.shenghuidai.base.activity.BaseActivity;
import com.delevin.shenghuidai.view.TitleView;
import com.yourenkeji.shenghuidai.R;

public class RegisterOtherActivity extends BaseActivity {
	private TitleView tvTitle;
	private TextView tvWenzi;

	@Override
	protected void findViews() {
		setContentView(R.layout.activity_register_other);
		View statusBarview = findViewById(R.id.statusBarview);
		// 设置状态栏一体化
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			getWindow().setFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			statusBarview.setVisibility(View.GONE);
		}
		tvTitle = (TitleView) findViewById(R.id.register_other_titleview);
		tvTitle.initViewsVisible(true, true, true, false);
		tvTitle.setAppTitle(getIntent().getStringExtra("title"));
		tvWenzi = (TextView) findViewById(R.id.register_other_tv);
		tvWenzi.setText(getIntent().getStringExtra("xieyi"));
	}

	@Override
	protected void getData() {
		// TODO Auto-generated method stub

	}

}
