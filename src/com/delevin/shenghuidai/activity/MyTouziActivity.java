package com.delevin.shenghuidai.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
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
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.delevin.application.Myapplication;
import com.delevin.shenghuidai.adapter.TouziAdapter;
import com.delevin.shenghuidai.base.activity.BaseActivity;
import com.delevin.shenghuidai.bean.BeanTouzi;
import com.delevin.shenghuidai.bean.BeanUrl;
import com.delevin.shenghuidai.utils.BoluoUtils;
import com.delevin.shenghuidai.utils.OkhttpManger.Funck4;
import com.delevin.shenghuidai.utils.ProessDilogs;
import com.delevin.shenghuidai.utils.QntUtils;
import com.delevin.shenghuidai.view.TitleView;
import com.delevin.shenghuidai.view.TitleView.OnRightButtonClickListener;
import com.yourenkeji.shenghuidai.R;

/**
 *     @author 李红涛  @version 创建时间：2017-1-4 下午2:37:58    类说明 
 */
public class MyTouziActivity extends BaseActivity implements OnItemClickListener {
	private ListView zijinListView;
	private List<BeanTouzi> datas;
	private LinearLayout zijinJiaZaiLayout;
	private ImageView zijinJiaZaiImg;

	private TouziAdapter adapter;
	private RelativeLayout rlNotData;
	private int index = 1;
	private TextView tvNotData;
	private MaterialRefreshLayout refreshLayout;

	@SuppressLint("InlinedApi")
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
		titleView.initViewsVisible(true, true, true, true);

		titleView.setAppTitle("投资明细");
		titleView.setRightTitle("截标记录");
		titleView.setOnRightButtonClickListener(new OnRightButtonClickListener() {

			@Override
			public void OnRightButtonClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(), JiebiaoJiluActivity.class));
			}
		});
		zijinListView = (ListView) findViewById(R.id.listview_jiazai_listView);
		zijinJiaZaiLayout = (LinearLayout) findViewById(R.id.listview_jiazai_layout);
		zijinJiaZaiImg = (ImageView) findViewById(R.id.listview_jiazai_img);
		// touziImgNone = (ImageView) findViewById(R.id.listview_jiazai_none);
		rlNotData = (RelativeLayout) findViewById(R.id.listview_jiazai_rl_none);
		tvNotData = (TextView) findViewById(R.id.listview_jiazai_tv_none);
		datas = new ArrayList<BeanTouzi>();
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

		zijinJiaZaiLayout = (LinearLayout) findViewById(R.id.listview_jiazai_layout);
		zijinJiaZaiImg = (ImageView) findViewById(R.id.listview_jiazai_img);
		rlNotData = (RelativeLayout) findViewById(R.id.listview_jiazai_rl_none);
		datas = new ArrayList<BeanTouzi>();
		zijinListView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		zijinListView.setDividerHeight(0);
		adapter = new TouziAdapter(getApplicationContext(), datas, R.layout.touzi_mingxi_item);
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

		Myapplication.okhttpManger.sendComplexForm(this, false, QntUtils.getURL(BeanUrl.TOUZI_MINGXI_STRING, BoluoUtils.getShareOneData(getApplicationContext(), "phone")) + index + "?index=1", null, new Funck4() {

			@Override
			public void onResponse(JSONObject jsonObject) {
				try {

					if (jsonObject.getString("code").equals("10000")) {
						JSONArray array = jsonObject.getJSONArray("content");
						List<BeanTouzi> list = null;
						if (array.length() == 0) {
							if (index == 1) {
								rlNotData.setVisibility(View.VISIBLE);
							} else {
								refreshLayout.setLoadMore(false);
							}
							Toast.makeText(getApplicationContext(), "已加载完毕", Toast.LENGTH_SHORT).show();
						} else {
							refreshLayout.setLoadMore(true);

							if (isrefresh) {
								datas.clear();
							}
							list = JSON.parseArray(array.toString(), BeanTouzi.class);
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
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		BeanTouzi touzi = datas.get(arg2);
		double d1 = QntUtils.getDouble(touzi.getInvest_money());
		double d2 = QntUtils.getDouble(touzi.getPro_fit());
		Intent intent = new Intent(MyTouziActivity.this, MyTouziCoarseDetailsActivity.class);// BidDetalsActivity
		intent.putExtra("chanPinId", touzi.getId());// 项目名称
		intent.putExtra("product_name", touzi.getProduct_name());// 项目ID
		intent.putExtra("invest_time", touzi.getInvest_time());// 项目期限
		intent.putExtra("end_time", touzi.getEnd_time());// 项目状态
		intent.putExtra("interest_time", touzi.getInterest_time());//
		intent.putExtra("invest_status", touzi.getInvest_status());//
		intent.putExtra("invest_money", touzi.getInvest_money());//
		intent.putExtra("order_id", touzi.getOrder_id());//
		intent.putExtra("yuqiMoney", QntUtils.getFormat(d2 - d1));//
		intent.putExtra("rate", touzi.getRate());//
		intent.putExtra("rate_increase", touzi.getRate_increase());//
		intent.putExtra("link", touzi.getLink());//
		intent.putExtra("repay_type", touzi.getRepay_type());//
		intent.putExtra("hongbao", touzi.getHongbao());//
		intent.putExtra("isnew", touzi.getIsnew());//
		startActivity(intent);

	}

}
