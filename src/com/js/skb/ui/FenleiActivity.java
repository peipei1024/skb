package com.js.skb.ui;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.js.skb.R;
import com.js.skb.adapter.DishAdapter;
import com.js.skb.adapter.DishAdapter2;
import com.js.skb.bean.Dish;
import com.js.skb.db.Constant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class FenleiActivity extends Activity implements OnClickListener {
	private LinearLayout shuan, tian, la, xian, more;
	private TextView type_text;
	private ImageView back;
	private Handler mHandler;
	private PullToRefreshGridView mPullRefreshListView;
	private DishAdapter2 dishadapter;
	private List<Dish> dishlist = new ArrayList<Dish>();

	private String STYLE;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fenlei_sec);
		init();

	}

	private void init() {
		shuan = (LinearLayout) findViewById(R.id.shuan);
		tian = (LinearLayout) findViewById(R.id.tian);
		la = (LinearLayout) findViewById(R.id.la);
		xian = (LinearLayout) findViewById(R.id.xian);
		more = (LinearLayout) findViewById(R.id.more);
		type_text = (TextView) findViewById(R.id.type_text);
		back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(this);

		Intent intent = getIntent();
		String type = intent.getStringExtra("type");
		if (type.equals("1")) {
			STYLE = Constant.STYLE_TASTE_shuang;
			shuan.setVisibility(View.VISIBLE);
		}
		if (type.equals("2")) {
			STYLE = Constant.STYLE_TASTE_tian;
			tian.setVisibility(View.VISIBLE);
		}
		if (type.equals("3")) {
			STYLE = Constant.STYLE_TASTE_la;
			la.setVisibility(View.VISIBLE);
		}
		if (type.equals("4")) {
			STYLE = Constant.STYLE_TASTE_xian;
			xian.setVisibility(View.VISIBLE);
		}
		if (type.equals("5")) {
			STYLE = Constant.STYLE_TASTE_gengduo;
			more.setVisibility(View.VISIBLE);
		}
		type_text.setText(STYLE);

		// 得到控件
		mPullRefreshListView = (PullToRefreshGridView) findViewById(R.id.pull_refresh_grid);
		mPullRefreshListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				// Intent it=new Intent();
				// it.setClass(getActivity(), DishActivity.class);
				// startActivity(it);

			}
		});
		// 初始化数据和数据源
		// initDatas();
		dishadapter = new DishAdapter2(FenleiActivity.this, (ArrayList<Dish>) dishlist);

		/*
		 * mAdapter = new ArrayAdapter<String>(getActivity(),
		 * R.layout.layout_gridview_dish, R.id.dishname, mListItems);
		 */
		mPullRefreshListView.setAdapter(dishadapter);
		mPullRefreshListView.autoRefresh();
		mHandler = new Handler();
		mPullRefreshListView.setOnRefreshListener(new OnRefreshListener2<GridView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
				Log.e("TAG", "onPullDownToRefresh"); // Do work to
				mHandler.postDelayed(new Runnable() {
					@Override
					public void run() {
						dishlist.clear();
						getTradeItemData(0, STATE_REFRESH);
						curPage = 0;
						mPullRefreshListView.onRefreshComplete();
					}
				}, 2000);

			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
				Log.e("TAG", "onPullUpToRefresh"); // Do work to refresh
													// the list here.
				mHandler.postDelayed(new Runnable() {
					@Override
					public void run() {
						getTradeItemData(curPage, STATE_MORE);
						dishadapter.notifyDataSetChanged();
						mPullRefreshListView.onRefreshComplete();
						// onLoad();
					}
				}, 2000);

			}
		});
	}

	// 下拉刷新
	private static final int STATE_REFRESH = 0;// 下拉刷新
	private static final int STATE_MORE = 1;// 加载更多
	private int limit = 10; // 每页的数据是10条
	private int curPage = 0; // 当前页的编号，从0开始

	/**
	 * 分页获取数据
	 * 
	 * @param page
	 *            页码
	 * @param actionType
	 *            ListView的操作类型（下拉刷新、上拉加载更多）
	 */
	private void getTradeItemData(final int page, final int actionType) {
		Log.i("bmob", "pageN:" + page + " limit:" + limit + " actionType:" + actionType);
		BmobQuery<Dish> query = new BmobQuery<Dish>();
		query.order("-updatedAt");
		query.setLimit(limit); // 设置每页多少条数据
		query.setSkip(page * limit);
		query.addWhereContains("dishstyle", STYLE);
		query.findObjects(FenleiActivity.this, new FindListener<Dish>() {

			@Override
			public void onSuccess(List<Dish> arg0) {
				// TODO Auto-generated method stub
				if (arg0.size() > 0) {

					// 将本次查询的数据添加到bankCards中
					for (Dish dish : arg0) {
						dishlist.add(dish);
					}
					// 通知Adapter数据更新
					dishadapter.notifyDataSetChanged();
					// 这里在每次加载完数据后，将当前页码+1，这样在上拉刷新的onPullUpToRefresh方法中就不需要操作curPage了
					curPage++;
					//toast("第" + (page + 1) + "页数据加载完成");
				} else {
					toast("没有更多数据了");
				}
			}

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				toast("加载失败");
				Log.w("info", "arg1");
			}
		});

	}

	public void toast(String toast) {

		Toast.makeText(FenleiActivity.this, toast, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;

		default:
			break;
		}
	}
}
