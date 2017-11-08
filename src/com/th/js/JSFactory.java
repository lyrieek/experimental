package com.th.js;

import java.io.File;

import org.apache.commons.io.FileUtils;

import chars.u.PointReader;

public class JSFactory {
	
	private String context;
	

	public static void main(String[] args) throws Exception {
		JSFactory js = new JSFactory();
		js.load(FileUtils.readFileToString(new File("g:/cms/CmsWebApp/client/app/repCat/repCat.controller.js")));
		js.scanner();
	}

	public void load(String context) {
		this.context = context;
	}

	public void scanner() {
		//empty,field,number,char,other
		PointReader reader = new PointReader(context,"(\\s+|([a-zA-Z_]+([0-9]+)?)|[0-9]+|((\\\\)?('|\"|`))|\\S)");
		RootManager doc = new RootManager();
		JsDrive drive = new JsDrive();
		while (reader.pushRegex()) {
			drive.instance(reader.getTextPoint());
			doc.change(drive.translation(reader.item()));
		}
		doc.getDocument().printf();
	}
	
	public void fill() {
		
	}
		
	
	
	public String getContext() {
		return context;
	}


	
}
