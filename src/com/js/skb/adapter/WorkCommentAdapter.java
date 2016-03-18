package com.js.skb.adapter;

import java.util.ArrayList;

import com.js.skb.MyApplication;
import com.js.skb.R;
import com.js.skb.bean.Comment;
import com.js.skb.bean.User;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.peixuze.ui.CircularImage;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class WorkCommentAdapter extends BaseAdapter{

	private ArrayList<Comment> mList = null;
	private Context mContext;
	private LayoutInflater mInflater =null;
	public WorkCommentAdapter(Context context,ArrayList<Comment> list) {
		// TODO Auto-generated constructor stub
		mList = list;
		mContext = context;
		mInflater = LayoutInflater.from(mContext);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return getItem(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		WorkCommentHolder holder;
		if(convertView==null)
		{
			convertView = mInflater.inflate(R.layout.layout_comment, null);
			holder = new WorkCommentHolder();
			holder.mHead = (CircularImage) convertView.findViewById(R.id.head);
			holder.mName = (TextView) convertView.findViewById(R.id.name);
			holder.mTime = (TextView) convertView.findViewById(R.id.time);
			holder.mComment = (TextView) convertView.findViewById(R.id.comment);
			convertView.setTag(holder);
		}else{
			holder = (WorkCommentHolder) convertView.getTag();
		}
		final Comment cm = mList.get(position);
		User user = cm.getAuthor();
		holder.mName.setText(user.getUsername());
		String avatarUrl = null;
		if (user.getAvatar() != null) {
			avatarUrl = user.getAvatar().getFileUrl(mContext);
		}
		ImageLoader.getInstance().displayImage(avatarUrl, holder.mHead,
				MyApplication.getInstance().getOptions(R.drawable.head), new SimpleImageLoadingListener() {

					@Override
					public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
						// TODO Auto-generated method stub
						super.onLoadingComplete(imageUri, view, loadedImage);
					}

				});
		holder.mTime.setText(cm.getCreatedAt());
		holder.mComment.setText(cm.getComment());
		
		return convertView;
	}

}
