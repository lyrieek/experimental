package pers.http;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import pers.th.util.FileReader;

public class Blog implements Serializable {

	private transient static final long serialVersionUID = -3144902876193364243L;

	private transient static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private String context;
	private String title;
	private String time;
	private int count;
	private String author;
	private String source;
	private final Date createDate;

	public Blog() {
		createDate = new Date();
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void parse(String context, String author, String source) {
		Document doc = Jsoup.parse(context);
		this.context = doc.getElementById("article_content").html();
		this.title = doc.title();
		this.time = doc.getElementsByClass("time").eq(0).html();
		this.count = Integer.parseInt(doc.getElementsByClass("txt").eq(0).html());
		this.author = author;
		this.source = source;
	}

	public void outputFile(Template template, String fileOutPath) {
		template.replace("${context}", context);
		template.replace("${title}", title);
		template.replace("${time}", time);
		template.replace("${count}", count + "");
		template.replace("${author}", author);
		template.replace("${source}", source);
		template.replace("${createDate}", format.format(createDate));
		FileReader.writer(fileOutPath, template.toString());
	}

	@Override
	public String toString() {
		return "Blog [context=" + context + ", title=" + title + ", time=" + time + ", count=" + count + ", author="
				+ author + ", source.length=" + (source == null ? 0 : source.length()) + "]";
	}

}
