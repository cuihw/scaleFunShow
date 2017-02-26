package com.example.scalefunshow.bean;

import java.util.ArrayList;
import java.util.List;

public class XiaoYouTiao {
    List<Material> list = new ArrayList<Material>();
    
    public XiaoYouTiao(){
    	Material m = new Material("面粉", 500);

    	list.add(m);
    	m =  new Material("糖", 10);
    	list.add(m);
    	m =  new Material("味精", 20);
    	list.add(m);
    	m =  new Material("食盐", 50);
    	list.add(m);
    	m =  new Material("调料", 30);
    	list.add(m);
    }

	public List<Material> getList() {
		return list;
	}

	public void setList(List<Material> list) {
		this.list = list;
	}
    
}
