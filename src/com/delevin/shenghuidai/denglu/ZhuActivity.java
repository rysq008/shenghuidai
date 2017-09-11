package com.delevin.shenghuidai.denglu;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.delevin.application.Myapplication;
import com.delevin.shenghuidai.base.activity.BaseActivity;
import com.delevin.shenghuidai.bean.BeanUrl;
import com.delevin.shenghuidai.utils.NetUtils;
import com.delevin.shenghuidai.utils.OkhttpManger.Funck4;
import com.delevin.shenghuidai.utils.ProessDilogs;
import com.delevin.shenghuidai.view.ClearEditText;
import com.delevin.shenghuidai.view.TitleView;
import com.yourenkeji.shenghuidai.R;

/**
 *     @author 李红涛  @version 创建时间：2016-12-21 下午3:30:52    类说明 
 */
public class ZhuActivity extends BaseActivity {

	private ClearEditText phonEdit;
	public static ZhuActivity zhuActivity;
	private LinearLayout layout;
	private ImageView img;
	private TitleView titleView;

	@Override
	protected void findViews() {
		setContentView(R.layout.boluos_activity_zhu);
		titleView = (TitleView) findViewById(R.id.titleView_bidDetals);
		View statusBarview = findViewById(R.id.statusBarview);
		// 设置状态栏一体化
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			statusBarview.setVisibility(View.GONE);
		}

		titleView.initViewsVisible(true, true, true, false);
		titleView.setAppTitle("登录或注册");

		phonEdit = (ClearEditText) findViewById(R.id.zhu_phone);
		Button bt = (Button) findViewById(R.id.zhu_bt);
		layout = (LinearLayout) findViewById(R.id.zhu_visibility_layout);
		img = (ImageView) findViewById(R.id.zhu_visibility_image);
		zhuActivity = this;
		bt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (NetUtils.getNetWorkState(ZhuActivity.this) != -1) {
					if (phonEdit.length() == 11) {
						layout.setVisibility(View.VISIBLE);
						ProessDilogs.getProessAnima(img,
								getApplicationContext());
						String url = String.format(
								BeanUrl.LOGINORDENGLU_STRING, phonEdit
										.getText().toString());
						Myapplication.okhttpManger.sendComplexForm(
								getApplicationContext(), false, url, null,
								new Funck4() {
									@Override
									public void onResponse(JSONObject result) {
										String code;
										try {
											code = result.getString("code");
											ProessDilogs.closeAnimation(img,
													layout);
											if (TextUtils.equals(code, "10000")) {

												Intent intent = new Intent(
														ZhuActivity.this,
														DengluActvity.class);
												intent.putExtra("phone",
														phonEdit.getText()
																.toString()
																.trim());
												startActivity(intent);
												ZhuActivity.this.finish();

											} else if (TextUtils.equals(code,
													"10001")) {
												Intent intent = new Intent(
														ZhuActivity.this,
														LoginActivity.class);
												intent.putExtra("phone",
														phonEdit.getText()
																.toString()
																.trim());
												startActivity(intent);
											} else if (TextUtils.equals(code,
													"10002")) {
												Intent intent = new Intent(
														ZhuActivity.this,
														LoginActivity.class);
												intent.putExtra("phone",
														phonEdit.getText()
																.toString()
																.trim());
												startActivity(intent);
											} else {
												Toast.makeText(
														getApplicationContext(),
														result.getString("desc"),
														Toast.LENGTH_SHORT)
														.show();
											}
										} catch (JSONException e) {
											e.printStackTrace();
										}
									}
								});
					}
				} else {
					Toast.makeText(getApplicationContext(), "您当前的网络不可用",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	@Override
	protected void getData() {
		// TODO Auto-generated method stub

	}

}
