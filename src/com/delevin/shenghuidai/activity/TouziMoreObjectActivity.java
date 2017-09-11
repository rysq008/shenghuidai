package com.delevin.shenghuidai.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.delevin.application.Myapplication;
import com.delevin.shenghuidai.adapter.TouziMoreObjectAdapter;
import com.delevin.shenghuidai.base.activity.BaseActivity;
import com.delevin.shenghuidai.bean.BeanMoreObject;
import com.delevin.shenghuidai.bean.BeanUrl;
import com.delevin.shenghuidai.utils.BoluoUtils;
import com.delevin.shenghuidai.utils.NetUtils;
import com.delevin.shenghuidai.utils.OkhttpManger.Funck4;
import com.delevin.shenghuidai.utils.ProessDilogs;
import com.delevin.shenghuidai.view.TitleView;
import com.yourenkeji.shenghuidai.R;

/**
 *     @author 李红涛  @version 创建时间：2017-1-4 下午2:37:58    类说明 
 */
public class TouziMoreObjectActivity extends BaseActivity implements OnItemClickListener{

	private ListView zijinListView;
	private List<BeanMoreObject> datas;
	private LinearLayout zijinJiaZaiLayout;
	private ImageView zijinJiaZaiImg;
	private TouziMoreObjectAdapter adapter;
	// private ImageView zijinJiaZaiImgNone;
	private RelativeLayout rlNotData;
	private int index = 1;
	private MaterialRefreshLayout refreshLayout;
	private String type;
	public static TouziMoreObjectActivity touziMoreObjectActivity;

	@Override
	protected void findViews() {
		setContentView(R.layout.boluos_listview_jiazai);
		touziMoreObjectActivity = this;

		TitleView titleView = (TitleView) findViewById(R.id.titleView_listview_jiazai);
		View statusBarview = findViewById(R.id.statusBarview);
		// 设置状态栏一体化
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			statusBarview.setVisibility(View.GONE);
		}
		type = getIntent().getStringExtra("type");
		titleView.initViewsVisible(true, true, true, false);
		titleView.setAppTitle("项目列表");
		refreshLayout = (MaterialRefreshLayout) findViewById(R.id.listview_jiazai_layouts);
		refreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {

			@Override
			public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
				// TODO Auto-generated method stub
				getDatas(false, true);
			}

			@Override
			public void onRefreshLoadMore(
					MaterialRefreshLayout materialRefreshLayout) {
				// TODO Auto-generated method stub
				super.onRefreshLoadMore(materialRefreshLayout);

				getDatas(false, false);
			}
		});

		zijinListView = (ListView) findViewById(R.id.listview_jiazai_listView);
		zijinJiaZaiLayout = (LinearLayout) findViewById(R.id.listview_jiazai_layout);
		zijinJiaZaiImg = (ImageView) findViewById(R.id.listview_jiazai_img);
		// zijinJiaZaiImgNone = (ImageView)
		// findViewById(R.id.listview_jiazai_none);
		rlNotData = (RelativeLayout) findViewById(R.id.listview_jiazai_rl_none);
		datas = new ArrayList<BeanMoreObject>();
		zijinListView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		zijinListView.setDividerHeight(0);
		zijinListView.setOnItemClickListener(this);
		adapter = new TouziMoreObjectAdapter(this, datas,
				R.layout.boluo_touzi_more_item);
		zijinListView.setAdapter(adapter);
	}

	@Override
	protected void getData() {
		getDatas(true, true);
	}

	private void getDatas(final boolean isenter, final boolean isrefresh) {
		if (isenter) {
			ProessDilogs.getProessAnima(zijinJiaZaiImg, this);
		}
		if (isrefresh) {
			index = 1;
		} else {
			index++;
		}

		Myapplication.okhttpManger.sendComplexForm(this, false,
				BeanUrl.MOREOBJECT_STRING.replace("type", type) + index, null,
				new Funck4() {

					@Override
					public void onResponse(JSONObject jsonObject) {
						try {

							if (jsonObject.getString("code").equals("10000")) {
								JSONArray array = jsonObject
										.getJSONArray("content");
								List<BeanMoreObject> list = null;
								if (array.length() == 0) {
									if (index == 1) {
										rlNotData.setVisibility(View.VISIBLE);
									} else {
										refreshLayout.setLoadMore(false);
									}
									Toast.makeText(getApplicationContext(),
											"已加载完毕", Toast.LENGTH_SHORT).show();
								} else {
									refreshLayout.setLoadMore(true);

									if (isrefresh) {
										datas.clear();
									}
									list = JSON.parseArray(array.toString(),
											BeanMoreObject.class);
									datas.addAll(list);

									adapter.notifyDataSetChanged();

								}
							}
						} catch (JSONException e) {
							e.printStackTrace();
						} finally {
							if (isenter) {
								ProessDilogs.closeAnimation(zijinJiaZaiImg,
										zijinJiaZaiLayout);
							}
							refreshLayout.finishRefresh();
							refreshLayout.finishRefreshLoadMore();
						}
					}
				});
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// String memberId = BoluoUtils.getShareOneData(getApplicationContext(),
		// "memberId");
		// if (!BoluoUtils.isEmpty(memberId)) {
		// if (TextUtils.equals(datas.get(arg2).getProduct_status(), "2")) {
		if (NetUtils.getNetWorkState(touziMoreObjectActivity) != -1) {
			Intent intent = new Intent(TouziMoreObjectActivity.this,
					BidDetalsActivity.class);
			intent.putExtra("bidId", datas.get(arg2).getId());
			intent.putExtra("status", datas.get(arg2).getProduct_status());
			if (TextUtils.equals(type, "1")) {
				intent.putExtra("isNewer", false);
			} else {
				intent.putExtra("isNewer", true);
			}
			startActivity(intent);
		} else {
			BoluoUtils.getDilogDome(touziMoreObjectActivity, "温馨提示",
					"您当前的网络不可用", "确定");
		}
		// }
		// }else {
		// startActivity(new
		// Intent(TouziMoreObjectActivity.this,ZhuActivity.class));
		// finish();
		// }

	}	

}
