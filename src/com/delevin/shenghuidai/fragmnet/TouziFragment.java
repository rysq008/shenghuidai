package com.delevin.shenghuidai.fragmnet;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.delevin.application.Myapplication;
import com.delevin.shenghuidai.activity.BidDetalsActivity;
import com.delevin.shenghuidai.activity.TouziMoreObjectActivity;
import com.delevin.shenghuidai.base.fragment.BaseFragment;
import com.delevin.shenghuidai.bean.BeanTJCP;
import com.delevin.shenghuidai.bean.BeanUrl;
import com.delevin.shenghuidai.utils.BoluoUtils;
import com.delevin.shenghuidai.utils.NetUtils;
import com.delevin.shenghuidai.utils.OkhttpManger.Funck4;
import com.delevin.shenghuidai.utils.ProessDilogs;
import com.delevin.shenghuidai.utils.QntUtils;
import com.delevin.shenghuidai.view.RoundProgressBar;
import com.yourenkeji.shenghuidai.R;

/**
 *     @author 李红涛  @version 创建时间：2016-12-15 下午12:59:15    类说明 投资首页
 */

public class TouziFragment extends BaseFragment implements OnClickListener {
	private List<BeanTJCP> newerList; // 新手专享
	private List<BeanTJCP> tjcpList;// 推荐产品
	private LinearLayout Touzi_Layout_tjcp;
	private LinearLayout layoutNewerObject;
	private MaterialRefreshLayout pullToRefreshView;
	private LinearLayout layout_V;
	private ImageView img_V;

	@Override
	protected View initView(LayoutInflater inflaters, ViewGroup container) {
		View view = inflaters.inflate(R.layout.boluos_fragment_touzi,
				container, false);
		return view;
	}

	@Override
	protected void getFindById(View view) {
		layout_V = (LinearLayout) view
				.findViewById(R.id.touzi_visibility_layout);
		img_V = (ImageView) view.findViewById(R.id.touzi_visibility_image);
		pullToRefreshView = (MaterialRefreshLayout) view
				.findViewById(R.id.touzi_pull);
		pullToRefreshView
				.setMaterialRefreshListener(new MaterialRefreshListener() {

					@Override
					public void onRefresh(
							MaterialRefreshLayout materialRefreshLayout) {
						// TODO Auto-generated method stub
						getData();
					}

					@Override
					public void onRefreshLoadMore(
							MaterialRefreshLayout materialRefreshLayout) {
						// TODO Auto-generated method stub
						super.onRefreshLoadMore(materialRefreshLayout);
						getData();
					}
				});
		layoutNewerObject = (LinearLayout) view.findViewById(R.id.newer_object);
		layoutNewerObject.setOnClickListener(this);
		TextView newMoreObject = (TextView) view
				.findViewById(R.id.Touzi_moreNew_object);
		LinearLayout moreObject = (LinearLayout) view
				.findViewById(R.id.Touzi_more_object);
		Touzi_Layout_tjcp = (LinearLayout) view
				.findViewById(R.id.Touzi_Layout_tjcp);
		tjcpList = new ArrayList<BeanTJCP>();
		newerList = new ArrayList<BeanTJCP>();
		newMoreObject.setOnClickListener(this);
		moreObject.setOnClickListener(this);
	}

	@Override
	protected void getData() {
		ProessDilogs.getProessAnima(img_V, getActivity());
		// 请求数据
		Myapplication.okhttpManger.sendComplexForm(getActivity(), false,
				BeanUrl.XIANGMU_STRING, null, new Funck4() {
					@Override
					public void onResponse(JSONObject result) {
						try {
							String code = result.getString("code");
							if (TextUtils.equals(code, "10000")) {
								tjcpList = JSON.parseArray(
										result.getString("content"),
										BeanTJCP.class);
								getLayoutRecommended(tjcpList, false,
										Touzi_Layout_tjcp);
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							// Toast.makeText(getActivity(), "页面数据出错请稍候再试",
							// Toast.LENGTH_LONG).show();
						} finally {
							ProessDilogs.closeAnimation(img_V, layout_V);
							pullToRefreshView.finishRefresh();
						}
					}
				});
	}

	/************************************** banner banner滑动图片展示设置结束 *********************************************/
	/** 推荐产品布局与加载数据 **/
	@SuppressLint("InflateParams")
	private void getLayoutRecommended(List<BeanTJCP> list, final Boolean b,
			LinearLayout layouts) {
		layouts.removeAllViews();
		for (int i = 0; i < list.size(); i++) {
			View view = LayoutInflater.from(getActivity()).inflate(
					R.layout.item_object, null);
			LinearLayout layout = (LinearLayout) view
					.findViewById(R.id.object_layout);
			TextView object_product_name = (TextView) view
					.findViewById(R.id.object_product_name);
			TextView object_rate = (TextView) view
					.findViewById(R.id.object_rate);
			TextView object_rate_increase = (TextView) view
					.findViewById(R.id.object_rate_increase);
			TextView object_time_limit = (TextView) view
					.findViewById(R.id.object_time_limit);
			TextView object_yues = (TextView) view
					.findViewById(R.id.object_yues);
			ImageView imgLimit = (ImageView) view
					.findViewById(R.id.item_newLimit);
			TextView object_tag = (TextView) view
					.findViewById(R.id.object_product_tag_tv);

			ImageView imgGone = (ImageView) view.findViewById(R.id.object_gone);
			if (b) {
				imgLimit.setVisibility(View.VISIBLE);
			}
			BeanTJCP bean = list.get(i);
			if ("新手标".equals(bean.getProduct_type())) {
				imgLimit.setVisibility(View.VISIBLE);
				object_tag
						.setBackgroundResource(R.drawable.blue_border_arrow_left);
				object_tag.setTextColor(getResources().getColor(
						R.color.base_application_blue_lite_font));
			} else {
				object_tag
						.setBackgroundResource(R.drawable.red_border_arrow_left);
				object_tag.setTextColor(getResources().getColor(
						R.color.base_application_deep_red));
			}
			RoundProgressBar grProgressBar = (RoundProgressBar) view
					.findViewById(R.id.object_PieCharViewBuy);
			grProgressBar.setMax(100);
			object_product_name.setText(bean.getProduct_name());
			object_rate.setText(""
					+ QntUtils.getDoubleToInt(QntUtils.getDouble(list.get(i)
							.getRate())));
			object_rate_increase.setText(QntUtils.getDoubleToInt(QntUtils
					.getDouble(bean.getRate_increase())) + "%");
			object_time_limit.setText(bean.getTime_limit());
			String product_remain = bean.getProduct_remain();
			int gress = QntUtils.getDoubleToInt(QntUtils.getDouble(list.get(i)
					.getPercentage()));
			if (gress == 100) {
				grProgressBar.setVisibility(View.GONE);
				imgGone.setVisibility(View.VISIBLE);
			} else {
				grProgressBar.setProgress(gress);
			}
			object_yues.setText(QntUtils.getFormat(QntUtils
					.getDouble(product_remain)) + "元");
			final String id = bean.getId();
			final String status = bean.getProduct_status();
			layout.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (NetUtils.getNetWorkState(getActivity()) != -1) {
						// if (!BoluoUtils.isEmpty(memberId)) {

						Intent intent = new Intent(getActivity(),
								BidDetalsActivity.class);
						intent.putExtra("bidId", id);
						intent.putExtra("status", status);

						if (b) {
							intent.putExtra("isNewer", true);
						} else {
							intent.putExtra("isNewer", false);
						}
						startActivity(intent);

						// }else {
						// startActivity(new
						// Intent(getActivity(),ZhuActivity.class));
						// }
					} else {
						BoluoUtils.getDilogDome(getActivity(), "温馨提示",
								"您当前的网络不可用", "确定");
					}
				}
			});
			layouts.addView(view);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.Touzi_more_object:
			if (NetUtils.getNetWorkState(getActivity()) != -1) {
				Intent intentMore = new Intent(getActivity(),
						TouziMoreObjectActivity.class);
				intentMore.putExtra("type", "1");
				startActivity(intentMore);
			} else {
				BoluoUtils.getDilogDome(getActivity(), "温馨提示", "您当前的网络不可用",
						"确定");
			}
			break;
		case R.id.Touzi_moreNew_object:
			if (NetUtils.getNetWorkState(getActivity()) != -1) {
				Intent intentMoreNew = new Intent(getActivity(),
						TouziMoreObjectActivity.class);
				intentMoreNew.putExtra("type", "0");
				startActivity(intentMoreNew);
			} else {
				BoluoUtils.getDilogDome(getActivity(), "温馨提示", "您当前的网络不可用",
						"确定");
			}
			break;
		// 新手标投资
		case R.id.newer_object:
			if (NetUtils.getNetWorkState(getActivity()) != -1) {
				// if (!BoluoUtils.isEmpty(memberId)) {
				Intent intentNewer = new Intent(getActivity(),
						BidDetalsActivity.class);
				intentNewer.putExtra("bidId", newerList.get(0).getId());
				intentNewer.putExtra("status", newerList.get(0).getProduct_status());
				intentNewer.putExtra("isNewer", true);//是否是新手标
				startActivity(intentNewer);
				// }else {
				// startActivity(new Intent(getActivity(),ZhuActivity.class));
				// }
			} else {
				BoluoUtils.getDilogDome(getActivity(), "温馨提示", "您当前的网络不可用",
						"确定");
			}
			break;
		default:
			break;
		}
	}

}
