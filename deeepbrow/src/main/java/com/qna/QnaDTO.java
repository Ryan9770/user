package com.qna;

public class QnaDTO {
	private int qNo,listNum;
	private String qSubject;
	private String qContent;
	private String qReg_date;
	private String qCategory;
	private String mId;
	private String pNo;
	
	public int getqNo() {
		return qNo;
	}
	public void setqNo(int qNo) {
		this.qNo = qNo;
	}
	public String getqSubject() {
		return qSubject;
	}
	public void setqSubject(String qSubject) {
		this.qSubject = qSubject;
	}
	public String getqContent() {
		return qContent;
	}
	public void setqContent(String qContent) {
		this.qContent = qContent;
	}
	public String getqReg_date() {
		return qReg_date;
	}
	public void setqReg_date(String qReg_date) {
		this.qReg_date = qReg_date;
	}
	public String getqCategory() {
		return qCategory;
	}
	public void setqCategory(String qCategory) {
		this.qCategory = qCategory;
	}
	public String getmId() {
		return mId;
	}
	public void setmNo(String mId) {
		this.mId = mId;
	}
	public String getpNo() {
		return pNo;
	}
	public void setpNo(String pNo) {
		this.pNo = pNo;
	}
	public int getListNum() {
		return listNum;
	}
	public void setListNum(int listNum) {
		this.listNum = listNum;
	}
	
	
}
