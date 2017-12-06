package pers.th.model;

import java.io.File;

public class FileTool {

	public File createFile(String fileName) {
		File html = new File(fileName);
		try {
			html.createNewFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return html;
	}

	public static void main(String[] args) throws Exception {
		System.out.println("class_".equals("class_"));
//		new FileTool().deleteDir(new File("F:\\winscp_temp_files"));
//		new File("F:\\winscp_temp_files").mkdirs();
//		Thread.sleep(700);
//		Runtime.getRuntime().exec("explorer F:\\winscp_temp_files");///explorer select, 
	}

	public void deleteDir(File dir) {
		if (!dir.delete()) {
			if (dir.isDirectory()) {
				for (File item : dir.listFiles()) {
					deleteDir(item);
				}
			}
			dir.delete();
		}
	}

}
