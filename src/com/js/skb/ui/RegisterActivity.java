package com.js.skb.ui;

import java.io.File;
import java.util.HashMap;

import com.js.skb.R;
import com.js.skb.bean.User;
import com.peixuze.ui.LineEditText;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

public class RegisterActivity extends Activity implements OnClickListener {
	private LineEditText username, password,email;
	private ImageView add;
	String phone;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		username = (LineEditText) findViewById(R.id.username);
		password = (LineEditText) findViewById(R.id.password);
		email = (LineEditText) findViewById(R.id.email);

		add = (ImageView) findViewById(R.id.add);
		add.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.add) {
			RegisterPage registerPage = new RegisterPage();
			registerPage.setRegisterCallback(new EventHandler() {
				public void afterEvent(int event, int result, Object data) {
					// 解析注册结果
					if (result == SMSSDK.RESULT_COMPLETE) {
						@SuppressWarnings("unchecked")
						HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
						String country = (String) phoneMap.get("country");
						phone = (String) phoneMap.get("phone");

						// 提交用户信息
						register();
					}
				}
			});
			registerPage.show(RegisterActivity.this);

		}
	}
	
	private void register() {
		final ProgressDialog progress = new ProgressDialog(RegisterActivity.this);
		progress.setMessage("正在注册...");
		progress.setCanceledOnTouchOutside(false);
		progress.show();
		String StrName = username.getText().toString().trim();
		String StrPassword = password.getText().toString().trim();
		String StrEmail = email.getText().toString().trim();
		User user = new User();
		user.setUsername(StrName);
		user.setPassword(StrPassword);
		user.setMobilePhoneNumber(phone);
		user.setMobilePhoneNumberVerified(true);
		user.setEmail(StrEmail);
		Log.w("用户信息", StrName);
		// 开始提交注册信息
		user.signUp(RegisterActivity.this, new SaveListener() {

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				Log.w("用户信息", "注册成功");
				toast("注册成功");
				progress.dismiss();
				RegisterActivity.this.finish();
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Log.w("用户信息", arg1);
				toast("注册失败");
				progress.dismiss();
			}
		});
	}

	

	public void toast(String toast) {
		Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
	}
}
