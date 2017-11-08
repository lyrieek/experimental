package com.th.js;

public class AnalysisResult {
	
	private Status status;
	
	private Type type;
	
	private Content content;
	
	private long baseIndex;
	
	public AnalysisResult() {
		status = Status.READ;
		type = Type.ADD;
		content = new Content();
	}

	public void full(String text) {
		ContextBlack cb = new ContextBlack(baseIndex);
		cb.full(text);
		content = new Content(cb);
	}
	
	public void full(Content context) {
		this.content = context;
	}
	
	public Status status() {
		return status;
	}
	
	public void change(Status status) {
		this.status = status;
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
