package com.js.skb.ui;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.js.skb.R;
import com.js.skb.adapter.StyleDishAdapter;
import com.js.skb.bean.Dish;
import com.js.skb.db.Constant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class IndexFenleiActivity extends Activity implements OnClickListener {
	private TextView type_text;
	private ImageView type_image;
	private ImageView back;

	private Handler mHandler;
	private PullToRefreshGridView mPullRefreshListView;
	private StyleDishAdapter styledishadapter;
	private List<Dish> styledishlist = new ArrayList<Dish>();

	
	private String STYLE;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_indexfenlei);
		preinit();

		init();

	}

	public void init() {
		mPullRefreshListView.autoRefresh();
		back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(this);
	}

	public void preinit() {
		mPullRefreshListView = (PullToRefreshGridView) findViewById(R.id.pull_refresh_grid);
		
		styledishadapter = new StyleDishAdapter(IndexFenleiActivity.this, (ArrayList<Dish>) styledishlist);
		mPullRefreshListView.setAdapter(styledishadapter);
		mHandler = new Handler();
		mPullRefreshListView.setOnRefreshListener(new OnRefreshListener2<GridView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
				Log.e("TAG", "onPullDownToRefresh"); // Do work to
				mHandler.postDelayed(new Runnable() {
					@Override
					public void run() {
						initData(0, STATE_REFRESH);
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
						initData(curPage, STATE_MORE);
						// onLoad();
						styledishadapter.notifyDataSetChanged();
						mPullRefreshListView.onRefreshComplete();
					}
				}, 2000);
				
			}
		});

		type_text = (TextView) findViewById(R.id.type_text);
		type_image = (ImageView) findViewById(R.id.type_image);

		Intent intent = getIntent();
		String type = intent.getStringExtra("type");
		if (type.equals("1")) {
			STYLE = Constant.STYLE_METHOD_chao;
			type_image.setImageResource(R.drawable.button_fenlei_chao);
		}
		if (type.equals("2")) {
			STYLE = Constant.STYLE_METHOD_dun;
			type_image.setImageResource(R.drawable.button_fenlei_dun);
		}
		if (type.equals("3")) {
			STYLE = Constant.STYLE_METHOD_meng;
			type_image.setImageResource(R.drawable.button_fenlei_meng);
		}
		if (type.equals("4")) {
			STYLE = Constant.STYLE_METHOD_sao;
			type_image.setImageResource(R.drawable.button_fenlei_sao);
		}
		if (type.equals("5")) {
			STYLE = Constant.STYLE_METHOD_hui;
			type_image.setImageResource(R.drawable.button_fenlei_hui);
		}
		if (type.equals("6")) {
			STYLE = Constant.STYLE_METHOD_liu;
			type_image.setImageResource(R.drawable.button_fenlei_liu);
		}
		if (type.equals("7")) {
			STYLE = Constant.STYLE_METHOD_zha;
			type_image.setImageResource(R.drawable.button_fenlei_zha);
		}
		if (type.equals("8")) {
			STYLE = Constant.STYLE_METHOD_zheng;
			type_image.setImageResource(R.drawable.button_fenlei_zheng);
		}
		if (type.equals("9")) {
			STYLE = Constant.STYLE_METHOD_ba;
			type_image.setImageResource(R.drawable.button_fenlei_ba);
		}
		if (type.equals("10")) {
			STYLE = Constant.STYLE_METHOD_gong;
			type_image.setImageResource(R.drawable.button_fenlei_gong);
		}
		type_text.setText(STYLE);
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
	private void initData(final int page, final int actionType) {
		Log.i("bmob", "pageN:" + page + " limit:" + limit + " actionType:" + actionType);
		BmobQuery<Dish> query = new BmobQuery<Dish>();
		query.order("-updatedAt");
		query.setLimit(limit); // ����ÿҳ����������
		query.setSkip(page * limit);
		
		query.addWhereContains("dishstyle", STYLE);
		query.findObjects(IndexFenleiActivity.this, new FindListener<Dish>() {

			@Override
			public void onSuccess(List<Dish> arg0) {
				// TODO Auto-generated method stub
				if (arg0.size() > 0) {

					// �����β�ѯ��������ӵ�bankCards��
					for (Dish dish : arg0) {
						styledishlist.add(dish);
					}
					// ֪ͨAdapter���ݸ���
					styledishadapter.notifyDataSetChanged();
					// ������ÿ�μ��������ݺ󣬽���ǰҳ��+1������������ˢ�µ�onPullUpToRefresh�����оͲ���Ҫ����curPage��
					curPage++;
					 //toast("��" + (page + 1) + "ҳ���ݼ������");
				} else {
					toast("û�и���������");
				}
			}

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				toast("����ʧ��");
			}
		});

	}

	public void toast(String toast) {

		Toast.makeText(IndexFenleiActivity.this, toast, Toast.LENGTH_SHORT).show();
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
