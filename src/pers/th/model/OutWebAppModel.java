package pers.th.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class OutWebAppModel {

	private static Configs config;

	private static FileTool ft;

	public static void main(String[] args) {
		args = new String[]{"messagePage"};
		if (args.length!=1) {
			System.out.println("input a param");
			return;
		}
		config = new Configs();
		ft = new FileTool();
		config.loadProperties();
		config.addTemplate("demo_name", args[0]);
		config.readTemplates();
		printfFile();
		System.out.println(args[0]+" output success!");
		// System.out.println(template);
	}

	private static void printfFile() {
		String demoName = config.get("demo_name");
		String outPath = config.get("out_path");
		File out = new File(outPath);
		if (!out.exists()) {
			System.out.println("out_path not find");
			return;
		}
		out = new File(outPath + "/" + demoName);
		if (out.exists() && out.isDirectory()) {
			ft.deleteDir(out);
		}
		out.mkdir();
		String rootPath = out.getPath() + "/";
		if (Boolean.parseBoolean(config.get("out_files_append"))) {
			rootPath += demoName;
		}
		Map<String,File> fileMap = new HashMap<>();
		String[] files = config.get("out_files").split(",");
		for (String item : files) {
			fileMap.put(item, ft.createFile(rootPath + item));
		}
		
		for (String key : config.getTemplates().keySet()) {
			String tName = key;
			if (tName.endsWith(".template")) {
				tName = tName.substring(0,tName.indexOf(".template"));
			}
			for (String item : files) {
				String oName = item;
				if (oName.startsWith(".")) {
					oName = oName.substring(1);
				}
				if (oName.equals(tName)) {
					try {
						FileWriter fw = new FileWriter(fileMap.get(item));
						fw.write(config.getTemplate(key));
						fw.flush();
						fw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

}
