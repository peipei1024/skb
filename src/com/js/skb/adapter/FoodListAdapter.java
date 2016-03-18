package com.js.skb.adapter;

import java.util.List;

import com.js.skb.R;
import com.js.skb.bean.FoodMsg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

public class FoodListAdapter extends BaseAdapter {

	List<FoodMsg> mList;
	LayoutInflater inflater;
	
	public FoodListAdapter(Context context,List<FoodMsg> list) {
		this.mList=list;
		inflater=LayoutInflater.from(context);
	}
	public void onDataChanged(List<FoodMsg> list){
		this.mList=list;
		this.notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		FoodMsg food=mList.get(position);
		if(convertView==null){
			holder=new ViewHolder();
			convertView=inflater.inflate(R.layout.food_msg, null);
			holder.name=(EditText) convertView.findViewById(R.id.food_name);
			holder.much=(EditText) convertView.findViewById(R.id.food_much);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		holder.name.setText(food.getFoodName());
		holder.much.setText(food.getFoodMuch());
		
		return convertView;
	}
	class ViewHolder{
		EditText name,much;
	}

}
