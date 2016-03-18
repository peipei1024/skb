package com.js.skb.ui.fragment;

import com.js.skb.MyApplication;
import com.js.skb.R;
import com.js.skb.bean.User;
import com.js.skb.ui.CollectionDishActivity;
import com.js.skb.ui.FootActivity;
import com.js.skb.ui.LandActivity;
import com.js.skb.ui.MoreActivity;
import com.js.skb.ui.PersonInfoActivity;
import com.js.skb.ui.ShakeDishActivity;
import com.js.skb.ui.ShopListActitity;
import com.js.skb.ui.UploadMenu;
import com.js.skb.ui.UplodsWorkActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.peixuze.ui.CircularImage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

public class MyFragment extends Fragment implements OnClickListener {
	private LinearLayout uplodsdishs, upload_works, userinfo, foot;
	private LinearLayout mShopList, mShakeDish, mCollection;
	private TextView username, userphone, user_dish_work;
	private CircularImage userhead;
	private ImageView my_image_setting;
	User user;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_my, container, false);
		uplodsdishs = (LinearLayout) view.findViewById(R.id.upload_dishs);
		uplodsdishs.setOnClickListener(this);
		upload_works = (LinearLayout) view.findViewById(R.id.upload_works);
		upload_works.setOnClickListener(this);
		userinfo = (LinearLayout) view.findViewById(R.id.userinfo);
		userinfo.setOnClickListener(this);
		my_image_setting = (ImageView) view.findViewById(R.id.setting);
		my_image_setting.setOnClickListener(this);
		foot = (LinearLayout) view.findViewById(R.id.foot);
		foot.setOnClickListener(this);

		username = (TextView) view.findViewById(R.id.username);
		userphone = (TextView) view.findViewById(R.id.userphone);
		user_dish_work = (TextView) view.findViewById(R.id.user_dish_work);
		userhead = (CircularImage) view.findViewById(R.id.userhead);

		mShopList = (LinearLayout) view.findViewById(R.id.shoplist);
		mShopList.setOnClickListener(this);
		mShakeDish = (LinearLayout) view.findViewById(R.id.shakedish);
		mShakeDish.setOnClickListener(this);
		mCollection = (LinearLayout) view.findViewById(R.id.collection);
		mCollection.setOnClickListener(this);
		initPersonalInfo();
		return view;

	}

	private void initPersonalInfo() {
		User user = BmobUser.getCurrentUser(getActivity(), User.class);
		if (user != null) {
			String name = user.getUsername();
			String phone = user.getMobilePhoneNumber();
			int dish = user.getDish();
			int works = user.getWorks();
			username.setText(name);
			userphone.setText(phone);

			// SpannableStringBuilder style=new SpannableStringBuilder(str);
			// str代表要显示的全部字符串
			// style.setSpan(this.getResources().getColor(R.color.orange),3,8,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			// ３代表从第几个字符开始变颜色，注意第一个字符序号是０．
			// ８代表变色到第几个字符．
			// textview.setText(style);
			user_dish_work.setText("菜谱" + "  " + dish + "     " + "|" + "     " + "作品" + "  " + works);
			BmobFile avatarFile = user.getAvatar();
			if (null != avatarFile) {
				ImageLoader.getInstance().displayImage(avatarFile.getFileUrl(getActivity()), userhead,
						MyApplication.getInstance().getOptions(R.drawable.background_head),
						new SimpleImageLoadingListener() {

							@Override
							public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
								// TODO Auto-generated method stub
								super.onLoadingComplete(imageUri, view, loadedImage);
							}

						});
			}
		} else {

			userphone.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
			;
		}
	}

	private Bitmap compressImageFromFile(String srcPath) {
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		newOpts.inJustDecodeBounds = true;// 只读边,不读内容
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);

		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		float hh = 800f;//
		float ww = 480f;//
		int be = 1;
		if (w > h && w > ww) {
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// 设置采样率

		newOpts.inPreferredConfig = Config.ARGB_8888;// 该模式是默认的,可不设
		newOpts.inPurgeable = true;// 同时设置才会有效
		newOpts.inInputShareable = true;// 。当系统内存不够时候图片自动被回收

		bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
		// return compressBmpFromBmp(bitmap);//原来的方法调用了这个方法企图进行二次压缩
		// 其实是无效的,大家尽管尝试
		return bitmap;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		initPersonalInfo();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent it = new Intent();
		switch (v.getId()) {
		case R.id.upload_dishs:
			it.setClass(getActivity(), UploadMenu.class);
			startActivity(it);
			break;
		case R.id.upload_works:
			it.setClass(getActivity(), UplodsWorkActivity.class);
			startActivity(it);
			break;
		case R.id.userinfo:
			user = BmobUser.getCurrentUser(getActivity(), User.class);
			if (user != null) {
				it.setClass(getActivity(), PersonInfoActivity.class);
				startActivity(it);
			} else {
				it.setClass(getActivity(), LandActivity.class);
				startActivity(it);
			}
			break;
		case R.id.setting:
			it.setClass(getActivity(), MoreActivity.class);
			startActivity(it);
			break;
		case R.id.foot:
			it.setClass(getActivity(), FootActivity.class);
			startActivity(it);
			break;
		case R.id.shoplist:
			it.setClass(getActivity(), ShopListActitity.class);
			startActivity(it);
			break;
		case R.id.shakedish:
			it.setClass(getActivity(), ShakeDishActivity.class);
			startActivity(it);
			break;
		case R.id.collection:
			// if(user!=null)
			// {
			it.setClass(getActivity(), CollectionDishActivity.class);
			startActivity(it);
			// }else {
			// toast("请先登录");
			// }

			break;
		}
	}

	public void toast(String toast) {

		Toast.makeText(getActivity(), toast, Toast.LENGTH_SHORT).show();
	}
}
