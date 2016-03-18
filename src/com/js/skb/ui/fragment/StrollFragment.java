package com.js.skb.ui.fragment;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.js.skb.R;
import com.js.skb.adapter.StrollAdapter;
import com.js.skb.bean.Dish;
import com.js.skb.bean.Stroll;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.peixuze.ui.AdDomain;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class StrollFragment extends Fragment{
	public static String IMAGE_CACHE_PATH = "imageloader/Cache"; 

	private ViewPager adViewPager;
	private List<ImageView> imageViews;

	private List<View> dots; 
	private List<View> dotList;

	private TextView tv_date;
	private TextView tv_title;
	private TextView tv_topic_from;
	private TextView tv_topic;
	private int currentItem = 0;
	private View dot0;
	private View dot1;
	private View dot2;
	private View dot3;
	private View dot4;

	private ScheduledExecutorService scheduledExecutorService;

	private ImageLoader mImageLoader;
	private DisplayImageOptions options;

	private List<AdDomain> adList;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			adViewPager.setCurrentItem(currentItem);
		};
	};
	private LinkedList<String> mListItems;  
	private Handler mHandler1;
	private PullToRefreshListView mPullRefreshListView;   
   // private ArrayAdapter<String> mAdapter;  
    private StrollAdapter dishadapter;
    private List<Stroll> dishlist = new ArrayList<Stroll>();
	
	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState)
	    {
	        View view=inflater.inflate(R.layout.fragment_stroll, container, false);
	        initImageLoader();
	     // 得到控件  
	        mPullRefreshListView = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);  
	      
	        dishadapter=new StrollAdapter(getActivity(), (ArrayList<Stroll>)dishlist);
	        
	        
	        mPullRefreshListView.setAdapter(dishadapter); 
	        mPullRefreshListView.autoRefresh();
	        mHandler1 = new Handler();
	        mPullRefreshListView  
	        .setOnRefreshListener(new OnRefreshListener2<ListView>()  
	        {  
	            @Override  
	            public void onPullDownToRefresh(  
	                    PullToRefreshBase<ListView> refreshView)  
	            {    
	                        Log.e("TAG", "onPullDownToRefresh"); // Do work to  
	                        mHandler1.postDelayed(new Runnable() {
	        					@Override
	        					public void run() {
	        						dishlist.clear();
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
	                        Log.e("TAG", "onPullUpToRefresh"); // Do work to refresh  
	                                                            // the list here.  
	                        mHandler1.postDelayed(new Runnable() {
	                			@Override
	                			public void run() {
	                					getTradeItemData(curPage, STATE_MORE);
	                				//onLoad();
	                					dishadapter.notifyDataSetChanged();
	        	                        mPullRefreshListView.onRefreshComplete();  
	                			}
	                		}, 2000); 
	                          
	                    }  
	                });  
	        
	        
			mImageLoader = ImageLoader.getInstance();
			options = new DisplayImageOptions.Builder()
					.showStubImage(R.drawable.excample)
					.showImageForEmptyUri(R.drawable.excample)
					.showImageOnFail(R.drawable.excample)
					.cacheInMemory(true).cacheOnDisc(true)
					.bitmapConfig(Bitmap.Config.RGB_565)
					.imageScaleType(ImageScaleType.EXACTLY).build();

			adList = getBannerAd();

			imageViews = new ArrayList<ImageView>();

			dots = new ArrayList<View>();
			dotList = new ArrayList<View>();
			dot0 = view.findViewById(R.id.v_dot0);
			dot1 = view.findViewById(R.id.v_dot1);
			dot2 = view.findViewById(R.id.v_dot2);
			dot3 = view.findViewById(R.id.v_dot3);
			dot4 = view.findViewById(R.id.v_dot4);
			dots.add(dot0);
			dots.add(dot1);
			dots.add(dot2);
			dots.add(dot3);
			dots.add(dot4);
			
			tv_date =(TextView)view. findViewById(R.id.tv_date);
			tv_title = (TextView) view.findViewById(R.id.tv_title);
			tv_topic_from = (TextView) view.findViewById(R.id.tv_topic_from);
			tv_topic = (TextView)view. findViewById(R.id.tv_topic);

			adViewPager = (ViewPager)view. findViewById(R.id.vp);
			adViewPager.setAdapter(new MyAdapter());
			adViewPager.setOnPageChangeListener(new MyPageChangeListener());
			addDynamicView();

			startAd();
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
	 				BmobQuery<Stroll> query = new BmobQuery<Stroll>();
	 				query.order("-updatedAt");
	 				query.setLimit(limit); // 设置每页多少条数据
	 				query.setSkip(page * limit);
	 				query.include("dish1,dish2,dish3");
	 				query.findObjects(getActivity(),new FindListener<Stroll>() {
	 					
	 					@Override
	 					public void onSuccess(List<Stroll> arg0) {
	 						// TODO Auto-generated method stub
	 						if (arg0.size() > 0) {

	 							// 将本次查询的数据添加到bankCards中
	 							for (Stroll dish : arg0) {
	 								dishlist.add(dish);
	 							}
	 							dishlist = arg0;
	 							// 通知Adapter数据更新
	 							dishadapter.refresh((ArrayList<Stroll>) dishlist);
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
	 						toast("shibai");
	 						Log.w("info", "arg1");
	 					}
	 				});
	 					
	 				
	 						
	 					

	 					

	 			}
			public void toast(String toast) {

				Toast.makeText(getActivity(), toast, Toast.LENGTH_SHORT).show();
			}
	    private void initImageLoader() {
			File cacheDir = com.nostra13.universalimageloader.utils.StorageUtils
					.getOwnCacheDirectory(getActivity(),
							IMAGE_CACHE_PATH);

			DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
					.cacheInMemory(true).cacheOnDisc(true).build();

			ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
					getActivity()).defaultDisplayImageOptions(defaultOptions)
					.memoryCache(new LruMemoryCache(12 * 1024 * 1024))
					.memoryCacheSize(12 * 1024 * 1024)
					.discCacheSize(32 * 1024 * 1024).discCacheFileCount(100)
					.discCache(new UnlimitedDiscCache(cacheDir))
					.threadPriority(Thread.NORM_PRIORITY - 2)
					.tasksProcessingOrder(QueueProcessingType.LIFO).build();

			ImageLoader.getInstance().init(config);
		}
	   

	    private void addDynamicView() {
			// 动态添加图片和下面指示的圆点
			// 初始化图片资源
			for (int i = 0; i < adList.size(); i++) {
				ImageView imageView = new ImageView(getActivity());
				// 异步加载图片
				mImageLoader.displayImage(adList.get(i).getImgUrl(), imageView,
						options);
				imageView.setScaleType(ScaleType.CENTER_CROP);
				imageViews.add(imageView);
				dots.get(i).setVisibility(View.VISIBLE);
				dotList.add(dots.get(i));
			}
		}
	   

	    

	    private void startAd() {
			scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
			// 当Activity显示出来后，每两秒切换一次图片显示
			scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 2,
					TimeUnit.SECONDS);
		}

		private class ScrollTask implements Runnable {

			@Override
			public void run() {
				synchronized (adViewPager) {
					currentItem = (currentItem + 1) % imageViews.size();
					handler.obtainMessage().sendToTarget();
				}
			}
		}
		 @Override
		    public void onStop()
		    {
		        // TODO Auto-generated method stub
		        super.onStop();
		        scheduledExecutorService.shutdown();
		        System.out.println("ExampleFragment--onStop");
		    }
			// // 当Activity不可见的时候停止切换
			
		
		private class MyPageChangeListener implements OnPageChangeListener {

			private int oldPosition = 0;

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageSelected(int position) {
				currentItem = position;
				AdDomain adDomain = adList.get(position);
				tv_title.setText(adDomain.getTitle()); // 设置标题
				tv_date.setText(adDomain.getDate());
				tv_topic_from.setText(adDomain.getTopicFrom());
				tv_topic.setText(adDomain.getTopic());
				dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
				dots.get(position).setBackgroundResource(R.drawable.dot_focused);
				oldPosition = position;
			}
		}

		private class MyAdapter extends PagerAdapter {

			@Override
			public int getCount() {
				return adList.size();
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				ImageView iv = imageViews.get(position);
				((ViewPager) container).addView(iv);
				final AdDomain adDomain = adList.get(position);
				// 在这个方法里面设置图片的点击事件
				iv.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// 处理跳转逻辑
					}
				});
				return iv;
			}

			@Override
			public void destroyItem(View arg0, int arg1, Object arg2) {
				((ViewPager) arg0).removeView((View) arg2);
			}

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}

			@Override
			public void restoreState(Parcelable arg0, ClassLoader arg1) {

			}

			@Override
			public Parcelable saveState() {
				return null;
			}

			@Override
			public void startUpdate(View arg0) {

			}

			@Override
			public void finishUpdate(View arg0) {

			}

		}

		/**
		 * 轮播广播模拟数据
		 * 
		 * @return
		 */
		public static List<AdDomain> getBannerAd() {
			List<AdDomain> adList = new ArrayList<AdDomain>();

			AdDomain adDomain = new AdDomain();
			adDomain.setImgUrl("http://file.bmob.cn/M02/0C/FA/oYYBAFYbt9CAJ10oAADl4equ9QA334.jpg");
			adDomain.setAd(false);
			adList.add(adDomain);

			AdDomain adDomain2 = new AdDomain();
			
			adDomain2
					.setImgUrl("http://file.bmob.cn/M02/0D/29/oYYBAFYbwGGAfDbBAAC8deQf08Y980.jpg");
			adDomain2.setAd(false);
			adList.add(adDomain2);

			AdDomain adDomain3 = new AdDomain();
			
			adDomain3
					.setImgUrl("http://file.bmob.cn/M02/0D/5E/oYYBAFYbyjeAeE9WAADPqVqkWKo209.jpg");
			adDomain3.setAd(false);
			adList.add(adDomain3);


			AdDomain adDomain4 = new AdDomain();
			
			adDomain4
					.setImgUrl("http://file.bmob.cn/M02/0C/F1/oYYBAFYbtkSAGqRWAADwfLDuUgw377.jpg");
			adDomain4.setAd(false);
			adList.add(adDomain3);

			AdDomain adDomain5 = new AdDomain();
			
			adDomain5
					.setImgUrl("http://file.bmob.cn/M02/0C/FE/oYYBAFYbuJGAPvqDAADEAq8Hsnw014.jpg");
			adDomain5.setAd(false);
			adList.add(adDomain3);

			

			return adList;
		}
}
