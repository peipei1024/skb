package com.js.skb.ui;

import java.io.File;
import java.util.List;

import com.js.skb.R;
import com.js.skb.bean.User;
import com.js.skb.bean.Work;
import com.photoselector.model.PhotoModel;
import com.photoselector.ui.PhotoSelectorActivity;
import com.photoselector.util.CommonUtils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

public class UplodsWorkActivity extends Activity implements OnClickListener{
	private ImageView back;
	private TextView uplod_work;
	private EditText workname,workcontent;
	private ImageView workpic;
	public static String workpicfile;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_uplodswork);
		init();
	}
	private void init()
	{
		back=(ImageView) findViewById(R.id.back);
		back.setOnClickListener(this);
		uplod_work=(TextView) findViewById(R.id.button_uplod_work);
		uplod_work.setOnClickListener(this);
		
		workname=(EditText) findViewById(R.id.workname);
		workcontent=(EditText) findViewById(R.id.workcontent);
		workpic=(ImageView) findViewById(R.id.workpic);
		workpic.setOnClickListener(this);
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;

		case R.id.button_uplod_work:
			uploadpic(workpicfile);
			
			break;
		case R.id.workpic:
			CommonUtils.launchActivityForResult(this, PhotoSelectorActivity.class, 0);
			break;
		}
	}
	private void uploadpic(String path){
		final ProgressDialog progress = new ProgressDialog(UplodsWorkActivity.this);
		progress.setMessage("正在发表...");
		progress.setCanceledOnTouchOutside(false);
		progress.show();
		final BmobFile figureFile = new BmobFile(new File(path));
		figureFile.upload(UplodsWorkActivity.this, new UploadFileListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				String Strworkname=workname.getText().toString().trim();
				String Strworkcontent=workcontent.getText().toString().trim();
				publish( figureFile,Strworkname,Strworkcontent);
				//toast("图片上传成功");
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
	public void publish(BmobFile bombfilepic,String puworkname,String puworkcontent)
	{
		
		final ProgressDialog progress = new ProgressDialog(UplodsWorkActivity.this);
		progress.setMessage("正在发表...");
		progress.setCanceledOnTouchOutside(false);
		progress.show();
		final Work work=new Work();
		User user = BmobUser.getCurrentUser(
				UplodsWorkActivity.this, User.class);
		Log.w("info", user.getUsername());
		
		work.setAuthor(user);
		work.setWorkname(puworkname);;
		work.setWorkcontent(puworkcontent);
		work.setWorkpic(bombfilepic);
		
		work.save(UplodsWorkActivity.this, new SaveListener() {
			
			
			
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
					workpic.setImageBitmap(bitmap);
					files(s[0]);
				}
				
				
			}

		}
	}
	public void files(String file)
	{
		workpicfile=file;
	}
	public void toast(String toast) {

		Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
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
}
