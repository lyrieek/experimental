package com.th.js;

public class JsDrive {

	protected AnalysisResult result;
	protected Variables storage;

	public JsDrive(Variables vals) {
		storage = vals;
	}

	public void clear() {
		storage.clear();
	}
	
	/**
	 * Ω‚ ÕJS
	 * 
	 * @param item
	 * @return
	 */
	public AnalysisResult translation(String item) {
		if (result == null) {
			System.err.println("JsDrive.translation():AnalysisResult need instance,item:" + item);
			return null;
		}
		result.full(item);
		return result;
	}

	public void instance(CharPoint textPoint, StatusBlack sBlack) {
		result = new AnalysisResult();
		result.setBaseIndex(textPoint.getIndex());
		result.change(sBlack.status());
	}

}
