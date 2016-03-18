package com.js.skb.ui;

import java.io.File;
import java.util.List;

import com.js.skb.R;
import com.js.skb.bean.Dish;
import com.photoselector.model.PhotoModel;
import com.photoselector.ui.PhotoSelectorActivity;
import com.photoselector.util.CommonUtils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

public class UplodsDishActivity extends Activity implements OnClickListener{
	private LinearLayout addcailiao,caoliao;
	public static int CAILIAONUMBER=0;
	private EditText dishname,dishtime,dishmaterial,dishmethod,dishstyle;
	private TextView textview_uplods_dish;
	private ImageView image_dishpic;
String dateTime;
	
	private static final int REQUEST_CODE_ALBUM = 1;
	private static final int REQUEST_CODE_CAMERA = 2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_uplods_dish);
		init();
	}
	public void init()
	{
		dishname=(EditText) findViewById(R.id.dishname);
		dishtime=(EditText) findViewById(R.id.dishtime);
		dishmaterial=(EditText) findViewById(R.id.dishmaterial);
		dishmethod=(EditText) findViewById(R.id.dishmethod);
		dishstyle=(EditText)findViewById(R.id.dishstyle);
		image_dishpic=(ImageView)findViewById(R.id.dishpic);
		image_dishpic.setOnClickListener(this);
		
		textview_uplods_dish=(TextView) findViewById(R.id.text_uplods_dish);
		textview_uplods_dish.setOnClickListener(this);
		
		DisplayMetrics dm = new DisplayMetrics();//获取屏幕信息
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenWidth = dm.widthPixels;
		int screenHeigh = dm.heightPixels;
		Log.d("屏幕参数", "宽"+screenWidth+"高"+screenHeigh);
		
		addcailiao=(LinearLayout) findViewById(R.id.addcailiao);
		addcailiao.setOnClickListener(this);
		
		caoliao=(LinearLayout) findViewById(R.id.cailiao);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.addcailiao:
			CAILIAONUMBER++;
			if(CAILIAONUMBER!=1)
			{
				
			}
			LinearLayout layout=new LinearLayout(this);
			layout.setOrientation(LinearLayout.HORIZONTAL);
			EditText foodname=new EditText(this);
			TextView foodnu=new TextView(this);
			layout.addView(foodname);
			layout.addView(foodnu);
			caoliao.addView(layout);
			break;

		case R.id.text_uplods_dish:
			//publish();
			
			break;
		case R.id.dishpic:
			CommonUtils.launchActivityForResult(this, PhotoSelectorActivity.class, 0);
			//new PopupWindows(UplodsDishActivity.this,image_dishpic );
			break;
		}
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
				
				Bitmap bitmap=compressImageFromFile(s[0]);
				image_dishpic.setImageBitmap(bitmap);
				uploadpic(s[0]);
			}

		}
	}
	public class PopupWindows extends PopupWindow {

		public PopupWindows(Context mContext, View parent) {

			View view = View
					.inflate(mContext, R.layout.item_popupwindows, null);
			view.startAnimation(AnimationUtils.loadAnimation(mContext,
					R.anim.fade_ins));
			LinearLayout ll_popup = (LinearLayout) view
					.findViewById(R.id.ll_popup);
			ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
					R.anim.push_bottom_in_2));

			setWidth(LayoutParams.MATCH_PARENT);
			setHeight(LayoutParams.MATCH_PARENT);
			setFocusable(true);
			setOutsideTouchable(true);
			setContentView(view);
			showAtLocation(parent, Gravity.BOTTOM, 0, 0);
			update();

			Button bt1 = (Button) view
					.findViewById(R.id.item_popupwindows_camera);
			Button bt2 = (Button) view
					.findViewById(R.id.item_popupwindows_Photo);
			Button bt3 = (Button) view
					.findViewById(R.id.item_popupwindows_cancel);
			bt1.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					
					dismiss();
				}
			});
			bt2.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					
					dismiss();
				}
			});
			bt3.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					dismiss();
				}
			});

		}
	}
	
	
	private void uploadpic(String path){
		final ProgressDialog progress = new ProgressDialog(UplodsDishActivity.this);
		progress.setMessage("正在发表...");
		progress.setCanceledOnTouchOutside(false);
		progress.show();
		final BmobFile figureFile = new BmobFile(new File(path));
		figureFile.upload(UplodsDishActivity.this, new UploadFileListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				publish( figureFile);
				toast("图片上传成功");
				progress.dismiss();
				
			}

			@Override
			public void onProgress(Integer arg0) {
				// TODO Auto-generated method stub上传进度
				
				
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				toast("图片上传失败");
				Log.i("失败", arg0+arg1);
				progress.dismiss();
			}
		});
	
	}
	public void publish(BmobFile bombfilepic)
	{
		String sdishname=dishname.getText().toString();
		String sdishtime=dishtime.getText().toString();
		String sdishmaterial=dishmaterial.getText().toString();
		String sdishmethod=dishmethod.getText().toString();
		String sdishstyle=dishstyle.getText().toString();
		final ProgressDialog progress = new ProgressDialog(UplodsDishActivity.this);
		progress.setMessage("正在发表...");
		progress.setCanceledOnTouchOutside(false);
		progress.show();
		final Dish dish=new Dish();
		dish.setDishName(sdishname);
		dish.setDishTime(sdishtime);
		dish.setDishstyle(sdishstyle);
		dish.setDishMaterial(sdishmaterial);
		dish.setDishMethod(sdishmethod);
		dish.setDishPic(bombfilepic);
		dish.setDishid("1");
		dish.save(UplodsDishActivity.this, new SaveListener() {
			
			
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				toast("发表成功");
				progress.dismiss();
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				toast(arg1);
				progress.dismiss();
			}
		});
		
	}
	public void toast(String toast) {

		Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
	}
}
