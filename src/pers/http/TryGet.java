package pers.http;

import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Properties;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;

import pers.th.util.FileReader;
import pers.th.util.text.XStrings;

public class TryGet {
	public static final Properties prop = new Properties();
	public static String template;

	public static void main(String[] args) throws Exception {
		prop.load(new FileInputStream("src/request.properties"));
		template = FileReader.reader("G://th/CmsApplicationTest/src/html/csdn.html");
		// String html =
		// getHTML("http://blog.csdn.net/qq_28379809/article/details/76196150");
		// System.out.println(html);
		// FileReader.writer("G:/th/CmsApplicationTest/src/html/qq_28379809-76196150.html",
		// html);
		String html = FileReader.reader("G:/th/CmsApplicationTest/src/html/qq_28379809-76196150.html");
		outputFile(html, "G:/th/CmsApplicationTest/src/html/qq_28379809-76196150-out.html");
		// System.out.println(trySplit(html, "class=\"list_item
		// article_item\""));

		// pullUserBlog("tragedyxd");

		// String html =
		// getHTML("http://so.csdn.net/so/search/s.do?q=spring%20mvc&t=blog");
		// Set<String> result = XStrings.getRegexList(html,
		// "http://blog.csdn.net/[a-z0-9A-Z_-]+/");
		// for (String item : result) {
		// System.out.println(item.substring(21,item.length() - 1));
		// }

		// pullUserBlog("u011054333");
		// pullUserBlog("qq_30059235");
	}

	public static void pullUserBlog(String userId) throws Exception {
		for (int i = 1; i <= 3; i++) {
			String result = getHTML("http://blog.csdn.net/" + userId + "/article/list/" + i + "?t=1");
			if (trySplit(result, "class=\"blog-unit\"") || trySplit(result, "class=\"list_item article_item\"")) {
				continue;
			}
			return;
		}
	}

	private static boolean trySplit(String result, String search) throws Exception {
		if (!result.contains(search)) {
			return false;
		}
		TextSearch ts = new TextSearch(result, search);
		ts.eachAHref();
		for (String item : ts) {
			System.out.println(item);
			String url = "http://blog.csdn.net/" + item;
			outputFile(getHTML(url),
					"G://th/CmsApplicationTest/src/html/csdn-" + item.replace("/article/details/", "-") + ".html");
		}
		return true;
	}

	public static void outputFile(String context, String filePath) throws Exception {
		// int fromIndex = result.indexOf("class=\"markdown_views\">") + 23;
		// result = result.substring(fromIndex, result.indexOf("<script",
		// fromIndex));
		System.out.println(Jsoup.parse(context).getElementsByClass("main").eq(0).html());
		context = XStrings.tear(context, "class=\"markdown_views\">", "<script", 0);
		context = template.replace("${context}", context);
		FileReader.writer(filePath, context);
	}

	public static String getHTML(String path) throws Exception {
		HttpURLConnection huc = (HttpURLConnection) new URL(path).openConnection();
		for (Entry<Object, Object> item : prop.entrySet()) {
			huc.addRequestProperty(item.getKey().toString(), item.getValue().toString());
		}
		huc.setConnectTimeout(2000);
		huc.connect();
		final String result = IOUtils.toString(huc.getInputStream(), Charset.forName("utf-8"));// .defaultCharset());;
		IOUtils.close(huc);
		return result;
	}

}
