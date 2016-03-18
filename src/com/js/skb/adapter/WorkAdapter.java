package com.js.skb.adapter;

import java.util.ArrayList;
import java.util.List;

import com.js.skb.MyApplication;
import com.js.skb.R;
import com.js.skb.bean.User;
import com.js.skb.bean.Work;
import com.js.skb.db.WorkPraiseDBHelp;
import com.js.skb.ui.DishActivity;
import com.js.skb.ui.LandActivity;
import com.js.skb.ui.PopupWindows;
import com.js.skb.ui.UpCommentActivity;
import com.js.skb.ui.WorkAllActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.peixuze.ui.CircularImage;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class WorkAdapter extends BaseAdapter {
	private Context mContext;
	private LayoutInflater mInflater = null;
	private ArrayList<Work> worklist = null; // 物品列表
	boolean workfav = false;
	boolean worklov = true;

	public WorkAdapter(Context context, ArrayList<Work> list) {
		// TODO Auto-generated constructor stub
		mContext = context;
		this.worklist = list;
		mInflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return worklist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return worklist.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	// 刷新列表中的数据
	public void refresh(ArrayList<Work> list) {
		worklist = list;
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final WorkHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.layout_work, null);
			holder = new WorkHolder();
			holder.head = (CircularImage) convertView.findViewById(R.id.head);
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.time = (TextView) convertView.findViewById(R.id.time);
			holder.content = (TextView) convertView.findViewById(R.id.content);
			holder.workpic = (ImageView) convertView.findViewById(R.id.idworkpic);
			holder.workname = (TextView) convertView.findViewById(R.id.workname);
			holder.collectionwork = (ImageView) convertView.findViewById(R.id.collectionwork);
			holder.iconcomment = (ImageView) convertView.findViewById(R.id.iconcomment);
			holder.share = (ImageView) convertView.findViewById(R.id.share);
			holder.iconpraise = (ImageView) convertView.findViewById(R.id.iconpraise);
			holder.textpraise = (TextView) convertView.findViewById(R.id.textpraise);
			convertView.setTag(holder);
		} else {
			holder = (WorkHolder) convertView.getTag();
		}

		final Work work = worklist.get(position);
		User user = work.getAuthor();
		String avatarUrl = null;
		if (user.getAvatar() != null) {
			avatarUrl = user.getAvatar().getFileUrl(mContext);
		}
		ImageLoader.getInstance().displayImage(avatarUrl, holder.head,
				MyApplication.getInstance().getOptions(R.drawable.head), new SimpleImageLoadingListener() {

					@Override
					public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
						// TODO Auto-generated method stub
						super.onLoadingComplete(imageUri, view, loadedImage);
					}

				});
		holder.name.setText(user.getUsername());
		holder.time.setText(work.getCreatedAt());
		holder.content.setText(work.getWorkcontent());
		ImageLoader.getInstance().displayImage(work.getWorkpic().getFileUrl(mContext), holder.workpic,
				MyApplication.getInstance().getOptions(R.drawable.defaultdishpic), new SimpleImageLoadingListener() {

					@Override
					public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
						// TODO Auto-generated method stub
						super.onLoadingComplete(imageUri, view, loadedImage);
					}

				});
		holder.workname.setText(work.getWorkname());
		queryFavo(holder.collectionwork,work);
		queryLove(holder.iconpraise,work);
		holder.textpraise.setText(String.valueOf(work.getLove()));
		holder.workpic.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it = new Intent();
				it.setClass(mContext, WorkAllActivity.class);
				it.putExtra("workid", work.getObjectId());
				mContext.startActivity(it);
			}
		});
		holder.collectionwork.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				addCollection(holder.collectionwork, work);
			}
		});
		holder.share.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new PopupWindows(mContext, v);
			}
		});
		holder.iconpraise.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				addLove(work,holder.iconpraise,holder.textpraise);
			}
		});
		holder.iconcomment.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*CommentDialog commentdialog = new CommentDialog(mContext);
				Window win = commentdialog.getWindow();
				
				commentdialog.setCanceledOnTouchOutside(true);//设置点击Dialog外部任意区域关闭Dialog
				commentdialog.show();*/
				/*AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();  
				alertDialog.show();  
				Window window = alertDialog.getWindow();  
				window.setContentView(R.layout.activity_comment);  
				TextView tv_title = (TextView) window.findViewById(R.id.tv_dialog_title);  
				tv_title.setText("评论");  */
				Intent it = new Intent();
				it.setClass(mContext, UpCommentActivity.class);
				it.putExtra("workid", work.getObjectId());
				mContext.startActivity(it);
			}
		});
		
		return convertView;
	}
	private void addLove(final Work w,final ImageView v,final TextView tv) {
		User user = BmobUser.getCurrentUser(mContext, User.class);
		if (user != null) {
			
			if(worklov)
			{
				int oldup = Integer.valueOf(tv.getText().toString().trim());
				final int nowup = oldup+1;
				
				w.setLove(nowup);
				w.update(mContext, w.getObjectId(), new UpdateListener() {
					
					
					@Override
					public void onSuccess() {
						// TODO Auto-generated method stub
						WorkPraiseDBHelp fdbh = new WorkPraiseDBHelp(mContext);
						SQLiteDatabase db = null;
						db = fdbh.getWritableDatabase();
						User user = BmobUser.getCurrentUser(mContext, User.class);
						ContentValues cvOfLiHua = new ContentValues();
						cvOfLiHua.put("userid", user.getObjectId());
						cvOfLiHua.put("workobjectid",w.getObjectId());
						db.insert("workpraise", null, cvOfLiHua);
						v.setImageResource(R.drawable.button_praise_choose);
						worklov = false;
						Toast.makeText(mContext, "点赞成功", Toast.LENGTH_SHORT).show();
						tv.setText(nowup+"");
					}
					
					@Override
					public void onFailure(int arg0, String arg1) {
						// TODO Auto-generated method stub
						
					}
				});
				
			}else {
				Toast.makeText(mContext, "已经赞过了", Toast.LENGTH_SHORT).show();
			}
			
		} else {
			Toast.makeText(mContext, "请登录！", Toast.LENGTH_SHORT).show();
			Intent it = new Intent();
			it.setClass(mContext, LandActivity.class);
			mContext.startActivity(it);
		}
	}
	private void queryLove(ImageView v,Work mwork) {
		User user = BmobUser.getCurrentUser(mContext, User.class);
		if(user!=null)
		{
			WorkPraiseDBHelp fdbh = new WorkPraiseDBHelp(mContext);
			SQLiteDatabase db = null;
			db = fdbh.getReadableDatabase();
			Cursor c3 = db.rawQuery("select * from workpraise", null);
			if (c3 != null) {
				String[] cols = c3.getColumnNames();
				while (c3.moveToNext()) {
					String userid = c3.getString(c3.getColumnIndex("userid"));
					String workobjectid = c3.getString(c3.getColumnIndex("workobjectid"));
					if (workobjectid.equals(mwork.getObjectId())) {
						if (user.getObjectId().equals(userid)) {
							v.setImageResource(R.drawable.button_praise_choose);
							worklov = false;
						}
						
					}else{
						v.setImageResource(R.drawable.button_praise_normol);
					}
					
				}
				c3.close();
			}
			db.close();
		}
		
	}
	private void addCollection(final ImageView v,Work work) {
		User user = BmobUser.getCurrentUser(mContext, User.class);
		if (user != null) 
		{
			BmobRelation relation = new BmobRelation();
			if(workfav){
				relation.remove(work);
				user.setWorkfavorite(relation);
				user.update(mContext, new UpdateListener() {
					@Override
					public void onSuccess() {
						// TODO Auto-generated method stub
						Log.w("info", "取消收藏成功");
						Toast.makeText(mContext, "取消收藏", Toast.LENGTH_SHORT).show();
						v.setImageResource(R.drawable.circle_ispass);
						workfav = false;
					}

					@Override
					public void onFailure(int arg0, String arg1) {
						// TODO Auto-generated method stub

					}
				});
			}else{
				
				relation.add(work);
				user.setWorkfavorite(relation);
				user.update(mContext, new UpdateListener() {
						@Override
						public void onSuccess() 
						{
							// TODO Auto-generated method stub
							Log.w("info", "收藏成功");
							Toast.makeText(mContext, "收藏成功", Toast.LENGTH_SHORT).show();
							v.setImageResource(R.drawable.collection_work_choose);
							workfav = true;
						}

						@Override
						public void onFailure(int arg0, String arg1) 
						{
								
						}});
			}
			
					

		}else{
			Toast.makeText(mContext,"请登录！",Toast.LENGTH_SHORT).show();
			Intent it = new Intent();
			it.setClass(mContext,LandActivity.class);
			mContext.startActivity(it);
			}
	}
	private void queryFavo(final ImageView v,final Work mwork) {
		User user = BmobUser.getCurrentUser(mContext, User.class);
		if (user != null) {
			BmobQuery<Work> query = new BmobQuery<Work>();

			// likes是Post表中的字段，用来存储所有喜欢该帖子的用户
			query.addWhereRelatedTo("workfavorite", new BmobPointer(user));
			query.findObjects(mContext, new FindListener<Work>() {

				@Override
				public void onSuccess(List<Work> object) {
					// TODO Auto-generated method stub
					
					Log.i("life", "查询个数：" + object.size());
					int a = object.size();
					for (int i = 0; i < a; i++)
					{
						Work work = object.get(i);
						if (work.getObjectId().equals(mwork.getObjectId())) {
							workfav = true;
						} else {
							workfav = false;
						}
					}

					if (workfav) {
						Log.i("life", "查询个数：" + "true");
						v.setImageResource(R.drawable.collection_work_choose);
					} else {
						Log.i("life", "查询个数：" + "false");
						v.setImageResource(R.drawable.circle_ispass);
					}
				}

				@Override
				public void onError(int code, String msg) {
					// TODO Auto-generated method stub
					Log.i("life", "查询失败：" + code + "-" + msg);
				}
			});
		}

	}

}
