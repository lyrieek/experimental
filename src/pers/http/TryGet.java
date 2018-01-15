package pers.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
	public static final List<String> userList = new ArrayList<>();

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
		// String html =
		// getHTML("http://blog.csdn.net/qq_28379809/article/details/76196150");
		// System.out.println(html);
		// FileReader.writer("G:/th/CmsApplicationTest/src/html/qq_28379809-76196150.html",
		// html);
		String html = FileReader.reader("src/html/qq_28379809-76196150.html");
		Blog blog = new Blog();
		blog.parse(html, "qq_28379809", "http://blog.csdn.net/qq_28379809/article/details/76196150");
		blog.outputFile(template, "src/html/qq_28379809-76196150-out2.html");
		findUsers(html);
		writeUser();
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
			userList.add(element.html());
		}
	}

	public static void writeUser() {
		FileWriter writer = null;
		try {
			writer = new FileWriter(new File("src/html/userlist"));
			for (String userItem : userList) {
				writer.write(userItem+System.lineSeparator());
				writer.flush();
			}
			userList.clear();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
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
