package com.delevin.shenghuidai.activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.calendar.component.CalendarAttr;
import com.calendar.component.CalendarViewAdapter;
import com.calendar.interf.OnSelectDateListener;
import com.calendar.model.CalendarDate;
import com.calendar.view.Calendar;
import com.calendar.view.MonthPager;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.delevin.shenghuidai.base.activity.BaseActivity;
import com.delevin.shenghuidai.base.adapter.MyBaseAdapter;
import com.delevin.shenghuidai.base.adapter.ViewHolder;
import com.delevin.shenghuidai.bean.BeanReturnCalendar;
import com.delevin.shenghuidai.view.CustomDayView;
import com.delevin.shenghuidai.view.TitleView;
import com.delevin.shenghuidai.view.ListView.XListView;
import com.yourenkeji.shenghuidai.R;

public class ReturnCalendarActivity extends BaseActivity implements
		OnClickListener {

	private MonthPager monthPager;
	private ArrayList<Calendar> currentCalendars = new ArrayList<Calendar>();
	private CalendarViewAdapter calendarAdapter;
	private OnSelectDateListener onSelectDateListener;
	private int mCurrentPage = MonthPager.CURRENT_DAY_INDEX;
	private Context context;
	private CalendarDate currentDate;
	private boolean initiated = false;
	private TextView textViewMonthDisplay;
	private TextView textViewYearDisplay;
	private TextView backToday;
	private TextView nextMonthBtn;
	private TextView lastMonthBtn;
	private MaterialRefreshLayout mRefreshLayout;
	private XListView mListView;
	private ArrayList<BeanReturnCalendar> datas = new ArrayList<BeanReturnCalendar>();
	private MyBaseAdapter<BeanReturnCalendar> adapter;

	@Override
	protected void findViews() {
		setContentView(R.layout.boluos_return_calendar);
		TitleView titleView = (TitleView) findViewById(R.id.titleView_listview_jiazai);
		View statusBarview = findViewById(R.id.statusBarview);
		// 设置状态栏一体化
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			statusBarview.setVisibility(View.GONE);
		}
		titleView.initViewsVisible(true, true, true, false);
		titleView.setAppTitle("回款日历");

		monthPager = (MonthPager) findViewById(R.id.calendar_view);
		// 此处强行setViewHeight，毕竟你知道你的日历牌的高度
		// monthPager.setViewheight(Utils.dpi2px(context, 270));
		textViewYearDisplay = (TextView) findViewById(R.id.show_year_view);
		textViewMonthDisplay = (TextView) findViewById(R.id.show_month_view);
		backToday = (TextView) findViewById(R.id.back_today_button);
		nextMonthBtn = (TextView) findViewById(R.id.next_month);
		lastMonthBtn = (TextView) findViewById(R.id.last_month);
		mRefreshLayout = (MaterialRefreshLayout) findViewById(R.id.calendar_refresh_layout);
		mListView = (XListView) findViewById(R.id.boluo_calendar_income_list);

		adapter = new MyBaseAdapter<BeanReturnCalendar>(
				ReturnCalendarActivity.this, datas,
				R.layout.item_calendar_return) {

			@Override
			public void holdItem(ViewHolder holder, BeanReturnCalendar item) {
				// TODO Auto-generated method stub
				TextView tv_month_day = holder
						.getView(R.id.item_calendar_month_day);
				TextView tv_year = holder.getView(R.id.item_calendar_year);
				TextView tv_desc = holder.getView(R.id.item_calendar_desc);
				TextView tv_income = holder
						.getView(R.id.item_calendar_income_money);
				TextView tv_time = holder.getView(R.id.item_calendar_time);
				tv_month_day.setText(item.getDate_monty_day());
				tv_year.setText(item.getDate_year());
				tv_time.setText(item.getDate_time());
				tv_desc.setText(item.getDesc());
				// Html.fromHtml(source),<u>下划线</u>、<i>斜体字</i>、<font
				// color='blue'>蓝色字</font>
				tv_income.setText("本金"
						+ HtmlColorFont("#f15a4a", item.getIncome()) + "元+收益"
						+ HtmlColorFont("#f15a4a", item.getIncome()) + "元");

				ImageView iv_icon = holder.getView(R.id.item_calendar_iv);
				iv_icon.setBackgroundDrawable(null);
				if (datas.size() > 1 && datas.indexOf(item) == datas.size() - 1) {
					iv_icon.setImageResource(R.drawable.red_stroke_icon);
				} else {
					iv_icon.setBackgroundResource(R.drawable.red_stroke_line_icon);
				}
			}

		};
		mListView.setPullLoadEnable(false);
		mRefreshLayout.setLoadMore(true);
		mListView.setAdapter(adapter);

		mRefreshLayout
				.setMaterialRefreshListener(new MaterialRefreshListener() {

					@Override
					public void onRefresh(
							MaterialRefreshLayout materialRefreshLayout) {
						// TODO Auto-generated method stub
						loadData(true);
					}

					@Override
					public void onRefreshLoadMore(
							MaterialRefreshLayout materialRefreshLayout) {
						// TODO Auto-generated method stub
						super.onRefreshLoadMore(materialRefreshLayout);
						loadData(false);
					}
				});

		initCurrentDate();
		initCalendarView();
		initToolbarClickListener();
	}

	private String HtmlColorFont(String color, String text) {
		return Html.fromHtml("<font color='color'>" + text + "</font>")
				.toString();
	}

	@Override
	protected void getData() {
		loadData(true);
	}

	@SuppressWarnings("unchecked")
	public void loadData(boolean isRefresh) {
		// List list = new ArrayList<>();
		// for (int i = 0; i < 10; i++) {
		//
		// BeanReturnCalendar brc = new BeanReturnCalendar();
		// brc.setDate_monty_day(new DateFormat().format(
		// "yyyy:mm:dd--HH:mm:ss", new Date()).toString());
		// brc.setDate_time(i + "");
		// brc.setDate_year("2017");
		// brc.setDesc("供应链新手标");
		// brc.setIncome(i * 100 + "");
		// brc.setMoney(i * 10 + "");
		// list.add(brc);
		// }
		if (isRefresh) {
			// datas.clear();
			// datas.addAll(list);
			// ((ScrollView) mRefreshLayout.getChildAt(0)).smoothScrollTo(0, 0);
			mRefreshLayout.finishRefresh();
		} else {
			// datas.addAll(list);
			mRefreshLayout.finishRefreshLoadMore();
		}

		adapter.notifyDataSetChanged();

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		}
	}

	/**
	 * onWindowFocusChanged回调时，将当前月的种子日期修改为今天
	 * 
	 * @return void
	 */
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (hasFocus && !initiated) {
			refreshMonthPager();
			initiated = true;
		}
	}

	/**
	 * 初始化对应功能的listener
	 * 
	 * @return void
	 */
	private void initToolbarClickListener() {
		backToday.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onClickBackToDayBtn();
			}
		});
		nextMonthBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				monthPager.setCurrentItem(monthPager.getCurrentPosition() + 1);
			}
		});
		lastMonthBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				monthPager.setCurrentItem(monthPager.getCurrentPosition() - 1);
			}
		});
	}

	/**
	 * 初始化currentDate
	 * 
	 * @return void
	 */
	private void initCurrentDate() {
		currentDate = new CalendarDate();
		textViewYearDisplay.setText(currentDate.getYear() + "年");
		textViewMonthDisplay.setText(currentDate.getMonth() + "月");
	}

	/**
	 * 初始化CustomDayView，并作为CalendarViewAdapter的参数传入
	 * 
	 * @return void
	 */
	private void initCalendarView() {
		initListener();
		CustomDayView customDayView = new CustomDayView(this,
				R.layout.calendar_custom_day);
		// SignInDayView customDayView = new SignInDayView(this,
		// R.layout.calendar_custom_day);
		calendarAdapter = new CalendarViewAdapter(this, onSelectDateListener,
				CalendarAttr.CalendayType.MONTH, customDayView);
		initMarkData();
		initSignInData();
		initMonthPager();
	}

	/**
	 * 初始化标记数据，HashMap的形式，可自定义
	 * 
	 * @return void
	 */
	private void initMarkData() {
		HashMap<String, String> markData = new HashMap<String, String>();
		markData.put("2017-8-9", "1");
		markData.put("2017-7-2", "0");
		markData.put("2017-8-19", "1");
		markData.put("2017-7-10", "0");
		calendarAdapter.setMarkData(markData);
	}

	private void initSignInData() {
		HashMap<String, String> signinData = new HashMap<String, String>();
		signinData.put("2017-8-9", "1");
		signinData.put("2017-8-19", "0");
		signinData.put("2017-7-9", "1");
		signinData.put("2017-8-10", "0");
		calendarAdapter.setSignInData(signinData);
	}

	private void initListener() {
		onSelectDateListener = new OnSelectDateListener() {
			@Override
			public void onSelectDate(CalendarDate date) {
				refreshClickDate(date);
			}

			@Override
			public void onSelectOtherMonth(int offset) {
				// 偏移量 -1表示刷新成上一个月数据 ， 1表示刷新成下一个月数据
				monthPager.selectOtherMonth(offset);
			}
		};
	}

	private void refreshClickDate(CalendarDate date) {
		currentDate = date;
		textViewYearDisplay.setText(date.getYear() + "年");
		textViewMonthDisplay.setText(date.getMonth() + "");
	}

	/**
	 * 初始化monthPager，MonthPager继承自ViewPager
	 * 
	 * @return void
	 */
	private void initMonthPager() {
		monthPager.setAdapter(calendarAdapter);
		monthPager.setCurrentItem(MonthPager.CURRENT_DAY_INDEX);
		monthPager.setPageTransformer(false, new ViewPager.PageTransformer() {
			@SuppressLint("NewApi")
			@Override
			public void transformPage(View page, float position) {
				position = (float) Math.sqrt(1 - Math.abs(position));
				page.setAlpha(position);
			}
		});
		monthPager
				.addOnPageChangeListener(new MonthPager.OnPageChangeListener() {
					@Override
					public void onPageScrolled(int position,
							float positionOffset, int positionOffsetPixels) {
					}

					@Override
					public void onPageSelected(int position) {
						mCurrentPage = position;
						currentCalendars = calendarAdapter.getPagers();
						if (currentCalendars.get(position
								% currentCalendars.size()) instanceof Calendar) {
							CalendarDate date = currentCalendars.get(
									position % currentCalendars.size())
									.getSeedDate();
							currentDate = date;
							textViewYearDisplay.setText(date.getYear() + "年");
							textViewMonthDisplay.setText(date.getMonth() + "月");
						}
					}

					@Override
					public void onPageScrollStateChanged(int state) {
					}
				});
	}

	public void onClickBackToDayBtn() {
		refreshMonthPager();
	}

	private void refreshMonthPager() {
		CalendarDate today = new CalendarDate();
		textViewYearDisplay.setText(today.getYear() + "年");
		textViewMonthDisplay.setText(today.getMonth() + "月");
		calendarAdapter.notifyDataChanged(today);
	}

}
