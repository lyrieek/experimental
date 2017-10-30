package chars.u.test;

import java.util.LinkedList;
import java.util.List;

import chars.u.*;

public class BaseHandle {

	private List<ContextBlack> cbs;

	private ContextBlack current;

	private TextPoint textPoint;

	public List<ContextBlack> getContext() {
		return cbs;
	}

	public void init(TextPoint textPoint) {
		this.textPoint = textPoint;
		cbs = new LinkedList<>();
		cbs.add(current = new ContextBlack(textPoint.getIndex()));
	}

	public TextPoint getTextPoint() {
		return textPoint;
	}

	@Handle("read")
	public String read(String item) {
		if (item.trim().equals("")) {
			return "read";
		}
		int point = item.indexOf("/*");
		if (point != -1) {
			current.full("remark", item);
			return "remark";
		}
		return "read";
	}

	@Handle("chars")
	public String chars(String item) {
		if (item.endsWith("\"")) {
			return "read";
		}
		return "chars";
	}

	@Handle("remark")
	public String remark(String item) {
		if (item.endsWith("*/")) {
			return "remark|read";
		}
		return "remark";
	}

}
