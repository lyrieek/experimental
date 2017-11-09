package com.th.js;

import java.io.File;
import org.apache.commons.io.FileUtils;

import com.th.js.core.JSFactory;

public class JSReader {
	

	public static void main(String[] args) throws Exception {
		JSFactory js = new JSFactory();
		js.load(FileUtils.readFileToString(new File("g:/cms/CmsWebApp/client/app/repCat/repCat.controller.js")));
		js.scanner();
	}
	
	
}
