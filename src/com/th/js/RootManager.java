package com.th.js;

public class RootManager {
	
	private Status currentStatus;
	private JSDocument document;
	
	public RootManager() {
		currentStatus = Status.READ;
		document = new JSDocument();
	}

	/**
	 * ĞŞ¶©ÕûÌåÎÄµµ
	 * @param result
	 */
	public void receive(AnalysisResult result) {
		currentStatus = result.status();
		if (result.type() == Type.ADD) {
			document.append(result.context());
		}
		currentStatus = result.getNextStatus();
	}
	
	public JSDocument getDocument() {
		return document;
	}
	
	public Status getCurrentStatus() {
		return currentStatus;
	}
	
}
