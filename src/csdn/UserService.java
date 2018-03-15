package csdn;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import pers.th.util.http.HttpClient;

/**
 * Created by Tianhao on 2018-3-15.
 * 用户拉取
 */
public class UserService {
    public static final HttpClient HTTP_CLIENT = new HttpClient("src/request.properties");

    private static final UserManager um = new UserManager();

    public static void main(String[] args) throws InterruptedException {
//        um.add("u013083576");
//        findUsers(IOUtils.reader("src/html/sanqima-35816883.html"));
//        bbs();
        for (int i = 38; i < 2950; i++) {
            Thread.sleep((int)(Math.random() * 7000));
            System.out.println("sum = [" + aHref("https://bbs.csdn.net/forums/JavaScript?page="+i) + "] index="+i);
            um.sync();
        }
        um.printf();
//        System.out.println("args = [" + HTTP_CLIENT.get("https://bbs.csdn.net/home") + "]");
    }

    private static int aHref(String url) {
        int sum = 0;
        for (Element element : Jsoup.parse(HTTP_CLIENT.get(url)).getElementsByTag("a")) {
            sum += um.analysis(element.attr("href"));
        }
        return sum;
    }

    private static void bbs() {
        for (Element element : Jsoup.parse(HTTP_CLIENT.get("https://bbs.csdn.net/home")).getElementById("week_ranks").getElementsByTag("a")) {
            um.analysis(element.attr("href"));
        }
    }

    private static void findUsers(String html) {
        for (Element element : Jsoup.parse(html).getElementsByClass("user_name")) {
            um.add(element.html());
        }
    }

}
