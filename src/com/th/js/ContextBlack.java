package com.th.js;

/**
 * �ı��飬������¼ÿһ���ı�
 * 
 * @author user
 *
 */
public class ContextBlack {

	private String id;

	private CharPoint startPoint;

	private CharPoint endPoint;

	private int level;

	private StringBuffer item;

	private ContextBlack base;

	private boolean isClosed;

	private String remark;

	private Status status;

	public ContextBlack(long index) {
		startPoint = CharPoint.get(index);
		isClosed = false;
		id = Integer.toHexString((int) (Math.random() * 0xFFFFFFF))
				+ Integer.toHexString((int) (Math.random() * 0xFFFFFFF)) + this.hashCode();
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

	public static ContextBlack getEmpty(long start, long length) {
		ContextBlack cb = new ContextBlack(start);
		cb.endPoint = CharPoint.get(length);
		return cb;
	}

	public int length() {
		return item().length();
	}

	public void close() {
		this.isClosed = true;
	}

	public boolean isEmpty() {
		return item().trim().isEmpty();
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

	public CharPoint getStartIndex() {
		return startPoint;
	}

	public void setStartIndex(CharPoint startPoint) {
		this.startPoint = startPoint;
	}

	public void setBase(ContextBlack base) {
		this.base = base;
	}

	public ContextBlack getBase() {
		return base;
	}

	public CharPoint getEndInedx() {
		return endPoint;
	}

	public void setEndInedx(CharPoint endPoint) {
		this.endPoint = endPoint;
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

	public void setStatus(Status status) {
		this.status = status;
	}
	
	public Status getStatus() {
		return status;
	}
	
	@Override
	public String toString() {
		return startPoint + ":" + item;
	}

}
