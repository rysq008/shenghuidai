package com.delevin.shenghuidai.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.delevin.shenghuidai.base.adapter.MyBaseAdapter;
import com.delevin.shenghuidai.base.adapter.ViewHolder;
import com.delevin.shenghuidai.bean.BeanChongzhiJilu;
import com.delevin.shenghuidai.utils.QntUtils;
import com.yourenkeji.shenghuidai.R;

public class ChongzhiJiluAdapter extends MyBaseAdapter<BeanChongzhiJilu> {

	public ChongzhiJiluAdapter(Context mContext,
			List<BeanChongzhiJilu> listDatas, int mLayoutId) {
		super(mContext, listDatas, mLayoutId);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void holdItem(ViewHolder holder, BeanChongzhiJilu item) {
		// TODO Auto-generated method stub
		TextView tvOR = holder
				.getView(R.id.item_chongzhi_jilu_tv_chongzhi_or_tixian);
		TextView tvTime = holder.getView(R.id.item_chongzhi_jilu_tv_time);
		TextView tvMoney = holder.getView(R.id.item_chongzhi_jilu_tv_money);
		TextView tvState = holder.getView(R.id.item_chongzhi_jilu_tv_state);
		TextView tvShouxufei = holder
				.getView(R.id.item_chongzhi_jilu_tv_tixianshouxxufei);
		tvShouxufei.setVisibility(View.GONE);
		tvOR.setText("充值");
		tvTime.setText(QntUtils.getSubStringW(item.getTime(), 0, 10));
		tvMoney.setText(item.getMoney() + "元");
		if (item.getIs_effect().equals("1")) {
			tvState.setText("成功");
		} else if (item.getIs_effect().equals("0")) {
			tvState.setText("失败");
		}

	}

}
