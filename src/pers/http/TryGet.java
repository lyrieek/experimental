package pers.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import pers.th.util.FileReader;

public class TryGet {
	public static final Properties prop = new Properties();

	public static final Template template = new Template(
			FileReader.reader("G://th/CmsApplicationTest/src/html/csdn.html"));

	static {
		try {
			prop.load(new FileInputStream("src/request.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
//		String html = getHTML("http://blog.csdn.net/sanqima/article/details/35816883");
//		FileReader.writer("src/html/sanqima-35816883.html", html);
		// Blog blog = Blog.read("src/html/qq_28379809-76196150.dat");
		// System.out.println(blog);
		String html = FileReader.reader("src/html/sanqima-35816883.html");
		Blog blog = new Blog();
		blog.parse(html, "moneyshi", "http://blog.csdn.net/moneyshi/article/details/79013906");
		blog.outputFile(template, "src/html/moneyshi-79013906-out.html");
		findUsers(html);
		UserManager.writeUser();
		// blog.serialize("src/html/qq_28379809-76196150.dat");

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

	public static void findUsers(String html) {
		Elements elems = Jsoup.parse(html).getElementsByClass("user_name");
		for (Element element : elems) {
			UserManager.users().add(element.html());
		}
	}

	public static void pullUserBlog(String userId) throws Exception {
		for (int i = 1; i <= 3; i++) {
			String result = getHTML("http://blog.csdn.net/" + userId + "/article/list/" + i + "?t=1");
			if (trySplit(userId, result, "class=\"blog-unit\"")
					|| trySplit(userId, result, "class=\"list_item article_item\"")) {
				continue;
			}
			return;
		}
	}

	private static boolean trySplit(String userId, String result, String search) throws Exception {
		if (!result.contains(search)) {
			return false;
		}
		TextSearch ts = new TextSearch(result, search);
		ts.eachAHref();
		for (String item : ts) {
			System.out.println(item);
			String url = "http://blog.csdn.net/" + item;
			Blog blog = new Blog();
			blog.parse(getHTML(url), userId, url);
			blog.outputFile(template,
					"G://th/CmsApplicationTest/src/html/csdn-" + item.replace("/article/details/", "-") + ".html");
		}
		return true;
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
