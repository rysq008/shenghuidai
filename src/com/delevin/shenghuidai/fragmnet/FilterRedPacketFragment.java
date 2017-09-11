package com.delevin.shenghuidai.fragmnet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.delevin.shenghuidai.adapter.DescfRedPacketAdapter;
import com.delevin.shenghuidai.base.fragment.BaseFragment;
import com.delevin.shenghuidai.bean.BeanRedPacket;
import com.delevin.shenghuidai.bean.BeanUrl;
import com.delevin.shenghuidai.utils.BoluoUtils;
import com.delevin.shenghuidai.utils.OkhttpManger.Funck4;
import com.delevin.shenghuidai.utils.ProessDilogs;
import com.delevin.shenghuidai.utils.QntUtils;
import com.delevin.shenghuidai.view.TitleView;
import com.yourenkeji.shenghuidai.R;

/**
 *     @author 李红涛  @version 创建时间：2017-1-5 下午1:45:22    类说明 
 */
public class FilterRedPacketFragment extends BaseFragment implements OnItemClickListener {

	private ListView zijinListView;
	private List<BeanRedPacket> datas;
	private LinearLayout zijinJiaZaiLayout;
	private ImageView zijinJiaZaiImg;
	private DescfRedPacketAdapter adapter;
	private RelativeLayout rlNotData;
	private MaterialRefreshLayout refreshLayout;

	@Override
	protected View initView(LayoutInflater inflaters, ViewGroup container) {
		View view = inflaters.inflate(R.layout.boluos_listview_jiazai, container, false);

		TitleView titleView = (TitleView) view.findViewById(R.id.titleView_listview_jiazai);
		View statusBarview = view.findViewById(R.id.statusBarview);
		statusBarview.setVisibility(View.GONE);
		titleView.setVisibility(View.GONE);
		refreshLayout = (MaterialRefreshLayout) view.findViewById(R.id.listview_jiazai_layouts);
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

			}
		});

		zijinListView = (ListView) view.findViewById(R.id.listview_jiazai_listView);
		zijinJiaZaiLayout = (LinearLayout) view.findViewById(R.id.listview_jiazai_layout);
		zijinJiaZaiImg = (ImageView) view.findViewById(R.id.listview_jiazai_img);

		rlNotData = (RelativeLayout) view.findViewById(R.id.listview_jiazai_rl_none);
		datas = new ArrayList<BeanRedPacket>();
		zijinListView.setOnItemClickListener(this);
		zijinListView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		zijinListView.setDividerHeight(0);
		adapter = new DescfRedPacketAdapter(getActivity(), datas, R.layout.item_red_packet);
		zijinListView.setAdapter(adapter);
		return view;
	}

	@Override
	protected void getFindById(View view) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void getData() {
		getDatas(true, true);
	}

	private void getDatas(final boolean isenter, final boolean isrefresh) {
		if (isenter) {
			ProessDilogs.getProessAnima(zijinJiaZaiImg, getActivity());
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("start_money", getActivity().getIntent().getStringExtra("start_money"));
		Myapplication.okhttpManger.sendComplexForm(getActivity(), false, QntUtils.getURL(BeanUrl.SHAIXUAN_RED_STRING, BoluoUtils.getShareOneData(getActivity(), "phone")), params, new Funck4() {

			@Override
			public void onResponse(JSONObject jsonObject) {
				try {

					if (jsonObject.getString("code").equals("10000")) {
						JSONArray array = jsonObject.getJSONArray("content");
						List<BeanRedPacket> list = null;
						if (array.length() == 0) {
							rlNotData.setVisibility(View.VISIBLE);
							Toast.makeText(getActivity(), "已加载完毕", Toast.LENGTH_SHORT).show();
						} else {
							// refreshLayout.setLoadMore(true);
							if (isrefresh) {
								datas.clear();
							}
							list = JSON.parseArray(array.toString(), BeanRedPacket.class);
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
				}
			}
		});
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

	}

}
