package com.th.js;

import java.io.File;

import org.apache.commons.io.FileUtils;

public class JSFactory {
	
	private String context;
	

	public static void main(String[] args) throws Exception {
		JSFactory js = new JSFactory();
		js.load(FileUtils.readFileToString(new File("g:/cms/CmsWebApp/client/app/tag/tag.controller.js")));
		js.scanner();
	}

	public void load(String context) {
		this.context = context;
	}

	public void scanner() {
		//empty,field,number,char,other
		Variables var = new Variables();
		JSScanner reader = new JSScanner(context,"(\\s+|([a-zA-Z_]+([0-9]+)?)|[0-9]+|((\\\\)?('|\"|`))|\\S)");
		RootManager doc = new RootManager();
		JsDrive drive = new JsDrive(var);
		while (reader.pushRegex()) {
			drive.instance(reader.getCharPoint(),doc.getCurrentStatus());
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
