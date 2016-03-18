package com.js.skb.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class Dish extends BmobObject{
	private String dishid;//编号
	private String dishName;//名字
	private BmobFile dishPic;//图片 
	private String dishMaterial;//主料
	private String dishMaterial2;//调料
	private String dishMethod;//做法
	private String dishTime;//时间
	private String dishstyle;//分类
	
	private String love;//点赞数量
	public String getDishid() {
		return dishid;
	}
	public void setDishid(String dishid) {
		this.dishid = dishid;
	}
	public String getDishName() {
		return dishName;
	}
	public void setDishName(String dishName) {
		this.dishName = dishName;
	}
	public BmobFile getDishPic() {
		return dishPic;
	}
	public void setDishPic(BmobFile dishPic) {
		this.dishPic = dishPic;
	}
	public String getDishMaterial() {
		return dishMaterial;
	}
	public void setDishMaterial(String dishMaterial) {
		this.dishMaterial = dishMaterial;
	}
	public String getDishMaterial2() {
		return dishMaterial2;
	}
	public void setDishMaterial2(String dishMaterial2) {
		this.dishMaterial2 = dishMaterial2;
	}
	public String getDishMethod() {
		return dishMethod;
	}
	public void setDishMethod(String dishMethod) {
		this.dishMethod = dishMethod;
	}
	public String getDishTime() {
		return dishTime;
	}
	public void setDishTime(String dishTime) {
		this.dishTime = dishTime;
	}
	public String getDishstyle() {
		return dishstyle;
	}
	public void setDishstyle(String dishstyle) {
		this.dishstyle = dishstyle;
	}
	
	
	public String getLove() {
		return love;
	}
	public void setLove(String love) {
		this.love = love;
	}
	
	
}
