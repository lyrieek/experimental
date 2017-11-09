package com.th.js;

public class JSHandle extends JsDrive{
	
	public final static String MARKS = ".={}:;,()";

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
			result.temporary(Status.EMPTY);
		}else if (item.length() == 1 && MARKS.contains(item)) {
			result.temporary(Status.MARK);
		}else if (item.matches("('|\"|`)")) {
			result.change(Status.STRING);
			storage.update("last.string.identifier", item);
		}else if (item.startsWith("//") || item.startsWith("/*")) {
			result.temporary(Status.REMARK);
		}else if (item.matches("((\\-)?\\d{1,}(\\.{1}\\d+)?)")) {
			result.temporary(Status.NUMBER);
		}
	}

	public void string(String item) {
		if (item.equals(storage.get("last.string.identifier"))) {
			result.lazyChange(Status.READ);
		}
	}

}
