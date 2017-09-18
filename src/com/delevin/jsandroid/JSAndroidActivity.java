package com.delevin.jsandroid;

import java.util.Timer;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.delevin.application.Myapplication;
import com.delevin.shenghuidai.activity.WebActivity;
import com.delevin.shenghuidai.bean.BeanFirstEvent;
import com.delevin.shenghuidai.bean.BeanUrl;
import com.delevin.shenghuidai.denglu.ZhuActivity;
import com.delevin.shenghuidai.interfaces.ShareCallBack;
import com.delevin.shenghuidai.main.MainActivity;
import com.delevin.shenghuidai.utils.BoluoUtils;
import com.delevin.shenghuidai.utils.OkhttpManger.Funck4;
import com.delevin.shenghuidai.utils.ProessDilogs;
import com.delevin.shenghuidai.utils.QntUtils;
import com.delevin.shenghuidai.utils.ShareUtils;
import com.delevin.shenghuidai.utils.StatusBarUtil;
import com.delevin.shenghuidai.view.TitleView;
import com.delevin.shenghuidai.view.TitleView.OnRightButtonClickListener;
import com.yourenkeji.shenghuidai.R;
import com.yourenkeji.shenghuidai.wxapi.ShareActivity;

import de.greenrobot.event.EventBus;

public class JSAndroidActivity extends Activity implements OnClickListener {

	private WebView				mWebView			= null;
	private String				memberId;
	private String				phone;
	private TitleView			tvTitle;
	private LinearLayout		layout;
	private ImageView			imageView;
	private long				TIME_OUT			= 5000;
	public static final int		MSG_PAGE_TIMEOUT	= 11;
	@SuppressLint("HandlerLeak")
	Handler						mHandler			= new Handler() {
														@Override
														public void handleMessage(Message msg) {
															switch (msg.what) {
															// mWebView !=
															// null&&mWebView.getProgress()<100&&mWebView.getContentHeight()
																case MSG_PAGE_TIMEOUT:
																	// 这里对已经显示出页面且加载超时的情况不做处理
																	if (mWebView != null && mWebView.getProgress() < 100)
																	if (TextUtils.isEmpty(getIntent().getStringExtra("js"))) {

																		mWebView.loadUrl(BeanUrl.URLZB + getIntent().getStringExtra("jsUrl"));
																	} else {
																		mWebView.loadUrl(getIntent().getStringExtra("jsUrl"));
																	}
																	break;
															}
														}
													};
	private Timer				mTimer;
	private String				type;
	private String				right;
	public static final String	contentshare		= "胜辉贷迎新年活动，豪礼大富翁    活动隆重上线。iphone7、	mini4,话费，现金红包丰厚豪礼只等您来拿";

	@SuppressLint("InlinedApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		phone = BoluoUtils.getShareOneData(getApplicationContext(), "phone");
		memberId = BoluoUtils.getShareOneData(getApplicationContext(), "memberId");
		setContentView(R.layout.activity_js_webview);
		StatusBarUtil.setPrimaryColor(this);
		View statusBarview = findViewById(R.id.statusBarview);
		tvTitle = (TitleView) findViewById(R.id.js_webview_titleview);
		imageView = (ImageView) findViewById(R.id.js_webview_visibility_image);
		layout = (LinearLayout) findViewById(R.id.js_webview_visibility_layout);
		right = getIntent().getStringExtra("right");
		mWebView = (WebView) findViewById(R.id.js_webview_webview);
		type = getIntent().getStringExtra("type");
		ProessDilogs.getProessAnima(imageView, this);
		// 设置状态栏一体化
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			statusBarview.setVisibility(View.GONE);
		}

		String title = getIntent().getStringExtra("title");
		tvTitle.setAppTitle(title);
		if (title.equals("邀请好友")) {
			statusBarview.setVisibility(View.GONE);
			tvTitle.setVisibility(View.VISIBLE);
			LinearLayout share_layout = (LinearLayout) findViewById(R.id.share_invate_layout);
			share_layout.setVisibility(View.VISIBLE);
			findViewById(R.id.share_invate_btn).setOnClickListener(this);
			findViewById(R.id.qrcode_invate_btn).setOnClickListener(this);
			getData();
		}
		if (!TextUtils.isEmpty(right)) {
			if (TextUtils.equals(type, "1")) {
				tvTitle.setRightIcon(R.drawable.icon_fenxiang);
				tvTitle.initViewsVisible(true, true, true, false);
				tvTitle.setOnRightButtonClickListener(new RightButton());
			} else if (TextUtils.equals(type, "3")) {
				tvTitle.setRightIcon(R.drawable.bang_help);
				tvTitle.initViewsVisible(true, true, true, false);
				tvTitle.setOnRightButtonClickListener(new RightButton());
			} else {
				tvTitle.initViewsVisible(true, true, false, false);
			}
		} else {
			tvTitle.initViewsVisible(true, true, false, false);
		}
		mWebView.reload();

		showWebView();
	}

	@SuppressLint({ "SetJavaScriptEnabled", "NewApi" })
	private void showWebView() {
		// webView与js交互代码

		try {
			mWebView.setWebChromeClient(new WebChromeClient() {
				@Override
				public void onProgressChanged(WebView view, int progress) {
					JSAndroidActivity.this.setTitle("Loading...");
					JSAndroidActivity.this.setProgress(progress);
					if (progress >= 80) {
						// JSAndroidActivity.this.setTitle("JsAndroid");
						ProessDilogs.closeAnimation(imageView, layout);
					}
				}
			});
			WebSettings webSettings = mWebView.getSettings();
			webSettings.setBuiltInZoomControls(true);
			webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
			webSettings.setUseWideViewPort(true);
			webSettings.setLoadWithOverviewMode(true);
			webSettings.setSaveFormData(true);
			webSettings.setGeolocationEnabled(true);
			webSettings.setTextZoom(100);
			webSettings.setDomStorageEnabled(true);
			webSettings.setDisplayZoomControls(false);
			mWebView.requestFocus();
			mWebView.setScrollBarStyle(0);
			webSettings.setUseWideViewPort(true);// 设置此属性，可任意比例缩放
			webSettings.setJavaScriptEnabled(true);
			webSettings.setLoadWithOverviewMode(true);
			webSettings.setJavaScriptEnabled(true);
			webSettings.setDefaultTextEncodingName("utf-8");
			mWebView.addJavascriptInterface(getHtmlObject(), "jsObj");
			if (TextUtils.isEmpty(getIntent().getStringExtra("js"))) {

				mWebView.loadUrl(BeanUrl.URLZ + getIntent().getStringExtra("jsUrl"));
			} else {
				mWebView.loadUrl(getIntent().getStringExtra("jsUrl"));
			}
			mWebView.setWebViewClient(new WebViewClient() {
				@Override
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					//
					view.loadUrl(url);
					return super.shouldOverrideUrlLoading(view, url);
				}

				// @Override
				// public void onPageStarted(WebView view, String url,
				// Bitmap favicon) {
				// // TODO Auto-generated method stub
				// super.onPageStarted(view, url, favicon);
				// // mTimer = new Timer();
				// // TimerTask tt = new TimerTask() {
				// // @Override
				// // public void run() {
				// // Message m = new Message();
				// // m.what = MSG_PAGE_TIMEOUT ;
				// // mHandler.sendMessage(m);
				// //
				// // mTimer.cancel();
				// // mTimer.purge();
				// // }
				// // };
				// // mTimer.schedule(tt, TIME_OUT);
				// }
				//
				// @Override
				// public void onPageFinished(WebView view, String url) {
				// // TODO Auto-generated method stub
				// super.onPageFinished(view, url);
				// // mTimer.cancel();
				// // mTimer.purge();
				// }
				//
				// @Override
				// public void onReceivedError(WebView view, int errorCode,
				// String description, String failingUrl) {
				// // TODO Auto-generated method stub
				// super.onReceivedError(view, errorCode, description,
				// failingUrl);
				// Toast.makeText(JSAndroidActivity.this, "同步失败，请稍候再试",
				// Toast.LENGTH_SHORT).show();
				// }
			});
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	private Object getHtmlObject() {
		Object insertObj = new Object() {
			@JavascriptInterface
			public void HtmlcallJavaFinishJava() {
				mWebView.loadUrl(getIntent().getStringExtra("jsUrl"));
			}

			/**
			 * 跳转登录
			 * */
			@JavascriptInterface
			public String HtmlcallJava2() {

				if (memberId == null) {
					startActivity(new Intent(JSAndroidActivity.this, ZhuActivity.class));
					finish();
				} else {

				}
				return "0";
			}

			@JavascriptInterface
			public String HtmlcallJava3() {
				startActivity(new Intent(JSAndroidActivity.this, MainActivity.class));
				finish();
				return "0";
			}

			/**
			 * 跳转分享
			 * */
			@JavascriptInterface
			public String HtmlcallJava4() {

				// mWebView.loadUrl("javascript:show('"+memberId+"')");
				if (memberId != null) {
					Intent intent = new Intent(JSAndroidActivity.this, ShareActivity.class);
					intent.putExtra("phonelog", phone);
					startActivity(intent);
					finish();
				} else {
					startActivity(new Intent(JSAndroidActivity.this, ZhuActivity.class));
					finish();
				}
				return "0";
			}

			/**
			 * 四月加息
			 * */
			@JavascriptInterface
			public String HtmlcallJava5() {

				EventBus.getDefault().post(new BeanFirstEvent("payOrTian"));
				// startActivity(new Intent(JSAndroidActivity.this,
				// MainActivity.class));
				finish();
				return "0";
			}

			/**
			 * 跳转登录
			 * */
			@JavascriptInterface
			public String HtmlcallJava6() {

				// if(memberId!=null){
				startActivity(new Intent(JSAndroidActivity.this, ZhuActivity.class));
				finish();
				// }else {
				//
				// }
				return "0";
			}

			@JavascriptInterface
			public void JavacallHtml() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						mWebView.loadUrl("javascript: showFromHtml()");
					}
				});
			}
		};
		return insertObj;
	}

	public class RightButton implements OnRightButtonClickListener {

		@Override
		public void OnRightButtonClick(View v) {
			if (memberId != null) {
				if (TextUtils.equals(right, "rightQ")) {
					// Intent qiandao = new Intent(JSAndroidActivity.this, WebActivity.class);
					// qiandao.putExtra("jsUrl", BeanUrl.URLZ + BeanUrl.QIANDAOGUIZE_STRING);
					// qiandao.putExtra("title", "签到规则");
					// startActivity(qiandao);
					final Dialog dialog = new Dialog(JSAndroidActivity.this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
					dialog.setContentView(R.layout.qiandao_dialog_layout);
					dialog.findViewById(R.id.qiandao_dialog_img).setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.cancel();
						}
					});
					dialog.setCancelable(true);
					dialog.setCanceledOnTouchOutside(true);
					dialog.show();

				} else if (TextUtils.equals(right, "rightA")) {
					String type = getIntent().getStringExtra("type");
					ShareUtils.initShare(new ShareCallBack() {
						@Override
						public void onrespone() {}
					}, JSAndroidActivity.this, "胜辉贷：安全、短期、稳健收益", contentshare, BeanUrl.SHARE_STRING + phone + "/" + type, R.drawable.icon_fenxiang);
					ShareUtils.getOpen();
				}
			} else {
				startActivity(new Intent(JSAndroidActivity.this, ZhuActivity.class));
			}
		}

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if (mWebView.canGoBack()) {
			mWebView.goBack();
		} else {
			super.onBackPressed();
		}
	}

	private String	invite_code, qrcode;

	public void getData() {// 初始化分享并获取分享二维码地址
		ShareUtils.initShare(new ShareCallBack() {
			@Override
			public void onrespone() {
				Myapplication.okhttpManger.sendComplexForm(getApplicationContext(), false, BeanUrl.SHARESUCCESS_STRING + phone, null, new Funck4() {
					@Override
					public void onResponse(JSONObject result) {}
				});
			}
		}, JSAndroidActivity.this, "胜辉贷：安全、短期、稳健收益", "来胜辉贷，轻松享受短期、安全、高收益理财产品，现在体验还可以获赠50元新手红包。", BeanUrl.YAOQINGLIANJIE_STRING + invite_code, R.drawable.logo);

		// 获取分享二维码地址
		final String phone = BoluoUtils.getShareOneData(this, "phone");
		Myapplication.okhttpManger.sendComplexForm(this, false, QntUtils.getURL(BeanUrl.yaoqingMa, phone), null, new Funck4() {

			@Override
			public void onResponse(JSONObject result) {
				try {
					invite_code = result.getString("invite_code");
					qrcode = result.getString("qrcode");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.share_invate_btn:
				ShareUtils.getOpen();
				break;
			case R.id.qrcode_invate_btn:
				Intent intentQuyaoqinghaoyou = new Intent(this, ShareActivity.class);
				intentQuyaoqinghaoyou.putExtra("qrcode", qrcode);
				intentQuyaoqinghaoyou.putExtra("invite_code", invite_code);
				startActivity(intentQuyaoqinghaoyou);
				break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		/** attention to this below ,must add this **/
		ShareUtils.getShareOnActivityResult(JSAndroidActivity.this, requestCode, resultCode, data);
	}

	/**
	 * 屏幕横竖屏切换时避免出现window leak的问题
	 */
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		ShareUtils.getOnConfigurationChanged();
	}

}
