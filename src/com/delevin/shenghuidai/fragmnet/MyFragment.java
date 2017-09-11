package com.delevin.shenghuidai.fragmnet;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.delevin.application.Myapplication;
import com.delevin.jsandroid.JSAndroidActivity;
import com.delevin.shenghuidai.activity.MyHeadPicActivity;
import com.delevin.shenghuidai.activity.MyMoreActivity;
import com.delevin.shenghuidai.activity.MySafetyManagmentActivity;
import com.delevin.shenghuidai.activity.MyTouziActivity;
import com.delevin.shenghuidai.activity.MyZijinActivity;
import com.delevin.shenghuidai.activity.PayActivity;
import com.delevin.shenghuidai.activity.PayBindActivity;
import com.delevin.shenghuidai.activity.ReturnCalendarActivity;
import com.delevin.shenghuidai.activity.TiXianActivity;
import com.delevin.shenghuidai.base.fragment.BaseFragment;
import com.delevin.shenghuidai.bean.BeanFirstEvent;
import com.delevin.shenghuidai.bean.BeanMy;
import com.delevin.shenghuidai.bean.BeanUrl;
import com.delevin.shenghuidai.fragmentactivity.MyAssetsActivity;
import com.delevin.shenghuidai.fragmentactivity.MyRedPacketActivity;
import com.delevin.shenghuidai.utils.AndroidUtils;
import com.delevin.shenghuidai.utils.BoluoUtils;
import com.delevin.shenghuidai.utils.NetUtils;
import com.delevin.shenghuidai.utils.OkhttpManger.Funck4;
import com.delevin.shenghuidai.utils.ProessDilogs;
import com.delevin.shenghuidai.utils.QntUtils;
import com.yourenkeji.shenghuidai.R;

import de.greenrobot.event.EventBus;

/**
 *  @author 李红涛  @version 创建时间：2016-12-15 下午12:59:53    类说明 
 */
public class MyFragment extends BaseFragment implements OnClickListener {

	private TextView				total_money_txt;	// 总资产
	private TextView				balance_money_txt;	// 余额
	private TextView				time_txt;			// 时间
	private int						apm;
	private TextView				manyLayout;
	private String					phone;
	private String					pay_bind;
	private String					id_bind;
	private CheckBox				checkBox;
	private BeanMy					beanMy;
	private TextView				tvHeadPic;
	private String					strRemain_balanc, strTotal_money;
	private RelativeLayout			rlNotBnak;
	private LinearLayout			rlYesBank;
	private Button					btnBangdingBankCard;
	private TextView				redPacketNumBerView;
	private String					type;
	private MaterialRefreshLayout	pullToRefreshView;
	private LinearLayout			layout_V;
	private ImageView				img_V;

	@Override
	protected View initView(LayoutInflater inflaters, ViewGroup container) {
		View view = inflaters.inflate(R.layout.boluos_fragment_my, container, false);
		apm = AndroidUtils.getTime();
		return view;
	}

	@Override
	protected void getFindById(View view) {
		EventBus.getDefault().register(this);
		pullToRefreshView = (MaterialRefreshLayout) view.findViewById(R.id.my_pull);
		pullToRefreshView.setMaterialRefreshListener(new MaterialRefreshListener() {

			@Override
			public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
				// TODO Auto-generated method stub
				getData();
			}

			@Override
			public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
				// TODO Auto-generated method stub
				super.onRefreshLoadMore(materialRefreshLayout);
			}
		});
		time_txt = (TextView) view.findViewById(R.id.my_time);
		redPacketNumBerView = (TextView) view.findViewById(R.id.my_redPacketNumBer);
		rlNotBnak = (RelativeLayout) view.findViewById(R.id.my_not_bankcard);
		layout_V = (LinearLayout) view.findViewById(R.id.mys_visibility_layout);
		img_V = (ImageView) view.findViewById(R.id.mys_visibility_image);
		rlYesBank = (LinearLayout) view.findViewById(R.id.my_yes_bankcard);
		btnBangdingBankCard = (Button) view.findViewById(R.id.My_bangdingbankcard);
		btnBangdingBankCard.setOnClickListener(this);
		total_money_txt = (TextView) view.findViewById(R.id.my_total_money);
		balance_money_txt = (TextView) view.findViewById(R.id.my_balance_money);
		checkBox = (CheckBox) view.findViewById(R.id.my_eyes_checkbox);
		tvHeadPic = (TextView) view.findViewById(R.id.my_tv_headpic);

		if (apm == 0) time_txt.setText("上午好");
		else time_txt.setText("下午好");
		RelativeLayout totalLayout = (RelativeLayout) view.findViewById(R.id.my_total_layout);
		RelativeLayout balanceLayout = (RelativeLayout) view.findViewById(R.id.my_balance_layout);
		TextView paytView = (TextView) view.findViewById(R.id.my_pay);// 充值
		TextView receiptView = (TextView) view.findViewById(R.id.my_return_tv);// 代收明細
		TextView tixianView = (TextView) view.findViewById(R.id.my_tixian);// 提现
		TextView touziTv = (TextView) view.findViewById(R.id.my_touzi_tv);// 投资记录（我的投资）
		TextView moneyTv = (TextView) view.findViewById(R.id.my_money_tv);// 资金记录
		TextView infoTv = (TextView) view.findViewById(R.id.my_info_tv);// 人个信息
		RelativeLayout pageLayout = (RelativeLayout) view.findViewById(R.id.my_page_layout);// 我的优惠券
		// TextView bankTv = (TextView) view.findViewById(R.id.my_bank_tv);//
		// 我的银行卡
		TextView safeLayout = (TextView) view.findViewById(R.id.my_safe_tv);// 安全管理
		manyLayout = (TextView) view.findViewById(R.id.my_many_tv);// 更多
		totalLayout.setOnClickListener(this);
		balanceLayout.setOnClickListener(this);
		paytView.setOnClickListener(this);
		receiptView.setOnClickListener(this);
		tixianView.setOnClickListener(this);
		touziTv.setOnClickListener(this);
		moneyTv.setOnClickListener(this);
		pageLayout.setOnClickListener(this);
		// bankTv.setOnClickListener(this);
		safeLayout.setOnClickListener(this);
		infoTv.setOnClickListener(this);
		manyLayout.setOnClickListener(this);
		getshareData();
		tvHeadPic.setOnClickListener(this);
		checkBox.setChecked(false);
		checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					option(true);
				} else {
					option(false);
				}
			}
		});

		if (TextUtils.equals(pay_bind, "1")) {
			checkBox.setVisibility(View.VISIBLE);
			rlNotBnak.setVisibility(View.GONE);
			rlYesBank.setVisibility(View.VISIBLE);
		} else {
			checkBox.setVisibility(View.GONE);
			rlNotBnak.setVisibility(View.VISIBLE);
			rlYesBank.setVisibility(View.GONE);
		}
	}

	public void onEventMainThread(BeanFirstEvent event) {

		String msg = event.getMsg();
		if (TextUtils.equals(msg, "payOrTian")) {
			getshareData();
			getData();
		}
	}

	private void getshareData() {
		if (getActivity() != null) {
			Map<String, String> data = BoluoUtils.getShareData(getActivity());
			phone = data.get("phone");
			pay_bind = data.get("pay_bind");
			id_bind = data.get("id_bind");
		} else {
			Toast.makeText(getActivity(), "kongzhizhen", Toast.LENGTH_SHORT).show();
		}

	}

	private void option(boolean isChecked) {

		if (isChecked) {
			total_money_txt.setText("*****");
			balance_money_txt.setText("*****");
		} else {
			if (!TextUtils.isEmpty(strTotal_money)) {
				initData(beanMy);
			} else {
				total_money_txt.setText("---");
				balance_money_txt.setText("-");
			}
		}
	}

	@Override
	protected void getData() {
		ProessDilogs.getProessAnima(img_V, getActivity());
		if (TextUtils.equals(pay_bind, "1")) {
			checkBox.setVisibility(View.VISIBLE);
			rlNotBnak.setVisibility(View.GONE);
			rlYesBank.setVisibility(View.VISIBLE);
		} else {
			checkBox.setVisibility(View.GONE);
			rlNotBnak.setVisibility(View.VISIBLE);
			rlYesBank.setVisibility(View.GONE);
		}
		Myapplication.okhttpManger.sendComplexForm(getActivity(), false, QntUtils.getURL(BeanUrl.MY_STRING, phone), null, new Funck4() {

			@Override
			public void onResponse(JSONObject result) {
				try {
					String code = result.getString("code");
					if (TextUtils.equals(code, "10000")) {
						beanMy = new BeanMy();
						beanMy.getMyData(result, beanMy);
						Map<String, String> map = new HashMap<String, String>();
						map.put("is_vip", beanMy.getIs_vip());
						BoluoUtils.getShareCommit(getActivity(), map);
						redPacketNumBerView.setText("(" + beanMy.getHas_hongbao_num() + ")");
						initData(beanMy);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					ProessDilogs.closeAnimation(img_V, layout_V);
					pullToRefreshView.finishRefresh();
				}
			}
		});
	}

	private double getZong(String freezeamount, String remain_balance, String uncollectedamount) {

		double i = QntUtils.getDouble(freezeamount);
		double j = QntUtils.getDouble(remain_balance);
		double k = QntUtils.getDouble(uncollectedamount);
		double z = i + j + k;
		return z;

	}

	// 控件赋值
	private void initData(BeanMy beanMy) {
		double total_money = getZong(beanMy.getFreezeamount(), beanMy.getRemain_balance(), beanMy.getUncollectedamount());
		strTotal_money = QntUtils.getFormat(total_money);
		strRemain_balanc = beanMy.getRemain_balance();
		total_money_txt.setText(QntUtils.getFormat(total_money));
		balance_money_txt.setText(QntUtils.getFormat(QntUtils.getDouble(beanMy.getRemain_balance())));
		type = beanMy.getType();
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		// 总资产跳转
			case R.id.my_balance_layout:
			case R.id.my_total_layout:
				if (NetUtils.getNetWorkState(getActivity()) != -1) {

					Intent intent = new Intent(getActivity(), MyAssetsActivity.class);
					intent.putExtra("freezeamount", beanMy.getFreezeamount());
					intent.putExtra("remain_balance", strRemain_balanc);
					intent.putExtra("uncollectedamount", beanMy.getUncollectedamount());
					intent.putExtra("total_money", strTotal_money);
					intent.putExtra("rechargeamount", beanMy.getRechargeamount());
					intent.putExtra("remainamount", beanMy.getRemainamount());
					intent.putExtra("z_activity_interest", beanMy.getZ_activity_interest());
					intent.putExtra("z_hongbao", beanMy.getZ_hongbao());
					intent.putExtra("z_interest", beanMy.getZ_interest());
					intent.putExtra("z_yaoqing_shouyi", beanMy.getZ_yaoqing_shouyi());
					intent.putExtra("sum_profit", beanMy.getSum_profit());
					startActivity(intent);
				} else {
					BoluoUtils.getDilogDome(getActivity(), "温馨提示", "您当前的网络不可用", "确定");
				}
				break;
			// 充值跳转
			case R.id.my_pay:
				if (NetUtils.getNetWorkState(getActivity()) != -1) {
					if (TextUtils.equals(pay_bind, "1")) {

						startActivity(new Intent(getActivity(), PayBindActivity.class).putExtra("balance", balance_money_txt.getText()));

					} else {
						startActivity(new Intent(getActivity(), PayActivity.class));
					}
				} else {
					BoluoUtils.getDilogDome(getActivity(), "温馨提示", "您当前的网络不可用", "确定");
				}
				break;
			// 提现跳转
			case R.id.my_tixian:
				if (NetUtils.getNetWorkState(getActivity()) != -1) {
					if (TextUtils.equals(pay_bind, "1")) {
						startActivity(new Intent(getActivity(), TiXianActivity.class));
					} else {
						startActivity(new Intent(getActivity(), PayActivity.class));
					}
				} else {
					BoluoUtils.getDilogDome(getActivity(), "温馨提示", "您当前的网络不可用", "确定");
				}
				break;
			// 日历回款
			case R.id.my_return_tv:
				// if (NetUtils.getNetWorkState(getActivity()) != -1) {
				// if (TextUtils.equals(pay_bind, "1")) {
				startActivity(new Intent(getActivity(), ReturnCalendarActivity.class));
				// } else {
				// startActivity(new Intent(getActivity(),
				// AddNumberActivity.class));
				// }
				// } else {
				// BoluoUtils.getDilogDome(getActivity(), "温馨提示", "您当前的网络不可用",
				// "确定");
				// }
				break;
			// 我的投资
			case R.id.my_touzi_tv:
				if (NetUtils.getNetWorkState(getActivity()) != -1) {
					startActivity(new Intent(getActivity(), MyTouziActivity.class));
				} else {
					BoluoUtils.getDilogDome(getActivity(), "温馨提示", "您当前的网络不可用", "确定");
				}
				break;
			// 资金记录
			case R.id.my_money_tv:
				if (NetUtils.getNetWorkState(getActivity()) != -1) {
					startActivity(new Intent(getActivity(), MyZijinActivity.class));
				} else {
					BoluoUtils.getDilogDome(getActivity(), "温馨提示", "您当前的网络不可用", "确定");
				}
				break;
			// 我的优惠券
			case R.id.my_page_layout:
				if (NetUtils.getNetWorkState(getActivity()) != -1) {
					startActivity(new Intent(getActivity(), MyRedPacketActivity.class));
				} else {
					BoluoUtils.getDilogDome(getActivity(), "温馨提示", "您当前的网络不可用", "确定");
				}
				break;
			// 我的银行卡
			// case R.id.my_bank_tv:
			// if (NetUtils.getNetWorkState(getActivity()) != -1) {
			// if (TextUtils.equals(id_bind, "1")) {
			//
			// if (TextUtils.equals(pay_bind, "1")) {
			//
			// startActivity(new Intent(getActivity(),
			// MyBankActivity.class));
			// } else {
			//
			// }
			// }
			// } else {
			// BoluoUtils.getDilogDome(getActivity(), "温馨提示", "您当前的网络不可用",
			// "确定");
			// }
			// break;
			// 安全管理
			case R.id.my_safe_tv:
				startActivity(new Intent(getActivity(), MySafetyManagmentActivity.class));
				break;
			// 更多
			case R.id.my_many_tv:
				Intent intent = new Intent(getActivity(), MyMoreActivity.class);
				intent.putExtra("type", type);
				startActivity(intent);
				break;
			case R.id.my_tv_headpic:
				Intent intent1 = new Intent(getActivity(), JSAndroidActivity.class);
				intent1.putExtra("title", "会员等级");
				intent1.putExtra("jsUrl", String.format(BeanUrl.HostUrl.HUIYUANDENGJI_HOST_FMT_1, phone));
				intent1.putExtra("js", "js");
				startActivity(intent1);
				break;
			// 个人中心
			case R.id.my_info_tv:
				if (NetUtils.getNetWorkState(getActivity()) != -1) {
					startActivity(new Intent(getActivity(), MyHeadPicActivity.class));
				} else {
					BoluoUtils.getDilogDome(getActivity(), "温馨提示", "您当前的网络不可用", "确定");
				}
				break;
			case R.id.My_bangdingbankcard:// 绑定银行卡
				if (NetUtils.getNetWorkState(getActivity()) != -1) {
					startActivity(new Intent(getActivity(), PayActivity.class));
				} else {
					BoluoUtils.getDilogDome(getActivity(), "温馨提示", "您当前的网络不可用", "确定");
				}
				break;
			default:
				break;
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}
}
