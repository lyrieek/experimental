package pers;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class HTMLParser {
	public static void main(String[] args) throws IOException {
//		Document doc = Jsoup.connect("http://en.wikipedia.org/").get();
//		Elements newsHeadlines = doc.select("#mp-itn b a");
		Document doc = Jsoup.connect("https://accounts.zoho.com/login?servicename=ZohoProjects&amp;hide_signup=true&amp;hide_title=true&amp;hide_gsignup=false&amp;serviceurl=https%3A%2F%2Fprojects.zoho.com%2Fportal%2Fversionsystem").get();
		System.out.println(doc.html());
		Elements newsHeadlines = doc.select("#bugInnerDiv");
		System.out.println(newsHeadlines.html());
	}
}
