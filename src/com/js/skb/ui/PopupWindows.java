package com.js.skb.ui;

import java.util.ArrayList;
import java.util.Iterator;

import com.js.skb.R;
import com.js.skb.db.ListDBHelp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobObject;

public class PopupWindows extends PopupWindow {
	private Context mContext;
	public PopupWindows(Context context, View parent) {
		mContext=context;
		View view = View.inflate(mContext, R.layout.layout_dish_more, null);
		view.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_ins));
		LinearLayout ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
		ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.push_bottom_in_2));

		setWidth(LayoutParams.MATCH_PARENT);
		setHeight(LayoutParams.MATCH_PARENT);
		setFocusable(true);
		setOutsideTouchable(true);
		setContentView(view);
		showAtLocation(parent, Gravity.BOTTOM, 0, 0);
		update();
		
		
		TextView cancel1 = (TextView) view.findViewById(R.id.cancel1);
		cancel1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismiss();
			}
		});
		LinearLayout qq = (LinearLayout) view.findViewById(R.id.qq);
		qq.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismiss();
				//toast("还未实现");
				Toast.makeText(mContext, "功能还未实现", Toast.LENGTH_SHORT).show();
			}
		});
		LinearLayout weichat = (LinearLayout) view.findViewById(R.id.weichat);
		weichat.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismiss();
				Toast.makeText(mContext, "功能还未实现", Toast.LENGTH_SHORT).show();
			}
		});
		LinearLayout weichatfavorite = (LinearLayout) view.findViewById(R.id.weichatfavorite);
		weichatfavorite.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismiss();
				Toast.makeText(mContext, "功能还未实现", Toast.LENGTH_SHORT).show();
			}
		});
		LinearLayout sinaweibo = (LinearLayout) view.findViewById(R.id.sinaweibo);
		sinaweibo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismiss();
				Toast.makeText(mContext, "功能还未实现", Toast.LENGTH_SHORT).show();
			}
		});
		LinearLayout qzone = (LinearLayout) view.findViewById(R.id.qzone);
		qzone.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismiss();
				Toast.makeText(mContext, "功能还未实现", Toast.LENGTH_SHORT).show();
			}
		});
		Button cancel =(Button) view.findViewById(R.id.cancel);
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismiss();
			}
		});

	}
	

	
}
