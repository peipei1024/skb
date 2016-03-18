package com.js.skb.adapter;

import java.util.ArrayList;
import java.util.Iterator;

import com.js.skb.MyApplication;
import com.js.skb.R;
import com.js.skb.bean.Dish;
import com.js.skb.ui.DishActivity;
import com.js.skb.ui.FootActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import android.app.ProgressDialog;
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

public class FootAdapter extends BaseAdapter {
	private ArrayList<String> footlist;
	private Context mContext;
	private LayoutInflater mInflater;
	public FootAdapter(Context context,ArrayList<String> list) {
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
		
		BmobQuery<Dish> query = new BmobQuery<Dish>();
		query.getObject(mContext, footlist.get(position), new GetListener<Dish>() {

			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				holder.footitem.setVisibility(View.GONE);
			}

			@Override
			public void onSuccess(Dish arg0) {
				// TODO Auto-generated method stub
				//list.add(arg0);
				holder.dishname.setText(arg0.getDishName());
				if(arg0.getDishTime()!=null)
				{
					holder.dishtime.setText("”√ ±"+arg0.getDishTime());
				}
				if(arg0.getLove()!=null)
				{
					holder.dishcollection.setText(" ’≤ÿ"+arg0.getLove());
				}
				if(arg0.getDishPic()!=null)
				{
					arg0.getDishPic().getFileUrl(mContext);
					
					ImageLoader.getInstance().displayImage(arg0.getDishPic().getFileUrl(mContext), holder.dishpic,
							MyApplication.getInstance().getOptions(R.drawable.dishsxamplie1),
							new SimpleImageLoadingListener() {

						@Override
						public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
							// TODO Auto-generated method stub
							super.onLoadingComplete(imageUri, view, loadedImage);
						}

					});
				}
				Log.w("info",arg0.getDishName() );
			}
		});
		holder.footitem.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it = new Intent();
				it.setClass(mContext, DishActivity.class);
				it.putExtra("entityid", footlist.get(position));
				mContext.startActivity(it);
			}
		});
		return convertView;
	}
	
}
