package com.js.skb.ui;



import java.util.List;

import com.js.skb.R;
import com.js.skb.bean.Comment;
import com.js.skb.bean.User;
import com.js.skb.bean.Work;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class UpCommentActivity extends Activity implements OnClickListener {
	private EditText mComment;
	private TextView mPublish;
	private ImageView mBack;
	String workid;
	private User user;
	boolean booleansave = false;
	boolean booleanrelation = false;
	Work mwork = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment);
		init();
	}

	private void init() {
		user = BmobUser.getCurrentUser(UpCommentActivity.this, User.class);
		Intent it = getIntent();
		workid = it.getStringExtra("workid");
		mComment = (EditText) findViewById(R.id.commnet);
		mBack = (ImageView) findViewById(R.id.back);
		mPublish = (TextView) findViewById(R.id.publish);

		mBack.setOnClickListener(this);
		mPublish.setOnClickListener(this);
		queryWork();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:
				finish();
			break;

		case R.id.publish:
			publishComment();
			addRelation();
			break;

		}
	}
	private void queryWork()
	{
		BmobQuery<Work> query = new BmobQuery<Work>();
		query.getObject(UpCommentActivity.this, workid, new GetListener<Work>() {
			
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Log.w("info", "查询work失败");
			}
			
			@Override
			public void onSuccess(Work arg0) {
				// TODO Auto-generated method stub
				Log.w("info", "查询work成功");
				mwork = arg0;
			}
		});
	}
	private void addRelation()
	{
		BmobQuery<Comment> query = new BmobQuery<Comment>();
		query.addWhereEqualTo("comment", mComment.getText().toString().trim());
		query.findObjects(UpCommentActivity.this, new FindListener<Comment>() {
			
			@Override
			public void onSuccess(List<Comment> arg0) {
				// TODO Auto-generated method stub
				Log.w("info", "查询评论成功");
				BmobRelation relation = new BmobRelation();
				int a = arg0.size();
				for(int b =0;b<a;b++)
				{
					Comment cm = arg0.get(b);
					relation.add(cm);
					mwork.setComment(relation);
					mwork.update(UpCommentActivity.this,new UpdateListener() {
						
						
						@Override
						public void onSuccess() {
							// TODO Auto-generated method stub
							Log.w("info", "关联成功");
							Toast.makeText(UpCommentActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
						}
						
						@Override
						public void onFailure(int arg0, String arg1) {
							// TODO Auto-generated method stub
							Log.w("info", "关联失败");
							
						}
					});
				}
			}
			
			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Log.w("info", "查询评论失败");
				Toast.makeText(UpCommentActivity.this, "评论失败", Toast.LENGTH_SHORT).show();
			}
		});
		
	}
	
	private void publishComment()
	{
		if(user!=null)
		{
			Comment cm = new Comment();
			cm.setAuthor(user);
			cm.setComment(mComment.getText().toString().trim());
			cm.save(UpCommentActivity.this, new SaveListener() {
				
				@Override
				public void onSuccess() {
					// TODO Auto-generated method stub
					booleansave = true;
					Log.w("info", "上传评论成功");
				}
				
				@Override
				public void onFailure(int arg0, String arg1) {
					// TODO Auto-generated method stub
					Log.w("info", "上传评论失败");
					Toast.makeText(UpCommentActivity.this, "评论失败", Toast.LENGTH_SHORT).show();
				}
			});
		}else {
			Toast.makeText(UpCommentActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
		}
		
	}
}
