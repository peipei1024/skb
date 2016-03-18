package com.js.skb.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

public class Comment extends BmobObject{
	private User author;
	private String comment;
	
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
