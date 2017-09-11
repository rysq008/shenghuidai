package com.delevin.shenghuidai.utils;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.delevin.application.Myapplication;
import com.delevin.shenghuidai.activity.PayOrTianSuccessActivity;
import com.delevin.shenghuidai.bean.BeanUrl;
import com.delevin.shenghuidai.interfaces.PayInitCallBack;
import com.delevin.shenghuidai.interfaces.PayResultCallBack;
import com.delevin.shenghuidai.utils.OkhttpManger.Funck4;
import com.delevin.shenghuidai.view.CustomDialog;
import com.delevin.sorfkeyboard.SetPassword;
import com.delevin.sorfkeyboard.SetPassword.SetPwdCallback;

public class ShengHuiPayUtils {
	// 绑卡和充值（B为真：充值否则绑定卡）
	/**
	 * 
	 * @param layout 加载
	 * @param img   加载 
	 * @param b  判断是否绑定卡
	 * @param context 上下文
	 * @param localPhone 登录手机号
	 * @param real_name 真实姓名
	 * @param id_card 身份证号
	 * @param bank_card 银行卡号
	 * @param phone  银行卡预留手机号
	 * @param change_money 充值金额
	 * @param callBack  充值成功回调
	 */
	public static void getCommitPay(final LinearLayout layout, final ImageView img, final Boolean b, final Context context, String localPhone, String real_name, String id_card, String bank_card, String phone, final String change_money, final PayInitCallBack callBack) {
		Map<String, String> params = new HashMap<String, String>();
		if (b) {
			params.put("charge_money", change_money);
		} else {
			params.put("real_name", real_name);
			params.put("id_card", id_card);
			params.put("bank_card", bank_card);
			params.put("reserve_phone", phone);
		}
		Myapplication.okhttpManger.sendComplexForm(context, false, QntUtils.getURL(BeanUrl.PAY_STRING, localPhone), params, new Funck4() {
			@Override
			public void onResponse(JSONObject result) {
				try {
					String code = result.getString("code");
					String desc = result.getString("desc");
					if (TextUtils.equals(code, "10000")) {
						if (b) {
							JSONObject object = result.getJSONObject("content");
							String order_id = object.getString("order_id");
							callBack.onPaySucess(change_money, order_id);
						} else {
							callBack.onPaySucess(change_money, "");
						}

					} else {
						Toast.makeText(context, desc, Toast.LENGTH_SHORT).show();
					}
					ProessDilogs.closeAnimation(img, layout);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 设置绑卡密码 
	 * 
	 * @param money 充值金额
	 * @param context 
	 * @param phone 登录手机号
	 */
	public static void setPayORBidPassword(final String money, final Activity context, final String phone) {
		SetPassword.thod(context, new SetPwdCallback() {
			// private String pwd;
			@Override
			public void onPwd(String pwd) {
				Toast.makeText(context, pwd, 1).show();
				Map<String, String> params = new HashMap<String, String>();
				params.put("passwd", pwd);
				Myapplication.okhttpManger.sendComplexForm(context, true, String.format(BeanUrl.BANKCHONGZHIMIMA_STRING, phone), params, new Funck4() {
					@Override
					public void onResponse(JSONObject result) {
						String stringCode;
						try {
							stringCode = result.getString("code");
							if (stringCode.equals("10000")) {//设置密码成功
								Map<String, String> paMap = new HashMap<String, String>();
								Intent intent = new Intent(context, PayOrTianSuccessActivity.class);
								intent.putExtra("flag", "pay");
								intent.putExtra("money", money);
								context.startActivity(intent);
								context.finish();
								// 记录已经设置密码
								paMap.put("is_pay_passwd", "true");
								paMap.put("pay_bind", "1");
								paMap.put("id_bind", "1");
								BoluoUtils.getShareCommit(context, paMap);

							}
						} catch (JSONException e) {
							// TODO Auto-generated catch
							// block
							e.printStackTrace();
						}
					}
				});
			}

			@Override
			public void onTextView(TextView t) {
				t.setVisibility(View.GONE);
			}
		});
	}

	// 确认充值
	/**
	 * 
	 * @param dilogs
	 * @param context
	 * @param phone 登录手机号
	 * @param password 手机验证码
	 * @param money 充值金额
	 * @param order_id 订单号
	 * @param callBack 确认回调
	 */
	public static void SurePay(final CustomDialog  dilogs ,final Activity context, String phone, String password, final String money, String order_id,final PayResultCallBack callBack) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("code", password);
		params.put("order_id", order_id);
		
		Myapplication.okhttpManger.sendComplexForm(context, false, QntUtils.getURL(BeanUrl.PAY_CONFIRM, phone), params, new Funck4() {

			@Override
			public void onResponse(JSONObject result) {
				
					if (TextUtils.equals(result.optString("code"), "10000")) {
						
						callBack.onPaySuccess();
					}else {
						dilogs.dismiss();
						Toast.makeText(context, result.optString("desc"), Toast.LENGTH_SHORT).show();
					}
			}
		});
	}
}
