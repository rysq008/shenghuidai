package com.delevin.shenghuidai.activity;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.delevin.shenghuidai.adapter.HetongAdapter;
import com.delevin.shenghuidai.base.activity.BaseActivity;
import com.delevin.shenghuidai.bean.BeanjieKuan;
import com.delevin.shenghuidai.utils.ProessDilogs;
import com.delevin.shenghuidai.view.TitleView;
import com.yourenkeji.shenghuidai.R;

/**
 *     @author 李红涛  @version 创建时间：2017-1-4 下午2:37:58    类说明 
 */
public class HeTongActivity extends BaseActivity {

	private ListView zijinListView;
	private List<BeanjieKuan> datas;
	private LinearLayout zijinJiaZaiLayout;
	private ImageView zijinJiaZaiImg;
	private HetongAdapter adapter;
	// private ImageView zijinJiaZaiImgNone;
	private RelativeLayout rlNotData;
	private MaterialRefreshLayout refreshLayout;

	@Override
	protected void findViews() {
		setContentView(R.layout.boluos_listview_jiazai);
		TitleView titleView = (TitleView) findViewById(R.id.titleView_listview_jiazai);
		View statusBarview = findViewById(R.id.statusBarview);
		// 设置状态栏一体化
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			statusBarview.setVisibility(View.GONE);
		}
		titleView.initViewsVisible(true, true, true, false);
		titleView.setAppTitle("项目合同");
		refreshLayout = (MaterialRefreshLayout) findViewById(R.id.listview_jiazai_layouts);
		refreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {

			@Override
			public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
				// TODO Auto-generated method stub
				getData();
			}

			@Override
			public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
				// TODO Auto-generated method stub
				super.onRefreshLoadMore(materialRefreshLayout);
			}
		});

		zijinListView = (ListView) findViewById(R.id.listview_jiazai_listView);
		zijinJiaZaiLayout = (LinearLayout) findViewById(R.id.listview_jiazai_layout);
		zijinJiaZaiImg = (ImageView) findViewById(R.id.listview_jiazai_img);
		// zijinJiaZaiImgNone = (ImageView)
		// findViewById(R.id.listview_jiazai_none);
		rlNotData = (RelativeLayout) findViewById(R.id.listview_jiazai_rl_none);
		datas = new ArrayList<BeanjieKuan>();
		zijinListView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		zijinListView.setDividerHeight(0);
		adapter = new HetongAdapter(getApplicationContext(), datas, R.layout.activity_hetong_img);

		zijinListView.setAdapter(adapter);

	}

	@Override
	protected void getData() {
		datas.clear();
		try {
			ProessDilogs.getProessAnima(zijinJiaZaiImg, this);
			List<BeanjieKuan> list = (List<BeanjieKuan>) getIntent().getSerializableExtra("list");
			if (null != list && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					BeanjieKuan jiBeanjieKuan = new BeanjieKuan();
					String name = list.get(i).getName();
					String path = list.get(i).getPath();
					jiBeanjieKuan.setName(name);
					jiBeanjieKuan.setPath(path);
					datas.add(jiBeanjieKuan);
				}
			} else {
				rlNotData.setVisibility(View.VISIBLE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ProessDilogs.closeAnimation(zijinJiaZaiImg, zijinJiaZaiLayout);
			refreshLayout.finishRefresh();
			refreshLayout.finishRefreshLoadMore();
		}
	}
}
