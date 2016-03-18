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
     * ����ˢ�µĿؼ� 
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
	        //��ʼ������  
	        //����������  
	        workadapter=new WorkAdapter(getActivity(), (ArrayList<Work>)worklist);
	        mPullRefreshListView.setAdapter(workadapter);  
	        mPullRefreshListView.autoRefresh();
	        // ���ü����¼�  
	        mHandler = new Handler();
	        mPullRefreshListView  
	        .setOnRefreshListener(new OnRefreshListener2<ListView>()  
	        {  
	            @Override  
	            public void onPullDownToRefresh(  
	                    PullToRefreshBase<ListView> refreshView)  
	            {  
	                Log.e("TAG", "onPullDownToRefresh");  
	                //����д����ˢ�µ�����  
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
	 				Log.i("bmob", "pageN:" + page + " limit:" + limit + " actionType:"
	 						+ actionType);
	 				BmobQuery<Work> query = new BmobQuery<Work>();
	 				query.order("-updatedAt");
	 				query.include("author");
	 				query.setLimit(limit); // ����ÿҳ����������
	 				query.setSkip(page * limit);
	 				query.findObjects(getActivity(),new FindListener<Work>() {
	 					
	 					@Override
	 					public void onSuccess(List<Work> arg0) {
	 						// TODO Auto-generated method stub
	 						Log.w("info", "���ݼ��سɹ�");
	 						if (arg0.size() > 0) {

	 							// �����β�ѯ��������ӵ�bankCards��
	 							for (Work work : arg0) {
	 								worklist.add(work);
	 							}
	 							workadapter.notifyDataSetChanged();
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

	 				Toast.makeText(getActivity(), toast, Toast.LENGTH_SHORT).show();
	 			}
	   
}
