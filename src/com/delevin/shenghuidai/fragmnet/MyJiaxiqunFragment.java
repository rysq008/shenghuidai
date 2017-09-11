package com.delevin.shenghuidai.fragmnet;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.delevin.application.Myapplication;
import com.delevin.shenghuidai.adapter.RedPacketAdapter;
import com.delevin.shenghuidai.bean.BeanRedPacket;
import com.delevin.shenghuidai.bean.BeanUrl;
import com.delevin.shenghuidai.utils.BoluoUtils;
import com.delevin.shenghuidai.utils.OkhttpManger.Funck4;
import com.delevin.shenghuidai.utils.ProessDilogs;
import com.delevin.shenghuidai.utils.QntUtils;
import com.yourenkeji.shenghuidai.R;

public class MyJiaxiqunFragment extends Fragment {
	private ListView zijinListView;
	private List<BeanRedPacket> datas;
	private LinearLayout zijinJiaZaiLayout;
	private ImageView zijinJiaZaiImg;
	private Handler mHandler;
	private RedPacketAdapter adapter;
	// private ImageView zijinJiaZaiImgNone;
	private RelativeLayout rlNotData;
	private MaterialRefreshLayout refreshLayout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.biluos_listview_red_packet,
				container, false);
		refreshLayout = (MaterialRefreshLayout) view
				.findViewById(R.id.red_packet_pull);
		zijinListView = (ListView) view
				.findViewById(R.id.listview_red_packet_listView);
		zijinJiaZaiLayout = (LinearLayout) view
				.findViewById(R.id.listview_red_packet_layout);
		zijinJiaZaiImg = (ImageView) view
				.findViewById(R.id.listview_red_packet_img);
		// zijinJiaZaiImgNone = (ImageView)
		// findViewById(R.id.listview_jiazai_none);
		rlNotData = (RelativeLayout) view
				.findViewById(R.id.listview_red_packet_rl_none);
		datas = new ArrayList<BeanRedPacket>();
		// zijinListView.setOnItemClickListener(this);
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

		zijinListView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		zijinListView.setDividerHeight(0);
		// zijinListView.setXListViewListener(new IXListViewListener() {
		// @Override
		// public void onRefresh() {
		// }
		//
		// @Override
		// public void onLoadMore() {
		// mHandler.postDelayed(new Runnable() {
		// @Override
		// public void run() {
		// if (datas.size() == 0)
		// return;
		// else
		// getDatas();
		// adapter.notifyDataSetChanged();
		// // onLoad();
		// }
		// }, 2000);
		// }
		// });
		adapter = new RedPacketAdapter(getActivity(), datas,
				R.layout.item_red_packet);
		zijinListView.setAdapter(adapter);
//		zijinListView.setPullLoadEnable(false);
//		refreshLayout.setLoadMore(true);

		getDatas(true, true);
		mHandler = new Handler();
		return view;
	}

	private void getDatas(final boolean isFirstEnter, final boolean isRefresh) {
		if (isFirstEnter) {
			ProessDilogs.getProessAnima(zijinJiaZaiImg, getActivity());
		}
		Myapplication.okhttpManger.sendComplexForm(
				getActivity(),
				false,
				QntUtils.getURL(BeanUrl.JIAXIQUAN_STRING,
						BoluoUtils.getShareOneData(getActivity(), "phone")),
				null, new Funck4() {
					@Override
					public void onResponse(JSONObject jsonObject) {
						try {
							if (jsonObject.getString("code").equals("10000")) {
								List<BeanRedPacket> list = JSON.parseArray(
										jsonObject.optString("content"),
										BeanRedPacket.class);

								if (null != list && list.size() > 0) {
									rlNotData.setVisibility(View.GONE);
									if (isRefresh) {
										datas.clear();
									}
									datas.addAll(list);
									list.clear();
									adapter.notifyDataSetChanged();
								} else {
									rlNotData.setVisibility(View.VISIBLE);
								}
							}
						} catch (JSONException e) {
							e.printStackTrace();
							rlNotData.setVisibility(View.VISIBLE);
						} finally {
							if (isFirstEnter) {
								ProessDilogs.closeAnimation(zijinJiaZaiImg,
										zijinJiaZaiLayout);
							}
							if (isRefresh) {
								refreshLayout.finishRefresh();
							} else {
								refreshLayout.finishRefreshLoadMore();
							}
						}
					}
				});
	}
}