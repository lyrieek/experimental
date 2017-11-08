package com.th.js;

import java.util.LinkedList;
import java.util.List;

public class JSDocument {

	// �ı��ܳ���
	private long length;

	// ɨ�赽�ĳ���
	private long index;

	// �Ѷ�����
	private LinkedList<ContextBlack> codes;

	// δ�رյĴ���
	private List<ContextBlack> notClose;

	public void append(Content context) {
		if (context.isEmpty()) {
			codes.add(ContextBlack.getEmpty(index, index += context.getMovePoint()));
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

}
