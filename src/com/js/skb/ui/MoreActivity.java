package com.js.skb.ui;

import com.js.skb.MyApplication;
import com.js.skb.R;
import com.js.skb.bean.User;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.peixuze.ui.CircularImage;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobUser;

public class MoreActivity extends Activity implements OnClickListener{
	private ImageView back;
	private LinearLayout layoutuser;
	private CircularImage head;
	private TextView name;
	private Button logout;
	User user ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.more_activity);
		
		
		init();
		
	}
	private void init()
	{
		user = BmobUser.getCurrentUser(MoreActivity.this, User.class);
		back=(ImageView)findViewById(R.id.back);
		back.setOnClickListener(this);
		
		head = (CircularImage) findViewById(R.id.head);
		name = (TextView) findViewById(R.id.name);
		
		layoutuser = (LinearLayout) findViewById(R.id.user);
		
		logout = (Button) findViewById(R.id.logout);
		logout.setOnClickListener(this);
		if(user!=null)
		{
			if(user.getAvatar()!=null)
			{
				
				ImageLoader.getInstance().displayImage(user.getAvatar().getFileUrl(MoreActivity.this), head,
						MyApplication.getInstance().getOptions(R.drawable.dishsxamplie1),
						new SimpleImageLoadingListener() {

					@Override
					public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
						// TODO Auto-generated method stub
						super.onLoadingComplete(imageUri, view, loadedImage);
					}

				});
			}
			
			name.setText(user.getUsername());
		}else {
			layoutuser.setVisibility(View.INVISIBLE);
			logout.setVisibility(View.INVISIBLE);
		}
		
		
	}
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:
			finish();
			
			break;
		case R.id.logout:
			user.logOut(MoreActivity.this);
			toast("ÍË³öµÇÂ¼³É¹¦");
			break;

		default:
			break;
		}
		
	}
	public void toast(String toast) {

		Toast.makeText(MoreActivity.this, toast, Toast.LENGTH_SHORT).show();
	}
	
}
