package com.delevin.shenghuidai.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;

import com.delevin.application.Myapplication;
import com.delevin.jsandroid.JSAndroidActivity;
import com.delevin.shenghuidai.base.activity.BaseActivity;
import com.delevin.shenghuidai.bean.BeanUrl;
import com.delevin.shenghuidai.chat.activity.ChatActivity;
import com.delevin.shenghuidai.interfaces.KefuCallBack;
import com.delevin.shenghuidai.utils.BoluoUtils;
import com.delevin.shenghuidai.utils.NetUtils;
import com.delevin.shenghuidai.view.TitleView;
import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMContactManager;
import com.yourenkeji.shenghuidai.R;

public class HelpCenterActivity extends BaseActivity implements OnClickListener {

	@Override
	protected void findViews() {
		setContentView(R.layout.boluos_help_center);
		TitleView titleView = (TitleView) findViewById(R.id.titleView_listview_jiazai);
		View statusBarview = findViewById(R.id.statusBarview);
		// 设置状态栏一体化
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			getWindow().setFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			statusBarview.setVisibility(View.GONE);
		}
		titleView.initViewsVisible(true, true, true, false);
		titleView.setAppTitle("帮助中心");
		findViewById(R.id.boluos_help_email).setOnClickListener(this);
		findViewById(R.id.boluos_help_faq).setOnClickListener(this);
		findViewById(R.id.boluos_help_online).setOnClickListener(this);
		findViewById(R.id.boluos_help_tel).setOnClickListener(this);
		findViewById(R.id.boluos_help_wechat).setOnClickListener(this);
		findViewById(R.id.boluos_help_feedback).setOnClickListener(this);
	}

	@Override
	protected void getData() {}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		final String username = getIntent().getStringExtra("username");
		switch (v.getId()) {
			case R.id.boluos_help_faq:
				if (NetUtils.getNetWorkState(this) != -1) {
					Intent intent = new Intent(this, JSAndroidActivity.class);
					intent.putExtra("jsUrl", BeanUrl.XINSHOU_HELP_STRING);
					intent.putExtra("title", "常见问题");
					startActivity(intent);
				} else {
					BoluoUtils.getDilogDome(this, "温馨提示", "您当前的网络不可用", "确定");
				}
				break;
			case R.id.boluos_help_tel:
				BoluoUtils.getKefuDilog(this, new KefuCallBack() {
					@Override
					public void onCallPhone() {
						Intent intentTel = new Intent();
						intentTel.setAction(Intent.ACTION_DIAL);
						intentTel.setData(Uri.parse("tel:400-009-8087"));
						startActivity(intentTel);
					}

					@Override
					public void onCall() {
						// Toast.makeText(getActivity(), "敬请期待",
						// Toast.LENGTH_SHORT).show();
						// 调用sdk 登陆方法 登陆聊天服务器
						callServerOnline(username);
					}
				});

				break;
			case R.id.boluos_help_wechat:
				startActivity(new Intent(this, MoreWXServiceActivity.class));
				break;
			case R.id.boluos_help_online:
				callServerOnline(username);
				break;
			case R.id.boluos_help_email:
				Intent data = new Intent(Intent.ACTION_SEND);
				data.putExtra(Intent.EXTRA_EMAIL, new String[] { "kefu@shenghuidai.com" });
				data.putExtra(Intent.EXTRA_SUBJECT, "这是标题");
				data.putExtra(Intent.EXTRA_TEXT, "这是内容");
				data.putExtra(Intent.EXTRA_STREAM, Uri.parse(""));
				data.setType("text/plain");
				startActivity(data);
				break;
			case R.id.boluos_help_feedback:
				startActivity(new Intent(this, FeedBackActivity.class));
				break;
		}
	}

	private void callServerOnline(final String username) {
		// 调用sdk 登陆方法 登陆聊天服务器
		EMChatManager.getInstance().login(username, "123", new EMCallBack() {

			@Override
			public void onSuccess() {
				// 登陆成功，保存用户名密码
				((Myapplication) Myapplication.getInstance()).setUserName(username);
				((Myapplication) Myapplication.getInstance()).setPassword("123");

				new Thread(new Runnable() {
					public void run() {

						try {
							// 参数为要添加的好友的username和添加理由
							EMContactManager.getInstance().addContact("shenghuidai", "添加理由");
							runOnUiThread(new Runnable() {
								public void run() {
									// 跳转到聊天页面
									Intent intent = new Intent(HelpCenterActivity.this, ChatActivity.class);
									intent.putExtra("userId", "shenghuidai");
									startActivity(intent);
								}
							});
						} catch (final Exception e) {
							runOnUiThread(new Runnable() {
								public void run() {
									// Toast.makeText(getActivity(),"添加好友失败",
									// 1).show();
								}
							});
						}
					}
				}).start();
			}

			@Override
			public void onProgress(int progress, String status) {}

			@Override
			public void onError(final int code, final String message) {
				runOnUiThread(new Runnable() {
					public void run() {
						// Toast.makeText(getActivity(),"登录异常",Toast.LENGTH_SHORT).show();
					}
				});
			}
		});
	}
}
