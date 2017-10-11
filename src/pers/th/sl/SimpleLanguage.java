package pers.th.sl;

import java.util.ArrayList;
import java.util.List;

public class SimpleLanguage {
	
	private List<GroupBlock> group;
	
	public SimpleLanguage() {
		group = new ArrayList<GroupBlock>();
	}
	
	public void split(String s) {
		String[] all = s.split(System.lineSeparator());
		StringBuffer data = new StringBuffer(s.length());
		for (int j = 0; j < all.length; j++) {
			char[] chars = all[j].trim().toCharArray();
			if (chars.length > 0 && chars[0] != '*') {
				data.append(chars);
			}
		}
		all = data.toString().split("::");
		for (String item : all) {
			if (!item.trim().isEmpty()) {
				group.add(new GroupBlock(item));
			}
		}
	}
	
	public void pull() {
		for (GroupBlock groupBlock : group) {
			groupBlock.pullTask();
		}
	}
	
	public void printGroup() {
		for (int i = 0; i < group.size(); i++) {
			System.out.println(i+":"+group.get(i).getId()+": "+group.get(i).getCode());
		}
	}

	public void exec() {
		for (GroupBlock groupBlock : group) {
			groupBlock.exec();
		}
	}

}
