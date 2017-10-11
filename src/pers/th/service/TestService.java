package pers.th.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

import java.io.OutputStream;
import java.math.BigDecimal;

public class TestService {

	Properties prop;
	HttpURLConnection conn;
	String requestMethod = "GET";

	public TestService() {
		prop = new Properties();
		try {
			prop.load(new FileInputStream(new File("request.property")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	public TestService connect(String url) {
		try {
			conn = (HttpURLConnection) new URL(url).openConnection();
			for (Object key : prop.keySet()) {
				conn.setRequestProperty(key.toString(), prop.getProperty(key.toString()));
			}
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestMethod(requestMethod);
			conn.connect();
			return this;
		} catch (Exception e) {
			System.out.println("connect error");
			e.printStackTrace();
			return null;
		}
	}

	public void send(String s) {
		OutputStream out = null;
		try {
			out = conn.getOutputStream();
			out.write(s.getBytes());
			out.flush();
		} catch (Exception e) {
			System.out.println("Reader error");
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public String get() {
		StringBuffer data = new StringBuffer();
		InputStream in = null;
		try {
			in = conn.getInputStream();
			int length = 0;
			byte[] buffer = new byte[8192 * 4];
			while ((length = in.read(buffer)) != -1) {
				data.append(new String(buffer, 0, length, "GBK"));
			}
		} catch (Exception e) {
			System.out.println("Reader error");
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return data.toString();
	}
	
	

	public void getHeader() {
		for (Object key : prop.keySet()) {
			System.out.println(key + ":" + prop.getProperty(key.toString()));
		}
	}

}
