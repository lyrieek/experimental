package chars.u;

/**
 * 
 * @author user
 *
 */
public class ContextBlack {

	private int startIndex;

	private int endInedx;

	private int level;

	private String item;

	private StatusItem status;

	private ContextBlack base;

	private boolean isEnd;

	private String remark;

	public ContextBlack(int index) {
		startIndex = index;
	}

	public void full(String text, String... status) {
		if (status == null) {
			status = new String[] { "read" };
		}
		setStatus(new StatusItem(status[0], text));
		setItem(text);
		setIsEnd(true);
	}

	public void setIsEnd(boolean isEnd) {
		this.isEnd = isEnd;
	}

	public boolean isEnd() {
		return isEnd;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public void setBase(ContextBlack base) {
		this.base = base;
	}

	public ContextBlack getBase() {
		return base;
	}

	public int getEndInedx() {
		return endInedx;
	}

	public void setEndInedx(int endInedx) {
		this.endInedx = endInedx;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public StatusItem getStatus() {
		return status;
	}

	public void setStatus(StatusItem status) {
		this.status = status;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return startIndex + ":" + item;
	}

}
