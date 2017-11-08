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
	 * @param translation
	 */
	public void change(AnalysisResult translation) {
		currentStatus = translation.status();
		if (translation.type() == Type.ADD) {
			document.append(translation.context());
		}
	}
	
	public Status getCurrentStatus() {
		return currentStatus;
	}
	
}
