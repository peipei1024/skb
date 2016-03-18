package com.js.skb.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.js.skb.R;
import com.js.skb.adapter.FootAdapter;
import com.js.skb.bean.Dish;
import com.js.skb.db.FootDBHelp;


import android.app.Activity;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.GetListener;

public class FootActivity extends Activity implements OnClickListener{
	private PullToRefreshListView mPullRefreshListView;
	private FootAdapter adapter;
	private List<Dish> list = new ArrayList<Dish>();
	
	private ImageView mBack;
	List<String> footlist = new ArrayList<String>();// 从数据库读出的id

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_foot);
		readFromDB();

		init();
	}

	private void init() {
		mBack = (ImageView) findViewById(R.id.back);
		mBack.setOnClickListener(this);
		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
		//initData();
		
		// 初始化数据
		adapter = new FootAdapter(FootActivity.this, (ArrayList<String>) footlist);
		mPullRefreshListView.setAdapter(adapter);
		mPullRefreshListView
		.setOnRefreshListener(new OnRefreshListener2<ListView>()
		{
			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView)
			{
				Log.e("TAG", "onPullDownToRefresh");
				//adapter.notifyDataSetChanged();
				mPullRefreshListView.onRefreshComplete();
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView)
			{
				Log.e("TAG", "onPullUpToRefresh");
			
			}
		});
		// 设置适配器
	
	}

	

	
	private void readFromDB() {

		FootDBHelp fdbh = new FootDBHelp(FootActivity.this);
		SQLiteDatabase db = null;
		db = fdbh.getReadableDatabase();
		Cursor c = db.rawQuery("select * from foot", null);
		if (c != null) {
			String[] cols = c.getColumnNames();
			while (c.moveToNext()) {
				footlist.add(c.getString(c.getColumnIndex("dishid")));
				Log.i("info", c.getString(c.getColumnIndex("dishid")));
			}
			c.close();
		}
		db.close();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.back)
		{
			finish();
		}
	}
}
