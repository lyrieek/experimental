package com.th.js;

public class AnalysisResult {
	
	private StatusBlack status;
	
	private Status nextStatus;
	
	private Type type;
	
	private Content content;
	
	private long baseIndex;
	
	
	public AnalysisResult() {
		type = Type.ADD;
		status = new StatusBlack();
		content = new Content();
	}
	
	public void full(Content context) {
		this.content = context;
	}
	
	/**
	 * 初始化Content并添加一个基本ContextBlack
	 * @param text
	 */
	public void full(String text) {
		ContextBlack cb = new ContextBlack(baseIndex);
		cb.full(text);
		cb.setStatus(status.status());
		full(new Content(cb));
	}
	
	public void supplement(ContextBlack cb) {
		content.add(cb);
	}
	
	public StatusBlack status() {
		return status;
	}
	
	public boolean is(Status status) {
		return this.status.status().equals(status);
	}
	
	public void change(StatusBlack status) {
		this.status = status;
		nextStatus = status.status();
		if (content.getLastContext() != null) {
			content.getLastContext().setStatus(status.status());
		}
	}
	
	public void change(Status status) {
		change(new StatusBlack(nextStatus = status));
	}
	
	public void lazyChange(Status status) {
		nextStatus = status;
	}
	
	public Status getNextStatus() {
		return nextStatus;
	}

	public Type type() {
		return type;
	}

	public Type type(Type type) {
		return this.type = type;
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
