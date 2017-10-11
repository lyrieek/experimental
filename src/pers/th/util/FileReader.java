package pers.th.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

public class FileReader {

	public static void main2(String[] args) {
		final String base = "@#&$%*o+!;.";// �ַ����ɸ��ӵ���
		try {
			final BufferedImage image = ImageIO.read(new File("C:\\Users\\user\\Desktop/0.jpg"));
			for (int y = 0; y < image.getHeight(); y += 2) {
				for (int x = 0; x < image.getWidth(); x++) {
					final int pixel = image.getRGB(x, y);
					final int r = (pixel & 0xff0000) >> 16, g = (pixel & 0xff00) >> 8, b = pixel & 0xff;
					final float gray = 0.299f * r + 0.578f * g + 0.114f * b;
					final int index = Math.round(gray * (base.length() + 1) / 255);
					System.out.print(index >= base.length() ? " " : String.valueOf(base.charAt(index)));
				}
				System.out.println();
			}
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		String s = reader("G:/th/CmsApplicationTest/src/pers/th/i18n");
		for (String item : s.split(System.lineSeparator())) {
			String[] field = item.replace("$scope.", "").replace("translations.", "").replace(";", "").split("=");
			if (field.length != 2) {
				continue;
			}
			if (!field[0].trim().equals(field[1].trim())) {
				System.out.println(item);
			}
		}
//		Map<String, FileHandle> handlers = new HashMap<String, FileHandle>();
//		handlers.put("delete", new FileHandle() {
//
//			@Override
//			public boolean condition(File item) {
//				return item.exists();
//			}
//
//			@Override
//			public void analysis(File item) {
//				item.delete();
//			}
//		});
//
//		eachFile(listFile("/home/user/Documents/sts/CmsApplicationTest/src/com/fasterxml/jackson/core"),
//				new FileHandle() {
//
//					@Override
//					public boolean condition(File item) {
//						return item.getName().contains("son");
//					}
//
//					@Override
//					public void analysis(File item) {
//						System.out.println(item);
//					}
//				});
//		System.out.println(reader(""));

	}

	public static void eachFile(List<File> listFile, FileHandle handle) {
		for (File file : listFile) {
			if (handle.condition(file)) {
				handle.analysis(file);
			}
		}
	}

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
