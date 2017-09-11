package com.delevin.shenghuidai.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.delevin.application.Myapplication;
import com.delevin.shenghuidai.base.activity.BaseActivity;
import com.delevin.shenghuidai.bean.BeanUrl;
import com.delevin.shenghuidai.interfaces.PayInitCallBack;
import com.delevin.shenghuidai.utils.BoluoUtils;
import com.delevin.shenghuidai.utils.NetUtils;
import com.delevin.shenghuidai.utils.OkhttpManger.Funck4;
import com.delevin.shenghuidai.utils.QntUtils;
import com.delevin.shenghuidai.utils.ShengHuiPayUtils;
import com.delevin.shenghuidai.view.TitleView;
import com.delevin.shenghuidai.view.TitleView.OnRightButtonClickListener;
import com.yourenkeji.shenghuidai.R;

/**
 *  @author 李红涛  @version 创建时间：2016-12-23 下午3:45:05    类说明 
 */
public class PayActivity extends BaseActivity implements OnClickListener {
	private EditText editName;// 输入姓名
	private EditText editIdCode;// 输入身份证号
	private EditText editBankCode;// 输入银行卡号
	private TextView txtName;// 姓名
	private TextView txtIdCode;// 身份证号
	private EditText txtBankCode;// 银行卡号
	private TextView money;// 充值金额
	private Button bt_pay;// 充值按钮
	private String id_bind;
	private String is_pay_passwd;
	private String phone;
	private ImageView imgLater;
	private LinearLayout linLater;
	private EditText editBankPhone;
	private String pay_bind;
	private EditText tvPhone;

	@Override
	protected void findViews() {
		setContentView(R.layout.activity_pay);
		getshareData();
		initFindbyId();
	}

	// 控件初始化
	private void initFindbyId() {
		TitleView titleView = (TitleView) findViewById(R.id.titleView_pay);
		View statusBarview = findViewById(R.id.statusBarview);
		bt_pay = (Button) findViewById(R.id.bt_pay);
		imgLater = (ImageView) findViewById(R.id.pay_img_later);
		linLater = (LinearLayout) findViewById(R.id.pay_lin_later);
		bt_pay.setOnClickListener(this);
		money = (TextView) findViewById(R.id.pay_money);
		money.setText(getIntent().getStringExtra("number"));
		txtName = (EditText) findViewById(R.id.pay_Name);
		txtIdCode = (EditText) findViewById(R.id.pay_idCode);
		txtBankCode = (EditText) findViewById(R.id.pay_bankCode);
		tvPhone = (EditText) findViewById(R.id.pay_phone);
		// 设置状态栏一体化
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			statusBarview.setVisibility(View.GONE);
		}
		titleView.initViewsVisible(true, true, true, true);
		titleView.setAppTitle("绑定银行卡");
		titleView.setRightTitle("支持银行");
		titleView.setOnRightButtonClickListener(new OnRightButtonClickListener() {

			@Override
			public void OnRightButtonClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(PayActivity.this, ZhichiBankActivity.class));
			}
		});
	}

	private void getshareData() {
		pay_bind = BoluoUtils.getShareOneData(getApplicationContext(), "pay_bind");
		id_bind = BoluoUtils.getShareOneData(getApplicationContext(), "id_bind");
		is_pay_passwd = BoluoUtils.getShareOneData(getApplicationContext(), "is_pay_passwd");
		phone = BoluoUtils.getShareOneData(getApplicationContext(), "phone");
	}

	@Override
	protected void getData() {
		if (TextUtils.equals(id_bind, "1")) {
			initDatas(new PayInitCallBack() {
				@Override
				public void onRespose(String name, String idCard) {
					txtName.setInputType(InputType.TYPE_NULL);
					txtIdCode.setInputType(InputType.TYPE_NULL);
					txtName.setText(name);
					txtIdCode.setText(idCard);
				}

				@Override
				public void onPaySucess(String money, String order_id) {

				}
			});
		}
	}

	/**
	 * 
	 * 充值初始化
	 * 
	 * **/
	private void initDatas(final PayInitCallBack callBack) {
		Myapplication.okhttpManger.sendComplexForm(this, false, QntUtils.getURL(BeanUrl.PAY_INIT_STRING, phone), null, new Funck4() {
			@Override
			public void onResponse(JSONObject result) {
				try {
					String code = result.getString("code");
					if (TextUtils.equals(code, "10000")) {
						JSONObject object = result.getJSONObject("content");
						callBack.onRespose(object.getString("real_name"), object.getString("id_card"));
					} else {
						Toast.makeText(getApplicationContext(), result.getString("desc"), Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_pay:
			if (NetUtils.getNetWorkState(PayActivity.this) != -1) {
				if (!TextUtils.isEmpty(txtName.getText().toString().trim())) {
					if (!TextUtils.isEmpty(txtIdCode.getText().toString().trim())) {
						if (!TextUtils.isEmpty(txtBankCode.getText().toString().trim())) {
							if (!TextUtils.isEmpty(tvPhone.getText().toString().trim())) {
								ShengHuiPayUtils.getCommitPay(linLater, imgLater, false, PayActivity.this, phone, txtName.getText().toString().trim(), txtIdCode.getText().toString().trim(), txtBankCode.getText().toString().trim(), phone, "0", new PayInitCallBack() {

									@Override
									public void onRespose(String name, String idCard) {

									}

									@Override
									public void onPaySucess(String money, String order_id) {
										ShengHuiPayUtils.setPayORBidPassword("0", PayActivity.this, phone);
									}
								});
							} else {
								Toast.makeText(getApplicationContext(), "请输入您的预留手机号", Toast.LENGTH_SHORT).show();
							}
						} else {
							Toast.makeText(getApplicationContext(), "请输入您的银行卡号码", Toast.LENGTH_SHORT).show();
						}
					} else {
						Toast.makeText(getApplicationContext(), "请输入您的身份证号码", Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(getApplicationContext(), "请输入您的姓名", Toast.LENGTH_SHORT).show();
				}
			} else {
				BoluoUtils.getDilogDome(PayActivity.this, "温馨提示", "您当前的网络不可用", "确定");
			}
			break;

		default:
			break;
		}
	}
}
