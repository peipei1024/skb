package com.js.skb.ui;

import com.js.skb.R;
import com.js.skb.bean.User;
import com.peixuze.ui.LineEditText;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.listener.SaveListener;

public class LandActivity extends Activity implements OnClickListener {
	private LineEditText username, userpassword;
	private ImageView back, button_sms_land;
	private Button land;
	private TextView freeregister, resetPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_land);
		init();
	}

	private void init() {
		back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(this);
		land = (Button) findViewById(R.id.land);
		land.setOnClickListener(this);
		freeregister = (TextView) findViewById(R.id.freeregister);
		freeregister.setOnClickListener(this);
		resetPassword = (TextView) findViewById(R.id.resetPassword);
		resetPassword.setOnClickListener(this);
		username = (LineEditText) findViewById(R.id.username);
		userpassword = (LineEditText) findViewById(R.id.userpassword);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent it=new Intent();
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
			
		case R.id.land:
			String name = username.getText().toString();
			String password = userpassword.getText().toString();
			land(name,password);
			break;
		case R.id.freeregister:
			
			it.setClass(LandActivity.this, RegisterActivity.class);
			startActivity(it);
			break;
		case R.id.resetPassword:
			it.setClass(LandActivity.this, BackPasswordActivity.class);
			startActivity(it);
			break;
		}
	}
	private void land(String na,String pa)
	{
		final ProgressDialog progress = new ProgressDialog(
				LandActivity.this);
		progress.setMessage("正在登录...");
		progress.setCanceledOnTouchOutside(false);
		progress.show();
		User user = new User();
		
		user.setUsername(na);
		user.setPassword(pa);
		user.login(LandActivity.this, new SaveListener() {

			@Override
			public void onSuccess() {
				progress.dismiss();
				toast("登录成功");
				finish();

			}

			@Override
			public void onFailure(int arg0, String arg1) {
				progress.dismiss();
				if (arg0 == 101) {
					toast("用户名或密码不正确");
				}
			}
		});
	}
	public void toast(String toast) {
		Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
	}
}
