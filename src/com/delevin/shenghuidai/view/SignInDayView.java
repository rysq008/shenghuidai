package com.delevin.shenghuidai.view;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.calendar.Utils;
import com.calendar.component.State;
import com.calendar.interf.IDayRenderer;
import com.calendar.model.CalendarDate;
import com.calendar.view.DayView;
import com.yourenkeji.shenghuidai.R;

/**
 * Created by ldf on 17/6/26.
 */

public class SignInDayView extends DayView {

	private TextView dateTv;
	private ImageView marker;
	private View selectedBackground;
	private View todayBackground;
	private final CalendarDate today = new CalendarDate();

	/**
	 * 构造器
	 * 
	 * @param context
	 *            上下文
	 * @param layoutResource
	 *            自定义DayView的layout资源
	 */
	public SignInDayView(Context context, int layoutResource) {
		super(context, layoutResource);
		dateTv = (TextView) findViewById(R.id.date);
		marker = (ImageView) findViewById(R.id.maker);
		selectedBackground = findViewById(R.id.selected_background);
		todayBackground = findViewById(R.id.today_background);
	}

	@Override
	public void refreshContent() {
		renderToday(day.getDate());
		renderSelect(day.getState());
		renderSignin(day.getDate());
		super.refreshContent();
	}

	private void renderSignin(CalendarDate date) {
		marker.setVisibility(GONE);
		if (Utils.loadSignInData().containsKey(date.toString())) {
			selectedBackground.setVisibility(VISIBLE);
			dateTv.setTextColor(Color.WHITE);
		}
	}

	private void renderSelect(State state) {
		if (state == State.SELECT) {
			// selectedBackground.setVisibility(VISIBLE);
			// dateTv.setTextColor(Color.WHITE);
			selectedBackground.setVisibility(GONE);
			dateTv.setTextColor(Color.parseColor("#111111"));
		} else if (state == State.NEXT_MONTH || state == State.PAST_MONTH) {
			selectedBackground.setVisibility(GONE);
			dateTv.setTextColor(Color.parseColor("#d5d5d5"));
		} else {
			selectedBackground.setVisibility(GONE);
			dateTv.setTextColor(Color.parseColor("#111111"));
		}
	}

	private void renderToday(CalendarDate date) {
		if (date != null) {
			if (date.equals(today)) {
				dateTv.setText("今");
				// todayBackground.setVisibility(VISIBLE);
			} else {
				dateTv.setText(date.day + "");
				todayBackground.setVisibility(GONE);
			}
		}
	}

	@Override
	public IDayRenderer copy() {
		return new SignInDayView(context, layoutResource);
	}
}
