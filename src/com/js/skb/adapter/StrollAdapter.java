package com.js.skb.adapter;

import java.util.ArrayList;

import com.js.skb.MyApplication;
import com.js.skb.R;
import com.js.skb.bean.Dish;
import com.js.skb.bean.Stroll;
import com.js.skb.bean.Work;
import com.js.skb.ui.DishActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.peixuze.ui.CircularImage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class StrollAdapter extends BaseAdapter {

	private ArrayList<Stroll> strolllist = null;
	private LayoutInflater mInflater = null;
	private Context mContext;

	public StrollAdapter(Context context, ArrayList<Stroll> list) {
		// TODO Auto-generated constructor stub
		mContext = context;
		this.strolllist = list;
		mInflater = LayoutInflater.from(context);

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return strolllist.size();
	}

	// 刷新列表中的数据
	public void refresh(ArrayList<Stroll> list) {
		strolllist = list;
		notifyDataSetChanged();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return strolllist.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		StrollHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.layout_stroll, null);
			holder = new StrollHolder();
			holder.text = (TextView) convertView.findViewById(R.id.strolltext);
			holder.image1 = (CircularImage) convertView.findViewById(R.id.image1);
			holder.image2 = (CircularImage) convertView.findViewById(R.id.image2);
			holder.image3 = (CircularImage) convertView.findViewById(R.id.image3);
			convertView.setTag(holder);
		} else {
			holder = (StrollHolder) convertView.getTag();
		}
		final Stroll entity = strolllist.get(position);
		Log.i("stroll", entity.toString());

		holder.text.setText(entity.getText());
		final Dish dish1 = entity.getDish1();
		String dishUrl1 = null;
		if (dish1.getDishPic() != null) {
			dishUrl1 = dish1.getDishPic().getFileUrl(mContext);
		}

		ImageLoader.getInstance().displayImage(dishUrl1, holder.image1,
				MyApplication.getInstance().getOptions(R.drawable.defaultdishpic), new SimpleImageLoadingListener() {

					@Override
					public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
						// TODO Auto-generated method stub
						super.onLoadingComplete(imageUri, view, loadedImage);
					}

				});
		holder.image1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it = new Intent();
				it.setClass(mContext, DishActivity.class);
				it.putExtra("entityid", dish1.getObjectId());
				mContext.startActivity(it);
			}
		});
		final Dish dish2 = entity.getDish2();
		String dishUrl2 = null;
		if (dish2.getDishPic() != null) {
			dishUrl2 = dish2.getDishPic().getFileUrl(mContext);
		}

		ImageLoader.getInstance().displayImage(dishUrl2, holder.image2,
				MyApplication.getInstance().getOptions(R.drawable.defaultdishpic), new SimpleImageLoadingListener() {

					@Override
					public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
						// TODO Auto-generated method stub
						super.onLoadingComplete(imageUri, view, loadedImage);
					}

				});
		holder.image2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it = new Intent();
				it.setClass(mContext, DishActivity.class);
				it.putExtra("entityid", dish2.getObjectId());
				mContext.startActivity(it);
			}
		});
		final Dish dish3 = entity.getDish1();
		String dishUrl3 = null;
		if (dish3.getDishPic() != null) {
			dishUrl3 = dish3.getDishPic().getFileUrl(mContext);
		}

		ImageLoader.getInstance().displayImage(dishUrl3, holder.image3,
				MyApplication.getInstance().getOptions(R.drawable.defaultdishpic), new SimpleImageLoadingListener() {

					@Override
					public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
						// TODO Auto-generated method stub
						super.onLoadingComplete(imageUri, view, loadedImage);
					}

				});
		holder.image3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it = new Intent();
				it.setClass(mContext, DishActivity.class);
				it.putExtra("entityid", dish3.getObjectId());
				mContext.startActivity(it);
			}
		});
		/*
		 * convertView.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub Intent it=new Intent(); it.setClass(mContext,
		 * DishActivity.class); it.putExtra("entityid", entity.getObjectId());
		 * mContext.startActivity(it); } });
		 */

		return convertView;
	}

}
