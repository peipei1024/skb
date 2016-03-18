package com.js.skb.ui.fragment;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.js.skb.R;
import com.js.skb.adapter.DishAdapter;
import com.js.skb.bean.Dish;
import com.js.skb.ui.IndexFenleiActivity;
import com.js.skb.ui.SearchActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class IndexFragment extends Fragment implements OnClickListener{
	private LinkedList<String> mListItems;  
	private Handler mHandler;
    private PullToRefreshGridView mPullRefreshListView;  
    private DishAdapter dishadapter;
    private List<Dish> dishlist = new ArrayList<Dish>();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	private ImageView chao,dun,meng,sao,hui;
	private ImageView liu,zha,zheng,ba,gong;
	private ImageView more;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.fragment_index, container, false);
		chao=(ImageView) view.findViewById(R.id.button_chao);
		chao.setOnClickListener(this);
		dun=(ImageView) view.findViewById(R.id.button_dun);
		dun.setOnClickListener(this);
		meng=(ImageView) view.findViewById(R.id.button_meng);
		meng.setOnClickListener(this);
		sao=(ImageView) view.findViewById(R.id.button_sao);
		sao.setOnClickListener(this);
		hui=(ImageView) view.findViewById(R.id.button_hui);
		hui.setOnClickListener(this);
		
		liu=(ImageView) view.findViewById(R.id.button_liu);
		liu.setOnClickListener(this);
		zha=(ImageView) view.findViewById(R.id.button_zha);
		zha.setOnClickListener(this);
		zheng=(ImageView) view.findViewById(R.id.button_zheng);
		zheng.setOnClickListener(this);
		ba=(ImageView) view.findViewById(R.id.button_ba);
		ba.setOnClickListener(this);
		gong=(ImageView) view.findViewById(R.id.button_gong);
		gong.setOnClickListener(this);
		more = (ImageView) view.findViewById(R.id.vmore);
		more.setOnClickListener(this);
		
		// 得到控件  
        mPullRefreshListView = (PullToRefreshGridView) view.findViewById(R.id.pull_refresh_grid); 
        mPullRefreshListView.autoRefresh();
       
        dishadapter=new DishAdapter(getActivity(), (ArrayList<Dish>)dishlist);
        
        mPullRefreshListView.setAdapter(dishadapter);  
        mHandler = new Handler();
        mPullRefreshListView  
                .setOnRefreshListener(new OnRefreshListener2<GridView>()  
                {  
  
                     
                    @Override  
                    public void onPullDownToRefresh(  
                            PullToRefreshBase<GridView> refreshView)  
                    {  
                        Log.i("TAG", "onPullDownToRefresh"); // Do work to  
                        mHandler.postDelayed(new Runnable() {
        					@Override
        					public void run() {
        						new GetDataTask().execute();
        						//dishlist.clear();
        					//getTradeItemData(0, STATE_REFRESH);
        						//curPage=0;
        						//dishadapter.notifyDataSetChanged();
        						//mPullRefreshListView.onRefreshComplete();  
        					}
        				}, 2000);
                          
                    }  
                    @Override  
                    public void onPullUpToRefresh(  
                            PullToRefreshBase<GridView> refreshView)  
                    {  
                        Log.i("TAG", "onPullUpToRefresh"); // Do work to refresh  
                                                            // the list here.  
                        mHandler.postDelayed(new Runnable() {
                			@Override
                			public void run() {
                					getTradeItemData(curPage, STATE_MORE);
                					dishadapter.notifyDataSetChanged();
                					mPullRefreshListView.onRefreshComplete(); 
                			}
                		}, 2000); 
                           
                    }  
                });  
		return view;

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
				Log.i("bmob", "pageN:" + page + " limit:" + limit + " actionType:"
						+ actionType);
				BmobQuery<Dish> query = new BmobQuery<Dish>();
				query.order("-updatedAt");
				query.setLimit(limit); // 设置每页多少条数据
				query.setSkip(page * limit);
				query.findObjects(getActivity(),new FindListener<Dish>() {
					
					@Override
					public void onSuccess(List<Dish> arg0) {
						// TODO Auto-generated method stub
						if (arg0.size() > 0) {

							for (Dish dish : arg0) {
								dishlist.add(dish);
							}
							dishadapter.notifyDataSetChanged();
							curPage++;
							//toast("第" + (page + 1) + "页数据加载完成");
						} else {
							toast("没有更多数据了");
						}
					}
					
					@Override
					public void onError(int arg0, String arg1) {
						// TODO Auto-generated method stub
						toast("失败");
						Log.i("info", "arg1");
					}
				});
					
				
						
					

					

				
			}
			public void toast(String toast) {

				Toast.makeText(getActivity(), toast, Toast.LENGTH_SHORT).show();
			}
	 
	  
			private class GetDataTask extends AsyncTask<Void, Void, Void>
			{

				@Override
				protected Void doInBackground(Void... params)
				{
					getTradeItemData(0, STATE_REFRESH);
					curPage=0;
					return null;
				}

				@Override
				protected void onPostExecute(Void result)
				{
					dishadapter.notifyDataSetChanged();
					mPullRefreshListView.onRefreshComplete();
				}
			}
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.button_chao:
			intentFenlei("1");
			break;
		case R.id.button_dun:
			intentFenlei("2");
			break;
		case R.id.button_meng:
			intentFenlei("3");
			break;
		case R.id.button_sao:
			intentFenlei("4");
			break;
		case R.id.button_hui:
			intentFenlei("5");
			break;
			
		case R.id.button_liu:
			intentFenlei("6");
			break;
		case R.id.button_zha:
			intentFenlei("7");
			break;
		case R.id.button_zheng:
			intentFenlei("8");
			break;
		case R.id.button_ba:
			intentFenlei("9");
			break;
		case R.id.button_gong:
			intentFenlei("10");
			break;
		case R.id.vmore:
			Intent it = new Intent();
			it.setClass(getActivity(), SearchActivity.class);
			startActivity(it);
			break;
		default:
			break;
		}
	}
	public void intentFenlei(String s)
	{
		Intent it=new Intent();
		it.putExtra("type", s);
		it.setClass(getActivity(),IndexFenleiActivity.class );
		startActivity(it);
	}
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
}
