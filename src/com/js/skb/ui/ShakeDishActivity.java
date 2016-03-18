package com.js.skb.ui;

import java.util.List;
import java.util.Random;

import com.js.skb.R;
import com.js.skb.bean.Dish;

import android.app.Activity;
import android.app.Service;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.FindListener;

public class ShakeDishActivity extends Activity implements OnClickListener, SensorEventListener {
	private ImageView mBack;
	int dishnum;

	private ImageView dishpic;
	private TextView dishname;
	private LinearLayout shakedish;
	private SensorManager mSensorManager;
	private Vibrator mVibrator;
	private final int ROCKPOWER = 15;// 这是传感器系数

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shakedish);
		queryNum();
		init();
	}

	private void init() {
		mBack = (ImageView) findViewById(R.id.back);
		mBack.setOnClickListener(this);
		shakedish = (LinearLayout) findViewById(R.id.shakedish);
		dishpic = (ImageView) findViewById(R.id.dishpic);
		dishname = (TextView) findViewById(R.id.dishname);
		
		// 获取传感器管理服务
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		// 震动服务
		mVibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
		// 震动需要在androidmainfest里面注册哦亲

	}

	private void queryNum() {
		BmobQuery<Dish> query = new BmobQuery<Dish>();
		query.count(ShakeDishActivity.this, Dish.class, new CountListener() {

			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Log.w("info", "服务器没菜谱" + arg1);
			}

			@Override
			public void onSuccess(int arg0) {
				// TODO Auto-generated method stub
				Log.w("info", arg0 + "");
				randomDish(arg0 + 1);// 从0到arg0
			}
		});
	}

	private int randomDish(int dishtotal) {
		Random random = new Random();
		dishnum = random.nextInt(dishtotal);
		
		return dishnum;
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;

		default:
			break;
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		// 加速度传感器
		mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				// 还有SENSOR_DELAY_UI、SENSOR_DELAY_FASTEST、SENSOR_DELAY_GAME等，
				// 根据不同应用，需要的反应速率不同，具体根据实际情况设定
				SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	protected void onStop() {
		mSensorManager.unregisterListener(this);// 退出界面后，把传感器释放。
		super.onStop();
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		int sensorType = event.sensor.getType();
		// values[0]:X轴，values[1]：Y轴，values[2]：Z轴
		float[] values = event.values;
		if (sensorType == Sensor.TYPE_ACCELEROMETER) {
			// 在 这个if里面写监听
			if ((Math.abs(values[0]) > ROCKPOWER || Math.abs(values[1]) > ROCKPOWER
					|| Math.abs(values[2]) > ROCKPOWER)) {
				mVibrator.vibrate(500);// 设置震动。
				Toast.makeText(ShakeDishActivity.this, "菜谱产生", Toast.LENGTH_SHORT).show();
				//queryDish(dishnum);
				shakedish.setVisibility(View.VISIBLE);
				Log.w("info", "dishnum"+dishnum);
			}
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	private void queryDish(int a) {
		BmobQuery<Dish> query = new BmobQuery<Dish>();
		Log.w("info", "dishnum"+a);
		query.addWhereEqualTo("dishid",String.valueOf(a) );
		query.findObjects(ShakeDishActivity.this, new FindListener<Dish>() {

			@Override
			public void onSuccess(List<Dish> arg0) {
				// TODO Auto-generated method stub
				Log.w("info", "dishnum"+arg0.size());
				Dish dish = arg0.get(1);
				dishname.setText(dish.getDishName().trim());
			}

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub

			}
		});
	}
}
