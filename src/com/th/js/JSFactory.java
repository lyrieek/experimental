package com.th.js;

import java.io.File;

import org.apache.commons.io.FileUtils;

public class JSFactory {

	private String context;

	private final static String regex = "(\\s+|([a-zA-Z_]+([0-9]+)?)|"
			+ "((\\-)?\\d{1,}(\\.{1}\\d+)?)|((\\\\)?('|\"|`))|(\\/\\/[^\n]*)|"
			+ "(\\/\\*(\\s|.)*?\\*\\/)|\\S)";

	public static void main(String[] args) throws Exception {
		JSFactory js = new JSFactory();
		js.load(FileUtils.readFileToString(new File("g:/cms/CmsWebApp/client/app/tag/tag.controller.js")));
		// js.load(FileUtils.readFileToString(new
		// File("g:/cms/CmsWebApp/client/app/transaction/transaction.controller.js")));
		js.scanner();
	}

	public void load(String context) {
		this.context = context;
	}

	public void scanner() {
		// empty,field,number,char,other
		Variables var = new Variables();
		JSScanner reader = new JSScanner(context, regex);
		RootManager doc = new RootManager();
		JSHandle handle = new JSHandle(var);
		while (reader.pushRegex()) {
			handle.instance(reader.getCharPoint(), doc.getCurrentStatus());
			doc.receive(handle.translation(reader.item()));
		}
		doc.getDocument().printf();
	}

	public void fill() {

	}

	public String getContext() {
		return context;
	}

}
