package com.th.js;

import chars.u.PointReader;

public class JSFactory {
	
	private String context;

	public void load(String context) {
		this.context = context;
	}

	public void scanner() {
		//empty,field,number,other
		PointReader reader = new PointReader(context,"(\\s+|([a-zA-Z]+([0-9]+)?)|[0-9]+|\\S)");
		JsDrive drive = new JsDrive();
		while (reader.pushRegex()) {
			String item = drive.translation(reader.item());
			System.out.println(item);
		}
	}
	
	public void fill() {
		
	}
		
	
	
	public String getContext() {
		return context;
	}


	
}
