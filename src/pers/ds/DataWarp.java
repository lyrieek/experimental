package pers.ds;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataWarp implements Serializable{
	
	private static final long serialVersionUID = -9211101161227248962L;

	private String id;

	private String name;

	private Object obj;

	private String type;
	
	private Date createTime;
	
	public DataWarp(Object obj) {
		this.obj = obj;
		createTime = new Date();
		BigDecimal bd = new BigDecimal(Math.random());
		id = Double.toHexString(bd.multiply(new BigDecimal(Math.random())).doubleValue()).substring(4);
	}
	
	public static DataWarp toSet(String data) {
		String[] temp = data.split(":");
		if (data.contains("=")) {
			temp = data.split("=");
		}
		DataWarp dataWarp = new DataWarp(temp[1]);
		dataWarp.setName(temp[0]);
		return dataWarp;
	}

	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "DataWarp [id=" + id + ", name=" + name + ", obj=" + obj + ", createTime=" + createTime + "]";
	}
		
	
	
}
