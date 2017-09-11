package com.delevin.shenghuidai.activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.SystemClock;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.delevin.application.Myapplication;
import com.delevin.jsandroid.JSAndroidActivity;
import com.delevin.shenghuidai.base.activity.BaseActivity;
import com.delevin.shenghuidai.bean.BeanUrl;
import com.delevin.shenghuidai.interfaces.GengXinCallBack;
import com.delevin.shenghuidai.main.MainActivity;
import com.delevin.shenghuidai.utils.APPName;
import com.delevin.shenghuidai.utils.BoluoUtils;
import com.delevin.shenghuidai.utils.OkhttpManger.Funck4;
import com.delevin.shenghuidai.view.TitleView;
import com.delevin.shenghuidai.view.UpdateManager;
import com.yourenkeji.shenghuidai.R;

/**
 *     @author 李红涛  @version 创建时间：2017-1-6 上午9:13:44    类说明 
 */

public class MyMoreActivity extends BaseActivity implements OnClickListener {

	private LinearLayout layout_current_version;// 当前版本
	private LinearLayout layout_weixin_service;// 微信客服
//	private LinearLayout layout_service_phone;// 客服电话
	private LinearLayout layout_fengxian_pinggu;// 风险评估
	private LinearLayout layout_guanwang;// 官网
	private TextView txt_current_version_number;// 当前版本号
	private Button bt_exit;// 退出
	private int versioncode, curr_version_code;;
	private String strContent, strUpdateUrl;
	public static MyMoreActivity myMoreActivity;
	private String type;
	private String phone;
	private TextView tvFengxian;
	private TextView more_current_ziti;

	@Override
	protected void findViews() {
		myMoreActivity = this;
		setContentView(R.layout.activity_more);
		more_current_ziti = (TextView) findViewById(R.id.more_current_ziti);
		TitleView titleView = (TitleView) findViewById(R.id.titleView_more);
		View statusBarview = findViewById(R.id.statusBarview);
		layout_current_version = (LinearLayout) findViewById(R.id.more_current_version);
//		layout_service_phone = (LinearLayout) findViewById(R.id.more_service_phone);
		layout_weixin_service = (LinearLayout) findViewById(R.id.more_weixin_service);
		layout_fengxian_pinggu = (LinearLayout) findViewById(R.id.more_fengxian_pinggu);
		layout_guanwang = (LinearLayout) findViewById(R.id.more_guanwang);
		txt_current_version_number = (TextView) findViewById(R.id.more_current_version_number);
		// 获取packagemanager的实例
		PackageManager packageManager = getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo;
		try {
			packInfo = packageManager.getPackageInfo(getPackageName(), 0);
			String version = packInfo.versionName;
			txt_current_version_number.setText(version);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		type = getIntent().getStringExtra("type");
		if (!TextUtils.isEmpty(type)) {
			tvFengxian = (TextView) findViewById(R.id.more_weixin_fengxian);
			tvFengxian.setText(type);
		}
		phone = BoluoUtils.getShareOneData(MyMoreActivity.this, "phone");
		bt_exit = (Button) findViewById(R.id.boluos_exit);
		layout_current_version.setOnClickListener(this);
//		layout_service_phone.setOnClickListener(this);
		layout_weixin_service.setOnClickListener(this);
		layout_fengxian_pinggu.setOnClickListener(this);
		layout_guanwang.setOnClickListener(this);
		bt_exit.setOnClickListener(this);
		// 设置状态栏一体化
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			statusBarview.setVisibility(View.GONE);
		}
		titleView.initViewsVisible(true, true, true, false);
		titleView.setAppTitle("更多");
		
		
		//-------------------------------------(添加测试功能以下整段可以删除不影响工程运行)-------------------------------------
		TextView icon_tv = (TextView) findViewById(R.id.more_icon_tv);
		String host = PreferenceManager.getDefaultSharedPreferences(this).getString("HOST", "");
		if(!host.contains("api.shenghuidai.com")){
			icon_tv.setText(host);
		}else{
			icon_tv.setText("胜辉贷");
		}
		icon_tv.setOnClickListener((new View.OnClickListener() {
			final static int COUNTS = 5;// 点击次数
			final static long DURATION = 1* 1000;// 规定有效时间
			long[] mHits = new long[COUNTS];

			@Override
			public void onClick(View v) {
				/**
				 * 实现双击方法 src 拷贝的源数组 srcPos 从源数组的那个位置开始拷贝. dst 目标数组 dstPos
				 * 从目标数组的那个位子开始写数据 length 拷贝的元素的个数
				 */
				System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
				// 实现左移，然后最后一个位置更新距离开机的时间，如果最后一个时间和最开始时间小于DURATION，即连续5次点击
				mHits[mHits.length - 1] = SystemClock.uptimeMillis();
				if (mHits[0] >= (SystemClock.uptimeMillis() - DURATION)) {
//					String tips = "您已在[" + DURATION + "]ms内连续点击【"
//							+ mHits.length + "】次了！！！";
//					Toast.makeText(MyMoreActivity.this, tips, Toast.LENGTH_SHORT)
//							.show();

					if (mHits.length == 5) {
						final EditText et = new EditText(MyMoreActivity.this);
						new AlertDialog.Builder(MyMoreActivity.this)
								.setTitle("输入测试IP地址")
								.setView(et)
								.setNegativeButton("取消", null)
								.setPositiveButton("确定",
										new DialogInterface.OnClickListener() {

											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {
												// TODO Auto-generated method
												String url = et
														.getEditableText()
														.toString();
												BeanUrl.setHost(MyMoreActivity.this,
														url);
												dialog.cancel();
												Toast.makeText(
														MyMoreActivity.this,
														BeanUrl.HOST_SERVICE_ORIGINAL,
														0).show();
												Intent i = MyMoreActivity.this
														.getPackageManager()
														.getLaunchIntentForPackage(
																MyMoreActivity.this
																		.getPackageName());
												i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
												startActivity(i);

												/** 杀死整个进程 **/
												android.os.Process
														.killProcess(android.os.Process
																.myPid());
											}
										}).show();
					}

				}
			}
		}));
		
	}
	@Override
	protected void getData() {
		final int visonCode = APPName.getVersionCode(this);
		Myapplication.okhttpManger.sendComplexForm(getApplicationContext(),
				false, BeanUrl.GENGXINS_STRING, null, new Funck4() {
					@Override
					public void onResponse(JSONObject result) {
						String code;
						try {
							code = result.getString("code");
							if (TextUtils.equals(code, "10000")) {
								JSONArray array = result.getJSONArray("data");
								for (int i = 0; i < array.length(); i++) {
									JSONObject object2 = array.getJSONObject(i);
									versioncode = object2.getInt("code"); // 版本
									strContent = object2.getString("content"); // 内容
									strUpdateUrl = object2.getString("url");
									if (versioncode > visonCode) {
										more_current_ziti.setText("（发现新版本）");
										more_current_ziti.setTextColor(getApplicationContext().getResources().getColor(R.color.ff3939));
									}
								}
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.more_current_version:
			// 初始化更新管理
			UpdateManager manager = new UpdateManager(MyMoreActivity.this);
			// 检查软件更新
			manager.checkUpdate("0", new GengXinCallBack() {

				@Override
				public void onCode(String code) {
				}
				@Override
				public void onSerivce(String ser) {
					
				}
			});

			// update();
			break;
//		case R.id.more_service_phone:
//			Intent intentTel = new Intent();
//			intentTel.setAction(Intent.ACTION_DIAL);
//			intentTel.setData(Uri.parse("tel:400-009-8087"));
//			startActivity(intentTel);
//			break;
		case R.id.more_fengxian_pinggu:
			Intent intent = new Intent(MyMoreActivity.this,
					JSAndroidActivity.class);
			intent.putExtra("title", "风险评估");
			intent.putExtra("jsUrl", BeanUrl.FENGXIANPINGGU_STRING + phone);
			startActivity(intent);
			break;
		case R.id.more_weixin_service:
			startActivity(new Intent(MyMoreActivity.this,
					MoreWXServiceActivity.class));
			break;
		case R.id.boluos_exit:
			BoluoUtils.getDilogExit(MyMoreActivity.this, MainActivity.class);
			break;
		case R.id.more_guanwang:
			Intent intentF = new Intent();
			intentF.setAction("android.intent.action.VIEW");
			Uri content_url = Uri.parse("https://www.shenghuidai.com/?");
			intentF.setData(content_url);
			startActivity(intentF);
			break;
		default:
			break;
		}
	}
}
