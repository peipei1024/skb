package com.js.skb.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.js.skb.MyApplication;
import com.js.skb.R;
import com.js.skb.adapter.DishMethodAdapter;
import com.js.skb.adapter.MeterialAdapter;
import com.js.skb.bean.Dish;
import com.js.skb.bean.User;
import com.js.skb.db.FootDBHelp;
import com.js.skb.db.ListDBHelp;
import com.js.skb.db.PraiseDBHelp;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.peixuze.ui.ListViewForScrollView;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.UpdateListener;


public class DishActivity extends Activity implements OnClickListener {
	private ImageView dishpic, back;
	private TextView dishname;
	private TextView dishmaterial, dishmethod;
	String strdishname, strdishmaterial, strdishmaterial2, strdishmethod, strdishpic;
	String dishid;
	String id;
	String createdtime;
	private ScrollView sc;
	private ListViewForScrollView cailiao, tiaoliao, methodlistview;
	private MeterialAdapter matericaladapter;
	private DishMethodAdapter dishmethodadapter;
	private List matericallist = new ArrayList();
	private List matericallist2 = new ArrayList();
	private List methodlist = new ArrayList();

	private LinearLayout mShopList, mCollection, mUp;
	private ImageView mImageCollection, mImageUp,image_shoplist;
	private TextView mTextUp;
	private ImageView more;
	private boolean dishfav = false;
	private boolean dishlov = true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dish);
		init();
		queryLove();
		// show();
	}

	private void init() {

		Intent it = getIntent();
		dishid = it.getStringExtra("entityid");
		Log.w("info", dishid + "");
		back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(this);
		sc = (ScrollView) findViewById(R.id.myscollview);
		sc.smoothScrollTo(0, 0);
		dishpic = (ImageView) findViewById(R.id.dishpic);
		dishname = (TextView) findViewById(R.id.dishname);
		dishmaterial = (TextView) findViewById(R.id.dishmaterial);
		dishmethod = (TextView) findViewById(R.id.dishmethod);
		cailiao = (ListViewForScrollView) findViewById(R.id.cailiao);
		tiaoliao = (ListViewForScrollView) findViewById(R.id.tiaoliao);
		methodlistview = (ListViewForScrollView) findViewById(R.id.dishmethodlistview);
		// mPullRefreshListView =
		// (GridView)findViewById(R.id.pull_refresh_grid);
		show(dishid);
		more = (ImageView) findViewById(R.id.more);
		more.setOnClickListener(this);
		mShopList = (LinearLayout) findViewById(R.id.shoplist);
		mShopList.setOnClickListener(this);
		mCollection = (LinearLayout) findViewById(R.id.collection);
		mCollection.setOnClickListener(this);
		mImageCollection = (ImageView) findViewById(R.id.image_collection);
		mUp = (LinearLayout) findViewById(R.id.up);
		mUp.setOnClickListener(this);
		mTextUp = (TextView) findViewById(R.id.text_up);
		mImageUp = (ImageView) findViewById(R.id.image_up);
		image_shoplist = (ImageView) findViewById(R.id.image_shoplist);
		
		

	}

	public void show(String dishidd) {
		BmobQuery<Dish> query = new BmobQuery<Dish>();
		query.getObject(DishActivity.this, dishidd, new GetListener<Dish>() {

			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Log.w("info", arg1);
			}

			@Override
			public void onSuccess(Dish arg0) {
				// TODO Auto-generated method stub
				strdishname = arg0.getDishName().trim();
				arg0.getDishPic();
				createdtime = arg0.getCreatedAt();
				id = arg0.getObjectId();
				strdishpic = arg0.getDishPic().getFileUrl(DishActivity.this);
				strdishmaterial = arg0.getDishMaterial();
				strdishmaterial2 = arg0.getDishMaterial2();
				strdishmethod = arg0.getDishMethod();
				dishname.setText(strdishname);
				chuli(strdishmaterial);
				chuli2(strdishmaterial2);
				Log.w("info", strdishmaterial);
				chuli3(strdishmethod);
				queryFavo();
				mTextUp.setText(arg0.getLove());
				ImageLoader.getInstance().displayImage(strdishpic, dishpic,
						MyApplication.getInstance().getOptions(R.drawable.dishsxamplie1),
						new SimpleImageLoadingListener() {

					@Override
					public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
						// TODO Auto-generated method stub
						super.onLoadingComplete(imageUri, view, loadedImage);
					}

				});
				FootDBHelp fdbh = new FootDBHelp(DishActivity.this);
				SQLiteDatabase db = null;
				db = fdbh.getWritableDatabase();
				ContentValues cvOfLiHua = new ContentValues();
				cvOfLiHua.put("dishid", id);
				cvOfLiHua.put("time", createdtime);
				db.insert("foot", null, cvOfLiHua);
				Log.d("msg", "写入数据库完成" + id + createdtime);
			}
		});

	}

	public void chuli3(String str) {

		String B[] = str.split("/");
		for (int num2 = 0; num2 < B.length; num2++) {
			methodlist.add(B[num2]);
		}
		dishmethodadapter = new DishMethodAdapter(DishActivity.this, (ArrayList) methodlist);

		methodlistview.setAdapter(dishmethodadapter);

		Log.w("info", "菜谱步骤" + methodlist.size() );
	}

	public void chuli2(String str) {

		String B[] = str.split("，");
		for (int num2 = 0; num2 < B.length; num2++) {
			matericallist2.add(B[num2]);
		}
		matericaladapter = new MeterialAdapter(DishActivity.this, (ArrayList) matericallist2);

		tiaoliao.setAdapter(matericaladapter);

		Log.w("info", "调料" + matericallist.size() );
	}

	public void chuli(String str) {

		String a[] = str.split("，");
		for (int num = 0; num < a.length; num++) {
			matericallist.add(a[num]);
		}

		matericaladapter = new MeterialAdapter(DishActivity.this, (ArrayList) matericallist);

		cailiao.setAdapter(matericaladapter);
		// setListViewHeightBasedOnChildren(cailiao);

	}
	//处理listview与scrollview的滑动冲突问题，没使用
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		if (listView == null)
			return;
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.more:
			new PopupWindows(DishActivity.this, more);
			break;
		case R.id.shoplist:
			addShopList((ArrayList) matericallist, (ArrayList) matericallist2);
			break;
		case R.id.collection:
			addCollection();
			break;
		case R.id.up:
			addLove();
			break;
		}
	}
	
	private void queryLove() {
		User user = BmobUser.getCurrentUser(DishActivity.this, User.class);
		if(user!=null)
		{
			PraiseDBHelp fdbh = new PraiseDBHelp(DishActivity.this);
			SQLiteDatabase db = null;
			db = fdbh.getReadableDatabase();
			Cursor c2 = db.rawQuery("select * from praise", null);
			if (c2 != null) {
				String[] cols = c2.getColumnNames();
				while (c2.moveToNext()) {
					String userid = c2.getString(c2.getColumnIndex("userid"));
					String dishobjectid = c2.getString(c2.getColumnIndex("dishobjectid"));
					Log.w("info", userid+"   "+user.getObjectId());
					Log.w("info", dishobjectid+"   "+dishid);
					if (dishobjectid.equals(dishid)) {
						if (user.getObjectId().equals(userid)) {
							mImageUp.setImageResource(R.drawable.button_prise_chosse);
							dishlov = false;
						}
						
					}else{
						mImageUp.setImageResource(R.drawable.button_prise_normal);
					}
					
				}
				c2.close();
			}
			db.close();
		}
		
	}

	private void addLove() {
		User user = BmobUser.getCurrentUser(DishActivity.this, User.class);
		if (user != null) {
			
			if(dishlov)
			{
				String oldup = mTextUp.getText().toString().trim();
				int intnowup = Integer.valueOf(oldup)+1;
				final String nowup = String.valueOf(intnowup);
				Log.w("nowup", nowup);
				Dish dish = new Dish();
				dish.setLove(nowup);
				dish.update(DishActivity.this, dishid, new UpdateListener() {
					
					
					@Override
					public void onSuccess() {
						// TODO Auto-generated method stub
						PraiseDBHelp fdbh = new PraiseDBHelp(DishActivity.this);
						SQLiteDatabase db = null;
						db = fdbh.getWritableDatabase();
						User user = BmobUser.getCurrentUser(DishActivity.this, User.class);
						ContentValues cvOfLiHua = new ContentValues();
						cvOfLiHua.put("userid", user.getObjectId());
						cvOfLiHua.put("dishobjectid", dishid);
						db.insert("praise", null, cvOfLiHua);
						mImageUp.setImageResource(R.drawable.button_prise_chosse);
						dishlov = false;
						Toast.makeText(DishActivity.this, "点赞成功", Toast.LENGTH_SHORT).show();
						mTextUp.setText(nowup);
						Log.d("msg", "点赞" + id + createdtime);
					}
					
					@Override
					public void onFailure(int arg0, String arg1) {
						// TODO Auto-generated method stub
						
					}
				});
				
			}else {
				Toast.makeText(DishActivity.this, "已经赞过了", Toast.LENGTH_SHORT).show();
			}
			
		} else {
			Toast.makeText(DishActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
			Intent it = new Intent();
			it.setClass(DishActivity.this, LandActivity.class);
			startActivity(it);
		}
	}

	private void queryFavo() {
		User user = BmobUser.getCurrentUser(DishActivity.this, User.class);
		if (user != null) {
			BmobQuery<Dish> query = new BmobQuery<Dish>();

			query.addWhereRelatedTo("favorite", new BmobPointer(user));
			query.findObjects(this, new FindListener<Dish>() {

				@Override
				public void onSuccess(List<Dish> object) {
					// TODO Auto-generated method stub
					User user = BmobUser.getCurrentUser(DishActivity.this, User.class);
					int a = object.size();
					Log.i("life", "查询个数：" + object.size());
					for (int i = 0; i < a; i++) {
						Dish dish = object.get(i);
						Log.i("life", "查询个数：" + dish.getObjectId()+dishid);
						if (dish.getObjectId().equals(dishid)) {
							dishfav = true;
							
						} 
					}

					if (dishfav) {
						mImageCollection.setImageResource(R.drawable.button_collection_choose);
					} else {
						mImageCollection.setImageResource(R.drawable.button_collection_normal);
					}
				}

				@Override
				public void onError(int code, String msg) {
					// TODO Auto-generated method stub
				}
			});
		}

	}

	private void addCollection() {
		User user = BmobUser.getCurrentUser(DishActivity.this, User.class);
		if (user != null) {
			BmobQuery<Dish> query = new BmobQuery<Dish>();
			query.getObject(DishActivity.this, dishid, new GetListener<Dish>() {

				@Override
				public void onFailure(int arg0, String arg1) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onSuccess(Dish arg0) {
					// TODO Auto-generated method stub
					User user = BmobUser.getCurrentUser(DishActivity.this, User.class);
					BmobRelation relation = new BmobRelation();
					if (dishfav) {
						relation.remove(arg0);
						user.setFavorite(relation);
						user.update(DishActivity.this, new UpdateListener() {
							@Override
							public void onSuccess() {
								// TODO Auto-generated method stub
								Toast.makeText(DishActivity.this, "取消收藏", Toast.LENGTH_SHORT).show();
								mImageCollection.setImageResource(R.drawable.button_collection_normal);
								dishfav = false;
							}

							@Override
							public void onFailure(int arg0, String arg1) {
								// TODO Auto-generated method stub

							}
						});
					} else {
						relation.add(arg0);
						user.setFavorite(relation);
						user.update(DishActivity.this, new UpdateListener() {
							@Override
							public void onSuccess() {
								// TODO Auto-generated method stub
								Toast.makeText(DishActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
								mImageCollection.setImageResource(R.drawable.button_collection_choose);
								dishfav = true;
							}

							@Override
							public void onFailure(int arg0, String arg1) {
								// TODO Auto-generated method stub

							}
						});
					}

				}
			});
		} else {
			Toast.makeText(DishActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
			Intent it = new Intent();
			it.setClass(DishActivity.this, LandActivity.class);
			startActivity(it);
		}

	}

	private void addShopList(ArrayList list1, ArrayList list2) {
		ListDBHelp fdbh = new ListDBHelp(DishActivity.this);
		SQLiteDatabase db = null;
		db = fdbh.getWritableDatabase();
		ContentValues cvOfLiHua = new ContentValues();
		for (Iterator iter = list1.iterator(); iter.hasNext();) {
			String str = (String) iter.next();
			String a[] = str.split("。");
			cvOfLiHua.put("name", a[0]);
			cvOfLiHua.put("num", a[1]);
			db.insert("list", null, cvOfLiHua);
			cvOfLiHua.clear();
		}
		for (Iterator iter = list2.iterator(); iter.hasNext();) {
			String str = (String) iter.next();
			String a[] = str.split("。");
			cvOfLiHua.put("name", a[0]);
			cvOfLiHua.put("num", a[1]);
			db.insert("list", null, cvOfLiHua);
			cvOfLiHua.clear();
		}
		Toast.makeText(DishActivity.this, "已加入购物车", Toast.LENGTH_SHORT).show();
		image_shoplist.setImageResource(R.drawable.button_shoplist_choose);
	}
}
