package com.delevin.shenghuidai.welcome;

import java.util.Map;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;

import com.delevin.application.Myapplication;
import com.delevin.shenghuidai.base.activity.BaseActivity;
import com.delevin.shenghuidai.bean.BeanUrl;
import com.delevin.shenghuidai.gestureedit.GestureEditActivity;
import com.delevin.shenghuidai.gestureedit.GestureVerifyActivity;
import com.delevin.shenghuidai.main.MainActivity;
import com.delevin.shenghuidai.utils.BoluoUtils;
import com.delevin.shenghuidai.utils.StatusBarUtil;
import com.delevin.shenghuidai.view.CountDownProgress;
import com.yourenkeji.shenghuidai.R;

/**
 *     @author 李红涛  @version 创建时间：2016-12-15 下午5:09:43    类说明 
 */
public class WelcomeActivity extends BaseActivity {
	private SharedPreferences preferences;
	private Boolean isFirstIn;
	Handler handler;
	Runnable runnable;
	private CountDownProgress gProgress;
	private android.content.SharedPreferences.Editor editor;
	private String memberId;
	public static boolean isForeground = false;

	@Override
	protected void onDestroy() {
		super.onDestroy();

	}

	@SuppressLint("InlinedApi")
	@Override
	protected void findViews() {
		// String visonCode =
		// BoluoUtils.getShareOneDataGengxin(WelcomeActivity.this, "visonCode");
		Map<String, String> shareData = BoluoUtils.getShareData(this);
		memberId = shareData.get("memberId");
		Myapplication.registrationID = JPushInterface
				.getRegistrationID(getApplicationContext());
		setContentView(R.layout.boluos_activity_welcome);
		StatusBarUtil.setColorFullScreenNoTitle(this);

		// if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
		// getWindow().setFlags(
		// WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
		// WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		// }
		getApp();
		gProgress = (CountDownProgress) findViewById(R.id.countdownProgressView);
		gProgress.start();
		gProgress
				.setProgressListener(new CountDownProgress.OnProgressListener() {
					@Override
					public void onProgress(int progress) {
						if (progress == 0) {
							getgress();
						}
					}
				});
		gProgress.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				gProgress.stop();
				getgress();
			}
		});

		ImageView iv = (ImageView) findViewById(R.id.Welcome_imgView);

		final GestureDetector detector = new GestureDetector(getBaseContext(),
				new GestureDetector.SimpleOnGestureListener() {
					@Override
					public boolean onSingleTapConfirmed(MotionEvent e) {
						gProgress.stop();
						getgress();

						return true;
					}
				});

		iv.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				detector.onTouchEvent(event);
				return true;
			}
		});
	}

	@Override
	protected void getData() {
		// 获得设备信息 以及设备唯一ID
		TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		Myapplication.DEVICE_ID = tm.getDeviceId();
		Myapplication.INFORMATION = Build.MODEL;
	}

	/**
	 * 判断APP是否第一次安装
	 * */
	@SuppressLint("CommitPrefEdits")
	private void getApp() {

		preferences = getApplicationContext().getSharedPreferences(
				"isProgramFirstIn", MODE_PRIVATE);

		isFirstIn = preferences.getBoolean("isFirstIn", true); // isFirstIn

		editor = preferences.edit();

	}

	private void getgress() {

		if (isFirstIn) {
			editor = preferences.edit();
			editor.putBoolean("isFirstIn", false); // isFirstIn
			editor.commit();
			startActivity(new Intent(WelcomeActivity.this,
					SHDGuideActivity.class)); // 第一次在安装运行
			WelcomeActivity.this.finish();
		} else {
			if (memberId != null) {
				SharedPreferences shareDate = getSharedPreferences(
						"is_set_pwd", 0);
				boolean is_pwd = shareDate.getBoolean("is_pwd", false);
				if (is_pwd) {
					startActivity(new Intent(WelcomeActivity.this,
							GestureVerifyActivity.class));
					WelcomeActivity.this.finish();
				} else {
					startActivity(new Intent(WelcomeActivity.this,
							GestureEditActivity.class)); // 不是第一次安装运行
					WelcomeActivity.this.finish();
				}
			} else {
				startActivity(new Intent(WelcomeActivity.this,
						MainActivity.class));
				WelcomeActivity.this.finish();
			}
		}
	}
}
