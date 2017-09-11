package com.delevin.shenghuidai.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.delevin.shenghuidai.main.MainActivity;
import com.delevin.shenghuidai.utils.ProessDilogs;
import com.delevin.shenghuidai.utils.StatusBarUtil;
import com.delevin.shenghuidai.view.TitleView;
import com.yourenkeji.shenghuidai.R;

public class WebActivity extends Activity {
	private LinearLayout layout;
	private ImageView imageView;
	private WebView webView;
	private TitleView tvTitle;
	private String strUrl;
	private String invite_code, qrcode;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_js_webview);
		StatusBarUtil.setPrimaryColor(this);
		View statusBarview = findViewById(R.id.statusBarview);
		imageView = (ImageView) findViewById(R.id.js_webview_visibility_image);
		layout = (LinearLayout) findViewById(R.id.js_webview_visibility_layout);
		ProessDilogs.getProessAnima(imageView, this);

		tvTitle = (TitleView) findViewById(R.id.js_webview_titleview);
		webView = (WebView) findViewById(R.id.js_webview_webview);
		WebSettings webSettings = webView.getSettings();
		webSettings.setBuiltInZoomControls(true);
		webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
		webSettings.setUseWideViewPort(true);
		webSettings.setLoadWithOverviewMode(true);
		webSettings.setSaveFormData(true);
		webSettings.setGeolocationEnabled(true);
		webSettings.setTextZoom(100);
		String strTitle = getIntent().getStringExtra("title");
		if (strTitle.equals("服务器维护中")) {
			statusBarview.setVisibility(View.GONE);
			tvTitle.setVisibility(View.GONE);
			strUrl = getIntent().getStringExtra("jsUrl");
			MainActivity.mainActivity.finish();
		} else {
			tvTitle.setVisibility(View.VISIBLE);
			tvTitle.initViewsVisible(true, true, false, false);
			tvTitle.setAppTitle(strTitle);
			strUrl = getIntent().getStringExtra("jsUrl");
		}
		try {
			webView.setWebChromeClient(new WebChromeClient() {
				@Override
				public void onProgressChanged(WebView view, int progress) {
					WebActivity.this.setTitle("Loading...");
					WebActivity.this.setProgress(progress);
					if (progress >= 80) {
						// JSAndroidActivity.this.setTitle("JsAndroid");
						ProessDilogs.closeAnimation(imageView, layout);
					}
				}
			});
			webView.loadUrl(strUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if (webView.canGoBack()) {
			webView.goBack();
		} else {
			super.onBackPressed();
		}
	}
}