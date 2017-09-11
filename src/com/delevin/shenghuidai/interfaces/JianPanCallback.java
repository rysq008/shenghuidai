package com.delevin.shenghuidai.interfaces;

import android.widget.Button;
import android.widget.TextView;

import com.delevin.shenghuidai.utils.JiaoYiUtils.CountTimer;
import com.delevin.shenghuidai.view.CustomDialog;

public interface JianPanCallback {
	void passWord(String password,CountTimer countTimer,CustomDialog dialog);
	void setText(TextView tvStates,TextView tvMoney);
	void setPhone(Button btnGetCode,TextView tvPhone,CountTimer countTimer);
}
