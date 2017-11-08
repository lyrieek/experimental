package com.th.js;

import java.math.BigDecimal;
import java.util.HashMap;

public class Variables extends HashMap<String, DataValue> {
	private static final long serialVersionUID = -2335975233695300987L;

	private String currentKey;
	private DataValue currentValue;
	private String type;

	public Variables() {
	}

	public Object add(String key) {
		return add(key, null);
	}

	public Object add(String key, DataValue value) {
		return super.put(currentKey = key, currentValue = value);
	}

	public void update(DataValue value) {
		super.put(currentKey, currentValue = value);
	}

	public void update(Object value) {
		update(currentValue = new DataValue(value));
	}

	public String getCurrentKey() {
		return currentKey;
	}

	public Object getCurrentValue() {
		return currentValue;
	}
	
	public String type() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
}

class DataValue {

	private String dataType;
	private Object obj;

	public DataValue() {
	}

	public DataValue(Object obj, String dataType) {
		super();
		set(obj, dataType);
	}

	private DataValue set(Object obj, String dataType) {
		this.obj = obj;
		this.dataType = dataType;
		return this;
	}

	public DataValue(Object obj) {
		this(obj, "object");
	}

	public String toType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public static DataValue reverse(String data) {
		DataValue dv = new DataValue();
		String warp[] = { "\"", "'", "`" };
		for (String warpItem : warp) {
			if (data.startsWith(warpItem) && data.endsWith(warpItem)) {
				return dv.set(data.substring(1, data.length() - 1), "string");
			}
		}
		
		if (data.matches("[0-9]+")) {
			return dv.set(new BigDecimal(data), "number");
		}
		
		if (data.matches("(true|false)")) {
			return dv.set(data.equals("true"), "boolean");
		}
		
		return dv;
	}

	@Override
	public String toString() {
		return obj == null ? null : obj.toString();
	}

}
