package com.js.skb.ui;

import java.util.ArrayList;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.js.skb.R;
import com.js.skb.adapter.CollectionDishAdapter;
import com.js.skb.bean.Dish;
import com.js.skb.bean.User;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.FindListener;

public class CollectionDishActivity extends Activity implements OnClickListener {
	private ImageView mBack;
	private List<Dish> mList = new ArrayList<Dish>();
	private CollectionDishAdapter mAdapter;
	private PullToRefreshListView mListview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_collectiondish);
		initData();
		init();
	}

	private void init() {
		mBack = (ImageView) findViewById(R.id.back);
		mBack.setOnClickListener(this);
		
		mListview = (PullToRefreshListView)findViewById(R.id.pull_refresh_list);
		mAdapter = new CollectionDishAdapter(CollectionDishActivity.this, (ArrayList<Dish>)mList);
		mListview.setAdapter(mAdapter);
	}

	private void initData() {
		User user = BmobUser.getCurrentUser(CollectionDishActivity.this, User.class);
		if (user != null) {
			BmobQuery<Dish> query = new BmobQuery<Dish>();

			// likes��Post���е��ֶΣ������洢����ϲ�������ӵ��û�
			query.addWhereRelatedTo("favorite", new BmobPointer(user));
			query.findObjects(this, new FindListener<Dish>() {

				@Override
				public void onSuccess(List<Dish> object) {
					// TODO Auto-generated method stub
					Log.i("life", "��ѯ������" + object.size());
					if (object.size() > 0) {

						// �����β�ѯ��������ӵ�bankCards��
						for (Dish dish : object) {
							mList.add(dish);
						}
						// ֪ͨAdapter���ݸ���
						mAdapter.notifyDataSetChanged();
					} else {
						toast("��û���ղز���");
					}

				}

				@Override
				public void onError(int code, String msg) {
					// TODO Auto-generated method stub
					Log.i("life", "��ѯʧ�ܣ�" + code + "-" + msg);
				}
			});
		}

	}
	public void toast(String toast) {

		Toast.makeText(CollectionDishActivity.this, toast, Toast.LENGTH_SHORT).show();
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
