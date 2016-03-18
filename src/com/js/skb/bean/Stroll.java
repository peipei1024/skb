package com.js.skb.bean;

import android.widget.TextView;
import cn.bmob.v3.BmobObject;

public class Stroll extends BmobObject{
	private Dish dish1;
	private Dish dish2;
	private Dish dish3;
	private String text;
	public Dish getDish1() {
		return dish1;
	}
	public void setDish1(Dish dish1) {
		this.dish1 = dish1;
	}
	public Dish getDish2() {
		return dish2;
	}
	public void setDish2(Dish dish2) {
		this.dish2 = dish2;
	}
	public Dish getDish3() {
		return dish3;
	}
	public void setDish3(Dish dish3) {
		this.dish3 = dish3;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
}
