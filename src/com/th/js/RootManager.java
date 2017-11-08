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
	 * @param translation
	 */
	public void change(AnalysisResult translation) {
		currentStatus = translation.status();
		if (translation.type() == Type.ADD) {
			document.append(translation.context());
		}
	}
	
	public JSDocument getDocument() {
		return document;
	}
	
	public StatusBlack getCurrentStatus() {
		return currentStatus;
	}
	
}
