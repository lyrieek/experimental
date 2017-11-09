package com.th.js.core;

public class AnalysisResult {
	
	private Status status;
	
	private Status nextStatus;
	
	private Type type;
	
	private Content lastContent;
	
	private Content content;
	
	private CharPoint point;
	
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
		ContextBlack cb = new ContextBlack(point);
		cb.full(text);
		cb.setStatus(status);
		content.add(cb);
		this.content = new Content(cb);
	}

	public void merge() {
		content.merge();
		allow = true;
	}

	public void further() {
		lastContent.addAll(content.getAllBlack());
		content = lastContent;
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
	
	public CharPoint point() {
		return point;
	}
	
	public void setPoint(CharPoint point) {
		this.point = point;
	}
	
	public void setLastContent(Content content) {
		this.lastContent = content;
	}

	@Override
	public String toString() {
		return "AnalysisResult [status=" + status + ", type=" + type + ", content=" + content + "]";
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
