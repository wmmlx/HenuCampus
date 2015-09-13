package com.henucampus.object;

import cn.bmob.v3.BmobObject;

public class Lost extends BmobObject{
	private String title;
	private String describe;
	private String phone;
	private String date;
	private Object photo;
	public Object getPhoto() {
		return photo;
	}
	public void setPhoto(Object photo) {
		this.photo = photo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	

}
