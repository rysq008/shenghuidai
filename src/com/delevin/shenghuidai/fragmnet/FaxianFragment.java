package com.delevin.shenghuidai.fragmnet;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.delevin.application.Myapplication;
import com.delevin.jsandroid.JSAndroidActivity;
import com.delevin.shenghuidai.activity.TouziMeiTiActivity;
import com.delevin.shenghuidai.base.adapter.MeiTiAdapter;
import com.delevin.shenghuidai.base.fragment.BaseFragment;
import com.delevin.shenghuidai.bean.BeanMeiTi;
import com.delevin.shenghuidai.bean.BeanUrl;
import com.delevin.shenghuidai.fragmentactivity.ShareYaoqingActivity;
import com.delevin.shenghuidai.utils.AndroidUtils;
import com.delevin.shenghuidai.utils.BoluoUtils;
import com.delevin.shenghuidai.utils.NetUtils;
import com.delevin.shenghuidai.utils.OkhttpManger.Funck4;
import com.delevin.shenghuidai.utils.ProessDilogs;
import com.delevin.shenghuidai.view.ListView.XListView;
import com.yourenkeji.shenghuidai.R;

/**
 *     @author 李红涛  @version 创建时间：2016-12-15 下午1:00:06    类说明 
 */
public class FaxianFragment extends BaseFragment implements OnClickListener, OnItemClickListener {
	private TextView				tvFanliShuoming;
	private TextView				linAnQuanBaoZhang, linJifensShangcheng, linHongdongZhongxin, linYaoQingHaoYou;
	private String					phone;
	private TextView				tvFanli, tvYaoqing;
	private String					token;
	private MaterialRefreshLayout	pullToRefreshView;
	private LinearLayout			layout_V;
	private XListView				mListview;
	private ImageView				img_V, img_xshb, img_xydzp, img_banner;
	private List<BeanMeiTi>			datas	= new ArrayList<BeanMeiTi>();
	private MeiTiAdapter			adapter;

	@Override
	protected View initView(LayoutInflater inflaters, ViewGroup container) {
		View view = inflaters.inflate(R.layout.boluos_fragment_faxian, container, false);
		return view;
	}

	@Override
	protected void getFindById(View view) {
		getShareData();
		layout_V = (LinearLayout) view.findViewById(R.id.faxian_visibility_layout);
		img_V = (ImageView) view.findViewById(R.id.faxian_visibility_image);
		img_banner = (ImageView) view.findViewById(R.id.boluos_faxian_banner_iv);
		img_xshb = (ImageView) view.findViewById(R.id.faxian_xshb_iv);
		img_xydzp = (ImageView) view.findViewById(R.id.faxian_xydzp_iv);
		img_xshb.setOnClickListener(this);
		img_xydzp.setOnClickListener(this);
		mListview = (XListView) view.findViewById(R.id.faxian_meiti_content_list);
		RelativeLayout layout_meiti_more = (RelativeLayout) view.findViewById(R.id.faxian_meiti_more_layout);
		RelativeLayout layoutFanli = (RelativeLayout) view.findViewById(R.id.faxian_ShareLeiji);
		pullToRefreshView = (MaterialRefreshLayout) view.findViewById(R.id.faxian_pull);
		pullToRefreshView.setMaterialRefreshListener(new MaterialRefreshListener() {

			@Override
			public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
				// TODO Auto-generated method stub
				loadData(true, false);
			}

			@Override
			public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
				// TODO Auto-generated method stub
				super.onRefreshLoadMore(materialRefreshLayout);
				loadData(false, false);
			}
		});

		RelativeLayout layoutFan = (RelativeLayout) view.findViewById(R.id.faxian_ShareFan);
		// Button btQuyaoqinghaoyou = (Button)
		// view.findViewById(R.id.open_share);
		tvFanliShuoming = (TextView) view.findViewById(R.id.guize_bt);
		linYaoQingHaoYou = (TextView) view.findViewById(R.id.yaoqinghaoyou);
		linAnQuanBaoZhang = (TextView) view.findViewById(R.id.anquanbaozhang);
		linJifensShangcheng = (TextView) view.findViewById(R.id.jifenshangcheng);
		linHongdongZhongxin = (TextView) view.findViewById(R.id.huodongzhongxin);
		tvFanli = (TextView) view.findViewById(R.id.leijifanli);
		tvYaoqing = (TextView) view.findViewById(R.id.leijiyaoqing);
		layoutFan.setOnClickListener(this);
		layoutFanli.setOnClickListener(this);
		// btQuyaoqinghaoyou.setOnClickListener(this);
		tvFanliShuoming.setOnClickListener(this);
		linYaoQingHaoYou.setOnClickListener(this);
		linAnQuanBaoZhang.setOnClickListener(this);
		linJifensShangcheng.setOnClickListener(this);
		linHongdongZhongxin.setOnClickListener(this);
		layout_meiti_more.setOnClickListener(this);

		adapter = new MeiTiAdapter(getActivity(), datas, R.layout.activity_touzi_meiti_item);
		mListview.setAdapter(adapter);
		mListview.setOnItemClickListener(this);
		mListview.setPullLoadEnable(false);

	}

	//
	private void getShareData() {
		phone = BoluoUtils.getShareOneData(getActivity(), "phone");
		BoluoUtils.getShareOneData(getActivity(), "memberId");
		token = BoluoUtils.getShareOneData(getActivity(), "login_token");
	}

	@Override
	protected void getData() {
		loadData(true, true);
	}

	/***
	 * 数据加载函数
	 * 
	 * @param isRefresh
	 *            true:上拉刷新,false:下拉加载更多
	 * @param isGetData
	 *            是否在getData()函数中调用此方法
	 */
	private void loadData(final boolean isRefresh, final boolean isGetData) {
		if (isGetData) {
			ProessDilogs.getProessAnima(img_V, getActivity());
		}
		if (isRefresh) {
			getBannerData();
		}
		Myapplication.okhttpManger.sendComplexForm(getActivity(), false, BeanUrl.MEITIBAODAO_STRING, null, new Funck4() {

			@Override
			public void onResponse(JSONObject jsonObject) {
				try {
					if (jsonObject.optString("code").equals("10000")) {

						JSONArray array = jsonObject.getJSONArray("content");
						List<BeanMeiTi> list = null;
						if (array.length() == 0) {
							Toast.makeText(getActivity(), "已加载完毕", Toast.LENGTH_SHORT).show();
						} else {
							list = JSON.parseArray(array.toString(), BeanMeiTi.class);
						}
						if (isRefresh || isGetData) {
							datas.clear();
						}
						datas.addAll(list);
						adapter.notifyDataSetChanged();

					}
				} catch (JSONException e) {
					e.printStackTrace();
				} finally {
					if (isGetData) {
						ProessDilogs.closeAnimation(img_V, layout_V);
					}
					if (isRefresh) {
						pullToRefreshView.finishRefresh();
					} else {
						pullToRefreshView.finishRefreshLoadMore();
					}
				}
			}
		});
	}

	private void getBannerData() {
		Myapplication.okhttpManger.sendComplexForm(getActivity(), false, BeanUrl.FAXIAN_BANNERS, null, new Funck4() {

			@Override
			public void onResponse(JSONObject result) {
				// TODO Auto-generated method stub
				try {

					if ("10000".equals(result.opt("code"))) {
						JSONObject job_content = result.optJSONObject("content");
						if (null != job_content) {
							JSONArray jry = job_content.optJSONArray("banner");
							if (null != jry && jry.length() == 3) {

								AndroidUtils.getImg(getActivity(), jry.optJSONObject(0).optString("url"), img_banner, R.drawable.faxian_pic_people, R.drawable.faxian_pic_people);

								AndroidUtils.getImg(getActivity(), jry.optJSONObject(1).optString("url"), img_xshb, R.drawable.faxian_novice_red_envelope, R.drawable.faxian_novice_red_envelope);

								AndroidUtils.getImg(getActivity(), jry.optJSONObject(2).optString("url"), img_xydzp, R.drawable.faxian_lucky_wheel, R.drawable.faxian_lucky_wheel);
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {}
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.faxian_ShareFan:
				if (NetUtils.getNetWorkState(getActivity()) != -1) {
					String tag = "0";
					String check = "true";
					Intent intent = new Intent(getActivity(), ShareYaoqingActivity.class);
					intent.putExtra("tag", tag);
					intent.putExtra("check", check);
					startActivity(intent);
				} else {
					BoluoUtils.getDilogDome(getActivity(), "温馨提示", "您当前的网络不可用", "确定");
				}
				break;
			case R.id.faxian_ShareLeiji:
				if (NetUtils.getNetWorkState(getActivity()) != -1) {
					String tagLeiji = "1";
					String checkLeiji = "true";
					Intent intentLeiji = new Intent(getActivity(), ShareYaoqingActivity.class);
					intentLeiji.putExtra("tag", tagLeiji);
					intentLeiji.putExtra("check", checkLeiji);
					startActivity(intentLeiji);
				} else {
					BoluoUtils.getDilogDome(getActivity(), "温馨提示", "您当前的网络不可用", "确定");
				}
				break;
			case R.id.faxian_meiti_more_layout:
				if (NetUtils.getNetWorkState(getActivity()) != -1) {
					startActivity(new Intent(getActivity(), TouziMeiTiActivity.class));
				} else {
					BoluoUtils.getDilogDome(getActivity(), "温馨提示", "您当前的网络不可用", "确定");
				}
				break;
			case R.id.yaoqinghaoyou:
				// case R.id.open_share:
				if (NetUtils.getNetWorkState(getActivity()) != -1) {
					Intent intent1 = new Intent(getActivity(), JSAndroidActivity.class);
					intent1.putExtra("title", "邀请好友");
					intent1.putExtra("jsUrl", String.format(BeanUrl.YAOQINHAOYOU_STRING, TextUtils.isEmpty(phone) ? "" : phone, TextUtils.isEmpty(token) ? "" : token));
					startActivity(intent1);
				} else {
					BoluoUtils.getDilogDome(getActivity(), "温馨提示", "您当前的网络不可用", "确定");
				}
				break;
			case R.id.guize_bt:
				if (NetUtils.getNetWorkState(getActivity()) != -1) {
					Intent intentgui = new Intent(getActivity(), JSAndroidActivity.class);
					intentgui.putExtra("jsUrl", BeanUrl.FanliShuoming);
					intentgui.putExtra("title", "规则说明");
					startActivity(intentgui);
				} else {
					BoluoUtils.getDilogDome(getActivity(), "温馨提示", "您当前的网络不可用", "确定");
				}
				break;
			case R.id.anquanbaozhang:
				// case R.id.home_anquanbaozhang:
				if (NetUtils.getNetWorkState(getActivity()) != -1) {
					Intent intentSafe = new Intent(getActivity(), JSAndroidActivity.class);
					intentSafe.putExtra("jsUrl", BeanUrl.SAFE_STRING/*
																	 * BeanUrl.HostUrl . ANQUANBAOZHANG_HOST
																	 */);
					intentSafe.putExtra("title", "安全保障");
					// -----------add--------------
					startActivity(intentSafe);
				} else {
					BoluoUtils.getDilogDome(getActivity(), "温馨提示", "您当前的网络不可用", "确定");
				}

				break;
			case R.id.jifenshangcheng:
				Intent jifen = new Intent(getActivity(), JSAndroidActivity.class);
				jifen.putExtra("jsUrl", BeanUrl.JifenShangcheng + "?phone=" + phone + "&" + "token=" + token/*
																											 * String . format ( BeanUrl . HostUrl . JIFENSHANGCHENG_FMT_1 , phone )
																											 */);
				jifen.putExtra("title", "积分商城");
				// -------------add----------------
				startActivity(jifen);
				break;
			case R.id.huodongzhongxin:
				Intent huodong = new Intent(getActivity(), JSAndroidActivity.class);
				huodong.putExtra("jsUrl", BeanUrl.HuodongZhongxin + "?phone=" + phone + "&" + "token=" + token/*
																											 * String . format ( BeanUrl . HostUrl . HUODONGLIEBIAO_HOST_FMT_1 , phone )
																											 */);
				huodong.putExtra("title", "活动中心");
				huodong.putExtra("type", "1");
				// --------------add--------------
				startActivity(huodong);
				break;
			case R.id.faxian_xshb_iv:
				Intent intent_xshb = new Intent(getActivity(), JSAndroidActivity.class);
				intent_xshb.putExtra("jsUrl", BeanUrl.HostUrl.PAIHONGBAO_HOST);
				intent_xshb.putExtra("title", "新手红包");
				intent_xshb.putExtra("js", "js");
				startActivity(intent_xshb);
				break;
			case R.id.faxian_xydzp_iv:
				Intent intent_xydzp = new Intent(getActivity(), JSAndroidActivity.class);
				intent_xydzp.putExtra("jsUrl", String.format(BeanUrl.HostUrl.DAZHUANPAN_HOST_FMT_1, phone));
				intent_xydzp.putExtra("title", "幸运大转盘");
				intent_xydzp.putExtra("js", "js");
				startActivity(intent_xydzp);
				break;
			default:
				break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(getActivity(), JSAndroidActivity.class);
		intent.putExtra("jsUrl", datas.get(position).getUrl());
		intent.putExtra("title", "媒体报道");
		intent.putExtra("js", "js");
		startActivity(intent);
	}
}
