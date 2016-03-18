package com.js.skb.adapter;

import java.util.ArrayList;

import com.js.skb.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DishMethodAdapter extends BaseAdapter{
	private ArrayList methodlist;
	private Context mContext;
	private LayoutInflater mInflater;
	public DishMethodAdapter(Context content,ArrayList list) {
		// TODO Auto-generated constructor stub
		mContext=content;
		methodlist=list;
		
		mInflater=LayoutInflater.from(content);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return methodlist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return methodlist.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		DishMethodHolder holder;
		if(convertView==null)
		{
			convertView=mInflater.inflate(R.layout.layout_method, null);
			holder=new DishMethodHolder();
			holder.dishmethodnum=(TextView) convertView.findViewById(R.id.methodn);
			convertView.setTag(holder);
		}else{
			holder=(DishMethodHolder) convertView.getTag();
		}
		String ma=(String)methodlist.get(position);
		
		
		holder.dishmethodnum.setText("  "+ma);
		return convertView;
	}
}
