package com.review;

public class ReviewDTO {
	private int rNo;
	private String rContent;
	private String rReg_date;
	private String pNo;
	private String rPhoto;
	private String mNo;
	private int rRate;
	
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
	public String getpNo() {
		return pNo;
	}
	public void setpNo(String pNo) {
		this.pNo = pNo;
	}
	public String getrPhoto() {
		return rPhoto;
	}
	public void setrPhoto(String rPhoto) {
		this.rPhoto = rPhoto;
	}
	public String getmNo() {
		return mNo;
	}
	public void setmNo(String mNo) {
		this.mNo = mNo;
	}
	public int getrRate() {
		return rRate;
	}
	public void setrRate(int rRate) {
		this.rRate = rRate;
	}
	
	
}
