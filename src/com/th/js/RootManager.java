package com.th.js;

public class RootManager {
	
	private StatusBlack currentStatus;
	private JSDocument document;
	
	public RootManager() {
		currentStatus = new StatusBlack();
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
		currentStatus = new StatusBlack(result.getNextStatus());
	}
	
	public JSDocument getDocument() {
		return document;
	}
	
	public StatusBlack getCurrentStatus() {
		return currentStatus;
	}
	
}
