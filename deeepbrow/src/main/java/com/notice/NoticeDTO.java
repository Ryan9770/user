package com.notice;

public class NoticeDTO {
	private int nNo;
	private String nSubject;
	private String nContent;
	private String nReg_date;
	private String mId;
	private String mNo;
	
	public int getnNo() {
		return nNo;
	}
	public void setnNo(int nNo) {
		this.nNo = nNo;
	}
	public String getnSubject() {
		return nSubject;
	}
	public void setnSubject(String nSubject) {
		this.nSubject = nSubject;
	}
	public String getnContent() {
		return nContent;
	}
	public void setnContent(String nContent) {
		this.nContent = nContent;
	}
	public String getnReg_date() {
		return nReg_date;
	}
	public void setnReg_date(String nReg_date) {
		this.nReg_date = nReg_date;
	}
	public String getmId() {
		return mId;
	}
	public void setmId(String mId) {
		this.mId = mId;
	}
	public String getmNo() {
		return mNo;
	}
	public void setmNo(String mNo) {
		this.mNo = mNo;
	}
	
	
}
