package com.th.js.core;

public class CharPoint {

	private int line;

	private int column;

	private long index;

	private String text;

	private String before;

	private String after;
	
	public CharPoint() {
	}
	
	public CharPoint(String context) {
		text = context;
	}

	public static CharPoint get(long i) {
		CharPoint charPoint = new CharPoint();
		charPoint.setIndex(i);
		return charPoint;
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public long getIndex() {
		return index;
	}

	public void setIndex(long index) {
		this.index = index;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getBefore() {
		return before;
	}

	public void setBefore(String before) {
		this.before = before;
	}

	public String getAfter() {
		return after;
	}

	public void setAfter(String after) {
		this.after = after;
	}

	@Override
	public String toString() {
		return "{CharPoint [line:" + line + ";column:" + column + ";]}";
	}
	
}
