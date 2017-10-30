package chars.u;

public class StatusItem {

	private String code;
	private String context;
	
	public boolean is(String code) {
		return code.equals(this.code);
	}

	
	/**
	 * default read
	 */
	public StatusItem() {
		code = "read";
	}
	
	public StatusItem(String context) {
		this();
		this.context = context;
	}

	public StatusItem(String code, String context) {
		this(context);
		this.code = code;
	}

	public String code() {
		return code;
	}

	public void changeContext(String context) {
		this.context = context;
	}

	public String getContext() {
		return context;
	}

	@Override
	public String toString() {
		return code + ":" + context.replace("\n","\\n");
	}

}