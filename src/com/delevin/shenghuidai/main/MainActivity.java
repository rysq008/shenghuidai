package com.delevin.shenghuidai.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.delevin.jsandroid.JSAndroidActivity;
import com.delevin.shenghuidai.activity.WebActivity;
import com.delevin.shenghuidai.base.activity.BaseFragmentActivity;
import com.delevin.shenghuidai.bean.BeanUrl;
import com.delevin.shenghuidai.denglu.ZhuActivity;
import com.delevin.shenghuidai.fragmnet.FaxianFragment;
import com.delevin.shenghuidai.fragmnet.HomeFragment;
import com.delevin.shenghuidai.fragmnet.MyFragment;
import com.delevin.shenghuidai.fragmnet.TouziFragment;
import com.delevin.shenghuidai.interfaces.DilogCallBack;
import com.delevin.shenghuidai.interfaces.GengXinCallBack;
import com.delevin.shenghuidai.utils.AndroidUtils;
import com.delevin.shenghuidai.utils.BoluoUtils;
import com.delevin.shenghuidai.utils.StatusBarUtil;
import com.delevin.shenghuidai.view.TitleView;
import com.delevin.shenghuidai.view.UpdateManager;
import com.yourenkeji.shenghuidai.R;

public class MainActivity extends BaseFragmentActivity implements OnClickListener, OnPageChangeListener {
	private RadioButton			bt_home;					// 按钮 “主页”
	private RadioButton			bt_touzi;					// 按钮 “我的投资”
	private RadioButton			bt_my;						// 按钮 “我的账户”
	private RadioButton			bt_faxian;					// 按钮 “发现”
	public static Fragment[]	mFragments;
	private String				memberId;
	private boolean				isFirstred;
	private String				phone;
	private String				login_token;
	private Editor				editor;
	private LinearLayout		my_none;
	private Button				bt_denglu;
	private TextView			tlogin;
	private TitleView			titleView;
	public static MainActivity	mainActivity;
	public static boolean		isForeground	= false;
	private ViewPager			viewpage;
	private List<Fragment>		listFragment;
	private RadioGroup			radioGroup;

	// 初始化控件
	@SuppressLint("InlinedApi")
	@Override
	protected void findViews() {

		setContentView(R.layout.activity_main);
		mainActivity = this;
		titleView = (TitleView) findViewById(R.id.titleView_main_activity);
		titleView.initViewsVisible(false, true, true, false);
		titleView.setAppTitle("首页");

		SharedPreferences preferences = getApplicationContext().getSharedPreferences("isProgramFirstIn", MODE_PRIVATE);
		editor = preferences.edit();
		Map<String, String> shareData = BoluoUtils.getShareData(this);
		phone = shareData.get("phone");
		login_token = shareData.get("login_token");
		memberId = shareData.get("memberId");
		isFirstred = preferences.getBoolean("isDilogFirstIn", true); // isFirstIn
		radioGroup = (RadioGroup) findViewById(R.id.boluos_rg_home);
		bt_home = (RadioButton) findViewById(R.id.boluos_bt_home);
		bt_touzi = (RadioButton) findViewById(R.id.boluos_bt_touzi);
		bt_my = (RadioButton) findViewById(R.id.boluos_bt_my);
		bt_faxian = (RadioButton) findViewById(R.id.boluos_bt_faxian);
		bt_denglu = (Button) findViewById(R.id.bt_dilog_login);
		tlogin = (TextView) findViewById(R.id.bt_dilog_resger);
		viewpage = (ViewPager) findViewById(R.id.boluos_viewpager_home);
		bt_home.setOnClickListener(this);
		bt_denglu.setOnClickListener(this);
		tlogin.setOnClickListener(this);
		bt_touzi.setOnClickListener(this);
		bt_my.setOnClickListener(this);
		bt_faxian.setOnClickListener(this);
		String main = getIntent().getStringExtra("main");
		if (TextUtils.isEmpty(main)) {
			setFragmentIndicator(0);
		} else {
			setFragmentIndicator(2);
			bt_my.setChecked(true);
			bt_touzi.setChecked(false);
		}
	}

	// 获取数据
	@Override
	protected void getData() {

		SharedPreferences shareInit = BoluoUtils.getShareInit(this);
		Boolean b = shareInit.getBoolean("geng", true);
		if (b) {
			// 初始化更新管理
			UpdateManager manager = new UpdateManager(MainActivity.this);
			// 检查软件更新
			manager.checkUpdate("1", new GengXinCallBack() {

				@Override
				public void onCode(String code) {}

				@Override
				public void onSerivce(String ser) {
					// TODO Auto-generated method stub
					// Toast.makeText(MainActivity.this,
					// "hahhahhahh",Toast.LENGTH_SHORT).show();
					if (ser.equals("1")) {
						Intent serivce = new Intent(MainActivity.this, WebActivity.class);
						serivce.putExtra("jsUrl", BeanUrl.URLZB + BeanUrl.SERIVCE_STRING);
						serivce.putExtra("title", "服务器维护中");
						startActivity(serivce);
					} else {

					}
				}
			});
		}

		if (isFirstred) {
			int imgIcon = 0;
			String url = null;
			if (!BoluoUtils.isEmpty(memberId)) {
				imgIcon = R.drawable.huodongtanchuang1;
				// url = BeanUrl.DAFUWEN_STRING;
				url = BeanUrl.DAFUWEN_STRING;
				editor.putBoolean("isDilogFirstIn", false); // isFirstIn
				editor.commit();
			} else {
				imgIcon = R.drawable.huodongtanchuang1;
				url = BeanUrl.DAFUWEN_STRING;
			}
			final String urls = url + "?phone=" + phone + "&" + "token=" + login_token;
			AndroidUtils.getDilog(imgIcon, this, new DilogCallBack() {
				@Override
				public void onPaseat() {
					Intent intent2 = new Intent(MainActivity.this, JSAndroidActivity.class);
					intent2.putExtra("jsUrl", urls);
					intent2.putExtra("title", "大富翁活动");
					startActivity(intent2);
				}
			});
		}
	}

	/**
	 * 初始化fragment
	 */
	private void setFragmentIndicator(int whichIsDefault) {
		if (whichIsDefault == 0) {
			titleView.setVisibility(View.GONE);
		} else {
			titleView.setVisibility(View.VISIBLE);
		}
		my_none = (LinearLayout) findViewById(R.id.my_none);
		my_none.findViewById(R.id.iv_dialog_icon).setOnClickListener((new View.OnClickListener() {
			final static int	COUNTS		= 5;				// 点击次数
			final static long	DURATION	= 1 * 1000;		// 规定有效时间
			long[]				mHits		= new long[COUNTS];

			@Override
			public void onClick(View v) {
				/**
				 * 实现双击方法 src 拷贝的源数组 srcPos 从源数组的那个位置开始拷贝. dst 目标数组 dstPos 从目标数组的那个位子开始写数据 length 拷贝的元素的个数
				 */
				System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
				// 实现左移，然后最后一个位置更新距离开机的时间，如果最后一个时间和最开始时间小于DURATION，即连续5次点击
				mHits[mHits.length - 1] = SystemClock.uptimeMillis();
				if (mHits[0] >= (SystemClock.uptimeMillis() - DURATION)) {
					if (mHits.length == 5) {
						final EditText et = new EditText(MainActivity.this);
						new AlertDialog.Builder(MainActivity.this).setTitle("输入测试IP地址").setView(et).setNegativeButton("取消", null).setPositiveButton("确定", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method
								String url = et.getEditableText().toString();
								BeanUrl.setHost(MainActivity.this, url);
								dialog.cancel();
								Toast.makeText(MainActivity.this, BeanUrl.HOST_SERVICE_ORIGINAL, 0).show();
								Intent i = MainActivity.this.getPackageManager().getLaunchIntentForPackage(MainActivity.this.getPackageName());
								i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								startActivity(i);

								/** 杀死整个进程 **/
								android.os.Process.killProcess(android.os.Process.myPid());
							}
						}).show();
					}

				}
			}
		}));
		my_none.setVisibility(View.GONE);
		if (whichIsDefault == 0) {
			StatusBarUtil.setColorFullScreenNoTitle(this);
		} else {
			StatusBarUtil.setPrimaryColor(this);
		}

		listFragment = new ArrayList<Fragment>();
		// 实例化 Fragment 集合
		mFragments = new Fragment[4];

		mFragments[0] = getSupportFragmentManager().findFragmentById(R.id.boluos_fragment_home);

		mFragments[1] = getSupportFragmentManager().findFragmentById(R.id.boluos_fragment_touzi);

		mFragments[2] = getSupportFragmentManager().findFragmentById(R.id.boluos_fragment_my);

		mFragments[3] = getSupportFragmentManager().findFragmentById(R.id.boluos_fragment_faxian);

		listFragment.add(new HomeFragment());
		listFragment.add(new TouziFragment());
		listFragment.add(new FaxianFragment());
		listFragment.add(new MyFragment());

		viewpage.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return listFragment.size();
			}

			@Override
			public Fragment getItem(int arg0) {
				// TODO Auto-generated method stub
				return listFragment.get(arg0);
			}
		});
		viewpage.setOnPageChangeListener(this);
		viewpage.setOffscreenPageLimit(3);

		// // 显示默认的Fragment
		// getSupportFragmentManager().beginTransaction().hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2]).hide(mFragments[3]).show(mFragments[whichIsDefault]).commit();

		viewpage.setCurrentItem(whichIsDefault);
	}

	public void showFragment(int which) {
		if (which == 0) {
			titleView.setVisibility(View.GONE);
			StatusBarUtil.setColorFullScreenNoTitle(this);
		} else {
			titleView.setVisibility(View.VISIBLE);
			StatusBarUtil.setPrimaryColor(this);
		}
		// getSupportFragmentManager().beginTransaction().hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2]).hide(mFragments[3]).show(mFragments[which]).commit();
		viewpage.setCurrentItem(which);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		// 主页
			case R.id.boluos_bt_home:
				viewpage.setCurrentItem(0);
//				if (!BoluoUtils.isEmpty(memberId)) {
//					showFragment(0);
//				} else {
//					showFragment(0);
//					my_none.setVisibility(View.GONE);
//				}
//				titleView.setAppTitle("首页");
				break;
			// 我的投资显示
			case R.id.boluos_bt_touzi:
				viewpage.setCurrentItem(1);
//				if (!BoluoUtils.isEmpty(memberId)) {
//					showFragment(1);
//				} else {
//					showFragment(1);
//					my_none.setVisibility(View.GONE);
//				}
//				titleView.setAppTitle("项目");
				break;
			// 我的账户显示
			case R.id.boluos_bt_my:
				viewpage.setCurrentItem(3);
//				if (!BoluoUtils.isEmpty(memberId)) {
//					showFragment(2);
//				} else {
//					showFragment(2);
//					my_none.setVisibility(View.VISIBLE);
//				}
//				titleView.setAppTitle("账户");
				break;
			// 发现显示
			case R.id.boluos_bt_faxian:
				viewpage.setCurrentItem(2);
//				if (!BoluoUtils.isEmpty(memberId)) {
//					showFragment(3);
//				} else {
//					showFragment(3);
//					my_none.setVisibility(View.VISIBLE);
//				}
//				titleView.setAppTitle("发现");
				break;
			case R.id.bt_dilog_login:
				startActivity(new Intent(MainActivity.this, ZhuActivity.class));
				break;
			case R.id.bt_dilog_resger:
				startActivity(new Intent(MainActivity.this, ZhuActivity.class));
				break;

			default:
				break;
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		if (arg0 == 0) {
			titleView.setVisibility(View.GONE);
			StatusBarUtil.setColorFullScreenNoTitle(this);
		} else {
			titleView.setVisibility(View.VISIBLE);
			StatusBarUtil.setPrimaryColor(this);
		}
		((RadioButton) radioGroup.getChildAt(arg0)).setChecked(true);
		switch (arg0) {
			case 0:
				if (!BoluoUtils.isEmpty(memberId)) {
//					showFragment(0);
				} else {
//					showFragment(0);
					my_none.setVisibility(View.GONE);
				}
				titleView.setAppTitle("首页");
				break;
			// 我的投资显示
			case 1:
				if (!BoluoUtils.isEmpty(memberId)) {
//					showFragment(1);
				} else {
//					showFragment(1);
					my_none.setVisibility(View.GONE);
				}
				titleView.setAppTitle("项目");
				break;
			// 发现显示
			case 2:
				if (!BoluoUtils.isEmpty(memberId)) {
//					showFragment(3);
				} else {
//					showFragment(3);
					my_none.setVisibility(View.VISIBLE);
				}
				titleView.setAppTitle("发现");
				break;
			// 我的账户显示
			case 3:
				if (!BoluoUtils.isEmpty(memberId)) {
//					showFragment(2);
				} else {
//					showFragment(2);
					my_none.setVisibility(View.VISIBLE);
				}
				titleView.setAppTitle("账户");
				break;
		}
	}

}
