package pers.th.t;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;

import org.apache.commons.io.IOUtils;

public class QuickQuery {
	
	public static void main(String[] args) throws Exception {
		IOUtils.readLines(new FileInputStream(new File("Test.java")));
		new FileWriter("").write("//$c;");
	}
	
}
