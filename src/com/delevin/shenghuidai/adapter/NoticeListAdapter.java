package com.delevin.shenghuidai.adapter;

import java.util.List;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.delevin.shenghuidai.base.adapter.MyBaseAdapter;
import com.delevin.shenghuidai.base.adapter.ViewHolder;
import com.delevin.shenghuidai.bean.NoticeList;
import com.delevin.shenghuidai.utils.QntUtils;
import com.yourenkeji.shenghuidai.R;

public class NoticeListAdapter extends MyBaseAdapter<NoticeList> {

	public NoticeListAdapter(Context mContext, List<NoticeList> listDatas,
			int mLayoutId) {
		super(mContext, listDatas, mLayoutId);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void holdItem(ViewHolder holder, NoticeList item) {
		TextView tvTitle=holder.getView(R.id.item_notice_list_tv_title);
		TextView tvTime=holder.getView(R.id.item_notice_list_tv_time);
		RelativeLayout reLayout=holder.getView(R.id.item_notice_list_rl);
		tvTitle.setText(item.getTitle());
		tvTime.setText(QntUtils.getSubStringW(item.getPosttime(), 0, 10));
		
	}

}
