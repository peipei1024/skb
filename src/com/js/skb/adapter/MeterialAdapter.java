package com.js.skb.adapter;

import java.util.ArrayList;

import com.js.skb.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MeterialAdapter extends BaseAdapter{
	private ArrayList meteriallist;
	private Context mContext;
	private LayoutInflater mInflater;
	public MeterialAdapter(Context content,ArrayList list) {
		// TODO Auto-generated constructor stub
		mContext=content;
		meteriallist=list;
		
		mInflater=LayoutInflater.from(content);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return meteriallist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return meteriallist.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		MaterialHolder holder;
		if(convertView==null)
		{
			convertView=mInflater.inflate(R.layout.layout_material, null);
			holder=new MaterialHolder();
			holder.material=(TextView) convertView.findViewById(R.id.material);
			holder.materialnum=(TextView) convertView.findViewById(R.id.materialnum);
			convertView.setTag(holder);
		}else{
			holder=(MaterialHolder) convertView.getTag();
		}
		String ma=(String)meteriallist.get(position);
		String a[] = ma.split("¡£"); 
		
		holder.material.setText(" "+a[0]);
		holder.materialnum.setText(a[1]+"  ");
		return convertView;
	}

}
