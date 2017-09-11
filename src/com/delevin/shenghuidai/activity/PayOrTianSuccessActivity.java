package com.delevin.shenghuidai.activity;

import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.delevin.shenghuidai.base.activity.BaseActivity;
import com.delevin.shenghuidai.bean.BeanFirstEvent;
import com.delevin.shenghuidai.view.TitleView;
import com.yourenkeji.shenghuidai.R;

import de.greenrobot.event.EventBus;

/**
 * @author 李红涛 E-mail:
 * @version 创建时间：2017-3-8 下午3:09:34 类说明
 */
public class PayOrTianSuccessActivity extends BaseActivity implements OnClickListener {
	private TextView payortixianTitle;
	private TextView payortixianMoney;
	private TextView payortixianMessage;
	private String falg;
	private String money;
	private TitleView titleView;
	private PayOrTianSuccessActivity payOrTianSuccessActivity;

	@Override
	protected void findViews() {
		payOrTianSuccessActivity = this;
		setContentView(R.layout.activity_sucess_payortixian);
		titleView = (TitleView) findViewById(R.id.titleView_payOrTixianResult);
		View statusBarview = findViewById(R.id.statusBarview);
		// 设置状态栏一体化
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			statusBarview.setVisibility(View.GONE);
		}
		titleView.initViewsVisible(false, true, false, false);

		getShareData();
		init();
	}

	private void getShareData() {
		money = getIntent().getStringExtra("money");
		falg = getIntent().getStringExtra("flag");
	}

	private void init() {
		payortixianTitle = (TextView) findViewById(R.id.payortixian_title);
		payortixianMoney = (TextView) findViewById(R.id.payortixian_money);
		payortixianMessage = (TextView) findViewById(R.id.payortixian_message);
		Button bt = (Button) findViewById(R.id.payortixian_bt);
		bt.setOnClickListener(this);
		if (TextUtils.equals(falg, "pay")) {
			if(TextUtils.equals("0", money)){
				titleView.setAppTitle("绑卡成功");
				payortixianTitle.setText("您已成功绑定银行卡");
				payortixianMessage.setText(R.string.binding_card_success);
			}else{
				titleView.setAppTitle("充值成功");
				payortixianTitle.setText("您已充值 ");
				payortixianMessage.setText(R.string.recharge_success);
			}
		} else {
			titleView.setAppTitle("提现成功");
			payortixianTitle.setText("您已提现 ");
			payortixianMessage.setText(R.string.withdraw_success);
		}
//		payortixianMoney.setText(money);
	}

	@Override
	protected void getData() {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.payortixian_bt:
			if (payOrTianSuccessActivity != null) {
				// if (MainActivity.mainActivity!=null) {
				// MainActivity.mainActivity.finish();
				// }
				// Intent intent = new
				// Intent(PayOrTianSuccessActivity.this,MainActivity.class);
				// intent.putExtra("main", "two");
				// startActivity(intent);
				payOrTianSuccessActivity.finish();
				EventBus.getDefault().post(new BeanFirstEvent("payOrTian"));
			}

			// startActivity();
			finish();
			break;

		default:
			break;
		}
	}

}
