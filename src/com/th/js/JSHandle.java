package com.th.js;

public class JSHandle extends JsDrive{

	public JSHandle(Variables vals) {
		super(vals);
	}
	
	@Override
	public AnalysisResult translation(String item) {
		super.translation(item);
		if (result.is(Status.READ)) {
			read(item);
		} else if (result.is(Status.STRING)) {
			string(item);
		}
		return result;
	}

	public void read(String item) {
		if (item.trim().isEmpty()) {
			result.change(Status.EMPTY);
		}
		if (item.matches("('|\"|`)")) {
			result.change(Status.STRING);
			storage.put("last.string.identifier", item);
		}
	}

	public void string(String item) {
		if (item.equals(storage.get("last.string.identifier"))) {
			result.change(Status.READ);
		}
	}

}
