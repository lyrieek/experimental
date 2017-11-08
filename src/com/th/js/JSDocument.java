package com.th.js;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class JSDocument {

	// 文本总长度
	private long length;

	// 扫描到的长度
	private long index;

	// 已读代码
	private LinkedList<ContextBlack> codes;

	// 未关闭的代码
	private List<ContextBlack> notClose;

	public JSDocument() {
		codes = new LinkedList<ContextBlack>();
		notClose = new ArrayList<ContextBlack>();
	}

	public void append(Content context) {
		if (context.isEmpty()) {
			codes.add(context.getSingleContextBlack());
			return;
		}
		if (context.isSingle()) {
			codes.add(context.getSingleContextBlack());
		}
	}

	public long getLength() {
		return length;
	}

	public void setLength(long length) {
		this.length = length;
	}

	public long getIndex() {
		return index;
	}

	public void setIndex(long index) {
		this.index = index;
	}

	public LinkedList<ContextBlack> getCodes() {
		return codes;
	}

	public void setCodes(LinkedList<ContextBlack> codes) {
		this.codes = codes;
	}

	public List<ContextBlack> getNotClose() {
		return notClose;
	}

	public void setNotClose(List<ContextBlack> notClose) {
		this.notClose = notClose;
	}

	public void printf() {
		StringBuffer buffer = new StringBuffer();
		for (ContextBlack contextBlack : codes) {
			String item = contextBlack.item();
			if (!contextBlack.isEmpty()) {
				System.out.println(contextBlack.getStartIndex().getIndex() + ":" + contextBlack.item());
			}
			buffer.append(item);
		}
		System.out.println(buffer.toString().split("\n").length);
	}

}
