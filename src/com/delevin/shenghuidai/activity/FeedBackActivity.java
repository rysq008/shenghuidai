package com.delevin.shenghuidai.activity;

import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;

import com.delevin.shenghuidai.base.activity.BaseActivity;
import com.delevin.shenghuidai.utils.BoluoUtils;
import com.delevin.shenghuidai.utils.NetUtils;
import com.delevin.shenghuidai.view.TitleView;
import com.yourenkeji.shenghuidai.R;

public class FeedBackActivity extends BaseActivity implements OnClickListener {

	@Override
	protected void findViews() {
		setContentView(R.layout.boluos_help_feedback);
		TitleView titleView = (TitleView) findViewById(R.id.titleView_listview_jiazai);
		View statusBarview = findViewById(R.id.statusBarview);
		// 设置状态栏一体化
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			statusBarview.setVisibility(View.GONE);
		}
		titleView.initViewsVisible(true, true, true, false);
		titleView.setAppTitle("意见反馈");
		// findViewById(R.id.boluos_help_email).setOnClickListener(this);
		// findViewById(R.id.boluos_help_faq).setOnClickListener(this);
		// findViewById(R.id.boluos_help_online).setOnClickListener(this);
		// findViewById(R.id.boluos_help_tel).setOnClickListener(this);
		// findViewById(R.id.boluos_help_wechat).setOnClickListener(this);
		findViewById(R.id.feedback_submit_btn).setOnClickListener(this);
	}

	@Override
	protected void getData() {
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.feedback_submit_btn:
			if (NetUtils.getNetWorkState(this) != -1) {
//				Intent intent = new Intent(this, JSAndroidActivity.class);
//				intent.putExtra("jsUrl", BeanUrl.XINSHOU_HELP_STRING);
//				intent.putExtra("title", "常见问题");
//				startActivity(intent);
				
			} else {
				BoluoUtils.getDilogDome(this, "温馨提示", "您当前的网络不可用", "确定");
			}
			break;
		}
	}
}
