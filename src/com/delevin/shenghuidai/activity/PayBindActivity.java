package com.delevin.shenghuidai.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.delevin.application.Myapplication;
import com.delevin.jsandroid.JSAndroidActivity;
import com.delevin.shenghuidai.base.activity.BaseActivity;
import com.delevin.shenghuidai.bean.BeanUrl;
import com.delevin.shenghuidai.interfaces.JianPanCallback;
import com.delevin.shenghuidai.interfaces.PayInitCallBack;
import com.delevin.shenghuidai.interfaces.PayResultCallBack;
import com.delevin.shenghuidai.utils.AndroidUtils;
import com.delevin.shenghuidai.utils.BoluoUtils;
import com.delevin.shenghuidai.utils.JiaoYiUtils;
import com.delevin.shenghuidai.utils.JiaoYiUtils.CountTimer;
import com.delevin.shenghuidai.utils.NetUtils;
import com.delevin.shenghuidai.utils.OkhttpManger.Funck4;
import com.delevin.shenghuidai.utils.ProessDilogs;
import com.delevin.shenghuidai.utils.QntUtils;
import com.delevin.shenghuidai.utils.ShengHuiPayUtils;
import com.delevin.shenghuidai.view.CustomDialog;
import com.delevin.shenghuidai.view.TitleView;
import com.delevin.shenghuidai.view.TitleView.OnRightButtonClickListener;
import com.yourenkeji.shenghuidai.R;

/**
 *     @author 李红涛  @version 创建时间：2017-1-3 下午3:22:18    类说明 绑定充值
 */
public class PayBindActivity extends BaseActivity implements OnClickListener {
	private TextView txtBankName;
	private TextView txtBankCode;
	private EditText money;
	private Button bt_bind_pay;
	private String real_name;
	private String phone;
	private ImageView imgBank;
	private TextView tvSeeXiane, tvMyPalance;
	private LinearLayout linLater;
	private ImageView imgLater;
	private String bankCard;
	private ImageView progress_img;
	private LinearLayout progress_layout;
	private TextView tvDanriDanbi;
	private RelativeLayout layoutBank;
	private CustomDialog dilog;

	@SuppressLint({ "InlinedApi", "CutPasteId" })
	@Override
	protected void findViews() {
		setContentView(R.layout.activity_pay_bind);
		View statusBarview = findViewById(R.id.statusBarview);
		TitleView titleView = (TitleView) findViewById(R.id.titleView_payBind);
		phone = BoluoUtils.getShareOneData(getApplicationContext(), "phone");
		String balance = getIntent().getStringExtra("balance");

		// 设置状态栏一体化
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			statusBarview.setVisibility(View.GONE);
		}

		titleView.initViewsVisible(true, true, true, true);
		titleView.setAppTitle("账户充值");
		titleView.setRightTitle("充值记录");
		titleView.setOnRightButtonClickListener(new OnRightButtonClickListener() {
			@Override
			public void OnRightButtonClick(View v) {
				startActivity(new Intent(getApplicationContext(), ChongzhiJiluActivity.class));
			}
		});
		layoutBank = (RelativeLayout) findViewById(R.id.add_pay_layout_bank);
		layoutBank.setOnClickListener(this);
		tvDanriDanbi = (TextView) findViewById(R.id.danri_danbi);
		progress_img = (ImageView) findViewById(R.id.pay_bind_img_later);
		progress_layout = (LinearLayout) findViewById(R.id.pay_bind_lin_later);
		tvMyPalance = (TextView) findViewById(R.id.pay_bind_my_balance_tv);
		tvMyPalance.setText(TextUtils.isEmpty(balance) ? "" : balance);
		txtBankName = (TextView) findViewById(R.id.add_pay_tv_bank_name);
		txtBankCode = (TextView) findViewById(R.id.add_pay_tv_bank_id);
		tvMyPalance = (TextView) findViewById(R.id.pay_bind_my_balance_tv);
		money = (EditText) findViewById(R.id.pay_bind_money);
		AndroidUtils.setPricePoint(money);
		bt_bind_pay = (Button) findViewById(R.id.bt_pay_bind);
		imgBank = (ImageView) findViewById(R.id.add_pay_img_bank);
		tvSeeXiane = (TextView) findViewById(R.id.add_pay_tv_see_xiane);
		tvSeeXiane.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		linLater = (LinearLayout) findViewById(R.id.pay_bind_lin_later);
		imgLater = (ImageView) findViewById(R.id.pay_bind_img_later);
		bt_bind_pay.setOnClickListener(this);
		tvSeeXiane.setOnClickListener(this);
	}

	@Override
	protected void getData() {
		ProessDilogs.getProessAnima(progress_img, getApplicationContext());
		Myapplication.okhttpManger.sendComplexForm(this, false, QntUtils.getURL(BeanUrl.TIXIAN_INIT_STRING, phone), null, new Funck4() {
			@Override
			public void onResponse(JSONObject result) {
				try {
					String code = result.getString("code");
					if (TextUtils.equals(code, "10000")) {
						JSONObject object = result.getJSONObject("content");
						txtBankName.setText(object.getString("bank_name") + "元");
						object.getString("id_card");
						bankCard = object.getString("pay_bankcard");
						txtBankCode.setText(QntUtils.getBankCode(bankCard));
						real_name = object.getString("real_name");
						String bank_address = object.getString("bank_address");
						String day_amt = object.getString("day_amt");
						String single_amt = object.getString("single_amt");
						tvDanriDanbi.setText("单笔充值:" + single_amt + "元,单笔充值:" + single_amt + "元,最低充值2元");
						AndroidUtils.getImg(getApplicationContext(), bank_address, imgBank, R.drawable.boluo_center, R.drawable.boluo_fail);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				} finally {
					ProessDilogs.closeAnimation(progress_img, progress_layout);
				}
			}

		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_pay_bind:
			if (NetUtils.getNetWorkState(PayBindActivity.this) != -1) {
				if (!TextUtils.isEmpty(real_name)) {
					if (!TextUtils.isEmpty(txtBankCode.getText().toString().trim())) {
						if (!TextUtils.isEmpty(money.getText().toString().trim())) {
							if (QntUtils.getDouble(money.getText().toString().trim()) >= 1D) {
								getPay(money.getText().toString().trim(), linLater, imgLater);
							} else {
								Toast.makeText(getApplicationContext(), "充值不能小于1元", Toast.LENGTH_SHORT).show();
							}
						} else {
							Toast.makeText(getApplicationContext(), "请输入充值金额", Toast.LENGTH_SHORT).show();
						}
					} else {
						Toast.makeText(getApplicationContext(), "请求失败", Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(getApplicationContext(), "请求失败", Toast.LENGTH_SHORT).show();
				}
			} else {
				BoluoUtils.getDilogDome(PayBindActivity.this, "温馨提示", "您当前的网络不可用", "确定");
			}
			break;
		case R.id.add_pay_tv_see_xiane:

			Intent intent = new Intent(PayBindActivity.this, JSAndroidActivity.class);
			intent.putExtra("title", "查看限额");
			intent.putExtra("jsUrl", BeanUrl.addPayChakanXiane + phone);
			startActivity(intent);
			break;
		case R.id.add_pay_layout_bank:
			startActivity(new Intent(PayBindActivity.this, MyBankActivity.class));
			break;
		default:
			break;
		}
	}

	private void getPay(String money, LinearLayout layout, ImageView img) {
		// ProessDilogs.getProessAnima(img, getApplicationContext());
		ShengHuiPayUtils.getCommitPay( layout, img, true, PayBindActivity.this, phone, "", "", "", "", money, new PayInitCallBack() {

			@Override
			public void onRespose(String name, String idCard) {

			}

			@Override
			public void onPaySucess(final String money, final String order_id) {
				
				dilog = JiaoYiUtils.getPhoneCode2(PayBindActivity.this, new JianPanCallback() {

					@Override
					public void setText(TextView tvStates, TextView tvMoney) {
						// TODO Auto-generated method stub

					}

					@Override
					public void setPhone(Button btnGetCode, TextView tvPhone, final CountTimer countTimer) {
						countTimer.start();
						btnGetCode.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								countTimer.start();
								getPay(money, linLater, imgLater);
							}
						});

					}

					@Override
					public void passWord(String password, CountTimer countTimer, CustomDialog dialog) {
						ShengHuiPayUtils.SurePay(dilog, PayBindActivity.this, phone, password, money, order_id,new PayResultCallBack() {
							
							@Override
							public void onPaySuccess() {
								Intent intent = new Intent(PayBindActivity.this, PayOrTianSuccessActivity.class);
								intent.putExtra("flag", "pay");
								intent.putExtra("money", money);
								startActivity(intent);
								finish();
								
							}
						});
					}
				});

			}
		});
	}

	class OnRightCallBack implements OnRightButtonClickListener {
		@Override
		public void OnRightButtonClick(View v) {
			Toast.makeText(getApplicationContext(), "充值记录", Toast.LENGTH_SHORT).show();

		}
	}
}
