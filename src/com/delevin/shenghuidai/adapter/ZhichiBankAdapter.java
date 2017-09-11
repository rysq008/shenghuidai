package com.delevin.shenghuidai.adapter;

import java.util.List;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.delevin.shenghuidai.base.adapter.MyBaseAdapter;
import com.delevin.shenghuidai.base.adapter.ViewHolder;
import com.delevin.shenghuidai.bean.BeanZhichiBank;
import com.delevin.shenghuidai.utils.AndroidUtils;
import com.yourenkeji.shenghuidai.R;

public class ZhichiBankAdapter extends MyBaseAdapter<BeanZhichiBank> {

	public ZhichiBankAdapter(Context mContext, List<BeanZhichiBank> listDatas,
			int mLayoutId) {
		super(mContext, listDatas, mLayoutId);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void holdItem(ViewHolder holder, BeanZhichiBank item) {
		TextView tvName = holder.getView(R.id.item_zhichi_bank_tv_name);
		TextView tvxiane = holder.getView(R.id.item_zhichi_bank_tv_xiane);
		ImageView img = holder.getView(R.id.item_zhichi_bank_img);
		tvName.setText(item.getName());
		tvxiane.setText("单笔限额" + item.getSingle_amt() + "元，单日限额"
				+ item.getDay_amt() + "元");
		String strUrl = item.getUrl();
		AndroidUtils.getImg(mContext, strUrl, img, R.drawable.boluo_center,
				R.drawable.boluo_fail);

	}

}
