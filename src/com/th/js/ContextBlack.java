package com.th.js;

/**
 * 文本块，用来纪录每一块文本
 * 
 * @author user
 *
 */
public class ContextBlack {

	private String id;

	private int startIndex;

	private int endInedx;

	private int level;

	private StringBuffer item;

	private ContextBlack base;

	private boolean isClosed;

	private String remark;

	public ContextBlack(int index) {
		startIndex = index;
		isClosed = false;
		id = Integer.toHexString((int) (Math.random() * 0xFFFFFFF)) + Integer.toHexString((int) (Math.random() * 0xFFFFFFF))+this.hashCode();
	}
	
	public static void main(String[] args) {
		System.out.println(new ContextBlack(12).id);
	}

	public void full(String text) {
		setItem(text);
		close();
	}

	public void append(Object chars) {
		if (chars == null) {
			return;
		}
		if (item == null) {
			item = new StringBuffer(chars.toString());
			return;
		}
		item.append(chars);
	}

	public int length() {
		return item().length();
	}

	public void close() {
		this.isClosed = true;
	}

	public String item() {
		return item == null ? "" : item.toString();
	}

	public void setItem(String item) {
		this.item = new StringBuffer(item);
	}

	public boolean isClosed() {
		return isClosed;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public void setBase(ContextBlack base) {
		this.base = base;
	}

	public ContextBlack getBase() {
		return base;
	}

	public int getEndInedx() {
		return endInedx;
	}

	public void setEndInedx(int endInedx) {
		this.endInedx = endInedx;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return startIndex + ":" + item;
	}

}
