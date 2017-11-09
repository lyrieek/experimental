package com.th.js.core;

//主流程管理
public class RootManager {
	
	private Status currentStatus;
	private JSDocument document;
	private CharPoint currentPoint;
	private Content currentContent;
	
	public RootManager() {
		currentStatus = Status.READ;
		document = new JSDocument();
	}

	/**
	 * 修订整体文档
	 * @param result
	 */
	public void receive(AnalysisResult result) {
		currentStatus = result.status();
		currentContent = result.context();
		if (result.type() == Type.ADD) {
			if (result.isAllow()) {
				document.append(currentContent);
			}
		}
		currentStatus = result.getNextStatus();
	}
	
	
	
	public JSDocument getDocument() {
		return document;
	}
	
	public Status getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentPoint(CharPoint charPoint) {
		this.currentPoint = charPoint;
	}
	
	public CharPoint getCurrentPoint() {
		return currentPoint;
	}
	
}
