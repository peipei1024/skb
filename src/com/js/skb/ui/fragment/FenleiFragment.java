package com.js.skb.ui.fragment;

import com.js.skb.R;
import com.js.skb.ui.FenleiActivity;
import com.js.skb.ui.IndexFenleiActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class FenleiFragment extends Fragment implements OnClickListener {

	private LinearLayout shuan, tian, la, xian, more;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_fenlei, null);
		shuan = (LinearLayout) v.findViewById(R.id.shuan);
		tian = (LinearLayout) v.findViewById(R.id.tian);
		la = (LinearLayout) v.findViewById(R.id.la);
		xian = (LinearLayout) v.findViewById(R.id.xian);
		more = (LinearLayout) v.findViewById(R.id.more);
		shuan.setOnClickListener(this);
		tian.setOnClickListener(this);
		la.setOnClickListener(this);
		xian.setOnClickListener(this);
		more.setOnClickListener(this);
		return v;

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.shuan:
			intentFenlei("1");
			Log.w("info", "shuan");
			break;

		case R.id.tian:
			intentFenlei("2");
			break;
		case R.id.la:
			intentFenlei("3");
			break;
		case R.id.xian:
			intentFenlei("4");
			break;
		case R.id.more:
			intentFenlei("5");
			break;
		}
	}
	public void intentFenlei(String s)
	{
		Intent it=new Intent();
		it.putExtra("type", s);
		it.setClass(getActivity(),FenleiActivity.class );
		startActivity(it);
	}
}
