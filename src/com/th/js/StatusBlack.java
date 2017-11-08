package com.th.js;

public class StatusBlack{
	
	private Status status;
	
	private String label;
	
	public StatusBlack() {
		status = Status.READ;
	}
	
	public StatusBlack(Status s) {
		status = s;
	}
	
	public Status status() {
		return status;
	}
	
	public void change(Status status) {
		this.status = status;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
	
}

enum Status {
	
	READ,
	STRING,
	NUMBER,
	KEYWORDS,
	REGEX,
	EMPTY
	
}
