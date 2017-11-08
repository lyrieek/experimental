package com.th.js;

public class AnalysisResult {
	
	private StatusBlack status;
	
	private Type type;
	
	private Content content;
	
	private long baseIndex;
	
	public AnalysisResult() {
		status = new StatusBlack();
		type = Type.ADD;
		content = new Content();
	}

	/**
	 * 初始化Content并添加一个基本ContextBlack
	 * @param text
	 */
	public void full(String text) {
		ContextBlack cb = new ContextBlack(baseIndex);
		cb.full(text);
		cb.setStatus(status.status());
		content = new Content(cb);
	}
	
	public void full(Content context) {
		this.content = context;
	}
	
	public StatusBlack status() {
		return status;
	}
	
	public boolean is(Status status) {
		return this.status.status().equals(status);
	}
	
	public void change(StatusBlack status) {
		this.status = status;
	}
	
	public void change(Status status) {
		this.status = new StatusBlack(status);
	}

	public Type type() {
		return type;
	}

	public Type type(Type type) {
		return this.type = type;
	}
	
	public void supplement(ContextBlack cb) {
		content.add(cb);
	}
	
	public Content context() {
		return content;
	}
	
	public void setBaseIndex(long baseIndex) {
		this.baseIndex = baseIndex;
	}
	
	public long getBaseIndex() {
		return baseIndex;
	}

	@Override
	public String toString() {
		return "AnalysisResult [status=" + status + ", type=" + type + ", content=" + content + ", baseIndex="
				+ baseIndex + "]";
	}
	
	
}
enum Type{
	
	ADD,
	CHANGE
	
}
