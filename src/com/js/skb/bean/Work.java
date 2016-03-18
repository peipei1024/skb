package com.js.skb.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

public class Work extends BmobObject{
	private User author;
	private String workname;
	private String workcontent;//ÄÚÈÝ
	private BmobFile workpic;
	
	
	
	
	
	private int love;//ÔÞ´ÎÊý
	
	private BmobRelation comment;

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public String getWorkname() {
		return workname;
	}

	public void setWorkname(String workname) {
		this.workname = workname;
	}

	public String getWorkcontent() {
		return workcontent;
	}

	public void setWorkcontent(String workcontent) {
		this.workcontent = workcontent;
	}

	public BmobFile getWorkpic() {
		return workpic;
	}

	public void setWorkpic(BmobFile workpic) {
		this.workpic = workpic;
	}

	

	public int getLove() {
		return love;
	}

	public void setLove(int love) {
		this.love = love;
	}

	public BmobRelation getComment() {
		return comment;
	}

	public void setComment(BmobRelation comment) {
		this.comment = comment;
	}

	
}
