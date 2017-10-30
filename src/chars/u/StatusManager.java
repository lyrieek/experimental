package chars.u;

import java.util.LinkedList;

public class StatusManager {

	private String code;
	private String tempContext;
	private LinkedList<StatusItem> histroy;

	public StatusManager() {
		code = "read";
		histroy = new LinkedList<>();
	}

	public void change(String code) {
		this.code = code;
	}
	
	public String tempSave(String tempContext) {
		return this.tempContext = tempContext;
	}

	public void save() {
		histroy.addLast(new StatusItem(code, tempContext));
	}

	public void save(String param) {
		histroy.addLast(new StatusItem(code, param));
	}

	public void printfHistroy() {
		for (StatusItem item : histroy) {
			System.out.println(item);
		}
	}
	
	public LinkedList<StatusItem> histroy() {
		return histroy;
	}

	@Override
	public String toString() {
		return code;
	}

	public String code() {
		return code;
	}

}


