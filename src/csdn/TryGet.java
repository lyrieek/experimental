package csdn;

import csdn.entity.Blog;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import pers.th.util.http.HttpClient;
import pers.th.util.io.IOUtils;

public class TryGet {
	public static final HttpClient HTTP_CLIENT = new HttpClient("src/request.properties");

	public static final Template template = new Template(
			IOUtils.reader("G://th/CmsApplicationTest/src/html/csdn.html"));

	public static void main(String[] args) throws Exception {
//		String html = HTTP_CLIENT.get("http://blog.csdn.net/sanqima/article/details/35816883");
//		FileReader.writer("src/html/sanqima-35816883.html", html);
//		Blog blog = Blog.read("src/html/sanqima-35816883.dat");
		// System.out.println(blog);
		String html = IOUtils.reader("src/html/sanqima-35816883.html");
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

	private static void findUsers(String html) {
		for (Element element : Jsoup.parse(html).getElementsByClass("user_name")) {
			UserManager.users().add(element.html());
		}
	}

	public static void pullUserBlog(String userId) throws Exception {
		for (int i = 1; i <= 3; i++) {
			String result = HTTP_CLIENT.get("http://blog.csdn.net/" + userId + "/article/list/" + i + "?t=1");
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
			blog.parse(HTTP_CLIENT.get(url), userId, url);
			blog.outputFile(template,
					"G://th/CmsApplicationTest/src/html/csdn-" + item.replace("/article/details/", "-") + ".html");
		}
		return true;
	}

}
