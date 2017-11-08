package com.th.js;

import chars.u.TextPoint;

public class JsDrive {

	private AnalysisResult result;

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
		if (item.trim().isEmpty()) {
			result.change(Status.EMPTY);
			result.full(item);
			// System.out.println(result);
		}
		return result;
	}

	public void instance(TextPoint textPoint) {
		result = new AnalysisResult();
		result.setBaseIndex(textPoint.getIndex());
	}

}
