package com.js.skb.ui;

import com.js.skb.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;



public class AddMethod extends Activity {

	private EditText e_method;
	private Button sure;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_method);
		e_method=(EditText) findViewById(R.id.food_method);
		sure = (Button) findViewById(R.id.sure);

		sure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String method = e_method.getText().toString().trim();
				Intent intent = getIntent();
				intent.putExtra("method", method);
				setResult(3001, intent);
				finish();
			}
		});

	}
}
