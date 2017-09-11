package com.pusupanshi.shenghuidai.wxapi;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.delevin.application.Myapplication;
import com.delevin.shenghuidai.base.activity.BaseActivity;
import com.delevin.shenghuidai.bean.BeanUrl;
import com.delevin.shenghuidai.interfaces.ShareCallBack;
import com.delevin.shenghuidai.umeng.share.ShareUtils;
import com.delevin.shenghuidai.utils.AndroidUtils;
import com.delevin.shenghuidai.utils.BoluoUtils;
import com.delevin.shenghuidai.utils.OkhttpManger.Funck4;
import com.delevin.shenghuidai.utils.QntUtils;
import com.delevin.shenghuidai.view.TitleView;
import com.yourenkeji.shenghuidai.R;

public class ShareActivity extends BaseActivity implements OnClickListener {
	private Button bt_share;
	private TitleView tvTitle;
	private String invite_code;
	private String qrcode;
	private ImageView imageView;

	@SuppressLint("InlinedApi")
	@Override
	protected void findViews() {
		setContentView(R.layout.activity_share);
		bt_share = (Button) findViewById(R.id.fengxiang);
		bt_share.setOnClickListener(this);
		imageView = (ImageView) findViewById(R.id.imageView_erweima);
		View statusBarview = findViewById(R.id.statusBarview);
		// 设置状态栏一体化
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			// statusBarview.setVisibility(View.GONE);
		}

		tvTitle = (TitleView) findViewById(R.id.share_titleview);
		tvTitle.initViewsVisible(true, true, true, false);
		tvTitle.setAppTitle("我的二维码");
	}

	@Override
	protected void getData() {
		final String phone = BoluoUtils.getShareOneData(this, "phone");
		invite_code = getIntent().getStringExtra("invite_code");
		qrcode = getIntent().getStringExtra("qrcode");
		if (!TextUtils.isEmpty(invite_code) && !TextUtils.isEmpty(qrcode)) {
			initShare(phone);
			return;
		}
		Myapplication.okhttpManger.sendComplexForm(this, false,
				QntUtils.getURL(BeanUrl.yaoqingMa, phone), null, new Funck4() {

					@Override
					public void onResponse(JSONObject result) {
						try {
							invite_code = result.getString("invite_code");
							qrcode = result.getString("qrcode");
							initShare(phone);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	}

	public void initShare(final String phone) {
		AndroidUtils.getImg(getApplicationContext(), qrcode, imageView,
				R.drawable.boluo_center, R.drawable.boluo_fail);
		ShareUtils
				.initShare(
						new ShareCallBack() {
							@Override
							public void onrespone() {
								// Toast.makeText(getApplicationContext(),
								// "111111",
								// Toast.LENGTH_SHORT).show();
								Myapplication.okhttpManger.sendComplexForm(
										getApplicationContext(), false,
										/*
										 * String.format(BeanUrl .HostUrl.
										 * YAOQINGHAOYOU_HOST )
										 */
										BeanUrl.SHARESUCCESS_STRING + phone,
										null, new Funck4() {
											@Override
											public void onResponse(
													JSONObject result) {
											}
										});
							}
						},
						ShareActivity.this,
						"胜辉贷：安全、短期、稳健收益",
						"来胜辉贷，轻松享受短期、安全、高收益理财产品，现在体验还可以获赠50元新手红包。",
						/* BeanUrl.HostUrl.YAOQINGHAOYOU_HOST */BeanUrl.YAOQINGLIANJIE_STRING
								+ invite_code,/* R.drawable.share_icon */
						R.drawable.logo);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		/** attention to this below ,must add this **/
		ShareUtils.getShareOnActivityResult(ShareActivity.this, requestCode,
				resultCode, data);
	}

	/**
	 * 屏幕横竖屏切换时避免出现window leak的问题
	 */
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		ShareUtils.getOnConfigurationChanged();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.fengxiang:
			ShareUtils.getOpen();
			break;
		default:
			break;
		}
	}
}
