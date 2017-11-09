package com.th.js.core;

/**
 * 驱动处理器 处理器的父类
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
	 * 解释JS
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

	/**
	 * 实例化返回值
	 * @param manager
	 */
	public void instance(RootManager manager) {
		result = new AnalysisResult();
		manager.fullDrive(this);
	}
	
	public void setCharPoint(CharPoint charPoint) {
		this.charPoint = charPoint;
		result.setPoint(charPoint);
	}
	
	public CharPoint getCharPoint() {
		return charPoint;
	}

}
