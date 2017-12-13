package pers.th.outs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.IOUtils;

import pers.th.chars.Words;
import pers.th.chars.WordsFactory;

public class OutFile {
	
	private String a;
	public OutFile(String p) {
		a = p;
	}
	
	public void setA(String a) {
		this.a = a;
	}
	
	@Override
	public String toString() {
		return a;
	}

	public static void main(String[] args) throws Exception {
//		for (int i = 0; i < 100; i++) {
//			System.out.print(Integer.valueOf((int) (Math.random()*(10))));
//		}
		
		HashMap<String, OutFile> maps = new HashMap<>();
		maps.put("aa", new OutFile("we"));
		maps.put("5y", new OutFile("sd"));
		maps.put("e4", new OutFile("df"));
		maps.put("fer", new OutFile("fe"));
		for (String item : maps.keySet()) {
			System.out.println(item+":"+maps.get(item));
		}
		maps.get("5y").setA("1212");
		System.out.println("-----------");
		for (String item : maps.keySet()) {
			System.out.println(item+":"+maps.get(item));
		}
		//		outputFile();
//		getset();
	}

	public static void getset() throws Exception {
		List<String> ls = IOUtils.readLines(new FileInputStream(new File("template2\\list")),"utf-8");
		for (String item : ls) {
			Words field = new Words(item).camel();
//			System.out.println(field+":{from:\""+field+"\"},"); 
			System.out.println("{field:\""+field+"\",title:$scope. },");
		}
		System.out.println(); 
		for (String item : ls) {
			Words field = new Words(item).camel();
			System.out.println("private String "+field+";"); 
		}
		System.out.println(); 
		for (String item : ls) {
			Words field = new Words(item).camel();
			System.out.println(); 
			System.out.println("public String get"+field.pascal()+"(){"); 
			System.out.println("\treturn "+field+";");
			System.out.println("}"); 
			System.out.println(); 
			System.out.println("public void set"+field.pascal()+"(String "+field+"){"); 
			System.out.println("\tthis."+field+" = "+field+";");
			System.out.println("}"); 
		}
	}

	public static void outputFile() throws Exception {

		Properties prop = new Properties();
		prop.load(new FileInputStream(new File("template2\\set.properties")));
		// System.out.println(prop);
//		String text = "";
		
		String name = "Controller";
//		text = FileReader.reader("template2/Service.java");
//		text = FileReader.reader("template2/Controller.java");
//		text = FileReader.reader("template2/UI.java");
//		text = FileReader.reader("template2/Repository.java");
		String text = FileReader.reader("template2/"+name+".java");
		Words w = new Words(text);
		for (Words item : w.regex("\\$\\{[\\^A-Za-z_]+\\}")) {
			Words key = item.sub(2, item.size() - 1);
			Object value = prop.get(key.toString());
			if (value != null) {
				w.replace(item.toString(), value.toString());
			} else if (key.begin("^")) {
				value = prop.get(key.sub(1).toString());
				key = key.sub(1);
				w.replace(item.toString(), WordsFactory.builder(value).pascal().toString());
			}
		}
		IOUtils.write(w.toString(), new FileOutputStream(new File("G:\\MessagePage"+name+".java")),"utf-8");
		System.out.println(w);
	}

}
