package com.delevin.shenghuidai.denglu;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.delevin.shenghuidai.base.fragment.BaseFragment;
import com.delevin.shenghuidai.interfaces.PhoneCodeCallBack;
import com.delevin.shenghuidai.utils.AndroidUtils;
import com.delevin.shenghuidai.utils.BoluoUtils;
import com.delevin.shenghuidai.utils.NetUtils;
import com.delevin.shenghuidai.utils.TimeCount;
import com.yourenkeji.shenghuidai.R;

/**
 * @author 李红涛 E-mail:
 * @version 创建时间：2017-3-13 上午11:59:33 类说明
 */
public class YanZhengMaDengluFragment extends BaseFragment implements
		OnClickListener {
	private TextView fragment_yanzhenhmaBt;
	private EditText fragment_yanzhenhmaCode;
	private String phone;
	private boolean is_pwd;
	private RelativeLayout rlCallPhone;
	private LinearLayout linQ;
	private ImageView imgQ;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.fragment_yanzhenhma_goZhaomima:
			if (NetUtils.getNetWorkState(getActivity()) != -1) {
				TimeCount time = new TimeCount(60000, 1000,
						fragment_yanzhenhmaBt);
				getPhoneCode(time, phone);
			} else {
				Toast.makeText(getActivity(), "您当前的网络不可用", Toast.LENGTH_SHORT)
						.show();
			}

			break;
		case R.id.denglu_bt_yanzhengma:
			if (NetUtils.getNetWorkState(getActivity()) != -1) {
				if (!TextUtils.isEmpty(fragment_yanzhenhmaCode.getText()
						.toString())) {
					BoluoUtils.getDenglu(phone, "code", fragment_yanzhenhmaCode
							.getText().toString().trim(), getActivity(),
							is_pwd, imgQ, linQ);
				}
			} else {
				Toast.makeText(getActivity(), "您当前的网络不可用", Toast.LENGTH_SHORT)
						.show();
			}
			break;
		case R.id.yanzhengma_denglu_callPhone01:
			AndroidUtils.dial(getActivity(), "400-009-8087");
			break;
		default:
			break;
		}
	}

	/** 获取手机验证码 **/
	private void getPhoneCode(final TimeCount timeCount, String phone) {
		AndroidUtils.getCallPhoneCode(phone, getActivity(),
				new PhoneCodeCallBack() {

					@Override
					public void onResponse(JSONObject response) {
						try {
							String code = response.getString("code");
							if (TextUtils.equals(code, "10000")) {
								timeCount.start();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	}

	@Override
	protected View initView(LayoutInflater inflaters, ViewGroup container) {
		View view = inflaters.inflate(R.layout.fragment_yanzhengmas, container,
				false);
		fragment_yanzhenhmaBt = (TextView) view
				.findViewById(R.id.fragment_yanzhenhma_goZhaomima);
		fragment_yanzhenhmaBt.setOnClickListener(this);
		fragment_yanzhenhmaCode = (EditText) view
				.findViewById(R.id.fragment_yanzhenhma_password);
		phone = getActivity().getIntent().getStringExtra("phone");
		linQ = (LinearLayout) view
				.findViewById(R.id.yanzhengma_denglu_layout_Q);
		imgQ = (ImageView) view.findViewById(R.id.yanzhengma_denglu_img_Q);
		Button bt = (Button) view.findViewById(R.id.denglu_bt_yanzhengma);
		rlCallPhone = (RelativeLayout) view
				.findViewById(R.id.yanzhengma_denglu_callPhone01);
		rlCallPhone.setOnClickListener(this);
		bt.setOnClickListener(this);
		fragment_yanzhenhmaCode.setOnClickListener(this);
		return view;
	}

	@Override
	protected void getFindById(View view) {
		getShareData();
	}

	@Override
	protected void getData() {
		// TODO Auto-generated method stub

	}

	private void getShareData() {
		SharedPreferences preferences = getActivity().getSharedPreferences(
				"is_set_pwd", 0);
		is_pwd = preferences.getBoolean("is_pwd", false);
		Map<String, String> data = BoluoUtils.getShareData(getActivity());
		phone = getActivity().getIntent().getStringExtra("phone");
	}
}
