package com.js.skb.ui;

import java.util.HashMap;
import java.util.List;

import com.js.skb.R;
import com.js.skb.bean.User;
import com.peixuze.ui.LineEditText;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.ResetPasswordByEmailListener;
import cn.bmob.v3.listener.UpdateListener;

import cn.smssdk.gui.RegisterPage;

public class BackPasswordActivity extends Activity implements OnClickListener{
	private ImageView mBack,add;
	private LineEditText email;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_backpassword);
		init();
	}
	private void init()
	{
		mBack = (ImageView) findViewById(R.id.back);
		mBack.setOnClickListener(this);
		add = (ImageView) findViewById(R.id.add);
		add.setOnClickListener(this);
		email = (LineEditText) findViewById(R.id.email);
		//username = (LineEditText) findViewById(R.id.name);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.add:
			String stremail = email.getText().toString();
			User u = new User();
			u.resetPasswordByEmail(BackPasswordActivity.this, stremail, new ResetPasswordByEmailListener() {
				
				
				
				@Override
				public void onSuccess() {
					// TODO Auto-generated method stub
					toast("发送成功，请在邮件里填写新的密码");
					BackPasswordActivity.this.finish();
				}
				
				@Override
				public void onFailure(int arg0, String arg1) {
					// TODO Auto-generated method stub
					toast("找回密码失败，请重试");
				}
			});
			
			break;
		
		}
	}
	
	public void toast(String toast) {
		Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
	}
}
