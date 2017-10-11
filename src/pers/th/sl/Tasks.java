package pers.th.sl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class Tasks {
	
	public static void main(String[] args) {
		Tasks tasks = new Tasks();
		tasks.addTask("ll()");
		tasks.exec();
	}

	List<Task> arr;

	public Tasks() {
		arr = new ArrayList<Task>();
	}

	public void addTask(String taskContext) {
		Task task = new Task(taskContext);
		task.analysis();
		arr.add(task);
	}

	public void exec() {
		for (Task item : arr) {
			item.exec();
		}
	}

}

class Task {

	public final String useFnRegex = "([a-zA-Z\\.][0-9]*)*\\((\\s|\\S)*\\)";
	public final String fieldRegex = "\\$([a-zA-Z][0-9]*)*(=.+)?";

	public Command cmd;

	private String taskContext;

	public Task(String taskContext) {
		this.taskContext = taskContext;
		cmd = new Command();
	}

	public void exec() {
		cmd.exection();
	}

	/**
	 * $a=1 abc($a)
	 * 
	 * @return
	 */
	public boolean analysis() {
		if (Pattern.matches(useFnRegex, taskContext)) {
			int paramStartPoint = taskContext.indexOf("(") + 1;
			int paramEndPoint = taskContext.lastIndexOf(")");
			if (paramStartPoint == paramEndPoint) {
				cmd.cancelParam();
			} else {
				if (!reverseParams(taskContext.substring(paramStartPoint, paramEndPoint))) {
					cmd.cancelParam();
				}
			}
			cmd.analysis(taskContext.substring(0, paramStartPoint - 1));
			return true;
		}
		return false;
	}
	
	public boolean reverseParams(String param) {
		if (param.trim().isEmpty()) {
			return false;
		}
		IParam iParam = new IParam(param);
		iParam.autoAnalysis();
		cmd.addParams(iParam);
		return true;
	}

}