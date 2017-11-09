package com.th.js.core;

public class AnalysisResult {
	
	private Status status;
	
	private Status nextStatus;
	
	private Type type;
	
	private Content lastContent;
	
	private Content content;
	
	private long baseIndex;
	
	private boolean allow = true;
	
	
	public AnalysisResult() {
		type = Type.ADD;
		status = Status.READ;
		content = new Content();
	}
	
	/**
	 * @param text
	 */
	public void full(String text) {
		ContextBlack cb = new ContextBlack(baseIndex);
		cb.full(text);
		cb.setStatus(status);
		content.add(cb);
		this.content = new Content(cb);
	}

	public void supplement(String text) {
		if (content.isEmpty()) {
			content = lastContent;
		}
		ContextBlack cb = new ContextBlack(baseIndex);
		cb.full(text);
		cb.setStatus(status);
		content.add(cb);
	}
	
	public Status status() {
		return status;
	}
	
	public boolean is(Status status) {
		return this.status.equals(status);
	}
	
	public void change(Status status) {
		this.status = nextStatus = status;
		if (content.getLastContext() != null) {
			content.getLastContext().setStatus(status);
		}
	}
	
	public void lazyChange(Status status) {
		nextStatus = status;
	}
	
	public void temporary(Status status) {
		Status source = this.status;
		change(status);
		lazyChange(source);
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
	
	public void setLastContent(Content content) {
		this.lastContent = content;
	}

	@Override
	public String toString() {
		return "AnalysisResult [status=" + status + ", type=" + type + ", content=" + content + ", baseIndex="
				+ baseIndex + "]";
	}

	
	public void lazyCommit() {
		allow = false;
	}
	
	public boolean isAllow() {
		return allow;
	}
	
	
}
enum Type{
	
	ADD,
	CHANGE
	
}
