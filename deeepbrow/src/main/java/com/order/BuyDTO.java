package com.order;

public class BuyDTO {
	private int orderno;
	private String dname;
	private String dtel;
	private String del_memo;
	private int delno;
	private String daddr1;
	private String daddr2;
	private String dzipcode;
	private String order_date;
	private String pay_date;
	private String mid;
	private int pay_price;
	private int shipping_fee;
	private int quantity;
	private int odprice;
	private int pno;
	private int odno;
	private int whole_price;
	private String imageFilename;
	
	private int [] pNums;
	private int [] quantitys;
	private int [] onePrices;
	
	public int getOrderno() {
		return orderno;
	}
	public void setOrderno(int orderno) {
		this.orderno = orderno;
	}
	public String getDname() {
		return dname;
	}
	public void setDname(String dname) {
		this.dname = dname;
	}
	public String getDtel() {
		return dtel;
	}
	public void setDtel(String dtel) {
		this.dtel = dtel;
	}
	public String getDel_memo() {
		return del_memo;
	}
	public void setDel_memo(String del_memo) {
		this.del_memo = del_memo;
	}
	public int getDelno() {
		return delno;
	}
	public void setDelno(int delno) {
		this.delno = delno;
	}
	public String getDaddr1() {
		return daddr1;
	}
	public void setDaddr1(String daddr1) {
		this.daddr1 = daddr1;
	}
	public String getDaddr2() {
		return daddr2;
	}
	public void setDaddr2(String daddr2) {
		this.daddr2 = daddr2;
	}
	public String getDzipcode() {
		return dzipcode;
	}
	public void setDzipcode(String dzipcode) {
		this.dzipcode = dzipcode;
	}
	public String getOrder_date() {
		return order_date;
	}
	public void setOrder_date(String order_date) {
		this.order_date = order_date;
	}
	public String getPay_date() {
		return pay_date;
	}
	public void setPay_date(String pay_date) {
		this.pay_date = pay_date;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public int getPay_price() {
		return pay_price;
	}
	public void setPay_price(int pay_price) {
		this.pay_price = pay_price;
	}
	public int getShipping_fee() {
		return shipping_fee;
	}
	public void setShipping_fee(int shipping_fee) {
		this.shipping_fee = shipping_fee;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getOdprice() {
		return odprice;
	}
	public void setOdprice(int odprice) {
		this.odprice = odprice;
	}
	public int getPno() {
		return pno;
	}
	public void setPno(int pno) {
		this.pno = pno;
	}
	public int getOdno() {
		return odno;
	}
	public void setOdno(int odno) {
		this.odno = odno;
	}
	public int getWhole_price() {
		return whole_price;
	}
	public void setWhole_price(int whole_price) {
		this.whole_price = whole_price;
	}
	public String getImageFilename() {
		return imageFilename;
	}
	public void setImageFilename(String imageFilename) {
		this.imageFilename = imageFilename;
	}
	public int[] getpNums() {
		return pNums;
	}
	public void setpNums(int[] pNums) {
		this.pNums = pNums;
	}
	public int[] getQuantitys() {
		return quantitys;
	}
	public void setQuantitys(int[] quantitys) {
		this.quantitys = quantitys;
	}
	public int[] getOnePrices() {
		return onePrices;
	}
	public void setOnePrices(int[] onePrices) {
		this.onePrices = onePrices;
	}	
}
