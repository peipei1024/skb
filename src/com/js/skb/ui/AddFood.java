package com.js.skb.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import com.js.skb.R;

public class AddFood extends Activity {

	private EditText e_name, e_much;
	private Button sure;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_food);
		e_name = (EditText) findViewById(R.id.food_name);
		e_much = (EditText) findViewById(R.id.food_much);
		sure = (Button) findViewById(R.id.sure);

		sure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String name = e_name.getText().toString().trim();
				String much = e_much.getText().toString().trim();
				Intent intent = getIntent();
				intent.putExtra("name", name);
				intent.putExtra("much", much);
				setResult(1001, intent);
				finish();
			}
		});

	}
}
