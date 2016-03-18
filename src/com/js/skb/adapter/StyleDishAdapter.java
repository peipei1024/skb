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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class StyleDishAdapter extends BaseAdapter{
	private ArrayList<Dish> dishlist = null;
	private LayoutInflater mInflater = null;
	private Context mContext;
	public StyleDishAdapter(Context context,ArrayList<Dish> dish) {
		// TODO Auto-generated consructor stub
		mContext=context;
		dishlist=dish;
		mInflater=LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dishlist.size();
	}
	// ˢ���б��е�����
		public void refresh(ArrayList<Dish> list) {
			dishlist = list;
			notifyDataSetChanged();
		}
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return dishlist.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		StyleDishHolder holder;
		if(convertView==null)
		{
			convertView=mInflater.inflate(R.layout.layout_style_gridview_dish, null);
			holder=new StyleDishHolder();
			holder.dishpic = (ImageView) convertView.findViewById(R.id.dishpic);
			holder.dishname = (TextView) convertView.findViewById(R.id.dishname);
			holder.dishmethodnum=(TextView) convertView.findViewById(R.id.dishmethodnum);
			holder.dishtime=(TextView) convertView.findViewById(R.id.dishtime);
			convertView.setTag(holder);
		}else {
			holder=(StyleDishHolder) convertView.getTag();
		}
		final Dish entity = dishlist.get(position);
		Log.i("dish", entity.toString());

		String avatarUrl = null;
		if (entity.getDishPic() != null) {
			avatarUrl = entity.getDishPic().getFileUrl(mContext);
		}
		

		ImageLoader.getInstance().displayImage(avatarUrl, holder.dishpic,
				MyApplication.getInstance().getOptions(R.drawable.defaultdishpic), new SimpleImageLoadingListener() {

					@Override
					public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
						// TODO Auto-generated method stub
						super.onLoadingComplete(imageUri, view, loadedImage);
					}

				});
		holder.dishname.setText(entity.getDishName().trim());
		holder.dishmethodnum.setText("7������");
		holder.dishtime.setText(entity.getDishTime());
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it=new Intent();
				it.setClass(mContext, DishActivity.class);
				it.putExtra("entityid", entity.getObjectId());
				mContext.startActivity(it);
			}
		});
		return convertView;
	}

}
