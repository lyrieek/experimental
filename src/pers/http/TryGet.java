package pers.http;

import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Properties;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;

public class TryGet {
	public static final Properties prop = new Properties();

	public static void main(String[] args) throws Exception {
		prop.load(new FileInputStream("src/request.properties"));

		// String result =
		// getHTML("http://blog.csdn.net/qq_30059235/article/category/6906913");
		String result = getHTML("https://www.baidu.com/");
		System.out.println(result);
	}

	public static String getHTML(String path) throws Exception {
		HttpURLConnection huc = (HttpURLConnection) new URL(path).openConnection();
		for (Entry<Object, Object> item : prop.entrySet()) {
			huc.addRequestProperty(item.getKey().toString(), item.getValue().toString());
		}
		// System.out.println(huc.getRequestProperties());
		huc.setConnectTimeout(2000);
		huc.connect();
		System.out.println(huc.getResponseCode());
		final String result = IOUtils.toString(huc.getInputStream(), Charset.forName("gb2312"));// .defaultCharset());;
		IOUtils.close(huc);
		return result;
	}

}
