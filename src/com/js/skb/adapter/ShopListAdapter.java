package com.js.skb.adapter;

import java.util.ArrayList;

import com.js.skb.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ShopListAdapter extends BaseAdapter{
	private ArrayList<String> mList=null;
	private Context mContext;
	private LayoutInflater mInflater;
	public ShopListAdapter(Context context,ArrayList<String> list) {
		mContext=context;
		mList=list;
		mInflater=LayoutInflater.from(context);
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
		ShopListHolder holder;
		if(convertView==null)
		{
			convertView=mInflater.inflate(R.layout.layout_shoplist, null);
			holder = new ShopListHolder();
			holder.name = (TextView) convertView.findViewById(R.id.listname);
			holder.num = (TextView) convertView.findViewById(R.id.listnum);
			convertView.setTag(holder);
		}else {
			holder = (ShopListHolder) convertView.getTag();
		}
		
		
		
		
		String ma = mList.get(position);
		String a[] = ma.split(","); 
		holder.name.setText(a[0]);
		holder.num.setText(a[1]);
		return convertView;
	}

}
