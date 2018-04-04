package csdn.entity;

import csdn.Template;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import pers.th.util.io.IOUtils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Blog implements Serializable {

	private transient static final long serialVersionUID = -3144902876193364243L;

	private transient static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private String context;
	private String title;
	private String time;
	private int count;
	private String author;
	private String url;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String source) {
		this.url = source;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void parse(String context, String author, String url) {
		Document doc = Jsoup.parse(context);
		Elements elems = doc.getElementsByClass("link_postdate");
		this.time = elems.isEmpty() ? doc.getElementsByClass("time").eq(0).html() : elems.eq(0).html();

		String txtCount = doc.getElementsByClass("txt").eq(0).html();
		txtCount = !txtCount.isEmpty() ? txtCount : doc.getElementsByClass("link_view").html().replaceAll("[^0-9]", "");
		if (txtCount.isEmpty()) {
			System.err.println("count not find:" + url);
		} else {
			this.count = Integer.parseInt(txtCount);
		}

		this.context = doc.getElementById("article_content").html();
		this.title = doc.title();
		this.author = author;
		this.url = url;
	}

	public void outputFile(Template template, String fileOutPath) {
		template.replace("${context}", context);
		template.replace("${title}", title);
		template.replace("${time}", time);
		template.replace("${count}", count + "");
		template.replace("${author}", author);
		template.replace("${source}", url);
		template.replace("${createDate}", format.format(createDate));
		IOUtils.write(fileOutPath, template.toString());
	}

	@Override
	public String toString() {
		return "Blog [context.length=" + (context == null ? 0 : context.length()) + ", title=" + title + ", time="
				+ time + ", count=" + count + ", author=" + author + ", url=" + url + "]";
	}

	public void serialize(String path) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
			oos.writeObject(this);
			oos.flush();
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Blog read(String path) {
		Blog tempBlog = null;
		try {
			ObjectInputStream oos = new ObjectInputStream(new FileInputStream(path));
			tempBlog = (Blog) oos.readObject();
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempBlog;
	}

}
