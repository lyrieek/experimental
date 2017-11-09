package com.th.js.core;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

//�����ĵ�
public class JSDocument {

	// �ı��ܳ���
	private long length;

	// ɨ�赽�ĳ���
	private long index;

	// �Ѷ�����
	private LinkedList<ContextBlack> codes;

	// δ�رյĴ���
	private List<ContextBlack> notClose;

	public JSDocument() {
		codes = new LinkedList<ContextBlack>();
		notClose = new ArrayList<ContextBlack>();
	}

	public void append(Content context) {
		if (context.isEmpty() || context.isSingle()) {
			codes.add(context.getSingleContextBlack());
			return;
		}
		for (ContextBlack contextBlack : context.getAllBlack()) {
			codes.add(contextBlack);
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
			if (contextBlack.getStatus() != Status.STRING) {
				continue;
			}
			String item = contextBlack.item();
			System.out.print(contextBlack.getStartIndex().getIndex() + ">>");
			if (contextBlack.getStatus() != null) {
				System.out.print(contextBlack.getStatus() + ":");
			}
			System.out.println(contextBlack.isEmpty() ? "<<" : contextBlack.item() + "<<");
			buffer.append(item);
		}
		// System.out.println(buffer.toString().split("\n").length);
		// 250 10108
	}

}
