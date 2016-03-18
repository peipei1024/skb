package com.js.skb.ui;


import com.js.skb.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import cn.bmob.v3.Bmob;
import cn.smssdk.SMSSDK;

public class SkbActivity extends Activity{
	/**
	 * @author peixuze         
	 * @date 2015-4-7
	 * TODO �������棬����ָ��ʱ�������ת
	 */

	private static final long DELAY_TIME = 2000L;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_skb);
		Bmob.initialize(this, "f189a29e75175fa2b8b0ce9cc04db646");
		SMSSDK.initSDK(this, "9446e3808844", "74bebd20b2cdab357e3e10482143e1d8");
		redirectByTime();
	}	
	/**
	 * ����ʱ�����ҳ����ת
	 */
	private void redirectByTime() {
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				Intent it = new Intent();
				it.setClass(SkbActivity.this, BaseActivity.class);
				startActivity(it);
				finish();
			}
		}, DELAY_TIME);
	}
}
