package com.delevin.shenghuidai.view;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.delevin.shenghuidai.base.adapter.MyBaseAdapter;
import com.delevin.shenghuidai.base.adapter.ViewHolder;
import com.yourenkeji.shenghuidai.R;

/**
 * Created by ldf on 17/6/14.
 */

public class ExampleAdapter extends MyBaseAdapter {

	public ExampleAdapter(Context mContext, List listDatas, int mLayoutId) {
		super(mContext, listDatas, mLayoutId);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void holdItem(ViewHolder holder, Object item) {
		// TODO Auto-generated method stub
		// R.layout.calendar_item
		TextView textView = holder.getView(R.id.text_view);
		holder.getConvertView().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Log.d("ViewHolder", "onClick--> position = " +
				// getPosition());
				Toast.makeText(getContext(), "item click !!!",
						Toast.LENGTH_LONG).show();
			}
		});

	}

}
