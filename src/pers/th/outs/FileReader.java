package pers.th.outs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileReader {


	public static List<File> listFile(String path) {
		List<File> files = new ArrayList<>();
		File file = getFile(path);
		if (file == null || !file.isDirectory()) {
			return null;
		}
		for (File items : file.listFiles()) {
			if (items.isDirectory()) {
				files.addAll(listFile(items.getAbsolutePath()));
			} else {
				// remove else;add directory
				files.add(items);
			}
		}
		return files;
	}

	public static File getFile(String path) {
		if (path == null || path.trim().isEmpty()) {
			return null;
		}
		File file = new File(path);
		if (!file.exists()) {
			return null;
		}
		return file;
	}

	public static String reader(String path) {
		File file = getFile(path);
		if (file == null || !file.isFile()) {
			return "";
		}
		int length = 0;
		byte[] buffer = new byte[512];
		StringBuffer template = new StringBuffer();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(path);
			while ((length = fis.read(buffer)) != -1) {
				template.append(new String(buffer, 0, length));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (fis != null) {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return template.toString();
	}

}

interface FileHandle {

	boolean condition(File item);

	void analysis(File item);

}
