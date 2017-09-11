package com.delevin.shenghuidai.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.delevin.shenghuidai.base.adapter.MyBaseAdapter;
import com.delevin.shenghuidai.base.adapter.ViewHolder;
import com.delevin.shenghuidai.bean.BeanJiaxiquan;
import com.delevin.shenghuidai.utils.QntUtils;
import com.yourenkeji.shenghuidai.R;

/**
 *     @author 李红涛  @version 创建时间：2017-1-5 下午1:46:25    类说明 
 */
public class DescfJiaxiAdapter extends MyBaseAdapter<BeanJiaxiquan> {
	private final static int FLAG = 1;

	public DescfJiaxiAdapter(Context mContext, List<BeanJiaxiquan> listDatas,
			int mLayoutId) {
		super(mContext, listDatas, mLayoutId);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void holdItem(ViewHolder holder, final BeanJiaxiquan item) {
		LinearLayout layout = holder.getView(R.id.redpacket_layout);
		TextView money = holder.getView(R.id.redpacket_money);
		TextView icon = holder.getView(R.id.redpacket_icon);
		TextView title = holder.getView(R.id.redpacket_title);
		TextView time = holder.getView(R.id.redpacket_time);
		TextView bidMoney = holder.getView(R.id.redpacket_bidMoney);
		ImageView dian01 = holder.getView(R.id.redpacket_dian01);
		ImageView dian = holder.getView(R.id.redpacket_dian);
		Button stauts = holder.getView(R.id.redpacket_stauts);
		icon.setVisibility(View.GONE);
		int grays = mContext.getResources().getColor(R.color.gggggg);
		int coloryellow = mContext.getResources().getColor(R.color.juhuangse);
		stauts.setText("去使用");
		bidMoney.setText("起投金额" + item.getStart_money() + "元");
		title.setText(item.getName());
		time.setText("有效期至" + item.getDead_time());
		stauts.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("redId", item.getId());
				intent.putExtra("type", "1");
				intent.putExtra("redMoney", item.getRate());
				((Activity) mContext).setResult(FLAG, intent); // intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
				((Activity) mContext).finish();// 此处一定要调用finish()方法
			}
		});
		money.setText(QntUtils.getDouble(item.getRate()) * 100 + "%");
		dian01.setBackgroundResource(R.drawable.redpacget_liang);
		dian.setBackgroundResource(R.drawable.redpacget_liang);
		money.setTextColor(coloryellow);
		title.setTextColor(coloryellow);
		icon.setTextColor(coloryellow);
		bidMoney.setTextColor(grays);
		time.setTextColor(grays);
		layout.setBackgroundResource(R.drawable.redpacket_weishiyong);
		stauts.setBackgroundDrawable(mContext.getResources().getDrawable(
				R.drawable.shape_button_yellow));
	}
}
