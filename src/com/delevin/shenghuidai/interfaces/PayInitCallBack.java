package com.delevin.shenghuidai.interfaces;

public interface PayInitCallBack {
	void onRespose(String name, String idCard);

	void onPaySucess(String money, String order_id);
}
