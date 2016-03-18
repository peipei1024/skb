package com.js.skb.adapter;

import java.util.ArrayList;

import com.js.skb.MyApplication;
import com.js.skb.R;
import com.js.skb.bean.Dish;
import com.js.skb.ui.DishActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

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
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.GetListener;

public class CollectionDishAdapter extends BaseAdapter{
	private ArrayList<Dish> footlist;
	private Context mContext;
	private LayoutInflater mInflater;
	public CollectionDishAdapter(Context context,ArrayList<Dish> list) {
		// TODO Auto-generated constructor stub
		mContext=context;
		footlist=list;
		mInflater=LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return footlist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final FootHolder holder;
		if(convertView==null)
		{
			convertView=mInflater.inflate(R.layout.layout_foot, null);
			holder=new FootHolder();
			holder.footitem = (LinearLayout) convertView.findViewById(R.id.footitem);
			holder.dishpic=(ImageView) convertView.findViewById(R.id.dishpic);
			holder.dishname=(TextView) convertView.findViewById(R.id.dishname);
			holder.dishsteps=(TextView) convertView.findViewById(R.id.dishsteps);
			holder.dishtime=(TextView) convertView.findViewById(R.id.dishtime);
			holder.dishcollection=(TextView) convertView.findViewById(R.id.dishcollection);
			convertView.setTag(holder);
		}else {
			holder=(FootHolder) convertView.getTag();
		}
		final Dish dish = footlist.get(position);
		
		holder.dishname.setText(dish.getDishName());
		if(dish.getDishTime()!=null)
		{
			holder.dishtime.setText("”√ ±"+dish.getDishTime());
		}
		if(dish.getLove()!=null)
		{
			holder.dishcollection.setText(" ’≤ÿ"+dish.getLove());
		}
		if(dish.getDishPic()!=null)
		{
			dish.getDishPic().getFileUrl(mContext);
			
			ImageLoader.getInstance().displayImage(dish.getDishPic().getFileUrl(mContext), holder.dishpic,
					MyApplication.getInstance().getOptions(R.drawable.dishsxamplie1),
					new SimpleImageLoadingListener() {

				@Override
				public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
					// TODO Auto-generated method stub
					super.onLoadingComplete(imageUri, view, loadedImage);
				}

			});
		}
		
		holder.footitem.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it = new Intent();
				it.setClass(mContext, DishActivity.class);
				it.putExtra("entityid", dish.getObjectId());
				mContext.startActivity(it);
			}
		});
		return convertView;
	}
}
