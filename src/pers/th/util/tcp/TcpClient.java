package pers.th.util.tcp;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import pers.th.util.tcp.entity.DataWrap;
import pers.th.util.tcp.entity.DataWrapType;

public class TcpClient {

	// String charset = "utf-8";

	String charset = "gbk";

	ClientConnection sc = null;

	public TcpClient() {
		// getFile("F:\\Test");
		sendFile("F:\\readme.txt", "/home/user/th/12113.txt");
		// uploadFile("C:\\Users\\user\\Desktop\\ftp.jar","/home/user/th/ftp2.jar");
		// uploadFile("F:\\Test", "/home/user/th/test.jar");
		// sc.close();
	}

	public void sendFile(String data, String serverPath) {
		newConnection().send(new DataWrap(DataWrapType.FILE, data.getBytes(), serverPath));
	}

	public void uploadFile(String localPath, String serverPath) {
		sendFile(getContext(localPath), serverPath);
	}

	public void closeServer() {
		newConnection().send(new DataWrap(DataWrapType.DEFAULT, "close"));
	}

	public String getContext(String path) {
		StringBuffer context = new StringBuffer();
		try {
			FileInputStream fis = new FileInputStream(getFile(path));
			int length;
			byte[] buffer = new byte[1024];
			while ((length = fis.read(buffer)) != -1) {
				context.append(new String(buffer, 0, length, charset));
			}
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return context.toString();
	}

	public File getFile(String path) {
		File file = new File(path);
		if (file.isDirectory()) {
			try {
				ZipOutputStream out = new ZipOutputStream(new FileOutputStream("F:/temp/temp.zip"));
				zip(out, file);
				out.close();
				return new File("F:/temp/temp.zip");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}

	public void zip(ZipOutputStream out, File f) throws IOException {
		if (f.isDirectory()) {
			File[] files = f.listFiles();
			if (files == null || files.length == 0) {
				out.putNextEntry(new ZipEntry(f.getAbsolutePath() + "/"));
				return;
			}
			for (File file : files) {
				zip(out, file);
			}
		} else if (f.isFile() && f.exists()) {
			out.putNextEntry(new ZipEntry(f.getAbsolutePath()));
			FileInputStream fis = new FileInputStream(f);
			FileOutputStream fos = new FileOutputStream(f.getAbsolutePath());
			int length;
			byte[] buffer = new byte[1024];
			while ((length = fis.read(buffer)) != -1) {
				fos.write(buffer, 0, length);
			}
			fos.flush();
			fos.close();
			fis.close();
		}
	}

	public ClientConnection newConnection() {
		try {
			// sc = new ClientConnection("localhost", 1212);
			sc = new ClientConnection("192.168.2.47", 1212);
		} catch (Exception e) {
			System.out.println("can't connection");
			return null;
		}
		return sc;
	}

	public static void main(String[] args) {
		new TcpClient();
	}

}
