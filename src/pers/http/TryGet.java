package pers.http;

import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Properties;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;

import pers.th.util.FileReader;

public class TryGet {
	public static final Properties prop = new Properties();
	public static String template;

	public static void main(String[] args) throws Exception {

		prop.load(new FileInputStream("src/request.properties"));
		template = FileReader.reader("G://th/CmsApplicationTest/src/html/csdn.html");

		String result = getHTML("http://blog.csdn.net/qq_30059235");
		String searchStr = "class=\"list_item article_item\"";
		TextSearch ts = new TextSearch(result, searchStr);
		ts.eachItem(new EachItem() {

			@Override
			public String next(int index) {
				int fromIndex = result.indexOf("<a href=\"", index + searchStr.length()) + 9;
				return result.substring(fromIndex, result.indexOf("\"", fromIndex));
			}
		});
		for (String item : ts) {
			 outputFile("http://blog.csdn.net" + item,"G://th/CmsApplicationTest/src/html/csdn-" + item.substring(1).replace("/article/details/", "-") + ".html");
		}
		// System.out.println(result);
	}

	public static void outputFile(String url,String filePath) throws Exception {
		String result = getHTML(url);
		int fromIndex = result.indexOf("class=\"markdown_views\">") + 23;
		result = result.substring(fromIndex, result.indexOf("<script", fromIndex));
		result = template.replace("${context}", result);
		FileReader.writer(filePath, result);
	}

	public static String getHTML(String path) throws Exception {
		HttpURLConnection huc = (HttpURLConnection) new URL(path).openConnection();
		for (Entry<Object, Object> item : prop.entrySet()) {
			huc.addRequestProperty(item.getKey().toString(), item.getValue().toString());
		}
		huc.setConnectTimeout(2000);
		huc.connect();
		// System.out.println(huc.getResponseCode());
		final String result = IOUtils.toString(huc.getInputStream(), Charset.forName("utf-8"));// .defaultCharset());;
		IOUtils.close(huc);
		return result;
	}

}
