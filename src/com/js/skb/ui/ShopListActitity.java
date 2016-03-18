package com.js.skb.ui;

import java.util.ArrayList;

import com.js.skb.R;
import com.js.skb.adapter.ShopListAdapter;
import com.js.skb.db.FootDBHelp;
import com.js.skb.db.ListDBHelp;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;

public class ShopListActitity extends Activity implements OnClickListener{
	private ListView mListview;
	private ShopListAdapter mAdapter;
	private ArrayList<String> mArraylist = new ArrayList<String>();
	private ImageView mBack;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shoplist);
		initData();
		init();
	}
	private void initData()
	{

			ListDBHelp fdbh = new ListDBHelp(ShopListActitity.this);
			SQLiteDatabase db = null;
			db = fdbh.getReadableDatabase();
			Cursor c1 = db.rawQuery("select * from list", null);
			if (c1 != null) {
				String[] cols = c1.getColumnNames();
				while (c1.moveToNext()) {
					//String str = c1.getString(c1.getColumnIndex("name"));
					//Log.i("info", c1.getString(c1.getColumnIndex("name")));
					mArraylist.add(c1.getString(c1.getColumnIndex("name"))+","+c1.getString(c1.getColumnIndex("num")));
					//Log.i("info", c.getString(c.getColumnIndex("dishid")));
				}
				c1.close();
			}
			db.close();
		
	}
	private void init()
	{
		mBack = (ImageView) findViewById(R.id.back);
		mBack.setOnClickListener(this);
		mListview=(ListView) findViewById(R.id.listview);
		mAdapter = new ShopListAdapter(ShopListActitity.this, mArraylist);
		mListview.setAdapter(mAdapter);
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
