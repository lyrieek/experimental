package com.th.js.core;

/**
 * ���������� �������ĸ���
 */
public abstract class JsBaseDrive {

	protected AnalysisResult result;
	protected Variables storage;
	protected CharPoint charPoint;

	public JsBaseDrive(Variables vals) {
		storage = vals;
	}

	public void clear() {
		storage.clear();
	}

	/**
	 * ����JS
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
		manager.fullDrive(this);
	}
	
	public void setCharPoint(CharPoint charPoint) {
		this.charPoint = charPoint;
		result.setBaseIndex(charPoint.getIndex());
	}

}
