﻿package com.delevin.shenghuidai.gestureedit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.delevin.shenghuidai.activity.MySafetyManagmentActivity;
import com.delevin.shenghuidai.base.activity.BaseActivity;
import com.delevin.shenghuidai.gestureedit.fund.widget.GestureContentView;
import com.delevin.shenghuidai.gestureedit.fund.widget.LockIndicator;
import com.delevin.shenghuidai.main.MainActivity;
import com.delevin.shenghuidai.view.TitleView;
import com.yourenkeji.shenghuidai.R;

/**
 * 
 * 手势密码设置界面
 * 
 */
public class GestureEditActivity extends BaseActivity implements
		OnClickListener {
	/** 手机号码 */
	public static final String PARAM_PHONE_NUMBER = "PARAM_PHONE_NUMBER";
	/** 意图 */
	public static final String PARAM_INTENT_CODE = "PARAM_INTENT_CODE";
	/** 首次提示绘制手势密码，可以选择跳过 */
	public static final String PARAM_IS_FIRST_ADVICE = "PARAM_IS_FIRST_ADVICE";
	private TextView mTextTitle;
	private TextView mTextCancel;
	private LockIndicator mLockIndicator;
	private TextView mTextTip;
	private FrameLayout mGestureContainer;
	private GestureContentView mGestureContentView;
	private TextView mTextReset;
	private boolean mIsFirstInput = true;
	private String mFirstPassword = null;
	private SharedPreferences preferences;
	private TitleView tvTitle;

	@Override
	protected void findViews() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_gesture_edit);
		setUpViews();
		setUpListeners();
	}

	@Override
	protected void getData() {
	}

	private void setUpViews() {
		tvTitle = (TitleView) findViewById(R.id.titleView_gesture_password);
		tvTitle.initViewsVisible(true, true, true, false);
		tvTitle.setAppTitle(getResources().getString(
				R.string.setup_gesture_code));
		View statusBarview = findViewById(R.id.statusBarview);
		// 设置状态栏一体化
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			statusBarview.setVisibility(View.GONE);
		}

		mTextTitle = (TextView) findViewById(R.id.text_title);
		// mTextTitle.setText("15210310201");
		mTextCancel = (TextView) findViewById(R.id.text_cancel);
		mTextReset = (TextView) findViewById(R.id.text_reset);
		mTextReset.setClickable(false);
		mLockIndicator = (LockIndicator) findViewById(R.id.lock_indicator);
		mTextTip = (TextView) findViewById(R.id.text_tip);
		mGestureContainer = (FrameLayout) findViewById(R.id.gesture_container);
		// 初始化一个显示各个点的viewGroup
		mGestureContentView = new GestureContentView(this, false, "",
				new GestureCallBack() {
					@Override
					public void onGestureCodeInput(String inputCode) {
						if (!isInputPassValidate(inputCode)) {
							mTextTip.setText(Html
									.fromHtml("<font color='#f15a4a'>最少链接4个点, 请重新输入</font>"));
							mGestureContentView.clearDrawlineState(0L);
							return;
						}
						if (mIsFirstInput) {
							mFirstPassword = inputCode;
							updateCodeList(inputCode);
							mGestureContentView.clearDrawlineState(0L);
							mTextReset.setClickable(true);
							mTextReset
									.setText(getString(R.string.reset_gesture_code));
						} else {
							if (inputCode.equals(mFirstPassword)) {
								Toast.makeText(GestureEditActivity.this,
										"设置成功", Toast.LENGTH_SHORT).show();
								mGestureContentView.clearDrawlineState(0L);
								startActivity(new Intent(
										GestureEditActivity.this,
										MainActivity.class));
								preferences = getSharedPreferences(
										"is_set_pwd", 0);
								Editor editor = preferences.edit();
								editor.putString("pwd", mFirstPassword);
								editor.putBoolean("is_pwd", true);
								editor.commit();
								GestureEditActivity.this.finish();
								if (MySafetyManagmentActivity.activity != null) {
									MySafetyManagmentActivity.activity.finish();
								}
							} else {
								mTextTip.setText(Html
										.fromHtml("<font color='#f15a4a'>与上一次绘制不一致，请重新绘制</font>"));
								// 左右移动动画
								Animation shakeAnimation = AnimationUtils
										.loadAnimation(
												GestureEditActivity.this,
												R.anim.shake);
								mTextTip.startAnimation(shakeAnimation);
								// 保持绘制的线，1.5秒后清除
								mGestureContentView.clearDrawlineState(1300L);
							}
						}
						mIsFirstInput = false;
					}

					@Override
					public void checkedSuccess() {

					}

					@Override
					public void checkedFail() {

					}
				});
		// 设置手势解锁显示到哪个布局里面
		mGestureContentView.setParentView(mGestureContainer);
		updateCodeList("");
	}

	private void setUpListeners() {
		mTextCancel.setOnClickListener(this);
		mTextReset.setOnClickListener(this);
	}

	private void updateCodeList(String inputCode) {
		// 更新选择的图案
		mLockIndicator.setPath(inputCode);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.text_cancel:
			this.finish();
			break;
		case R.id.text_reset:
			mIsFirstInput = true;
			updateCodeList("");
			mTextTip.setText(getString(R.string.set_gesture_pattern));
			break;
		default:
			break;
		}
	}

	private boolean isInputPassValidate(String inputPassword) {
		if (TextUtils.isEmpty(inputPassword) || inputPassword.length() < 4) {
			return false;
		}
		return true;
	}

}
