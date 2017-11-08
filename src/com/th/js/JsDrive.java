package com.th.js;

import java.util.LinkedList;

public class JsDrive {

	private AnalysisResult result;
	private LinkedList<Variables> storage;

	public JsDrive() {
		storage = new LinkedList<>();
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
		if (result.is(Status.READ)) {
			read(item);
		}else if (result.is(Status.STRING)) {
			string(item);
		}
		
		return result;
	}

	public void read(String item) {
		if (item.trim().isEmpty()) {
			result.change(Status.EMPTY);
		}
		if (item.matches("('|\"|`)")) {
			StatusBlack sBlack = result.status();
			sBlack.change(Status.STRING);
			sBlack.setLabel(item);
		}
	}

	private void string(String item) {
		if (item.equals(result.status().getLabel())) {
			result.change(Status.READ);
		}
	}

	public void instance(CharPoint textPoint,StatusBlack sBlack) {
		result = new AnalysisResult();
		result.setBaseIndex(textPoint.getIndex());
		result.change(sBlack.status());
	}

}
