package com.js.skb.bean;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

public class User extends BmobUser {
	public static final String TAG = "User";
	// 父类中已经存在的属性
	// private String id;
	//private String username;
	//private String password;
	// private String email;
	//private String mobilePhoneNumber;
	private int dish;//上传的菜谱数量
	private int works;//上传的作品数量
	private String signature;//签名
	private BmobFile avatar;
	private BmobRelation favorite;
	private BmobRelation workfavorite;
	public BmobRelation getWorkfavorite() {
		return workfavorite;
	}
	public void setWorkfavorite(BmobRelation workfavorite) {
		this.workfavorite = workfavorite;
	}
	public BmobRelation getFavorite() {
		return favorite;
	}
	public void setFavorite(BmobRelation favorite) {
		this.favorite = favorite;
	}
	public int getDish() {
		return dish;
	}
	public void setDish(int dish) {
		this.dish = dish;
	}
	public int getWorks() {
		return works;
	}
	public void setWorks(int works) {
		this.works = works;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public BmobFile getAvatar() {
		return avatar;
	}
	public void setAvatar(BmobFile avatar) {
		this.avatar = avatar;
	}
	public static String getTag() {
		return TAG;
	}
}
