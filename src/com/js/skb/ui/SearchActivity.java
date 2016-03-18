package com.js.skb.ui;

import java.util.ArrayList;
import java.util.List;

import com.js.skb.R;
import com.js.skb.adapter.CollectionDishAdapter;
import com.js.skb.bean.Dish;
import com.peixuze.ui.LineEditText;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class SearchActivity extends Activity implements OnClickListener {
	private LineEditText neirong;
	private ImageView back;
	private TextView search;
	private CollectionDishAdapter adapter;
	private List<Dish> list = new ArrayList<Dish>();
	private ListView listview ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		init();
	}

	private void init() {
		neirong = (LineEditText) findViewById(R.id.neirong);
		back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(this);
		search = (TextView) findViewById(R.id.search);
		search.setOnClickListener(this);
		listview = (ListView) findViewById(R.id.listview);
		adapter = new CollectionDishAdapter(SearchActivity.this, (ArrayList<Dish>)list);
		listview.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.search:
			getTradeItemData(curPage, STATE_MORE);
			break;

		default:
			break;
		}
	}
	// ����ˢ��
		private static final int STATE_REFRESH = 0;// ����ˢ��
		private static final int STATE_MORE = 1;// ���ظ���
		private int limit = 10; // ÿҳ��������10��
		private int curPage = 0; // ��ǰҳ�ı�ţ���0��ʼ

		/**
		 * ��ҳ��ȡ����
		 * 
		 * @param page
		 *            ҳ��
		 * @param actionType
		 *            ListView�Ĳ������ͣ�����ˢ�¡��������ظ��ࣩ
		 */
		private void getTradeItemData(final int page, final int actionType) {
			Log.i("bmob", "pageN:" + page + " limit:" + limit + " actionType:" + actionType);
			BmobQuery<Dish> query = new BmobQuery<Dish>();
			query.order("-updatedAt");
			query.setLimit(limit); // ����ÿҳ����������
			query.setSkip(page * limit);
			
			query.addWhereContains("dishName", neirong.getText().toString());
			query.findObjects(SearchActivity.this, new FindListener<Dish>() {

				@Override
				public void onSuccess(List<Dish> arg0) {
					// TODO Auto-generated method stub
					if (arg0.size() > 0) {

						// �����β�ѯ��������ӵ�bankCards��
						for (Dish dish : arg0) {
							list.add(dish);
						}
						// ֪ͨAdapter���ݸ���
						adapter.notifyDataSetChanged();
						// ������ÿ�μ��������ݺ󣬽���ǰҳ��+1������������ˢ�µ�onPullUpToRefresh�����оͲ���Ҫ����curPage��
						curPage++;
						toast("��" + (page + 1) + "ҳ���ݼ������");
					} else {
						toast("û�и���������");
					}
				}

				@Override
				public void onError(int arg0, String arg1) {
					// TODO Auto-generated method stub
					toast("����ʧ��");
					Log.w("info", "arg1");
				}
			});

		}

		public void toast(String toast) {

			Toast.makeText(SearchActivity.this, toast, Toast.LENGTH_SHORT).show();
		}
}
