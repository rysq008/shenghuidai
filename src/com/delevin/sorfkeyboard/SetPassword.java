package com.delevin.sorfkeyboard;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.delevin.shenghuidai.activity.MySafetyManagmentActivity;
import com.delevin.sorfkeyboard.PayPasswordView.OnPayListener;
import com.delevin.sorfkeyboard.SorfKeyBoard.keyboardCallBack;

/**
 *     @author 李红涛  @version 创建时间：2017-1-4 上午10:40:00    类说明 
 */

public class SetPassword {

	private static String password01;
	private static String password02;
	private static DialogWidget mDialogWidget;

	public SetPassword(Activity activity, SetPwdCallback callback) {
		thod(activity, callback);
	}

	public interface SetPwdCallback {
		void onPwd(String pwd);

		void onTextView(TextView t);
	}

	public static void thod(final Activity activity, final SetPwdCallback callback) {
		thod("绑卡设置密码", activity, callback);
	}

	public static void thod(final String first, final Activity activity, final SetPwdCallback callback) {
		new SorfKeyBoard(activity, first, new keyboardCallBack() {

			@Override
			public void onPassword(String pwd) {
				// TODO Auto-generated method stub
				password01 = pwd;
				if (first.contains("密码")) {
					thod02("确认密码", activity, callback);
				} else if (first.contains("验证码")) {
					thod02("确认验证码", activity, callback);
				} else {
					thod02(activity, callback);
				}
			}

			@Override
			public void onTextView(TextView t) {
				// TODO Auto-generated method stub

			}
		});
	}

	private static void thod02(final Activity activity, final SetPwdCallback callback) {
		thod02("确认密码", activity, callback);
	}

	private static void thod02(String second, final Activity activity, final SetPwdCallback callback) {
		new SorfKeyBoard(activity, second, new keyboardCallBack() {

			@Override
			public void onPassword(String pwd) {
				// TODO Auto-generated method stub
				password02 = pwd;
				if (!TextUtils.equals(password01, password02)) {

					Toast.makeText(activity, "两次输入不一致", Toast.LENGTH_SHORT).show();
					thod(activity, callback);
				} else {
					callback.onPwd(password02);
				}
			}

			@Override
			public void onTextView(TextView t) {
				// TODO Auto-generated method stub

			}
		});
	}

	/**
	 * 输入提现和投标密码
	 * 
	 * @param callback
	 * @param activity
	 * @param title
	 */
	public static void getJIanpan(SetPwdCallback callback, Activity activity, String title) {
		mDialogWidget = new DialogWidget(activity, getDecorViewDialog(callback, activity, title));
		mDialogWidget.show();
	}

	public static void ChongPassword(SetPwdCallback callback) {

	}

	private static View getDecorViewDialog(final SetPwdCallback callback, final Activity activity, String title) {
		return PayPasswordView.getInstance(title, activity, new OnPayListener() {
			@Override
			public void onCancelPay() {
				activity.startActivity(new Intent(activity, MySafetyManagmentActivity.class));
				mDialogWidget.dismiss();
				mDialogWidget = null;
			}

			@Override
			public void onSurePay() {

				mDialogWidget.dismiss();
				mDialogWidget = null;
			}

			@Override
			public void onDismiss(String password) {
				callback.onPwd(password);
				mDialogWidget.dismiss();
				mDialogWidget = null;
			}

			@Override
			public void onVisibility(TextView money) {
				callback.onTextView(money);
			}
		}).getView();
	}

}
