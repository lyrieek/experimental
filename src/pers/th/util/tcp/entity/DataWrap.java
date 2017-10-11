package pers.th.util.tcp.entity;

import java.io.Serializable;
import java.util.Date;

public class DataWrap implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String id;

	private Date outputDate;
	
	private DataWrapType type;

	private byte[] data;

	private Object remark;

	public DataWrap() {
		id = new String(Double.toHexString(Math.random() * 1024));
		outputDate = new Date();
	}

	public DataWrap(DataWrapType type,byte[] bytes) {
		this();
		this.setType(type);
		data = bytes;
	}

	public DataWrap(DataWrapType type,byte[] bytes,Object remark) {
		this();
		this.setType(type);
		data = bytes;
		this.remark = remark;
	}

	public DataWrap(DataWrapType type, String str) {
		this(type, str.getBytes());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getOutputDate() {
		return outputDate;
	}

	public void setOutputDate(Date outputDate) {
		this.outputDate = outputDate;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	} 

	public DataWrapType getType() {
		return type;
	}

	public void setType(DataWrapType type) {
		this.type = type;
	}
	
	public Object getRemark() {
		return remark;
	}
	
	public void setRemark(Object remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "DataWrap [id=" + id + ", outputDate=" + outputDate + ", type=" + type + ", data length="
				+ data.length + "]";
	}

	public boolean isType(DataWrapType type) {
		return this.type == type;
	}
	
//	public void handle(DataHandle dh) {
//		
//	}
	
}
