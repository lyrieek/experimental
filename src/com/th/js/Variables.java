package com.th.js;

import java.util.LinkedList;

public class Variables extends LinkedList<DataValue> {

	private static final long serialVersionUID = 1L;

	public void put(String key, Object item) {
		add(new DataValue(key, item));
	}

	public Object get(String key) {
		for (DataValue value : this) {
			if (value.is(key)) {
				return value.value();
			}
		}
		return null;
	}

}

class DataValue {

	private String key;
	private Object value;

	// D:default S:system
	private String type;

	// remark
	private String label;

	public DataValue() {
		type = "D";
	}

	public DataValue(DataValue currentValue) {
		this();
		this.value = currentValue;
	}

	public DataValue(String label, Object obj) {
		this();
		put(label, obj);
	}

	public void put(String key, Object obj) {
		type = "S";
		setKey(key);
		value = obj;
	}

	public String key() {
		return key;
	}

	public Object value() {
		return value;
	}

	public String type() {
		return type;
	}
	
	public void setKey(String key) {
		this.key = key;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean is(String key) {
		return this.key.equals(key);
	}
	
	public boolean of(String param) {
		return type.equals(param);
	}

	public String label() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	

}
