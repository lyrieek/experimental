package com.th.js.core;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JSScanner {

	// private TextPoint textPoint;

	private Integer point;
	private String item;
	private String searchChar;
	private String source;
	private int count = 0;
	private boolean needSkip = false;
	private Matcher matcher;
	private String lineSeparator = System.lineSeparator();

	// constructor

	public JSScanner(String sourceText) {
		point = 0;
		this.source = sourceText;
		lineSeparator = "\n";
	}

	public JSScanner(String sourceText, String searchChar) {
		this(sourceText);
		saveRegex(searchChar);
	}
	
	public static void main(String[] args) {
		JSScanner reader = new JSScanner("ass sd \r\n wefw sa", "\\S+");
		while (reader.pushRegex()) {
			System.out.print(reader.getCharPoint());
			System.out.println(reader.item);
		}
	}

	public CharPoint getCharPoint() {
		CharPoint textPoint = new CharPoint(item);
		textPoint.setAfter(after());
		textPoint.setBefore(before());
		textPoint.setColumn(column());
		textPoint.setLine(line());
		textPoint.setIndex(point);
		return textPoint;
	}

	public int line() {
		return before().split(lineSeparator).length;
	}

	public int column() {
		int lastLine = before().lastIndexOf(lineSeparator);
		lastLine = (lastLine == -1) ? 0 : lastLine + 1;
		return point - lastLine;
	}

	/**
	 * only text
	 * 
	 * @param text
	 * @return is has
	 */
	public boolean search(String text) {
		skip();
		searchChar = text;
		int location = source.indexOf(text, point);
		if (location != -1) {
			skip();
			push(location, text);
			return true;
		}
		needSkip = false;
		return false;
	}

	// regex

	public void saveRegex(String regex) {
		searchChar = regex;
		this.matcher = Pattern.compile(regex).matcher(source);
	}

	public boolean pushRegex() {
		skip();
		if (!matcher.find(point)) {
			needSkip = false;
			return false;
		}
		push(matcher.start(), matcher.group());
		return true;
	}

	public String regexItem(String regex) {
		Matcher matcher = Pattern.compile(regex).matcher(item);
		if (matcher.find()) {
			return matcher.group();
		}
		return null;
	}

	public String regex(String regex) {
		skip();
		searchChar = regex;
		Matcher matcher = Pattern.compile(regex).matcher(source);
		if (!matcher.find(point)) {
			needSkip = false;
			return null;
		}
		push(matcher.start(), matcher.group());
		return item;
	}

	// process

	/**
	 * ��Ծ���ҵ�����,׼����һ�β���
	 */
	public void skip() {
		if (needSkip && item != null) {
			needSkip = false;
			this.point += item.length();
		}
	}

	/**
	 * �ƽ��ı�
	 * 
	 * @param location
	 *            �ƽ�����λ��
	 * @param search
	 *            �ƽ����ı�
	 */
	public void push(int location, String search) {
		move(location);
		needSkip = true;
		item = search;
		count++;
	}

	/**
	 * �ƶ�����λ��
	 * 
	 * @param point
	 */
	public void move(int point) {
		this.point = point;
	}

	public int location() {
		return point;
	}

	public boolean matches() {
		return source.matches(searchChar);
	}

	/**
	 * ֮ǰ���ı�
	 * 
	 * @return
	 */
	public String before() {
		return source.substring(0, point);
	}

	/**
	 * ֮����ı�
	 * 
	 * @return
	 */
	public String after() {
		return source.substring(point);
	}

	public int getCount() {
		return count;
	}

	public String item() {
		return item;
	}

	@Override
	public String toString() {
		return point + ":" + item;
	}

}
