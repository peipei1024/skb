package com.js.skb.ui.fragment;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.js.skb.R;
import com.js.skb.adapter.WorkAdapter;
import com.js.skb.bean.Dish;
import com.js.skb.bean.Work;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class WorkFragment extends Fragment{
	
    /** 
     * 上拉刷新的控件 
     */  
    private PullToRefreshListView mPullRefreshListView; 
    private WorkAdapter workadapter;
    private List<Work> worklist=new ArrayList<Work>();
    private Handler mHandler;
	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState)
	    {
	    	View view=inflater.inflate(R.layout.fragment_work, container, false);
	    	mPullRefreshListView = (PullToRefreshListView)view. findViewById(R.id.pull_refresh_list);  
	        //初始化数据  
	        //设置适配器  
	        workadapter=new WorkAdapter(getActivity(), (ArrayList<Work>)worklist);
	        mPullRefreshListView.setAdapter(workadapter);  
	        mPullRefreshListView.autoRefresh();
	        // 设置监听事件  
	        mHandler = new Handler();
	        mPullRefreshListView  
	        .setOnRefreshListener(new OnRefreshListener2<ListView>()  
	        {  
	            @Override  
	            public void onPullDownToRefresh(  
	                    PullToRefreshBase<ListView> refreshView)  
	            {  
	                Log.e("TAG", "onPullDownToRefresh");  
	                //这里写下拉刷新的任务  
	                mHandler.postDelayed(new Runnable() {
    					@Override
    					public void run() {
    						worklist.clear();
    					getTradeItemData(0, STATE_REFRESH);
    						curPage=0;
    						mPullRefreshListView.onRefreshComplete();  
    					}
    				}, 2000);
	            }  

	            @Override  
	            public void onPullUpToRefresh(  
	                    PullToRefreshBase<ListView> refreshView)  
	            {  
	                Log.e("TAG", "onPullUpToRefresh");  
	                mHandler.postDelayed(new Runnable() {
            			@Override
            			public void run() {
            					getTradeItemData(curPage, STATE_MORE);
            				//onLoad();
            					workadapter.notifyDataSetChanged();
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
	 				BmobQuery<Work> query = new BmobQuery<Work>();
	 				query.order("-updatedAt");
	 				query.include("author");
	 				query.setLimit(limit); // 设置每页多少条数据
	 				query.setSkip(page * limit);
	 				query.findObjects(getActivity(),new FindListener<Work>() {
	 					
	 					@Override
	 					public void onSuccess(List<Work> arg0) {
	 						// TODO Auto-generated method stub
	 						Log.w("info", "数据加载成功");
	 						if (arg0.size() > 0) {

	 							// 将本次查询的数据添加到bankCards中
	 							for (Work work : arg0) {
	 								worklist.add(work);
	 							}
	 							workadapter.notifyDataSetChanged();
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
	 					}
	 				});
	 					
	 				
	 						
	 					

	 					

	 				
	 			}
	 			public void toast(String toast) {

	 				Toast.makeText(getActivity(), toast, Toast.LENGTH_SHORT).show();
	 			}
	   
}
