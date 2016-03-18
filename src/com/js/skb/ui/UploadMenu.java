package com.js.skb.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.bmob.BmobProFile;
import com.js.skb.R;
import com.js.skb.adapter.ConListAdapter;
import com.js.skb.adapter.FoodListAdapter;
import com.js.skb.adapter.MethodListAdapter;
import com.js.skb.bean.CondimentMsg;
import com.js.skb.bean.Dish;
import com.js.skb.bean.FoodMethod;
import com.js.skb.bean.FoodMsg;
import com.peixuze.ui.ListViewForScrollView;
import com.photoselector.model.PhotoModel;
import com.photoselector.ui.PhotoSelectorActivity;
import com.photoselector.util.CommonUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

public class UploadMenu extends Activity implements OnClickListener {

	private String[] sorts = { "酸", "甜", "苦", "辣", "咸" };
	private String[] times = { "1-10分钟", "11-20分钟", "21-30分钟", "30分钟以上" };
	private Button setSort, setTime, addFood, addCon, addMethod;
	private EditText name, story;
	private TextView upload;
	FoodMsg food = new FoodMsg();
	CondimentMsg condi = new CondimentMsg();
	FoodMethod method = new FoodMethod();
	private ImageView back;
	ListViewForScrollView fList, conList, methodList;
	List<FoodMsg> foodList = new ArrayList<FoodMsg>();
	List<CondimentMsg> condimentList = new ArrayList<CondimentMsg>();
	List<FoodMethod> foodMethodList = new ArrayList<FoodMethod>();
	FoodListAdapter foodAdapter;
	ConListAdapter conAdapter;
	MethodListAdapter methodAdapter;

	private ImageView dishpic;
	private String workpicfile;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.upload_menu);
		initView();

		// Bmob.initialize(this, "8774785dac79711f4f8a914efce9a4bf");
		// BmobInstallation.getCurrentInstallation(this).save();
	}

	private void initView() {
		setSort = (Button) findViewById(R.id.sort);
		setTime = (Button) findViewById(R.id.Time);
		addFood = (Button) findViewById(R.id.addFood);
		addCon = (Button) findViewById(R.id.addCondiment);
		addMethod = (Button) findViewById(R.id.addMethod);
		upload = (TextView) findViewById(R.id.upload);
		name = (EditText) findViewById(R.id.name);
		dishpic = (ImageView) findViewById(R.id.dishpic);
		back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(this);
		showList(foodList);
		setSort.setOnClickListener(listener);
		setTime.setOnClickListener(listener);
		addFood.setOnClickListener(listener);
		addCon.setOnClickListener(listener);
		addMethod.setOnClickListener(listener);
		upload.setOnClickListener(listener);
		dishpic.setOnClickListener(this);
	}

	private void showList(List<FoodMsg> foods) {

		if (foodAdapter == null) {
			fList = (ListViewForScrollView) findViewById(R.id.food_List);
			foodAdapter = new FoodListAdapter(this, foods);
			fList.setAdapter(foodAdapter);
		} else {
			foodAdapter.onDataChanged(foods);
		}
	}

	private void showConList(List<CondimentMsg> foods) {
		if (conAdapter == null) {
			conList = (ListViewForScrollView) findViewById(R.id.condiment_List);
			conAdapter = new ConListAdapter(this, foods);
			conList.setAdapter(conAdapter);
		} else {
			conAdapter.onConDataChanged(foods);
		}
	}

	private void showMethodList(List<FoodMethod> foods) {
		if (methodAdapter == null) {
			methodList = (ListViewForScrollView) findViewById(R.id.method_List);
			methodAdapter = new MethodListAdapter(this, foods);
			methodList.setAdapter(methodAdapter);
		} else {
			methodAdapter.onMethodChanged(foods);
		}
	}

	OnClickListener listener = new OnClickListener() {
		Intent intent;

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.sort:
				AlertDialog.Builder sort = new AlertDialog.Builder(UploadMenu.this);
				sort.setTitle("选择一个类别");

				sort.setItems(sorts, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						setSort.setText(sorts[which]);
					}
				});
				sort.create().show();
				break;
			case R.id.Time:
				AlertDialog.Builder time = new AlertDialog.Builder(UploadMenu.this);
				time.setTitle("选择时间");
				time.setItems(times, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						setTime.setText(times[which]);
					}
				});
				time.create().show();
				break;
			case R.id.addFood:
				intent = new Intent(UploadMenu.this, AddFood.class);
				startActivityForResult(intent, 1000);
				break;

			case R.id.addCondiment:
				intent = new Intent(UploadMenu.this, AddCondiment.class);
				startActivityForResult(intent, 2000);
				break;

			case R.id.addMethod:
				intent = new Intent(UploadMenu.this, AddMethod.class);
				startActivityForResult(intent, 3000);
				break;

			case R.id.upload:
				uploadpic(workpicfile);

				finish();
				break;

			default:
				break;
			}
		}
	};
	private void uploadpic(String path){
		
		final BmobFile figureFile = new BmobFile(new File(path));
		figureFile.upload(UploadMenu.this, new UploadFileListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				uploadMenu( figureFile);
				//toast("图片上传成功");
				
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
			}
		});
	
	}
	private void uploadMenu(BmobFile bf) {
		Dish menu = new Dish();
		menu.setDishName(name.getText().toString().trim());
		menu.setDishstyle(setSort.getText().toString().trim());
		menu.setDishTime(setTime.getText().toString().trim());

		Iterator<FoodMsg> foodLists = foodList.iterator();
		String matrial1 = null;
		StringBuffer matrialFood = new StringBuffer();
		while (foodLists.hasNext()) {
			FoodMsg food = foodLists.next();
			String name = food.getFoodName();
			String much = food.getFoodMuch();
			matrial1 = name + "。" + much + "，";
			matrialFood.append(matrial1);
		}
		menu.setDishMaterial(matrialFood.toString());
		Iterator<CondimentMsg> condimentLists = condimentList.iterator();
		String matrial2 = null;
		StringBuffer matrialCondiment = new StringBuffer();
		while (condimentLists.hasNext()) {
			CondimentMsg condiment = condimentLists.next();
			String name = condiment.getCondiName();
			String much = condiment.getCondiMuch();
			matrial2 = name + "。" + much + ",";
			matrialCondiment.append(matrial2);
		}
		menu.setDishMaterial2(matrialCondiment.toString());

		Iterator<FoodMethod> menuLists = foodMethodList.iterator();
		String matrial3 = null;
		StringBuffer matrialMethod = new StringBuffer();
		while (menuLists.hasNext()) {
			FoodMethod method = menuLists.next();
			String foodMethod = method.getFoodMehod();
			matrial3 = foodMethod + "/";
			matrialMethod.append(matrial3);
		}
		menu.setDishMethod(matrialMethod.toString());
		menu.setDishPic(bf);
		menu.save(UploadMenu.this, new SaveListener() {

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				Toast.makeText(UploadMenu.this, "上传成功！", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Log.w("失败", arg1);
				Toast.makeText(UploadMenu.this, "上传失败！", Toast.LENGTH_SHORT).show();
			}
		});
	}

	

	public void toast(String toast) {

		Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
	}

	public void files(String file) {
		workpicfile = file;
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
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		if (requestCode == 1000 && resultCode == 1001) {
			String name = intent.getStringExtra("name");
			String much = intent.getStringExtra("much");
			FoodMsg food = new FoodMsg();
			food.setFoodName(name);
			food.setFoodMuch(much);
			foodList.add(food);

			showList(foodList);

		}
		if (requestCode == 2000 && resultCode == 2001) {
			String name = intent.getStringExtra("name");
			String much = intent.getStringExtra("much");

			CondimentMsg condi = new CondimentMsg();
			condi.setCondiName(name);
			condi.setCondiMuch(much);
			condimentList.add(condi);

			showConList(condimentList);

		}

		if (requestCode == 3000 && resultCode == 3001) {
			String method = intent.getStringExtra("method");
			FoodMethod fMethod = new FoodMethod();
			fMethod.setFoodMehod(method);
			foodMethodList.add(fMethod);
			showMethodList(foodMethodList);
		}
		if (requestCode == 0 && resultCode == RESULT_OK) {
			if (intent != null && intent.getExtras() != null) {
				@SuppressWarnings("unchecked")
				List<PhotoModel> photos = (List<PhotoModel>) intent.getExtras().getSerializable("photos");
				if (photos == null || photos.isEmpty())
					return;
				String[] s = new String[photos.size()];
				int a = 0;
				// StringBuffer sb = new StringBuffer();
				for (PhotoModel photo : photos) {
					s[a] = photo.getOriginalPath();
					a++;
					// sb.append(photo.getOriginalPath() + "\r\n");
				}
				if (photos.size() > 1) {
					toast("只能选择一张图片");
				} else if (photos.size() == 0) {
					toast("请选择一张图片");
				} else {
					Bitmap bitmap = compressImageFromFile(s[0]);
					dishpic.setImageBitmap(bitmap);
					files(s[0]);
				}

			}

		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.dishpic:
			CommonUtils.launchActivityForResult(this, PhotoSelectorActivity.class, 0);
			break;
		case R.id.back:
			finish();
			break;
		default:
			break;
		}
	}

}
