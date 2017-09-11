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
import com.delevin.jsandroid.JSAndroidActivity;
import com.delevin.shenghuidai.base.activity.BaseActivity;
import com.delevin.shenghuidai.base.adapter.MeiTiAdapter;
import com.delevin.shenghuidai.bean.BeanMeiTi;
import com.delevin.shenghuidai.bean.BeanUrl;
import com.delevin.shenghuidai.utils.OkhttpManger.Funck4;
import com.delevin.shenghuidai.utils.ProessDilogs;
import com.delevin.shenghuidai.view.TitleView;
import com.yourenkeji.shenghuidai.R;

/**
 *     @author 李红涛  @version 创建时间：2017-1-4 下午2:37:58    类说明 
 */
public class TouziMeiTiActivity extends BaseActivity implements
OnItemClickListener{

	private ListView zijinListView;
	private List<BeanMeiTi> datas;
	private LinearLayout zijinJiaZaiLayout;
	private ImageView zijinJiaZaiImg;
	private MeiTiAdapter adapter;
	// private ImageView zijinJiaZaiImgNone;
	private RelativeLayout rlNotData;
	private int index = 1;
	private MaterialRefreshLayout refreshLayout;

	@Override
	protected void findViews() {
		setContentView(R.layout.boluos_listview_jiazai);
		TitleView titleView = (TitleView) findViewById(R.id.titleView_listview_jiazai);
		View statusBarview = findViewById(R.id.statusBarview);
		// 设置状态栏一体化
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			statusBarview.setVisibility(View.GONE);
		}
		titleView.initViewsVisible(true, true, true, false);
		titleView.setAppTitle("媒体报道");
		refreshLayout = (MaterialRefreshLayout) findViewById(R.id.listview_jiazai_layouts);
		refreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {

			@Override
			public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
				// TODO Auto-generated method stub
				getDatas(false, true);
			}

			@Override
			public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
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
		datas = new ArrayList<BeanMeiTi>();
		zijinListView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		zijinListView.setDividerHeight(0);
		adapter = new MeiTiAdapter(this,
				datas, R.layout.activity_touzi_meiti_item);
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
		Myapplication.okhttpManger.sendComplexForm(this, false, BeanUrl.MEITIBAODAO_STRING, null, new Funck4() {

			@Override
			public void onResponse(JSONObject jsonObject) {
				try {

					if (jsonObject.getString("code").equals("10000")) {
						JSONArray array = jsonObject.getJSONArray("content");
						List<BeanMeiTi> list = null;
						if (array.length() == 0) {
							rlNotData.setVisibility(View.VISIBLE);
							Toast.makeText(getApplicationContext(), "已加载完毕", Toast.LENGTH_SHORT).show();
						} else {
							if (isrefresh) {
								datas.clear();
							}
							list = JSON.parseArray(array.toString(), BeanMeiTi.class);
							datas.addAll(list);

							adapter.notifyDataSetChanged();

						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				} finally {
					if (isenter) {
						ProessDilogs.closeAnimation(zijinJiaZaiImg, zijinJiaZaiLayout);
					}
					refreshLayout.finishRefresh();
					refreshLayout.finishRefreshLoadMore();
				}
			}
		});
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(TouziMeiTiActivity.this,
				JSAndroidActivity.class);
		intent.putExtra("jsUrl", datas.get(position).getUrl());
		intent.putExtra("title", "媒体报道");
		intent.putExtra("js", "js");
		startActivity(intent);		
	}

}
