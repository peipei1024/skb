package com.js.skb.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.js.skb.CacheUtils;
import com.js.skb.R;
import com.js.skb.bean.User;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.peixuze.ui.CircularImage;
import com.peixuze.ui.HoveringScrollview;
import com.peixuze.ui.HoveringScrollview.OnScrollListener;
import com.photoselector.model.PhotoModel;
import com.photoselector.ui.PhotoSelectorActivity;
import com.photoselector.util.CommonUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

public class PersonInfoActivity extends Activity implements OnClickListener,OnScrollListener {
	

	private String userheadfil;
	private HoveringScrollview hoveringScrollview;
	private LinearLayout layout_dish_item,layout_works_item;

	private LinearLayout hoveringLayout,title;
	private LinearLayout layout_original, layout_now;
	private LinearLayout layout_slide;
	
	private TextView text_dish,text_works;
	private ImageView image_dish,image_works;
	
	private TextView name,signature;
	
	private CircularImage head;
	private ImageView back;
	
	private int searchLayoutTop;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_persion);
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		//mo srf = new mo();
		//fragmentTransaction.replace(R.id.fra, srf);
		//fragmentTransaction.commit();
		
		initViews();
		initPersonalInfo();
		
	}
	private void initViews() {
		title=(LinearLayout)findViewById(R.id.title);
		hoveringScrollview = (HoveringScrollview) findViewById(R.id.hoveringScrollview);
		layout_slide = (LinearLayout) findViewById(R.id.layout_slide);
		
		hoveringLayout = (LinearLayout) findViewById(R.id.hoveringLayout);
		layout_original = (LinearLayout) findViewById(R.id.layout_original);
		layout_dish_item = (LinearLayout) findViewById(R.id.layout_dish_item);
		layout_dish_item.setOnClickListener(this);
		layout_works_item = (LinearLayout) findViewById(R.id.layout_works_item);
		layout_works_item.setOnClickListener(this);
		layout_now = (LinearLayout) findViewById(R.id.layout_now);
		
		text_dish = (TextView)findViewById(R.id.text_dish);
		text_works = (TextView)findViewById(R.id.text_works);
		image_dish=(ImageView)findViewById(R.id.image_dish);
		image_works=(ImageView)findViewById(R.id.image_works);
		
		head = (CircularImage) findViewById(R.id.head);
		head.setOnClickListener(this);
		name = (TextView)findViewById(R.id.name);
		signature = (TextView)findViewById(R.id.signature);
		
		back=(ImageView)findViewById(R.id.back);
		back.setOnClickListener(this);
		
		hoveringScrollview.setOnScrollListener(this);// set Listener
		
		
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		if (hasFocus) {
			int a=title.getBottom();
			int b= layout_original.getBottom();
			searchLayoutTop =b-a;
			Log.w(String.valueOf(a), String.valueOf(searchLayoutTop));
		}
	}

	@Override
	public void onScroll(int scrollY) {
		Log.w("String.valueOf(a)", String.valueOf(scrollY));
		// TODO Auto-generated method stub
		if (scrollY >= searchLayoutTop) {
			//toast(String.valueOf(scrollY));
			if (hoveringLayout.getParent() != layout_now) {
				layout_original.removeView(hoveringLayout);
				layout_now.addView(hoveringLayout);
			}
		} else {
			if (hoveringLayout.getParent() != layout_original) {
				layout_now.removeView(hoveringLayout);
				layout_original.addView(hoveringLayout);
			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		
		switch (v.getId()) {
		case R.id.layout_dish_item:
			text_dish.setTextColor(this.getResources().getColor(R.color.orange));
			image_dish.setVisibility(View.VISIBLE);
			text_works.setTextColor(this.getResources().getColor(R.color.mygrey));
			image_works.setVisibility(View.INVISIBLE);
			/*mo srf = new mo();
			fragmentTransaction.replace(R.id.fra, srf);
			fragmentTransaction.commit();*/
			break;
		case R.id.layout_works_item:
			text_works.setTextColor(this.getResources().getColor(R.color.orange));
			image_works.setVisibility(View.VISIBLE);
			text_dish.setTextColor(this.getResources().getColor(R.color.mygrey));
			image_dish.setVisibility(View.INVISIBLE);
			/*mo srf1 = new mo();
			fragmentTransaction.replace(R.id.fra, srf1);
			fragmentTransaction.commit();*/
			break;
		case R.id.back:
			finish();
			break;
		case R.id.head:
			CommonUtils.launchActivityForResult(this, PhotoSelectorActivity.class, 0);
			break;
		default:
			break;
		}

	}
	
	private void initPersonalInfo() {
		User user = BmobUser
				.getCurrentUser(PersonInfoActivity.this, User.class);
		if (user != null) {
			if(user.getUsername()!=null)
			{
				name.setText(user.getUsername());
			}
			
			int dishnum=user.getDish();
			int worksnum=user.getWorks();
			if(user.getSignature()!=null)
			{
				signature.setText(user.getSignature());
			}else{
				signature.setText("吃货不是在吃就是在去吃的路上，没时间写签名...");
			}
			
			
			text_dish.setText(String.valueOf(dishnum)+"菜谱");
			text_works.setText(String.valueOf(worksnum)+"作品");
			BmobFile avatarFile = user.getAvatar();
			if (avatarFile != null) {
				// avatarFile.loadImage(PersonInfoActivity.this, head);
				ImageLoader.getInstance().displayImage(
						avatarFile.getFileUrl(PersonInfoActivity.this), head,
						new SimpleImageLoadingListener() {

							@Override
							public void onLoadingComplete(String imageUri,
									View view, Bitmap loadedImage) {
								// TODO Auto-generated method stub
								super.onLoadingComplete(imageUri, view,
										loadedImage);
							}
						});
			}
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 0 && resultCode == RESULT_OK) {
			if (data != null && data.getExtras() != null) {
				@SuppressWarnings("unchecked")
				List<PhotoModel> photos = (List<PhotoModel>) data.getExtras().getSerializable("photos");
				if (photos == null || photos.isEmpty())
					return;
				String[] s=new String[photos.size()];
				int a=0;
				//StringBuffer sb = new StringBuffer();
				for (PhotoModel photo : photos) {
					s[a]=photo.getOriginalPath();
					a++;
					//sb.append(photo.getOriginalPath() + "\r\n");
				}
				if(photos.size()>1)
				{
					toast("只能选择一张图片");
				}else if(photos.size()==0){
					toast("请选择一张图片");
				}else{
					Bitmap bitmap=compressImageFromFile(s[0]);
					head.setImageBitmap(bitmap);
					files(s[0]);
					updateIcon(s[0]);
				}
				
				
			}

		}
	}

	public void files(String file)
	{
		userheadfil=file;
	}

	private Bitmap compressImageFromFile(String srcPath) {
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		newOpts.inJustDecodeBounds = true;//只读边,不读内容
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
		newOpts.inSampleSize = be;//设置采样率
		
		newOpts.inPreferredConfig = Config.ARGB_8888;//该模式是默认的,可不设
		newOpts.inPurgeable = true;// 同时设置才会有效
		newOpts.inInputShareable = true;//。当系统内存不够时候图片自动被回收
		
		bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
//		return compressBmpFromBmp(bitmap);//原来的方法调用了这个方法企图进行二次压缩
									//其实是无效的,大家尽管尝试
		return bitmap;
	}

	
	private boolean isLogined() {
		BmobUser user = BmobUser.getCurrentUser(PersonInfoActivity.this,
				User.class);
		if (user != null) {
			return true;
		}
		return false;
	}
	
	
	
	

	/**
	 * 
	 * 
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri) {

		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 320);
		intent.putExtra("outputY", 320);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, 3);
	}
	public String saveToSdCard(Bitmap bitmap) {
		BmobUser user = BmobUser.getCurrentUser(PersonInfoActivity.this,
				User.class);
		String headname = user.getUsername();
		String files = "sdcard/head.png";
		File file = new File(files);
		try {
			FileOutputStream out = new FileOutputStream(file);
			if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)) {
				out.flush();
				out.close();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return file.getAbsolutePath();
	}

	private void updateIcon(String avataPath) {
		if (avataPath != null) {
			final BmobFile file = new BmobFile(new File(avataPath));

			file.upload(PersonInfoActivity.this, new UploadFileListener() {

				@Override
				public void onSuccess() {
					// TODO Auto-generated method stub
					User currentUser = BmobUser.getCurrentUser(
							PersonInfoActivity.this, User.class);
					currentUser.setAvatar(file);

					currentUser.update(PersonInfoActivity.this,
							new UpdateListener() {

								@Override
								public void onSuccess() {
									// TODO Auto-generated method stub

									toast("头像跟新成功");
								}

								@Override
								public void onFailure(int arg0, String arg1) {
									// TODO Auto-generated method stub
									toast("头像跟新失败" + arg0 + arg1);
								}
							});

				}

				@Override
				public void onProgress(Integer arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onFailure(int arg0, String arg1) {
					// TODO Auto-generated method stub
					Log.i("头像", arg0 + arg1);
					toast("头像跟新失败" + arg0 + arg1);

				}
			});
		}
	}

	public void toast(String toast) {

		Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
	}
}
