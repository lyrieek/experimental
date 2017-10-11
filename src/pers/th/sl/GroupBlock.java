package pers.th.sl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GroupBlock {

	private String id;

	private String sourceCode;

	private List<String> code;

	private Tasks tasks;

	private Define type;

	public GroupBlock(String code) {
		this.id = "#" + (new Random().nextInt(9000) + 1000);
		this.sourceCode = code;
		this.code = new ArrayList<String>();
		this.tasks = new Tasks();
		analysisCode();
	}

	public void analysisCode() {
		String[] items = sourceCode.split(";");
		if (items.length > 0) {
			type = new Define(items[0]);
			int i = type.isDefault() ? 0 : 1;// 非默认跳过第一行
			if (type.isFunction() && items.length == 1) {
				System.err.println("空函数:" + type.getName());
			}
			for (; i < items.length; i++) {
				if (!items[i].trim().isEmpty()) {
					code.add(items[i]);
				}
			}
		}
	}

	public void pullTask() {
		for (String item : code) {
			tasks.addTask(item);
		}
	}

	public void exec() {
		tasks.exec();
	}

	public String getCode() {
		StringBuffer codes = new StringBuffer();
		for (String item : code) {
			codes.append(item + "→");
		}
		if (codes.length() > 0) {
			codes.deleteCharAt(codes.length() - 1);
		}
		return codes.toString();
	}

	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String code) {
		this.sourceCode = code;
	}

	public Define getType() {
		return type;
	}

	public void setType(Define type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return sourceCode;
	}

	public String getId() {
		return id;
	}

}
