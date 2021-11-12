package com.product;

public class ReviewDTO {
	private int rNo;
	private String rContent;
	private String rReg_date;
	private int pNo;
	private String mId;
	private int rRate;
	
	private int imageNo;
	private String image_name;
	private String[] image_names;
	
	public int getImageNo() {
		return imageNo;
	}
	public void setImageNo(int imageNo) {
		this.imageNo = imageNo;
	}
	public String getImage_name() {
		return image_name;
	}
	public void setImage_name(String image_name) {
		this.image_name = image_name;
	}
	public String[] getImage_names() {
		return image_names;
	}
	public void setImage_names(String[] image_names) {
		this.image_names = image_names;
	}
	public int getrNo() {
		return rNo;
	}
	public void setrNo(int rNo) {
		this.rNo = rNo;
	}
	public String getrContent() {
		return rContent;
	}
	public void setrContent(String rContent) {
		this.rContent = rContent;
	}
	public String getrReg_date() {
		return rReg_date;
	}
	public void setrReg_date(String rReg_date) {
		this.rReg_date = rReg_date;
	}
	public int getpNo() {
		return pNo;
	}
	public void setpNo(int pNo) {
		this.pNo = pNo;
	}
	
	public String getmId() {
		return mId;
	}
	public void setmId(String mId) {
		this.mId = mId;
	}
	public int getrRate() {
		return rRate;
	}
	public void setrRate(int rRate) {
		this.rRate = rRate;
	}
	
	
}
