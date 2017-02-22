package com.example.scalefunshow.bean;



public class TaskBean {
	String peifangming ; //  配方名称
	int Count;           //  份数
	String peiliaofangshi; // 配料方式
	String jiaobanjiID ;    // 搅拌机号

	public String getPeifangming() {
		return peifangming;
	}
	public void setPeifangming(String peifangming) {
		this.peifangming = peifangming;
	}
	public int getCount() {
		return Count;
	}
	public void setCount(int count) {
		Count = count;
	}
	public String getPeiliaofangshi() {
		return peiliaofangshi;
	}
	public void setPeiliaofangshi(String peiliaofangshi) {
		this.peiliaofangshi = peiliaofangshi;
	}

	public String getJiaobanjiID() {
		return jiaobanjiID;
	}

	public void setJiaobanjiID(String jiaobanjiID) {
		this.jiaobanjiID = jiaobanjiID;
	}
}
