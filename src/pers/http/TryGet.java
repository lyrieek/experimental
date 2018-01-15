package pers.http;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import pers.http.entity.Blog;
import pers.th.util.FileReader;
import pers.th.util.http.HttpClient;

public class TryGet {
	public static final HttpClient HTTP_CLIENT = new HttpClient("src/request.properties");

	public static final Template template = new Template(
			FileReader.reader("G://th/CmsApplicationTest/src/html/csdn.html"));

	public static void main(String[] args) throws Exception {
		// String html =
		// getHTML("http://blog.csdn.net/sanqima/article/details/35816883");
		// FileReader.writer("src/html/sanqima-35816883.html", html);
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
			String result = HTTP_CLIENT.getHTML("http://blog.csdn.net/" + userId + "/article/list/" + i + "?t=1");
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
			blog.parse(HTTP_CLIENT.getHTML(url), userId, url);
			blog.outputFile(template,
					"G://th/CmsApplicationTest/src/html/csdn-" + item.replace("/article/details/", "-") + ".html");
		}
		return true;
	}

}
