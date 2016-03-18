package com.js.skb.ui;

import java.util.ArrayList;
import java.util.List;

import com.js.skb.MyApplication;
import com.js.skb.R;
import com.js.skb.adapter.WorkCommentAdapter;
import com.js.skb.bean.Comment;
import com.js.skb.bean.User;
import com.js.skb.bean.Work;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.peixuze.ui.CircularImage;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;

public class WorkAllActivity extends Activity implements OnClickListener{
	private ImageView mBack,mWorkPic;
	
	private TextView mName,mTime,mContent,mWorkName;
	private CircularImage mHead;
	private ImageView collectionwork,iconcomment,share,iconpraise;
	private TextView textpraise;
	
	private ListView listview;
	private WorkCommentAdapter mAdapter;
	private List<Comment> mlist = new ArrayList<Comment>();
	String workid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_workall);
		Intent it = getIntent();
		workid = it.getStringExtra("workid");
		initData();
		init();
		queryWork(workid);
	}
	private void init(){
		
		listview = (ListView) findViewById(R.id.listview);
		mAdapter = new WorkCommentAdapter(WorkAllActivity.this, (ArrayList<Comment>)mlist);
		listview.setAdapter(mAdapter);
		mHead= (CircularImage) findViewById(R.id.idhead);
		mWorkPic = (ImageView) findViewById(R.id.idworkpic);
		mName= (TextView) findViewById(R.id.name);
		mTime = (TextView) findViewById(R.id.time);
		mContent  = (TextView) findViewById(R.id.content);
		mWorkName = (TextView) findViewById(R.id.workname);
		mBack = (ImageView) findViewById(R.id.back);
		mBack.setOnClickListener(this);
		Log.w("ifno", workid);		
		
		
	}
	private void queryWork(String id)
	{
		Log.e("infop", "不执行");
		BmobQuery<Work> query1 = new BmobQuery<Work>();
		query1.include("author");
		Log.e("infop", "不执行");
		query1.getObject(WorkAllActivity.this, id, new GetListener<Work>() {
			
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Log.e("infop", "不执行");
				toast("加载失败");
			}
			
			@Override
			public void onSuccess(Work arg0) {
				// TODO Auto-generated method stub
				Log.e("infop", "不执行"+arg0.toString());
					User user = arg0.getAuthor();
						BmobFile avatarUrl=user.getAvatar();
						Log.e("infop", "不执行"+arg0.getAuthor().toString());
						
							/*ImageLoader.getInstance().displayImage(avatarUrl.getFileUrl(WorkAllActivity.this), mHead,
									MyApplication.getInstance().getOptions(R.drawable.head), new SimpleImageLoadingListener() {

										@Override
										public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
											// TODO Auto-generated method stub
											super.onLoadingComplete(imageUri, view, loadedImage);
										}

									});*/
						
						mName.setText(user.getUsername());
					
					mTime.setText(arg0.getCreatedAt());
					mContent.setText(arg0.getWorkcontent());
					if(arg0.getWorkpic().getFileUrl(WorkAllActivity.this)!=null)
					{
						ImageLoader.getInstance().displayImage(arg0.getWorkpic().getFileUrl(WorkAllActivity.this), mWorkPic,
								MyApplication.getInstance().getOptions(R.drawable.defaultdishpic),
								new SimpleImageLoadingListener() {

							@Override
							public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
								// TODO Auto-generated method stub
								super.onLoadingComplete(imageUri, view, loadedImage);
							}

						});
					}
					mWorkName.setText(arg0.getWorkname());
				}
				
			
		});
	}
	private void initData()
	{
		BmobQuery<Comment> query = new BmobQuery<Comment>();
		Work work = new Work();
		work.setObjectId(workid);
		//likes是Post表中的字段，用来存储所有喜欢该帖子的用户
		query.include("author");
		query.addWhereRelatedTo("comment", new BmobPointer(work));    
		query.findObjects(this, new FindListener<Comment>() {

		    @Override
		    public void onSuccess(List<Comment> object) {
		        // TODO Auto-generated method stub
		        Log.i("life", "查询个数："+object.size());
		        if (object.size() > 0) {

						// 将本次查询的数据添加到bankCards中
						for (Comment cm : object) {
							mlist.add(cm);
						}
						mlist = object;
						// 通知Adapter数据更新
						mAdapter.notifyDataSetChanged();
		    }}

		    @Override
		    public void onError(int code, String msg) {
		        // TODO Auto-generated method stub
		        Log.i("life", "查询失败："+code+"-"+msg);
		    }
		});
	}
	public void toast(String toast) {

		Toast.makeText(WorkAllActivity.this, toast, Toast.LENGTH_SHORT).show();
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
