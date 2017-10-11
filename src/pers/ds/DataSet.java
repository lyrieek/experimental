package pers.ds;

import java.util.ArrayList;

public class DataSet extends ArrayList<DataWarp>{

	private static final long serialVersionUID = 3613628544494369355L;

	public DataWarp get(String name) {
		for (DataWarp item : this) {
			if (item.getName().equals(name)) {
				return item;
			}
		}
		return null;
	}

	public void printf() {
		for (DataWarp item : this) {
			System.out.println(item);
		}
	}
	
}
