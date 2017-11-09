package com.th.js.core;

public class JsDrive {

	protected AnalysisResult result;
	protected Variables storage;
	protected CharPoint charPoint;

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

	public void instance(RootManager manager) {
		result = new AnalysisResult();
		this.charPoint = manager.getCurrentPoint();
		result.setBaseIndex(charPoint.getIndex());
		result.change(manager.getCurrentStatus());
	}

}
